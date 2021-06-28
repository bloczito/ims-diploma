package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.model.Shipment;
import pl.wiktrans.ims.repository.ShipmentRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentElementService shipmentElementService;

    public Shipment getById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Sshipment was found with given id = " + id));
    }

    public List<Shipment> getAll() {
        return shipmentRepository.findAllByDeleted(false);
    }

    public void save(Shipment shipment) {
        shipmentRepository.save(shipment);
    }

    public void deleteShipments(List<Shipment> shipments) {
        for (Shipment shipment : shipments) {
            shipment.setDeleted(true);
            shipmentElementService.deleteShipmentElements(shipment.getShipmentElements());
        }

        shipmentRepository.saveAll(shipments);
    }

    public void deleteShipmentPermanently(Long id) {
        Shipment shipment = getById(id);
        shipmentElementService.deleteShipmentElementsPermanently(shipment.getShipmentElements());
        shipmentRepository.delete(shipment);
    }

}
