package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.dto.CustomerDto;
import pl.wiktrans.ims.dto.OrderDto;
import pl.wiktrans.ims.model.*;
import pl.wiktrans.ims.repository.OrderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private OrderElementService orderElementService;

    @Autowired
    private MerchOrderService merchOrderService;

    @Autowired
    private CustomerObjectService customerObjectService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private ShipmentElementService shipmentElementService;

    @Autowired
    private ProductPriceService productPriceService;


    public List<Order> getAll() {
        return (List<Order>) orderRepository.findAll();
    }

    public Page<Order> getPage(PageRequest page) {
        return orderRepository.findAll(page);
    }

    public Order getById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not fount with given id = " + id));
    }

    public long getTotalAmount() {
        return orderRepository.count();
    }

    public void addNewOrder(OrderDto orderDto) {
        Order order = toOrder(orderDto);

        orderRepository.save(order);
    }

    public Order toOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderNumber(orderDto.getOrderNumber());
        order.setOrderDate(orderDto.getOrderDate());
        order.setDeadline(orderDto.getDeadline());

        CustomerDto orderDtoCustomer = orderDto.getCustomer();
        if (orderDtoCustomer != null && orderDtoCustomer.getId() != null) {
            Customer customer = customerService.getById(orderDtoCustomer.getId());
            order.setCustomer(customer);
        }

        OrderDto.OrderDtoCompany dtoCompany = orderDto.getCompany();
        if (dtoCompany != null && dtoCompany.getId() != null) {
            Company company = companyService.getById(dtoCompany.getId());
            order.setCompany(company);
        }

        return order;
    }

    public void updateOrder(OrderDto orderDto) {
        Order order = getById(orderDto.getId());

        order.setOrderNumber(orderDto.getOrderNumber());
        order.setOrderDate(orderDto.getOrderDate());
        order.setDeadline(orderDto.getDeadline());
        order.setStatus(orderDto.getStatus());
        order.setPriority(orderDto.getPriority());

        orderDto.getMerchOrders().forEach(dto -> {
            if (dto.getId() == null) {
                MerchOrder merchOrder = new MerchOrder();
                merchOrder.setOrder(order);
                merchOrder.setComment(dto.getComment());
                merchOrderService.save(merchOrder);

                List<OrderElement> newElements = dto.getOrderElements()
                        .stream()
                        .map(elDto -> {
                            OrderElement orderElement = new OrderElement();
                            orderElement.setMerchOrder(merchOrder);
                            orderElement.setQuantity(elDto.getQuantity());
                            orderElement.setProduct(productsService.getOne(elDto.getProduct().getId()));
                            return orderElement;
                        })
                        .collect(Collectors.toList());

                orderElementService.saveAll(newElements);

                merchOrder.setOrderElements(newElements);
            } else {
                MerchOrder oldMerchOrder = merchOrderService.getById(dto.getId());
                oldMerchOrder.setComment(dto.getComment());
                merchOrderService.save(oldMerchOrder);
            }
        });


        Set<Product> productsWithPrices = order.getProductPrices()
                .stream()
                .map(ProductPrice::getProduct)
                .collect(Collectors.toSet());

        merchOrderService.getAllByOrder(order)
                .stream()
                .map(MerchOrder::getOrderElements)
                .flatMap(List::stream)
                .map(OrderElement::getProduct)
                .collect(Collectors.toSet())
                .forEach(product -> {
                    if (!productsWithPrices.contains(product)) {
                        ProductPrice productPrice = new ProductPrice();
                        productPrice.setProduct(product);
                        productPrice.setOrder(order);
                        productPrice.setPrice(product.getBasePrice());
                        productPriceService.save(productPrice);
                    }
                });



        orderDto.getShipments().forEach(dto -> {
            if (dto.getId() == null) {
                Shipment shipment = new Shipment();
                shipment.setOrder(order);
                shipment.setComment(dto.getComment());
                shipment.setShipmentDate(dto.getShipmentDate());
                shipment.setShipmentObject(customerObjectService.getById(dto.getShipmentObject().getId()));
                shipmentService.save(shipment);

                List<ShipmentElement> shipmentElements = dto.getShipmentElements()
                        .stream()
                        .map(elDto -> {
                            ShipmentElement shipmentElement = new ShipmentElement();
                            shipmentElement.setShipment(shipment);
                            shipmentElement.setQuantity(elDto.getQuantity());
                            shipmentElement.setProduct(productsService.getOne(elDto.getProduct().getId()));
                            return shipmentElement;
                        })
                        .collect(Collectors.toList());

                shipmentElementService.saveAll(shipmentElements);
            } else {
                Shipment oldShipment = shipmentService.getById(dto.getId());
                oldShipment.setComment(dto.getComment());
                shipmentService.save(oldShipment);
            }
        });

        orderDto.getProductPrices().forEach(dto -> {
            if (dto.getId() != null) {
                ProductPrice oldProductPrice = productPriceService.getById(dto.getId());

                if (!dto.getPrice().equals(oldProductPrice.getPrice())) {
                    oldProductPrice.setPrice(dto.getPrice());
                    productPriceService.save(oldProductPrice);
                }
            }
        });

    }


}
