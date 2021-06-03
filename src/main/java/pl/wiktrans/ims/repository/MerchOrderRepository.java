package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktrans.ims.model.MerchOrder;
import pl.wiktrans.ims.model.Order;

import java.util.List;

public interface MerchOrderRepository extends JpaRepository<MerchOrder, Long> {

    List<MerchOrder> findAllByOrder(Order order);
}
