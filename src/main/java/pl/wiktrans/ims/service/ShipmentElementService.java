package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.dto.ShipmentElementDto;
import pl.wiktrans.ims.model.Shipment;
import pl.wiktrans.ims.model.ShipmentElement;
import pl.wiktrans.ims.repository.ShipmentElementRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ShipmentElementService {

    @Autowired
    private ShipmentElementRepository shipmentElementRepository;

    @Autowired
    private ProductsService productsService;

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

    public void deleteShipmentElements(List<ShipmentElement> shipmentElements) {
        for (ShipmentElement shipmentElement : shipmentElements) {
            shipmentElement.setDeleted(true);
        }
        shipmentElementRepository.saveAll(shipmentElements);
    }

    public void deleteShipmentElementsPermanently(List<ShipmentElement> shipmentElements) {
        shipmentElementRepository.deleteAll(shipmentElements);
    }

    public List<ShipmentElement> createShipmentElements(Shipment shipment, List<ShipmentElementDto> shipmentElementDtos) {
        List<ShipmentElement> shipmentElements = shipmentElementDtos
                .stream()
                .map(elDto -> {
                    ShipmentElement shipmentElement = new ShipmentElement();
                    shipmentElement.setShipment(shipment);
                    shipmentElement.setQuantity(elDto.getQuantity());
                    shipmentElement.setProduct(productsService.getById(elDto.getProduct().getId()));
                    return shipmentElement;
                })
                .collect(Collectors.toList());

        saveAll(shipmentElements);
        return shipmentElements;
    }

}
