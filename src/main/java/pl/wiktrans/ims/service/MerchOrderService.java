package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.dto.MerchOrderDto;
import pl.wiktrans.ims.model.MerchOrder;
import pl.wiktrans.ims.model.Order;
import pl.wiktrans.ims.model.OrderElement;
import pl.wiktrans.ims.repository.MerchOrderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MerchOrderService {

    @Autowired
    private MerchOrderRepository merchOrderRepository;

    @Autowired
    private OrderElementService orderElementService;

    @Autowired
    private ProductPriceService productPriceService;

    @Autowired
    private ProductsService productsService;


    public List<MerchOrder> getAll() {
        return merchOrderRepository.findAllByDeleted(false);
    }

    public MerchOrder getById(Long id) {
        return merchOrderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No merch order found with given id"));
    }

    public List<MerchOrder> getAllByOrder(Order order) {
        return merchOrderRepository.findAllByOrder(order);
    }

    public void save(MerchOrder merchOrder) { merchOrderRepository.save(merchOrder); }

    public void deleteMerchOrders(List<MerchOrder> orders) {
        for (MerchOrder ord : orders) {
            ord.setDeleted(true);
            orderElementService.deleteOrderElements(ord.getOrderElements());
        }

        merchOrderRepository.saveAll(orders);
    }

    public void deleteMerchOrderPermanently(Long id) {
        MerchOrder merchOrder = getById(id);
        Order order = merchOrder.getOrder();
        orderElementService.deleteOrderElementsPermanently(merchOrder.getOrderElements());
        merchOrderRepository.delete(merchOrder);
        productPriceService.updateAndDeleteProductPrices(order);
    }

    public void updateMerchOrders(Order order, List<MerchOrderDto> merchOrderDtos) {
        merchOrderDtos.forEach(dto -> {
            if (dto.getId() == null) {
                MerchOrder merchOrder = new MerchOrder();
                merchOrder.setOrder(order);
                merchOrder.setComment(dto.getComment());
                save(merchOrder);

                List<OrderElement> newElements = orderElementService.createOrderElements(merchOrder, dto.getOrderElements());

                orderElementService.saveAll(newElements);
                merchOrder.setOrderElements(newElements);
//                save(merchOrder);

            } else {
                MerchOrder oldMerchOrder = getById(dto.getId());
                oldMerchOrder.setComment(dto.getComment());
                save(oldMerchOrder);
            }
        });
    }
}
