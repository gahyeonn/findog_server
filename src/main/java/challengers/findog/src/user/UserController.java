package challengers.findog.src.user;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.config.BaseResponseStatus;
import challengers.findog.src.user.model.*;
import challengers.findog.utils.JwtService;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static challengers.findog.config.BaseResponseStatus.*;
import static challengers.findog.utils.ValidationRegex.isRegexEmail;
import static challengers.findog.utils.ValidationRegex.isRegexNickname;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 회원가입 API
     * @param postSignUpReq
     * @return PostSignUpRes
     */

    @PostMapping("/sign-up")
    public BaseResponse<PostSignUpRes> createUser(@Valid @ModelAttribute PostSignUpReq postSignUpReq, BindingResult br){
        if(br.hasErrors()){
            String error = br.getAllErrors().get(0).getDefaultMessage();
            return new BaseResponse<>(BaseResponseStatus.of(error));
        }

        try{
            PostSignUpRes postSignUpRes = userService.createUser(postSignUpReq);
            return new BaseResponse<>(postSignUpRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 닉네임 중복 확인 API
     * @param nickname
     * @return
     */
    @GetMapping("/chk-nickname")
    public BaseResponse<String> checkNickname(@RequestParam String nickname){
        if(nickname.equals("") || nickname == null){
            return new BaseResponse<>(EMPTY_NICKNAME);
        }

        if(!isRegexNickname(nickname)){
            return new BaseResponse<>(INVALID_NICKNAME);
        }

        try{
            if(userService.checkNickname(nickname) == 1){
                return new BaseResponse<>(DUPLICATED_NICKNAME);
            }
            return new BaseResponse<>("사용 가능한 닉네임입니다.");
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 이메일 중복 확인 API
     * @param email
     * @return
     */
    @GetMapping("/chk-email")
    public BaseResponse<String> checkEmail(@RequestParam String email){
        if(email.equals("") || email == null){
            return new BaseResponse<>(EMPTY_EMAIL);
        }

        if(!isRegexEmail(email)){
            return new BaseResponse<>(INVALID_EMAIL);
        }

        try{
            if(userService.checkEmail(email) == 1){
                return new BaseResponse<>(DUPLICATED_EMAIL);
            }
            return new BaseResponse<>("사용 가능한 이메일입니다.");
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 회원 탈퇴 API
     * @param patchLeaveReq
     * @return
     */
    @PatchMapping("/leave")
    public BaseResponse<String> updateUserStatus(@RequestBody PatchLeaveReq patchLeaveReq){
        if(patchLeaveReq.getPassword().equals("") || patchLeaveReq.getPassword() == null){
            return new BaseResponse<>(EMPTY_PASSWORD);
        }

        try{
            int userid = jwtService.getUserIdx();
            return new BaseResponse<>(userService.leaveUser(userid, patchLeaveReq));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 로그인 API
     * @param postLoginReq
     * @param br
     * @return
     */
    @PostMapping("/log-in")
    public BaseResponse<PostLoginRes> logIn(@Valid @RequestBody PostLoginReq postLoginReq, BindingResult br){
        if(br.hasErrors()){
            String error = br.getAllErrors().get(0).getDefaultMessage();
            return new BaseResponse<>(BaseResponseStatus.of(error));
        }

        try{
            PostLoginRes postLoginRes = userService.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 자동 로그인 API
     * @return
     */
    @GetMapping("/auto-logIn")
    public BaseResponse<PostLoginRes> autoLogIn(){
        try{
            int userId = jwtService.getUserIdx();
            PostLoginRes postLoginRes = userService.autoLogIn(userId, jwtService.getJwt());
            return new BaseResponse<>(postLoginRes);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/{userId}")
    public BaseResponse<GetUserRes> getUserInfo(@PathVariable("userId") int userId){
        try{
            int userIdByIdx = jwtService.getUserIdx();
            if(userIdByIdx != userId){
                throw new BaseException(INVALID_USERID);
            }
            return new BaseResponse<>(userService.getUserInfo(userId));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
