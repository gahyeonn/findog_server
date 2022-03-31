package challengers.findog.src.user.model;

import lombok.Data;

@Data
public class PatchLeaveReq {
    private int userId;
    private String password;
}
