package challengers.findog.src.mypage.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static challengers.findog.config.Constant.phoneNumRegex;

@Data
public class PatchPhoneNumReq {
    @NotBlank(message = "EMPTY_PHONENUMBER")
    @Pattern(regexp = phoneNumRegex, message = "INVALID_PHONENUMBER")
    public String phoneNum;
}

