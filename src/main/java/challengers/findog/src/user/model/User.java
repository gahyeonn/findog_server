package challengers.findog.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int userId;
    private String email;
    private String nickname;
    private String password;
    private String phoneNum;
    private String profileUrl;
    private String userStatus;
    private String userCreateAt;
    private String userUpdateAt;

    public User(String email, String nickname, String password, String phoneNum, String profileUrl){
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phoneNum = phoneNum;
        this.profileUrl = profileUrl;
    }

    public User(int userId, String email, String nickname, String password, String phoneNum, String profileUrl, String userStatus){
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phoneNum = phoneNum;
        this.profileUrl = profileUrl;
        this.userStatus = userStatus;
    }
}
