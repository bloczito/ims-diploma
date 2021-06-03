package pl.wiktrans.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wiktrans.ims.model.ShipmentElement;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentElementDto {
    private Long id;
    private Integer quantity;
    private ProductDto product;


    public static ShipmentElementDto of(ShipmentElement shipmentElement) {
        return ShipmentElementDto.builder()
                .id(shipmentElement.getId())
                .quantity(shipmentElement.getQuantity())
                .product(ProductDto.of(shipmentElement.getProduct()))
                .build();
    }

}
