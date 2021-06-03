package pl.wiktrans.ims.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class MerchOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Order order;

    @Column(columnDefinition = "LONGTEXT")
    private String comment;

    @OneToMany(mappedBy = "merchOrder", fetch = FetchType.EAGER)
    private List<OrderElement> orderElements;




    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HHH:mm:ss")
    private LocalDateTime merchOrderDate;

}