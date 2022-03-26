package challengers.findog.src.user;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.config.BaseResponseStatus;
import challengers.findog.src.user.model.PostSignUpReq;
import challengers.findog.src.user.model.PostSignUpRes;
import challengers.findog.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

}
