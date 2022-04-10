package challengers.findog.src.board;

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

    // 회원정보 변경
    public int createBoard(PostBoardReq postBoardReq) {
        String query = "insert into Post(userId, title, category, content) value (?, ?, ?, ?)";
        Object[] postBoardParams = new Object[]{postBoardReq.getUserId(), postBoardReq.getTitle(), postBoardReq.getCategory(), postBoardReq.getContent()};
        this.jdbcTemplate.update(query, postBoardParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int createBoardPhoto(int postId, String imgUrl) {
        String query = "insert into Image(postId, imgUrl) value (?, ?)";
        Object[] postBoardParams = new Object[]{postId, imgUrl};

        return this.jdbcTemplate.update(query, postBoardParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }
}
