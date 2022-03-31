package challengers.findog.src.user;

import challengers.findog.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //닉네임 중복 검사
    public int checkNickname(String nickname){
        String query = "select exists(select userId from User where nickname = ? and userStatus='active')";
        return this.jdbcTemplate.queryForObject(query, int.class, nickname);
    }

    //이메일 중복 검사
    public int checkEmail(String email){
        String query = "select exists(select userId from User where email = ? and userStatus='active')";
        return this.jdbcTemplate.queryForObject(query, int.class, email);
    }

    //회원가입
    public int createUser(User user){
        String query = "insert into User(email, nickname, password, phoneNum, ProfileUrl) value (?, ?, ?, ?, ?)";
        Object[] params = new Object[]{user.getEmail(), user.getNickname(), user.getPassword(), user.getPhoneNum(), user.getProfileUrl()};
        this.jdbcTemplate.update(query, params);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    //유저 정보 조회
    public User getUser(int userId){
        String query = "select * from User where userId = ?";
        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new User(
                        rs.getInt("userId"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("password"),
                        rs.getString("phoneNum"),
                        rs.getString("profileUrl"),
                        rs.getString("userStatus")
                ), userId);
    }

    //회원탈퇴
    public int leaveUser(int userId){
        String query = "update User set userStatus = 'inactive' where userId = ?";
        return this.jdbcTemplate.update(query, userId);
    }
}
