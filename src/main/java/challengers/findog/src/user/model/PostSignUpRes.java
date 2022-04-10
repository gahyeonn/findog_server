package challengers.findog.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostSignUpRes {
    private int userId;
    private String userJWT;
}
