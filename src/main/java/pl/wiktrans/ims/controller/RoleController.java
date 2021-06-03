package pl.wiktrans.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wiktrans.ims.dto.RoleDto;
import pl.wiktrans.ims.misc.FailableResource;
import pl.wiktrans.ims.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public FailableResource<List<RoleDto>> getAll() {

        List<RoleDto> roles = roleService.getAll()
                .stream()
                .map(RoleDto::toRoleDto)
                .collect(Collectors.toList());

        return FailableResource.success(roles);
    }
}
