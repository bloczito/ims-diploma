package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.dto.CustomerDto;
import pl.wiktrans.ims.dto.OrderDto;
import pl.wiktrans.ims.model.Company;
import pl.wiktrans.ims.model.Customer;
import pl.wiktrans.ims.model.Order;
import pl.wiktrans.ims.repository.OrderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MerchOrderService merchOrderService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private ProductPriceService productPriceService;


    public List<Order> getAll() {
        return orderRepository.findAllByDeleted(false);
    }

    public Page<Order> getPage(Pageable page) {
        return orderRepository.findAllActivePage(false, page);
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

        merchOrderService.updateMerchOrders(order, orderDto.getMerchOrders());
        productPriceService.updateAndAddProductPrices(order, orderDto.getProductPrices());
        shipmentService.updateShipments(order, orderDto.getShipments());
    }

    public void deleteOrder(Long id) {
        Order order = getById(id);
        order.setDeleted(true);
        orderRepository.save(order);
        merchOrderService.deleteMerchOrders(order.getMerchOrders());
        shipmentService.deleteShipments(order.getShipments());
        productPriceService.deleteProductPrices(order.getProductPrices());
    }


}
