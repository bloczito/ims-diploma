package pl.wiktrans.ims.model;


import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long code;
    private String name;

    @Column(name = "description_eng")
    private String descriptionEng;

    @Column(name = "description_ger")
    private String descriptionGer;

//    private ProductType type;
    private Double height;
    private Double width;
    private Double depth;
    private Double weight;

    private BigDecimal basePrice;


    @OneToMany(mappedBy = "product")
    private List<SupplyElement> supplyElements;

    @OneToMany(mappedBy = "product")
    private List<OrderElement> orderElements;

    @OneToMany(mappedBy = "product")
    private List<ShipmentElement> shipmentElements;

    @OneToMany(mappedBy = "product")
    private List<DeprecatedElements> deprecatedElements;


    @OneToMany(mappedBy = "product")
    private List<ProductPrice> productPrices;

}
