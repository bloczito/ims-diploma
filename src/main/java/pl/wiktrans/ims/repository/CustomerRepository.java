package pl.wiktrans.ims.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.wiktrans.ims.model.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where c.isDeleted = ?1")
    Page<Customer> findActivePage(boolean isDeleted, Pageable pageable);

    List<Customer> findAllByIsDeleted(Boolean isDeleted);
}
