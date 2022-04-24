package challengers.findog.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class GetBoardRes {
    private int userId;
    private String title;
    private int category;
    private String content;
    private List<String> imgUrl;
    private String postCreateAt;
    private int likeCount;
    private int hits;

    public GetBoardRes(int userId, String title, int category, String content, String postCreateAt, int likeCount, int hits) {
        this.userId = userId;
        this.title = title;
        this.category = category;
        this.content = content;
        this.postCreateAt = postCreateAt;
        this.likeCount = likeCount;
        this.hits = hits;
    }

    //todo 댓글부분은 댓글에서 따로 할것인가, 그렇다면 commentList와 commentCount도 가져와야함.
}
