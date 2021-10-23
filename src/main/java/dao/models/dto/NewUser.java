package dao.models.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewUser {
    String login;
    String password;
}
