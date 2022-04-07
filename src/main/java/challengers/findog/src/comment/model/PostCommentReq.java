package challengers.findog.src.comment.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class PostCommentReq {
    @ApiModelProperty(value = "대댓글을 달 댓글 Id", notes = "대댓글이 아닌 경우 0 입력", required = true)
    private int parentCommentId;

    @ApiModelProperty(value = "게시글 Id", required = true)
    @Positive(message = "INVALID_POSTID")
    private int postId;

    @ApiModelProperty(value = "댓글 내용", required = true)
    @NotBlank(message = "EMPTY_COMMENT")
    private String content;
}
