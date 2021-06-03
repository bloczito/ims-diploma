package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktrans.ims.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
