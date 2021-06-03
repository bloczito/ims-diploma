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

    public Shipment getById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Sshipment was found with given id = " + id));
    }

    public List<Shipment> getAll() {
        return shipmentRepository.findAll();
    }

    public void save(Shipment shipment) {
        shipmentRepository.save(shipment);
    }



}
