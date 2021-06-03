package pl.wiktrans.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.wiktrans.ims.model.Role;

@Data
@AllArgsConstructor
@Builder
public class RoleDto {

    private Long id;

    private String name;
    private String info;

    private Boolean hidden;


    public static RoleDto toRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .info(role.getInfo())
                .hidden(role.getHidden())
                .build();

    }
}
