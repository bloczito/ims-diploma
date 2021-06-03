package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktrans.ims.model.Privilege;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findByName(String name);
}
