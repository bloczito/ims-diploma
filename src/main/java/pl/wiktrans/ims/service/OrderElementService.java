package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.dto.OrderElementDto;
import pl.wiktrans.ims.model.MerchOrder;
import pl.wiktrans.ims.model.OrderElement;
import pl.wiktrans.ims.repository.OrderElementRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class OrderElementService {

    @Autowired
    private OrderElementRepository orderElementRepository;

    @Autowired
    private ProductsService productsService;

    public List<OrderElement> getAll() {
        return orderElementRepository.findAll();
    }

    public OrderElement getById(Long id) {
        return orderElementRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No merch order found with given id"));
    }

    public void save(OrderElement orderElement) {
        orderElementRepository.save(orderElement);
    }

    public void saveAll(Iterable<OrderElement> orderElements) {
        orderElementRepository.saveAll(orderElements);
    }

    public void deleteOrderElementsPermanently(List<OrderElement> orderElements) {
        orderElementRepository.deleteAll(orderElements);
    }

    public void deleteOrderElements(List<OrderElement> orderElements) {
        for (OrderElement orderElement : orderElements) {
            orderElement.setDeleted(true);
        }
        orderElementRepository.saveAll(orderElements);
    }

    public List<OrderElement> createOrderElements(MerchOrder merchOrder, List<OrderElementDto> orderElementDtos) {
        List<OrderElement> newElements = orderElementDtos
                .stream()
                .map(elDto -> {
                    OrderElement orderElement = new OrderElement();
                    orderElement.setMerchOrder(merchOrder);
                    orderElement.setQuantity(elDto.getQuantity());
                    orderElement.setProduct(productsService.getById(elDto.getProduct().getId()));
                    return orderElement;
                })
                .collect(Collectors.toList());

        saveAll(newElements);

        return newElements;
    }

}
