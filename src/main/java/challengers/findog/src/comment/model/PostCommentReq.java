package challengers.findog.src.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class PostCommentReq {
    private int parentCommentId;

    @Positive(message = "INVALID_POSTID")
    private int postId;

    @NotBlank(message = "EMPTY_COMMENT")
    private String content;

//    public PostCommentReq(int postId, String content){
//        this.postId = postId;
//        this.content = content;
//    }
}
