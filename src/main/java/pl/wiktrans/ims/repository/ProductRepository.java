package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.wiktrans.ims.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where concat(p.code, '') like %?1% or p.name like %?1%")
    List<Product> getAllByQuery(String query);
}
