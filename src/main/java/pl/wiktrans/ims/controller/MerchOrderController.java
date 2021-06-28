package pl.wiktrans.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wiktrans.ims.util.FailableActionResult;
import pl.wiktrans.ims.service.MerchOrderService;

@RestController
@RequestMapping("/merchOrders")
public class MerchOrderController {

    @Autowired
    private MerchOrderService merchOrderService;

    @PostMapping("/{id}/delete")
    public FailableActionResult deleteMerchOrder(@PathVariable Long id) {
        try {
            merchOrderService.deleteMerchOrderPermanently(id);
            return FailableActionResult.success();
        } catch (Exception e) {
            return FailableActionResult.failure(e.getMessage());
        }
    }
}
