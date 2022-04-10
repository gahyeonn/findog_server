package challengers.findog.src.comment.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteCommentRes {
    @ApiModelProperty(value = "commentId", example = "1", required = true)
    private int commentId;

    @ApiModelProperty(value = "userId", example = "1", required = true)
    private int userId;
}
