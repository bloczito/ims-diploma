package pl.wiktrans.ims.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String phone;

    private String email;

    @Column(unique = true)
    private String nip;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<CustomerObject> customerObjects;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Order> orders;


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
