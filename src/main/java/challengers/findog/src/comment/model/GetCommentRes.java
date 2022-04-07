package challengers.findog.src.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class GetCommentRes {
    private int commentId;
    private int parentCommentId;
    private int userId;
    private String nickname;
    private String profileImgUrl;
    private int postId;
    private String content;
    private String commentUpdateAt;
}
