package pl.wiktrans.ims.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
public class CustomerObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_surname")
    private String contactSurname;

    @Column(name = "contact_title")
    private String contactTitle;

    private Boolean isDeleted = false;

    @ManyToOne
    private Customer customer;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "shipmentObject")
    private List<Shipment> shipments;
}
