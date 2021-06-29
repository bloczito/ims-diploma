package pl.wiktrans.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.wiktrans.ims.model.*;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private Long code;
    private String name;

    private String descriptionEng;
    private String descriptionGer;

    //    private ProductType type;
    private Double height;
    private Double width;
    private Double depth;
    private Double weight;

    private BigDecimal basePrice;

    public static ProductDto of(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .descriptionEng(product.getDescriptionEng())
                .descriptionGer(product.getDescriptionGer())
                .height(product.getHeight())
                .width(product.getWidth())
                .depth(product.getDepth())
                .weight(product.getWeight())
                .basePrice(product.getBasePrice())
                .build();
    }

    public Product toEntity() {
        return Product.builder()
        .id(this.id)
        .code(this.code)
        .name(this.name)
        .descriptionEng(this.descriptionEng)
        .descriptionGer(this.descriptionGer)
        .height(this.height)
        .width(this.width)
        .depth(this.depth)
        .weight(this.weight)
        .basePrice(this.basePrice)
        .deleted(false)
        .build();
    }

}
