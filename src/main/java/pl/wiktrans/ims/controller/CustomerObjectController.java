package pl.wiktrans.ims.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wiktrans.ims.service.CustomerObjectService;
import pl.wiktrans.ims.util.FailableActionResult;

@Slf4j
@RestController()
@RequestMapping("/customerObjects")
public class CustomerObjectController {

    @Autowired
    private CustomerObjectService customerObjectService;

    @PostMapping("/{id}/delete")
    public FailableActionResult deleteObject(@PathVariable Long id) {
        try {
            customerObjectService.deleteById(id);
            return FailableActionResult.success();
        } catch (Exception e) {
            log.error("Deleting customer object({}) error: {}", id, e.getMessage());
            return FailableActionResult.failure(e.getMessage());
        }
    }

}
