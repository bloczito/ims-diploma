package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.model.Customer;
import pl.wiktrans.ims.model.CustomerObject;
import pl.wiktrans.ims.repository.CustomerObjectRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerObjectService {

    @Autowired
    private CustomerObjectRepository customerObjectRepository;

    public CustomerObject getById(Long id) {
        return customerObjectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No customer object with given id =  " + id + " was found"));
    }

    public List<CustomerObject> getAllByCustomer(Customer customer) {
        return customerObjectRepository.findAllByCustomerAndIsDeleted(customer, false);
    }

    public void save(CustomerObject customerObject) {
        customerObjectRepository.save(customerObject);
    }

    public void saveAll(List<CustomerObject> customerObjects) {
        customerObjectRepository.saveAll(customerObjects);
    }

    public void deleteById(Long id) {
        CustomerObject customerObject = getById(id);
        customerObject.setIsDeleted(true);
        save(customerObject);
    }

    public void deleteCustomerObjects(List<CustomerObject> customerObjects) {
        customerObjects.forEach(customerObject -> customerObject.setIsDeleted(true));
        saveAll(customerObjects);
    }
}
