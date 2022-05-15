package challengers.findog.src.mypage;

import challengers.findog.config.BaseException;
import challengers.findog.src.mypage.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static challengers.findog.config.BaseResponseStatus.DATABASE_ERROR;
import static challengers.findog.config.BaseResponseStatus.FAIL_MODIFY_NICKNAME;


@RequiredArgsConstructor
@Service
public class MypageService {
    private final MypageRepository mypageRepository;

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
            throw new BaseException(FAIL_MODIFY_NICKNAME);
        }
        return "연락처를 성공적으로 수정하였습니다.";
    }

}
