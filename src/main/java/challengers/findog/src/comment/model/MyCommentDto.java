package challengers.findog.src.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyCommentDto {
    private int commentId;
    private String commentContent;
    private String date;

    private int postId;
    private int postCommentCount;
    private String postTitle;
}
