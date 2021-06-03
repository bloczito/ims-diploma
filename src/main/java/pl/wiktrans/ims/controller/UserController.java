package pl.wiktrans.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wiktrans.ims.dto.UserDto;
import pl.wiktrans.ims.misc.FailableActionResult;
import pl.wiktrans.ims.misc.FailableResource;
import pl.wiktrans.ims.model.User;
import pl.wiktrans.ims.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public FailableResource<List<UserDto>> getAll() {

        List<UserDto> users = userService.getAll()
                .stream()
                .map(UserDto::of)
                .collect(Collectors.toList());

        return FailableResource.success(users);
    }

    @GetMapping("{id}")
    public FailableResource<UserDto> getById(@PathVariable Long id) {
        try {
            User user = userService.getById(id);

            return FailableResource.success(UserDto.of(user));
        } catch (Exception e) {
            return FailableResource.failure(e.getMessage());
        }
    }

    @PostMapping
    public FailableActionResult addNewUser(@RequestBody UserDto userDto) {
        try {
            userService.addNewUser(userDto);
            return FailableActionResult.success();
        } catch (Exception e) {
            return FailableActionResult.failure(e.getMessage());
        }
    }

    @PostMapping("/{id}")
    public FailableActionResult updateUser(@RequestBody UserDto userDto) {
        try {
            userService.updateUser(userDto);
            return FailableActionResult.success();
        } catch (Exception e) {
            return FailableActionResult.failure(e.getMessage());
        }
    }
}
