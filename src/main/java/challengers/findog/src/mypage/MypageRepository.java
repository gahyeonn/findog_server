package challengers.findog.src.mypage;

import challengers.findog.src.mypage.model.PatchNicknameReq;
import challengers.findog.src.mypage.model.PatchPasswordReq;
import challengers.findog.src.mypage.model.PatchPhoneNumReq;
import challengers.findog.src.mypage.model.PatchProfileImgReq;
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

    //닉네임 수정
    public int modifyNickname(PatchNicknameReq patchNicknameReq, int userId){
        String query = "update User set nickname = ? where userId = ?";
        Object[] params = new Object[]{patchNicknameReq.getNickname(), userId};
        return jdbcTemplate.update(query, params);
    }

    //핸드폰 번호 수정
    public int modifyPhoneNum(PatchPhoneNumReq patchPhoneNumReq, int userId){
        String query = "update User set phoneNum = ? where userId = ?";
        Object[] params = new Object[]{patchPhoneNumReq.getPhoneNum(), userId};
        return jdbcTemplate.update(query, params);
    }

    //비밀번호 수정
    public int modifyPassword(PatchPasswordReq patchPasswordReq, int userId){
        String query = "update User set password = ? where userId = ?";
        Object[] params = new Object[]{patchPasswordReq.getNewPassword(), userId};
        return jdbcTemplate.update(query, params);
    }

    //프로필 이미지 삭제
    public int deleteProfileImg(int userId){
        String query = "update User set profileUrl = null where userId = ?";
        return jdbcTemplate.update(query, userId);
    }

    //프로필 이미지 수정
    public int modifyProfileImg(String profileImgUrl, int userId){
        String query = "update User set profileUrl = ? where userId = ?";
        Object[] params = new Object[]{profileImgUrl, userId};
        return jdbcTemplate.update(query, params);
    }
}
