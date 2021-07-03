package pl.wiktrans.ims.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.wiktrans.ims.model.Order;

import java.util.List;

//@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "*")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    @Query("select o from Order o where o.deleted = :isDeleted")
    Page<Order> findAllActivePage(@Param("isDeleted") boolean isDeleted, Pageable pageable);

    List<Order> findAllByDeleted(Boolean deleted);

}
