package pl.wiktrans.ims.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer_order")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "order_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    private LocalDate deadline;

    private OrderStatus status = OrderStatus.NEW;

    private OrderPriority priority = OrderPriority.LOW;


    @ManyToOne
    private Customer customer;

    @JsonIgnore
    @ManyToOne
    private Company company;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<MerchOrder> merchOrders;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<Shipment> shipments;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<ProductPrice> productPrices;


    @Setter(AccessLevel.NONE)
    @Column(name = "created_date")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HHH:mm:ss")
    private LocalDateTime createdDate;

    @Setter(AccessLevel.NONE)
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Setter(AccessLevel.NONE)
    @Column(name = "modification_date")
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modificationDate;

    @Setter(AccessLevel.NONE)
    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

}
