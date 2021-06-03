package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wiktrans.ims.model.Order;
import pl.wiktrans.ims.model.ProductPrice;

import java.util.List;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

    List<ProductPrice> findAllByOrder(Order order);

}
