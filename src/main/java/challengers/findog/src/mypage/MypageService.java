package challengers.findog.src.mypage;

import challengers.findog.config.BaseException;
import challengers.findog.config.secret.Secret;
import challengers.findog.src.mypage.model.*;
import challengers.findog.src.user.model.User;
import challengers.findog.utils.AES128;
import challengers.findog.utils.JwtService;
import challengers.findog.utils.s3Component.FileControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static challengers.findog.config.BaseResponseStatus.*;
import static challengers.findog.utils.ValidationRegex.isRegexImage;

@RequiredArgsConstructor
@Service
public class MypageService {
    private final MypageRepository mypageRepository;
    private final JwtService jwtService;

    public void updateUserInfo(User user) throws BaseException {
        try {
            //비밀번호 암호화
            String pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(user.getPassword());
            user.setPassword(pwd);

            int result = mypageRepository.updateUserInfo(user);
            if(result == 0) {
                throw new BaseException(FAIL_UPDATE_USER_INFO);
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
