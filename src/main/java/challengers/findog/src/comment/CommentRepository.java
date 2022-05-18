package challengers.findog.src.comment;

import challengers.findog.src.comment.model.*;
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
                        rs.getString("commentUpdateAt"),
                        rs.getString("commentStatus")
                )), postId);
    }

    //댓글 수정
    public int modifyComment(Comment comment){
        String query = "update Comment set content = ? where commentId = ? and userId = ?";
        Object[] params = new Object[]{comment.getContent(), comment.getCommentId(), comment.getUserId()};
        return jdbcTemplate.update(query, params);
    }

    //댓글 삭제
    public int deleteComment(int commentId){
        String query = "delete from Comment where commentId = ?";
        return jdbcTemplate.update(query, commentId);
    }

    //대댓글이 있는 댓글 삭제
    public int deleteParentComment(int commentId){
        String query = "update Comment set commentStatus = 'deleted' where commentId = ?";
        return jdbcTemplate.update(query, commentId);
    }

    //대댓글 존재 확인
    public int checkParentComment(int commentId){
        String query = "select exists (select commentId from Comment where parentCommentId = ?)";
        return this.jdbcTemplate.queryForObject(query, int.class, commentId);
    }

    //내가 작성한 댓글 조회
    public List<MyCommentDto> getMyCommentList(int userId, int page, int size){
        String query = "select * from" +
                "(select commentId, content, postId, DATE_FORMAT(commentUpdateAt, '%Y.%m.%d') as date from Comment where userId = ?) C " +
                "join (select postId, title from Post) P using(postId)" +
                "natural join (select postId, count(commentId) as postCommentCount from Comment group by postId) C2 " +
                "order by date desc limit ? offset ?";
        Object[] params = new Object[]{userId, size, (page - 1) * size};
        return jdbcTemplate.query(query,
                (rs, rowNum) -> new MyCommentDto(
                        rs.getInt("commentId"),
                        rs.getString("Content"),
                        rs.getString("date"),
                        rs.getInt("postId"),
                        rs.getInt("postCommentCount"),
                        rs.getString("title")
                ), params);
    }

    //내가 작성한 댓글 개수 조회
    public int getMyCommentTotalCount(int userId) {
        String query = "select count(*) from Comment where userId = ?";
        return jdbcTemplate.queryForObject(query, int.class, userId);
    }
}
