package pl.wiktrans.ims.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.wiktrans.ims.model.User;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String firstName;
    private String lastName;

    private String shortcut;
    private String username;

    @JsonIgnore
    private String password;

    private String email;
    private String phone;
    private String job;

    private Boolean active;
    private Boolean hidden;
    private Boolean enabled;

    private Set<RoleDto> roles;

    public static UserDto of(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .shortcut(user.getShortcut())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .job(user.getJob())
                .active(user.getActive())
                .hidden(user.getHidden())
                .enabled(user.getEnabled())
                .roles(user.getRoles()
                        .stream()
                        .map(RoleDto::of)
                        .collect(Collectors.toSet())
                )
                .build();
    }

//    public User toEntity() {
//        User user = new User();
//        user.setId(this.id);
//        user.setFirstName(this.firstName);
//        user.setLastName(this.lastName);
//        user.setShortcut(this.shortcut);
//        user.setUsername(this.username);
//        user.setPassword(this.password);
//        user.setEmail(this.email);
//        user.setJob(this.job);
//        user.setActive(this.active);
//        user.setHidden(this.hidden);
//        user.setEnabled(this.enabled);
//        user.setRoles();
//    }
}
