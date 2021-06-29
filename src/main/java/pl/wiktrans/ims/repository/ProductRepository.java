package pl.wiktrans.ims.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.wiktrans.ims.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where (concat(p.code, '') like %?1% or p.name like %?1%) and p.deleted = false")
    List<Product> getAllByQuery(String query);

    @Query("select p from Product p where p.deleted = :isDeleted")
    Page<Product> findActiveByPage(@Param("isDeleted") boolean isDeleted, Pageable pageable);
}
