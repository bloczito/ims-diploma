package pl.wiktrans.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wiktrans.ims.dto.CompanyDto;
import pl.wiktrans.ims.service.CompanyService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @GetMapping("/list")
    private List<CompanyDto> getAll() {
        return companyService
                .getAll()
                .stream()
                .map(CompanyDto::toCompanyDto)
                .collect(Collectors.toList());
    }

}
