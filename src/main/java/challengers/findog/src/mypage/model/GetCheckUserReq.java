package challengers.findog.src.mypage.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GetCheckUserReq {
    @NotBlank(message = "EMPTY_EMAIL")
    public String email;

    @NotBlank(message = "EMPTY_PASSWORD")
    public String password;
}
