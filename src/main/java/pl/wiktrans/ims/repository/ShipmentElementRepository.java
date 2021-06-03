package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktrans.ims.model.Shipment;
import pl.wiktrans.ims.model.ShipmentElement;

public interface ShipmentElementRepository extends JpaRepository<ShipmentElement, Long> {
}
