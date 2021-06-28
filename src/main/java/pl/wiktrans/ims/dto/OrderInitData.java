package pl.wiktrans.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wiktrans.ims.model.OrderPriority;
import pl.wiktrans.ims.model.OrderStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInitData {
    private OrderDto order;
    private List<OrderStatus> statuses;
    private List<OrderPriority> priorities;
}
