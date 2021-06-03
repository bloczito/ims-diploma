package pl.wiktrans.ims.service;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.dto.RoleDto;
import pl.wiktrans.ims.dto.UserDto;
import pl.wiktrans.ims.model.Role;
import pl.wiktrans.ims.model.User;
import pl.wiktrans.ims.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAll() { return userRepository.findAll(); }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user was found with given id = " + id));
    }

    public void addNewUser(UserDto dto) {
        User user = createUser(dto);
        userRepository.save(user);
    }

    public void updateUser(UserDto dto) {
        User oldUser = getById(dto.getId());
        oldUser.setFirstName(dto.getFirstName());
        oldUser.setLastName(dto.getLastName());
        oldUser.setShortcut(dto.getShortcut());
        oldUser.setUsername(dto.getUsername());
        if (!Strings.isNullOrEmpty(dto.getPassword())) {
            oldUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        oldUser.setEmail(dto.getEmail());
        oldUser.setPhone(dto.getPhone());
        oldUser.setJob(dto.getJob());

        oldUser.setActive(dto.getActive());
        oldUser.setHidden(dto.getHidden());
        oldUser.setEnabled(dto.getEnabled());

        Set<String> roleNames = dto
                .getRoles()
                .stream()
                .map(RoleDto::getName)
                .collect(Collectors.toSet());

        Set<Role> newRoles = (Set<Role>) roleService.getAllByNames(roleNames);

        Set<Role> roles = oldUser.getRoles();
        roles.clear();
        roles.addAll(newRoles);
        oldUser.setRoles(roles);


    }

    private User createUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setShortcut(userDto.getShortcut());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setJob(userDto.getJob());
        user.setActive(userDto.getActive());
        user.setHidden(userDto.getHidden());
        user.setEnabled(userDto.getEnabled());

        Set<String> roleNames = userDto
                .getRoles()
                .stream()
                .map(RoleDto::getName)
                .collect(Collectors.toSet());

        Set<Role> roles = (Set<Role>) roleService.getAllByNames(roleNames);
        user.setRoles(roles);

        return user;
    }
}
