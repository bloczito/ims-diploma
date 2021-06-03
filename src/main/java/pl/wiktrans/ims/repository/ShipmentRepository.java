package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktrans.ims.model.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
