package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktrans.ims.model.DeprecatedElements;

public interface DeprecatedElementsRepository extends JpaRepository<DeprecatedElements, Long> {
}
