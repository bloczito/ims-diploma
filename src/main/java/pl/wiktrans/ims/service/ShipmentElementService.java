package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.model.ShipmentElement;
import pl.wiktrans.ims.repository.ShipmentElementRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ShipmentElementService {

    @Autowired
    private ShipmentElementRepository shipmentElementRepository;

    public ShipmentElement getById(Long id) {
        return shipmentElementRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No ShipmentElement was found with given id = " + id));
    }

    public List<ShipmentElement> getAll() {
        return shipmentElementRepository.findAll();
    }

    public void save(ShipmentElement shipmentElement) {
        shipmentElementRepository.save(shipmentElement);
    }

    public void saveAll(List<ShipmentElement> shipmentElements) { shipmentElementRepository.saveAll(shipmentElements); }


}
