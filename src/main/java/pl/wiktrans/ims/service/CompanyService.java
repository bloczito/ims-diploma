package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.model.Company;
import pl.wiktrans.ims.repository.CompanyRepository;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    public List<Company> getAll() { return companyRepository.findAll(); }

    public Company getById(Long id) { return companyRepository.getOne(id); }

}
