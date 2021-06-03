package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktrans.ims.model.OrderElement;

public interface OrderElementRepository extends JpaRepository<OrderElement, Long> {
}
