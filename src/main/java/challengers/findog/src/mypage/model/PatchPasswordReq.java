package challengers.findog.src.mypage.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static challengers.findog.config.Constant.passwordRegex;

@Data
public class PatchPasswordReq {
    @NotBlank(message = "EMPTY_PASSWORD")
    @Pattern(regexp = passwordRegex, message = "INVALID_PASSWORD")
    public String newPassword;
}
