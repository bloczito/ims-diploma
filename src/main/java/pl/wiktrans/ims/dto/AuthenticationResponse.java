package pl.wiktrans.ims.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthenticationResponse {

    private final String username;
    private final String token;
    private final List<String> roles;
}
