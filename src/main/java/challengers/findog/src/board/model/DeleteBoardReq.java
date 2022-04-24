package challengers.findog.src.board.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteBoardReq {
    @ApiModelProperty(value = "userId", example = "1", required = true)
    private int userId;
}
