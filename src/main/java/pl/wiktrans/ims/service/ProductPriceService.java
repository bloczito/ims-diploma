package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.dto.ProductPriceDto;
import pl.wiktrans.ims.model.Order;
import pl.wiktrans.ims.model.ProductPrice;
import pl.wiktrans.ims.repository.ProductPriceRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductPriceService {

    @Autowired
    private ProductPriceRepository productPriceRepository;


    public List<ProductPrice> getAllByOrder(Order order) {
        return productPriceRepository.findAllByOrder(order);
    }

    public void save(ProductPrice productPrice) {
        productPriceRepository.save(productPrice);
    }

    public void saveAll(Iterable<ProductPrice> productPrices) {
        productPriceRepository.saveAll(productPrices);
    }

    public ProductPrice getById(Long id) {
        return productPriceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No product price with given id = " + id + " was found"));
    }
}
