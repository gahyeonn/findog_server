package challengers.findog.src.comment.model;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    @ApiModelProperty(value = "commentId", example = "1", required = true)
    private int commentId;

    @ApiModelProperty(hidden = true)
    private int parentCommentId;

    @ApiModelProperty(value = "userId", example = "1", required = true)
    private int userId;

    @ApiModelProperty(value = "postId", example = "1", required = true)
    private int postId;

    @ApiModelProperty(value = "댓글 내용", example = "댓글입니다.", required = true)
    private String content;

    @ApiModelProperty(hidden = true)
    private String commentStatus;

    @ApiModelProperty(hidden = true)
    private Timestamp commentCreateAt;

    @ApiModelProperty(hidden = true)
    private Timestamp commentUpdateAt;


    public Comment(int commentId, int userId){
        this.commentId = commentId;
        this.userId = userId;
    }
}
