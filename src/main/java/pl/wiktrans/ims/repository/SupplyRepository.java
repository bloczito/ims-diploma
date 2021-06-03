package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktrans.ims.model.Supply;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
