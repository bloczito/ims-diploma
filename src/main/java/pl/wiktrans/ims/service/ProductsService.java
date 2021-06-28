package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.dto.ProductDto;
import pl.wiktrans.ims.model.Product;
import pl.wiktrans.ims.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductsService {

    @Autowired
    private ProductRepository productRepository;


    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Page<Product> getPage(PageRequest pageRequest) {
        return productRepository.findActiveByPage(false, pageRequest);
    }


    public Product getById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with given id = " + id));
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void addNewProduct(ProductDto productDto) {
        Product product = productDto.toEntity();
        productRepository.save(product);
    }

    public List<Product> getByQuery(String query) {
        return productRepository.getAllByQuery(query);
    }

    public void deleteProduct(Long id) {
        Product product = getById(id);
        product.setDeleted(true);
        productRepository.save(product);
    }

}
