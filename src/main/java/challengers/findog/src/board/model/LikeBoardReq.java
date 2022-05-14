package challengers.findog.src.board.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LikeBoardReq {
    @ApiModelProperty(value = "postId", example = "1", required = true)
    private int postId;
}
