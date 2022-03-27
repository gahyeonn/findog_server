package challengers.findog.src.user.model;

import lombok.Data;

@Data
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
}
