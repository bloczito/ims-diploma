package pl.wiktrans.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wiktrans.ims.model.OrderElement;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderElementDto {

    private Long id;
    private Integer quantity;
    private ProductDto product;

    public static OrderElementDto of(OrderElement orderElement) {
        return OrderElementDto.builder()
                .id(orderElement.getId())
                .quantity(orderElement.getQuantity())
                .product(ProductDto.of(orderElement.getProduct()))
                .build();

    }
}
