package pl.wiktrans.ims.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wiktrans.ims.model.Address;
import pl.wiktrans.ims.model.CustomerObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerObjectDto {

    private Long id;

    private String contactName;
    private String contactSurname;
    private String contactTitle;


    private Address address;


    public static CustomerObjectDto of(CustomerObject object) {
        return CustomerObjectDto.builder()
                .id(object.getId())
                .contactName(object.getContactName())
                .contactSurname(object.getContactSurname())
                .contactTitle(object.getContactTitle())
                .address(object.getAddress())
                .build();
    }
}
