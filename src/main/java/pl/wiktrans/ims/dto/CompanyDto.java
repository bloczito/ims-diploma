package pl.wiktrans.ims.dto;

import lombok.*;
import pl.wiktrans.ims.model.Address;
import pl.wiktrans.ims.model.Company;
import pl.wiktrans.ims.model.Order;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {

    private Long id;

    private String name;
    private String nip;
    private String phone;
    private String email;

    private Address address;

    private List<OrderDto> orders;

    public static CompanyDto toCompanyDto(Company company) {
        List<OrderDto> orderDtoList = company.getOrders()
                .stream()
                .map(OrderDto::toOrderDto)
                .collect(Collectors.toList());

        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .nip(company.getNip())
                .phone(company.getPhone())
                .email(company.getEmail())
                .address(company.getAddress())
                .orders(orderDtoList)
                .build();
    }

}
