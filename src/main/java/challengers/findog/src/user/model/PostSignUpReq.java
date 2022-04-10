package challengers.findog.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import static challengers.findog.config.Constant.*;

@Data
public class PostSignUpReq {
    @NotBlank(message = "EMPTY_EMAIL")
    @Email(message = "INVALID_EMAIL")
    private String email;

    @NotBlank(message = "EMPTY_NICKNAME")
    @Pattern(regexp = nicknameRegex, message = "INVALID_NICKNAME")
    private String nickname;

    @NotBlank(message = "EMPTY_PASSWORD")
    @Pattern(regexp = passwordRegex, message = "INVALID_PASSWORD")
    private String password;

    @NotBlank(message = "EMPTY_PHONENUMBER")
    @Pattern(regexp = phoneNumRegex, message = "INVALID_PHONENUMBER")
    private String phoneNum;

    private MultipartFile profileImg;

    public PostSignUpReq(String email, String nickname, String password, String phoneNum){
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phoneNum = phoneNum;
    }
}
