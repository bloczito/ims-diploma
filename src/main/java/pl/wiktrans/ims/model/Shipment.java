package pl.wiktrans.ims.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String comment;

    @ManyToOne
    private Order order;

    @ManyToOne
    private CustomerObject shipmentObject;


    @OneToMany(mappedBy = "shipment")
    private List<ShipmentElement> shipmentElements;

    @Column(name = "shipment_date")
    @CreatedDate
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private LocalDate shipmentDate;

}
