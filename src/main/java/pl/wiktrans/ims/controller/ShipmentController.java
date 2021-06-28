package pl.wiktrans.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wiktrans.ims.util.FailableActionResult;
import pl.wiktrans.ims.service.ShipmentService;

@RequestMapping("/shipments")
@RestController
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;


    @PostMapping("/{id}/delete")
    public FailableActionResult deleteShipment(@PathVariable Long id) {
        try {
            shipmentService.deleteShipmentPermanently(id);
            return FailableActionResult.success();
        } catch (Exception e) {
            return FailableActionResult.failure(e.getMessage());
        }
    }


}
