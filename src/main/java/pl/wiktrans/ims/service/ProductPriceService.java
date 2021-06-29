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

    @Autowired
    private MerchOrderService merchOrderService;


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

    public void updateAndDeleteProductPrices(Order order) {
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

    public void updateAndAddProductPrices(Order order, List<ProductPriceDto> productPriceDtos) {
        Set<Product> productsWithPrices = order.getProductPrices()
                .stream()
                .map(ProductPrice::getProduct)
                .collect(Collectors.toSet());

        List<MerchOrder> allByOrder = merchOrderService.getAllByOrder(order);

        allByOrder
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
                        save(productPrice);
                    }
                });

        productPriceDtos.forEach(dto -> {
            if (dto.getId() != null) {
                ProductPrice oldProductPrice = getById(dto.getId());

                if (!dto.getPrice().equals(oldProductPrice.getPrice())) {
                    oldProductPrice.setPrice(dto.getPrice());
                    save(oldProductPrice);
                }
            }
        });
    }
}
