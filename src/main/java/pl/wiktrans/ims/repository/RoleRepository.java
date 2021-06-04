package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.wiktrans.ims.model.Role;

import java.util.List;
import java.util.Optional;

//@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    List<Role> findAllByNameIn(Iterable<String> roleNames);

}
