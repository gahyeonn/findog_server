package challengers.findog.src.board.model;

import challengers.findog.src.comment.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class GetBoardRes {
    private Board board;
    private List<String> imgList;
//    private List<Comment> commentList;
    private boolean userLiked;
}
