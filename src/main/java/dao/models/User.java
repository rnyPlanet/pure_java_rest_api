package dao.models;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class User {
    String id;
    String login;
    String password;
}
