package challengers.findog.src.board;

import challengers.findog.src.board.model.PatchBoardReq;
import challengers.findog.src.board.model.PostBoardReq;
import challengers.findog.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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

    //게시글 사진 저장
    public int createBoardPhoto(int postId, String imgUrl) {
        String query = "insert into Image(postId, imgUrl) value (?, ?)";
        Object[] postBoardParams = new Object[]{postId, imgUrl};

        return this.jdbcTemplate.update(query, postBoardParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    public int updateBoard(int userId, int postId, PatchBoardReq patchBoardReq) {
        String query = "update Post set title = ?, category = ?, content = ? where postId = ? and userId = ? ";
        Object[] postBoardParams = new Object[]{patchBoardReq.getTitle(), patchBoardReq.getCategory(), patchBoardReq.getContent(), postId, userId};
        this.jdbcTemplate.update(query, postBoardParams);

        return this.jdbcTemplate.update(query, postBoardParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }
}
