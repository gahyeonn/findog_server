package challengers.findog.src.board.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Board {
    @ApiModelProperty(value = "userId", example = "1", required = true)
    private int userId;
    private String nickname;
    private String userImgUrl;
    private String title;
    private int category;
    @ApiModelProperty(value = "게시글 내용", example = "내용입니다.", required = true)
    private String content;
    @ApiModelProperty(hidden = true)
    private Timestamp postCreateAt;
    private int likeCount;
    private int commentCount;

    public Board(int userId, String nickname, String userImgUrl, String title, int category, String content,
                 Timestamp postCreateAt, int likeCount, int commentCount){
        this.userId = userId;
        this.nickname = nickname;
        this.userImgUrl = userImgUrl;
        this.title = title;
        this.category = category;
        this.content = content;
        this.postCreateAt = postCreateAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;

    }
}
