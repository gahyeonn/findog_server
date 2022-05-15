package challengers.findog.src.mypage;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.config.BaseResponseStatus;
import challengers.findog.src.mypage.model.PatchNicknameReq;
import challengers.findog.src.mypage.model.PatchPasswordReq;
import challengers.findog.src.mypage.model.PatchPhoneNumReq;
import challengers.findog.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final JwtService jwtService;

    /**
     * 닉네임 수정 API
     * @param patchNicknameReq
     * @param br
     * @return
     */
    @PatchMapping("/myInfo/nickname")
    public BaseResponse<String> modifyNickname(@Valid @RequestBody PatchNicknameReq patchNicknameReq, BindingResult br){
        if(br.hasErrors()){
            String error = br.getAllErrors().get(0).getDefaultMessage();
            return new BaseResponse<>(BaseResponseStatus.of(error));
        }

        try{
            int userId = jwtService.getUserIdx();
            return new BaseResponse<>(mypageService.modifyNickname(patchNicknameReq, userId));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 핸드폰 번호 수정 API
     * @param patchPhoneNumReq
     * @param br
     * @return
     */
    @PatchMapping("/myInfo/phoneNum")
    public BaseResponse<String> modifyPhoneNum(@Valid @RequestBody PatchPhoneNumReq patchPhoneNumReq, BindingResult br){
        if(br.hasErrors()){
            String error = br.getAllErrors().get(0).getDefaultMessage();
            return new BaseResponse<>(BaseResponseStatus.of(error));
        }

        try{
            int userId = jwtService.getUserIdx();
            return new BaseResponse<>(mypageService.modifyPhoneNum(patchPhoneNumReq, userId));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 비밀번호 수정 API
     * @param patchPasswordReq
     * @param br
     * @return
     */
    @PatchMapping("/myInfo/password")
    public BaseResponse<String> modifyPassword(@Valid @RequestBody PatchPasswordReq patchPasswordReq, BindingResult br){
        if(br.hasErrors()){
            String error = br.getAllErrors().get(0).getDefaultMessage();
            return new BaseResponse<>(BaseResponseStatus.of(error));
        }

        try{
            int userId = jwtService.getUserIdx();
            return new BaseResponse<>(mypageService.modifyPassword(patchPasswordReq, userId));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
