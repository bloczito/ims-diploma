package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.dto.CustomerDto;
import pl.wiktrans.ims.misc.FailableActionResult;
import pl.wiktrans.ims.model.Customer;
import pl.wiktrans.ims.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getById(Long id) { return customerRepository.getOne(id); }

    public Page<Customer> getPage(PageRequest pageRequest) { return customerRepository.findAll(pageRequest); }

    public FailableActionResult addNewCustomer(CustomerDto customerDto) {
        try {
            Customer customer = customerDto.toEntity();
            customerRepository.save(customer);
            return FailableActionResult.success();
        } catch (Exception e){
            return FailableActionResult.failure(e.getMessage());
        }

    }
}
