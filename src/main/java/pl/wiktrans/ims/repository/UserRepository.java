package pl.wiktrans.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktrans.ims.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndHidden(String username, Boolean hidden);

    Optional<User> findByUsername(String username);

    List<User> findAllByHidden(Boolean isHidden);
}
