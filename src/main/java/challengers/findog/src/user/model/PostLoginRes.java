package challengers.findog.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostLoginRes {
    private int userId;
    private String userJWT;
    private String profileImgUrl;
}
