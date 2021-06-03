package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.model.OrderElement;
import pl.wiktrans.ims.repository.OrderElementRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderElementService {

    @Autowired
    private OrderElementRepository orderElementRepository;

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

}
