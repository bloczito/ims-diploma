package pl.wiktrans.ims.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.wiktrans.ims.dto.ProductDto;
import pl.wiktrans.ims.util.FailableActionResult;
import pl.wiktrans.ims.util.FailableResource;
import pl.wiktrans.ims.model.Product;
import pl.wiktrans.ims.service.ProductsService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductsService productsService;

    @GetMapping
    public PageImpl<ProductDto> getPaginated(@RequestParam(required = false, defaultValue = "0") int page,
                                          @RequestParam(required = false, defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Product> productsPage = productsService.getPage(pageRequest);

        List<ProductDto> pageContent = productsPage
                .getContent()
                .stream()
                .map(ProductDto::of)
                .collect(Collectors.toList());

        return new PageImpl<>(pageContent, pageRequest, productsPage.getTotalElements());
    }

    @PostMapping
    public FailableActionResult addNewProduct(@RequestBody ProductDto productDto) {
        try {
            productsService.addNewProduct(productDto);
            return FailableActionResult.success();
        } catch (Exception e) {
            return FailableActionResult.failure(e.getMessage());
        }

    }

    @GetMapping("/all")
    public FailableResource<List<ProductDto>> getByQuery(@RequestParam(required = false) String query) {

        List<ProductDto> productDtos = (query != null ? productsService.getByQuery(query) : productsService.getAll())
                .stream()
                .map(ProductDto::of)
                .collect(Collectors.toList());

        return FailableResource.success(productDtos);
    }

    @GetMapping("/{id}")
    public FailableResource<ProductDto> getById(@PathVariable Long id) {

       try {
           return FailableResource.success(
                   ProductDto.of(productsService.getById(id))
           );
       } catch (Exception e) {
           return FailableResource.failure(e.getMessage());
       }
    }

    @PostMapping("/{id}/delete")
    public FailableActionResult updateProduct(@PathVariable Long id) {
        try {
            productsService.deleteProduct(id);
            return FailableActionResult.success();
        } catch (Exception e) {
            log.error("Deleting product error: {}", e.getMessage());
            return FailableActionResult.failure(e.getMessage());
        }

    }
}
