package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.dto.ProductPriceDto;
import pl.wiktrans.ims.model.*;
import pl.wiktrans.ims.repository.ProductPriceRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void deleteProductPrices(List<ProductPrice> productPrices) {
        for (ProductPrice productPrice : productPrices) {
            productPrice.setDeleted(true);
        }

        productPriceRepository.saveAll(productPrices);
    }

    public void updateProductPrices(Order order) {
        Set<Product> orderedProducts = order.getMerchOrders()
                .stream()
                .map(MerchOrder::getOrderElements)
                .flatMap(List::stream)
                .map(OrderElement::getProduct)
                .collect(Collectors.toSet());

        List<ProductPrice> productPrices = getAllByOrder(order);
        List<ProductPrice> productPricesToDelete = productPrices.stream()
                .filter(productPrice -> !orderedProducts.contains(productPrice.getProduct()))
                .collect(Collectors.toList());

        productPriceRepository.deleteAll(productPricesToDelete);
    }
}
