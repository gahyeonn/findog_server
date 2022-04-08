package challengers.findog.src.comment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    private int commentId;
    private int parentCommentId;
    private int userId;
    private int postId;
    private String content;
    private Timestamp commentCreateAt;
    private Timestamp commentUpdateAt;
}
