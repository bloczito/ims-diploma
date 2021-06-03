package pl.wiktrans.ims.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.wiktrans.ims.model.Order;

//@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "*")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {



}
