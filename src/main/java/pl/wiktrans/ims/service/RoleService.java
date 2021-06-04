package pl.wiktrans.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktrans.ims.dto.RoleDto;
import pl.wiktrans.ims.model.Role;
import pl.wiktrans.ims.repository.RoleRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public List<Role> getAll() { return roleRepository.findAll(); }

    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No element was found with given id = " + id));
    }

    public void save(Role role) { roleRepository.save(role); }

    public Role getByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Role " + name + " was not found"));
    }

    public List<Role> getAllByNames(Iterable<String> roleNames) {
        return roleRepository.findAllByNameIn(roleNames);
    }

    public void addNewRole(RoleDto dto) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setInfo(dto.getInfo());
        roleRepository.save(role);
    }

    public void updateRole(RoleDto dto) {
        Role oldRole = getById(dto.getId());
        oldRole.setName(dto.getName());
        oldRole.setInfo(dto.getInfo());
        roleRepository.save(oldRole);
    }
}
