package pl.wiktrans.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wiktrans.ims.model.ProductPrice;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceDto {

    private Long id;
    private BigDecimal price;
    private ProductDto product;


    public static ProductPriceDto of(ProductPrice productPrice) {
        return ProductPriceDto.builder()
                .id(productPrice.getId())
                .price(productPrice.getPrice())
                .product(ProductDto.of(productPrice.getProduct()))
                .build();
    }

}
