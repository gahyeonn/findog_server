package challengers.findog.src.mypage;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.src.mypage.model.PatchUserInfoReq;
import challengers.findog.src.user.model.User;
import challengers.findog.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static challengers.findog.config.BaseResponseStatus.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final JwtService jwtService;

    /**
     * 내정보수정 API
     *
     * @param
     * @return
     */
    @PatchMapping("/{userId}")
    public BaseResponse<String> updateUserInfo(@PathVariable("userId") int userId, @ModelAttribute PatchUserInfoReq patchUserInfoReq) {
        //jwt에서 idx 추출
        int userIdxByJwt = 0;
        try {
            userIdxByJwt = jwtService.getUserIdx();
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
        //userIdx와 접근한 유저가 같은지 확인
        if (userId != userIdxByJwt) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        //같다면 유저정보 변경
        User user = new User(userId, patchUserInfoReq.getNickname(), patchUserInfoReq.getPassword(), patchUserInfoReq.getPhoneNum());
        try {
            mypageService.updateUserInfo(user);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        String result = "회원정보가 수정되었습니다.";
        return new BaseResponse<>(result);
    }
}
