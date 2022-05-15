package challengers.findog.src.mypage;

import challengers.findog.src.mypage.model.PatchNicknameReq;
import challengers.findog.src.mypage.model.PatchPhoneNumReq;
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

    public int modifyNickname(PatchNicknameReq patchNicknameReq, int userId){
        String query = "update User set nickname = ? where userId = ?";
        Object[] params = new Object[]{patchNicknameReq.getNickname(), userId};
        return jdbcTemplate.update(query, params);
    }

    public int modifyPhoneNum(PatchPhoneNumReq patchPhoneNumReq, int userId){
        String query = "update User set phoneNum = ? where userId = ?";
        Object[] params = new Object[]{patchPhoneNumReq.getPhoneNum(), userId};
        return jdbcTemplate.update(query, params);
    }
}
