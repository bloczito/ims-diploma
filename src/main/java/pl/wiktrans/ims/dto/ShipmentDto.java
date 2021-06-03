package pl.wiktrans.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wiktrans.ims.model.Shipment;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDto {

    private Long id;
    private String comment;
    private LocalDate shipmentDate;

    private CustomerObjectDto shipmentObject;

    private List<ShipmentElementDto> shipmentElements;

    public static ShipmentDto of(Shipment shipment) {
        return ShipmentDto.builder()
                .id(shipment.getId())
                .comment(shipment.getComment())
                .shipmentDate(shipment.getShipmentDate())
                .shipmentElements(
                        shipment.getShipmentElements()
                        .stream()
                        .map(ShipmentElementDto::of)
                        .collect(Collectors.toList())
                )
                .shipmentObject(CustomerObjectDto.of(shipment.getShipmentObject()))
                .build();
    }

}
