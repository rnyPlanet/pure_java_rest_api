package utils;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private String login;
    private String password;
}