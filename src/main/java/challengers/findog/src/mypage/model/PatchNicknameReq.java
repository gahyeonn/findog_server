package challengers.findog.src.mypage.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static challengers.findog.config.Constant.nicknameRegex;

@Data
public class PatchNicknameReq {
    @NotBlank(message = "EMPTY_NICKNAME")
    @Pattern(regexp = nicknameRegex, message = "INVALID_NICKNAME")
    private String nickname;
}
