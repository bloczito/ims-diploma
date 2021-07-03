package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.dto.ShipmentDto;
import pl.wiktrans.ims.model.Order;
import pl.wiktrans.ims.model.Shipment;
import pl.wiktrans.ims.model.ShipmentElement;
import pl.wiktrans.ims.repository.ShipmentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentElementService shipmentElementService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private CustomerObjectService customerObjectService;

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

    public void updateShipments(Order order, List<ShipmentDto> shipmentDtos) {
        shipmentDtos.forEach(dto -> {
            if (dto.getId() == null) {
                Shipment shipment = new Shipment();
                shipment.setOrder(order);
                shipment.setComment(dto.getComment());
                shipment.setShipmentDate(dto.getShipmentDate());
                shipment.setShipmentObject(customerObjectService.getById(dto.getShipmentObject().getId()));
                save(shipment);

                List<ShipmentElement> shipmentElements = shipmentElementService.createShipmentElements(shipment, dto.getShipmentElements());
                shipment.setShipmentElements(shipmentElements);
            } else {
                Shipment oldShipment = getById(dto.getId());
                oldShipment.setComment(dto.getComment());
                save(oldShipment);
            }
        });
    }

}
