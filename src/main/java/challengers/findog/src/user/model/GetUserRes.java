package challengers.findog.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserRes {
    private int userId;
    private String email;
    private String nickname;
    private String phoneNum;
    private String profileUrl;

    public static GetUserRes from (User user) {
        return new GetUserRes(user.getUserId(), user.getEmail(), user.getNickname(), user.getPhoneNum(), user.getProfileUrl());
    }
}
