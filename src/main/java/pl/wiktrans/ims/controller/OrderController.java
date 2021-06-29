package pl.wiktrans.ims.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.wiktrans.ims.dto.OrderDto;
import pl.wiktrans.ims.dto.OrderInitData;
import pl.wiktrans.ims.util.FailableActionResult;
import pl.wiktrans.ims.util.FailableResource;
import pl.wiktrans.ims.model.Order;
import pl.wiktrans.ims.model.OrderPriority;
import pl.wiktrans.ims.model.OrderStatus;
import pl.wiktrans.ims.service.OrderService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    


    @GetMapping
    public PageImpl<OrderDto> getPaginated(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "20") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Order> ordersPage = orderService.getPage(pageRequest);

        List<OrderDto> pageContent = ordersPage
                .stream()
                .map(OrderDto::toOrderDto)
                .collect(Collectors.toList());

        return new PageImpl<>(pageContent, pageRequest, ordersPage.getTotalElements());
    }


    @PostMapping
    public FailableActionResult addNewOrder(@RequestBody OrderDto orderDto) {

        orderService.addNewOrder(orderDto);

        return FailableActionResult.success();
    }


    @GetMapping("/{id}")
    public FailableResource<OrderInitData> getOne(@PathVariable long id) {
        OrderDto orderDto = OrderDto.toOrderDto(orderService.getById(id));

        return FailableResource.success(
                OrderInitData.builder()
                        .order(orderDto)
                        .priorities(Arrays.asList(OrderPriority.values().clone()))
                        .statuses(Arrays.asList(OrderStatus.values()))
                        .build()
        );
    }

    @PostMapping("/{id}")
    public FailableActionResult updateOrder(@PathVariable long id, @RequestBody OrderDto orderDto) {
        try {
            orderService.updateOrder(orderDto);
            return FailableActionResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return FailableActionResult.failure(e.getMessage());
        }

    }

    @PostMapping("/{id}/delete")
    public FailableActionResult deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return FailableActionResult.success();
        } catch (Exception e) {
            return FailableActionResult.failure(e.getMessage());
        }
    }
}
