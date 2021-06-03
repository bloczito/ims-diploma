package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktrans.ims.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
