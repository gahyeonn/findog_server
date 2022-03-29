package challengers.findog.src.user;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.config.BaseResponseStatus;
import challengers.findog.config.secret.Secret;
import challengers.findog.src.user.model.PostSignUpReq;
import challengers.findog.src.user.model.PostSignUpRes;
import challengers.findog.src.user.model.User;
import challengers.findog.utils.AES128;
import challengers.findog.utils.JwtService;
import challengers.findog.utils.s3Component.FileControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static challengers.findog.config.BaseResponseStatus.*;
import static challengers.findog.utils.ValidationRegex.isRegexImage;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileControlService fileControlService;
    private final JwtService jwtService;

    @Transactional(rollbackFor = Exception.class)
    public PostSignUpRes createUser(PostSignUpReq postSignUpReq) throws BaseException {
        if(userRepository.checkEmail(postSignUpReq.getEmail()) == 1){
            throw new BaseException(DUPLICATED_EMAIL);
        }

        if(userRepository.checkNickname(postSignUpReq.getNickname()) == 1){
            throw new BaseException(DUPLICATED_NICKNAME);
        }

        User user;
        try{
            String pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postSignUpReq.getPassword());
            user = new User(postSignUpReq.getEmail(), postSignUpReq.getNickname(), pwd, postSignUpReq.getPhoneNum(), null);
        } catch (Exception e){
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        if(!postSignUpReq.getProfileImg().isEmpty() && postSignUpReq.getProfileImg() != null){
            if(!isRegexImage(postSignUpReq.getProfileImg().getOriginalFilename())){
                throw new BaseException(INVALID_IMAGEFILEEXTENTION);
            }
            String imgUrl = fileControlService.uploadImage(postSignUpReq.getProfileImg());
            user.setProfileUrl(imgUrl);
        }

        try{
            int userId = userRepository.createUser(user);
            String userJwt = jwtService.createJwt(userId);
            return new PostSignUpRes(userId, userJwt);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //닉네임 중복 검사
    public int checkNickname(String nickname) throws BaseException{
        try{
             return userRepository.checkNickname(nickname);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
