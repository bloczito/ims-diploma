package pl.wiktrans.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wiktrans.ims.model.MerchOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchOrderDto {

    private Long id;
    private String comment;
    private LocalDateTime merchOrderDate;

    private List<OrderElementDto> orderElements;

    public static MerchOrderDto of(MerchOrder merchOrder) {
        return MerchOrderDto.builder()
                .id(merchOrder.getId())
                .comment(merchOrder.getComment())
                .merchOrderDate(merchOrder.getMerchOrderDate())
                .orderElements(merchOrder.getOrderElements()
                        .stream()
                        .map(OrderElementDto::of)
                        .collect(Collectors.toList()))
                .build();
    }

}
