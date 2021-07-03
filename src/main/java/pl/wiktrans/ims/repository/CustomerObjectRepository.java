package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wiktrans.ims.model.Customer;
import pl.wiktrans.ims.model.CustomerObject;

import java.util.List;

@Repository
public interface CustomerObjectRepository extends JpaRepository<CustomerObject, Long> {

    List<CustomerObject> findAllByCustomerAndIsDeleted(Customer customer, Boolean isDeleted);


}
