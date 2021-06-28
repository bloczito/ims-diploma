package pl.wiktrans.ims.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wiktrans.ims.dto.RoleDto;
import pl.wiktrans.ims.util.FailableActionResult;
import pl.wiktrans.ims.util.FailableResource;
import pl.wiktrans.ims.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public FailableResource<List<RoleDto>> getAll() {

        List<RoleDto> roles =  roleService.getAll()
                .stream()
                .map(RoleDto::of)
                .collect(Collectors.toList());

        return FailableResource.success(roles);
    }

    @GetMapping("/{id}")
    public FailableResource<RoleDto> getById(@PathVariable Long id) {
        try {
            RoleDto roleDto = RoleDto.of(roleService.getById(id));
            return FailableResource.success(roleDto);
        } catch (Exception e) {
            return FailableResource.failure(e.getMessage());
        }
    }

    @PostMapping
    public FailableActionResult addNewRole(@RequestBody RoleDto dto) {
        try {
            if (dto.getId() != null) {
                roleService.updateRole(dto);
            } else {
                roleService.addNewRole(dto);
            }
            return FailableActionResult.success();
        } catch (Exception e) {
            log.error("Add/edit user error: ", e.getMessage());
            return FailableActionResult.failure(e.getMessage());
        }
    }

//    @PostMapping("/{id}")
//    public FailableActionResult updateRole(@RequestBody RoleDto dto) {
//        try {
//            roleService.updateRole(dto);
//            return FailableActionResult.success();
//        } catch (Exception e) {
//            return FailableActionResult.failure(e.getMessage());
//        }
//    }


}
