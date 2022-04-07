package challengers.findog.src.comment;

import challengers.findog.src.comment.model.Comment;
import challengers.findog.src.comment.model.GetCommentRes;
import challengers.findog.src.comment.model.PostCommentReq;
import challengers.findog.src.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CommentRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //댓글 등록
    public int createComment(int userId, PostCommentReq postCommentReq){
        String query = "insert into Comment(parentCommentId, userId, postId, content) values(?,?,?,?)";
        Object[] params = new Object[]{postCommentReq.getParentCommentId(), userId, postCommentReq.getPostId(), postCommentReq.getContent()};
        return jdbcTemplate.update(query, params);
    }

    //댓글 조회
    public List<GetCommentRes> getCommentList(int postId){
        String query = "select * from Comment natural join User where postId = ?";
        return jdbcTemplate.query(query,
                ((rs, rowNum) -> new GetCommentRes(
                        rs.getInt("commentId"),
                        rs.getInt("parentCommentId"),
                        rs.getInt("userId"),
                        rs.getString("nickname"),
                        rs.getString("profileUrl"),
                        rs.getInt("postId"),
                        rs.getString("content"),
                        rs.getString("commentUpdateAt")
                )), postId);
    }
}
