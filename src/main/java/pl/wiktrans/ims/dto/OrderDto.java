package pl.wiktrans.ims.dto;

import lombok.*;
import pl.wiktrans.ims.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long id;
    private String orderNumber;

    private LocalDate orderDate;
    private LocalDate deadline;

    private OrderStatus status;

    private OrderPriority priority;

    private CustomerDto customer;

    private OrderDtoCompany company;

    private List<MerchOrderDto> merchOrders;

    private List<ShipmentDto> shipments;

    private List<ProductPriceDto> productPrices;

    private LocalDateTime createdDate;

    private String createdBy;

    private LocalDateTime modificationDate;

    private String modifiedBy;

    public static OrderDto toOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderDate(order.getOrderDate())
                .deadline(order.getDeadline())
                .status(order.getStatus())
                .priority(order.getPriority())
                .customer(CustomerDto.of(order.getCustomer()))
                .company(OrderDtoCompany.toCompany(order.getCompany()))
                .merchOrders(order.getMerchOrders()
                        .stream()
                        .map(MerchOrderDto::of)
                        .collect(Collectors.toList()))
                .shipments(order.getShipments()
                        .stream()
                        .map(ShipmentDto::of)
                        .collect(Collectors.toList()))
                .productPrices(order.getProductPrices()
                        .stream()
                        .map(ProductPriceDto::of)
                        .collect(Collectors.toList()))
                .createdDate(order.getCreatedDate())
                .createdBy(order.getCreatedBy())
                .modificationDate(order.getModificationDate())
                .modifiedBy(order.getModifiedBy())
                .build();
    }




    @Data
    @Builder
    public static class OrderDtoCompany {
        private Long id;

        private String name;
        private String nip;
        private String phone;
        private String email;

        private Address address;

        public static OrderDtoCompany toCompany(Company company) {
           return OrderDtoCompany.builder()
                   .id(company.getId())
                   .name(company.getName())
                   .nip(company.getNip())
                   .phone(company.getPhone())
                   .email(company.getEmail())
                   .build();
        }
    }

    @Data
    @Builder
    public static class OrderDtoCustomer {
        private Long id;

        private String name;
        private String phone;
        private String email;
        private String nip;

        private Address address;

        public static OrderDtoCustomer toCustomer(Customer customer) {
            return OrderDtoCustomer.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .phone(customer.getPhone())
                    .email(customer.getEmail())
                    .nip(customer.getNip())
                    .address(customer.getAddress())
                    .build();
        }

    }


}
