package challengers.findog.src.board;

import challengers.findog.src.board.model.Board;
import challengers.findog.src.board.model.GetBoardRes;
import challengers.findog.src.board.model.PatchBoardReq;
import challengers.findog.src.board.model.PostBoardReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class BoardRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //게시글 작성
    public int createBoard(PostBoardReq postBoardReq) {
        String query = "insert into Post(userId, title, category, content) value (?, ?, ?, ?)";
        Object[] postBoardParams = new Object[]{postBoardReq.getUserId(), postBoardReq.getTitle(), postBoardReq.getCategory(), postBoardReq.getContent()};
        this.jdbcTemplate.update(query, postBoardParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    //게시글 작성 - 게시글 사진 저장
    public int createBoardPhoto(int postId, String imgUrl) {
        String query = "insert into Image(postId, imgUrl) value (?, ?)";
        Object[] postBoardParams = new Object[]{postId, imgUrl};

        return this.jdbcTemplate.update(query, postBoardParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    //게시글 수정
    public int updateBoard(int userId, int postId, PatchBoardReq patchBoardReq) {
        String query = "update Post set title = ?, category = ?, content = ? where postId = ? and userId = ? ";
        Object[] postBoardParams = new Object[]{patchBoardReq.getTitle(), patchBoardReq.getCategory(), patchBoardReq.getContent(), postId, userId};
        this.jdbcTemplate.update(query, postBoardParams);

        return this.jdbcTemplate.update(query, postBoardParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    //게시글 수정 - 게시글 사진 유무 확인
    public List<String> checkImg(int postId) {
        String query = "select imgUrl FROM Image WHERE postId = ?";
        return this.jdbcTemplate.queryForList(query, String.class, postId);
    }

    //게시글 수정/삭제 - 게시글 사진 삭제
    public int deleteImg(int postId) {
        String query = "delete FROM Image WHERE postId = ?";
        return this.jdbcTemplate.update(query, postId);
    }

    //게시글 삭제
    public int deleteBoard(int postId) {
        String query = "delete FROM Post WHERE postId = ?";
        return this.jdbcTemplate.update(query, postId);
    }

    //해당 게시글 조회
    public Board getBoard(int postId) {
        String query = "select P.userId, nickname, profileUrl, title, category, P.content, postCreateAt, likeCount, commentCount " +
                "from Post P left join User U on P.userId = U.userId " +
                "left join (SELECT postId, Count(commentId) as commentCount FROM Comment GROUP BY postId) C on C.postId = P.postId " +
                "left join (SELECT postId, Count(likeId) as likeCount FROM `Like` GROUP BY postId) L on L.postId = P.postId where P.postId =?";
        return jdbcTemplate.queryForObject(query,
                ((rs, rowNum) -> new Board(
                        rs.getInt("userId"),
                        rs.getString("nickname"),
                        rs.getString("profileUrl"),
                        rs.getString("title"),
                        rs.getInt("category"),
                        rs.getString("content"),
                        rs.getTimestamp("postCreateAt"),
                        rs.getInt("likeCount"),
                        rs.getInt("commentCount")
                )), postId);
    }

    //해당 게시글 사진 조회
    public List<String> getBoardImage(int postId) {
        String query = "select imgUrl from Image inner join Post P on Image.postId = P.postId where P.postId = ?";
        return this.jdbcTemplate.queryForList(query, String.class, postId);
    }

    //게시글 조회수+1
    public int viewCount(int postId) {
        String query = "update Post set hits = hits + 1 where postId=?";
        return this.jdbcTemplate.update(query, postId);
    }

    //게시글 좋아요 여부
    public int userLiked(int postId, int userId) {
        String query = "select exists(select likeId from `Like` where postId = ? and userId = ?)";
        return this.jdbcTemplate.queryForObject(query, int.class, postId, userId);
    }
}
