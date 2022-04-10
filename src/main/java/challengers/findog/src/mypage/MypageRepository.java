package challengers.findog.src.mypage;

import challengers.findog.src.mypage.model.PatchUserInfoReq;
import challengers.findog.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class MypageRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 회원정보 변경
    public int updateUserInfo(User user) {
        String query = "update User set nickname = ?, password = ?, phoneNum = ? where userId = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyUserParams = new Object[]{user.getNickname(), user.getPassword(), user.getPhoneNum(), user.getUserId()};

        return this.jdbcTemplate.update(query, modifyUserParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }
}
