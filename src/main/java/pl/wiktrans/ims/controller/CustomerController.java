package pl.wiktrans.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wiktrans.ims.dto.CustomerDto;
import pl.wiktrans.ims.util.FailableActionResult;
import pl.wiktrans.ims.util.FailableResource;
import pl.wiktrans.ims.model.Customer;
import pl.wiktrans.ims.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping
    public PageImpl<CustomerDto> getPaginated(@RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Customer> customersPage = customerService.getPage(pageRequest);

        List<CustomerDto> pageContent = customersPage
                .getContent()
                .stream().map(CustomerDto::of)
                .collect(Collectors.toList());

        return new PageImpl<>(pageContent, pageRequest, customersPage.getTotalElements());

    }

    @GetMapping("/list")
    public ResponseEntity<List<CustomerDto>> getAll() {
        List<CustomerDto> customers = customerService
                .getAll()
                .stream()
                .map(CustomerDto::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public FailableActionResult addNewCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.addNewCustomer(customerDto);
    }

    @GetMapping("/{id}")
    public FailableResource<CustomerDto> getById(@PathVariable Long id) {
        try {
            CustomerDto customerDto = CustomerDto.of(customerService.getById(id));
            return FailableResource.success(customerDto);
//        } catch (ElementNotFoundException e) {
//            return FailableResource.failure(e.getMessage());
        } catch (Exception e) {
            return FailableResource.failure(e.getMessage());
        }
    }

    @PostMapping("/{id}")
    public FailableActionResult updateCustomer(@RequestBody CustomerDto customerDto) {
        try {
            customerService.updateCustomer(customerDto);
            return FailableActionResult.success();
        } catch (Exception e) {
            return FailableActionResult.failure(e.getMessage());
        }
    }

    @PostMapping("/{id}/delete")
    public FailableActionResult deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteById(id);
            return FailableActionResult.success();
        } catch (Exception e) {
            return FailableActionResult.failure(e.getMessage());
        }
    }

}
