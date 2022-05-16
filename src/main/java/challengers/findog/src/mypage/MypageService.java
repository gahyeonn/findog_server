package challengers.findog.src.mypage;

import challengers.findog.config.BaseException;
import challengers.findog.config.secret.Secret;
import challengers.findog.src.mypage.model.*;
import challengers.findog.src.user.UserRepository;
import challengers.findog.src.user.model.User;
import challengers.findog.utils.AES128;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static challengers.findog.config.BaseResponseStatus.*;


@RequiredArgsConstructor
@Service
public class MypageService {
    private final MypageRepository mypageRepository;
    private final UserRepository userRepository;

    //닉네임 수정
    public String modifyNickname(PatchNicknameReq patchNicknameReq, int userId) throws BaseException{
        int result;
        try{
            result = mypageRepository.modifyNickname(patchNicknameReq, userId);
        }  catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }

        if(result == 0){
            throw new BaseException(FAIL_MODIFY_NICKNAME);
        }
        return "닉네임을 성공적으로 수정하였습니다.";
    }

    //폰번호 수정
    public String modifyPhoneNum(PatchPhoneNumReq patchPhoneNumReq, int userId) throws BaseException{
        int result;
        try{
            result = mypageRepository.modifyPhoneNum(patchPhoneNumReq, userId);
        }  catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }

        if(result == 0){
            throw new BaseException(FAIL_MODIFY_PHONENUM);
        }
        return "연락처를 성공적으로 수정하였습니다.";
    }

    //비밀번호 수정
    public String modifyPassword(PatchPasswordReq patchPasswordReq, int userId) throws BaseException{
        int result;

        try{
            String pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(patchPasswordReq.getNewPassword());
            patchPasswordReq.setNewPassword(pwd);
        } catch (Exception e){
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        try{
            result = mypageRepository.modifyPassword(patchPasswordReq, userId);
        }  catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }

        if(result == 0){
            throw new BaseException(FAIL_MODIFY_PASSWORD);
        }
        return "비밀번호를 성공적으로 수정하였습니다.";
    }

    //비밀번호 수정 전 유저 정보 확인
    public String checkLogInInfo(GetCheckUserReq getCheckUserReq, int userId) throws BaseException{
        User user;
        String pwd;
        try{
            user = userRepository.getUser(userId);
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
        } catch (Exception e){
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        if(!(user.getEmail().equals(getCheckUserReq.getEmail()) && pwd.equals(getCheckUserReq.getPassword()))){
            throw new BaseException(NOT_EXISTS_USER);
        }

        return "일치하는 회원 정보입니다.";
    }
}
