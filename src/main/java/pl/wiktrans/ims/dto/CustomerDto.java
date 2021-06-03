package pl.wiktrans.ims.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.wiktrans.ims.model.Address;
import pl.wiktrans.ims.model.Customer;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@Builder
public class CustomerDto {

    private Long id;

    private String name;
    private String phone;
    private String email;
    private String nip;

    private Address address;

    private List<CustomerObjectDto> customerObjects;


    public static CustomerDto of(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .nip(customer.getNip())
                .address(customer.getAddress())
                .customerObjects(customer
                        .getCustomerObjects()
                        .stream()
                        .map(CustomerObjectDto::of)
                        .collect(Collectors.toList()))
                .build();
    }

    public Customer toEntity() {
        return Customer.builder()
                .id(this.id)
                .name(this.name)
                .phone(this.phone)
                .email(this.email)
                .nip(this.nip)
                .address(this.address)
                .build();
    }

}
