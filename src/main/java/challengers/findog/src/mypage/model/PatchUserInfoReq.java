package challengers.findog.src.mypage.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static challengers.findog.config.Constant.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchUserInfoReq {

    @NotBlank(message = "EMPTY_NICKNAME")
    @Pattern(regexp = nicknameRegex, message = "INVALID_NICKNAME")
    private String nickname;

    @NotBlank(message = "EMPTY_PASSWORD")
    @Pattern(regexp = passwordRegex, message = "INVALID_PASSWORD")
    private String password;

    @NotBlank(message = "EMPTY_PHONENUMBER")
    @Pattern(regexp = phoneNumRegex, message = "INVALID_PHONENUMBER")
    private String phoneNum;

//    private MultipartFile profileImg;
}


