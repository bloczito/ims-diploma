package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.model.MerchOrder;
import pl.wiktrans.ims.model.Order;
import pl.wiktrans.ims.repository.MerchOrderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MerchOrderService {

    @Autowired
    private MerchOrderRepository merchOrderRepository;


    public List<MerchOrder> getAll() {
        return merchOrderRepository.findAll();
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
}
