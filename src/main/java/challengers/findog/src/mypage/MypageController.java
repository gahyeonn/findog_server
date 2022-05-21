package challengers.findog.src.mypage;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.config.BaseResponseStatus;
import challengers.findog.src.board.model.Board;
import challengers.findog.src.mypage.model.*;
import challengers.findog.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static challengers.findog.config.BaseResponseStatus.EMPTY_EMAIL;
import static challengers.findog.config.BaseResponseStatus.EMPTY_PASSWORD;

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

    /**
     * 비밀번호 수정 전 유저 확인 API
     * @param getCheckUserReq
     * @return
     */
    @PostMapping("/myInfo/checkUser")
    public BaseResponse<String> checkUserForPassword(@Valid @RequestBody GetCheckUserReq getCheckUserReq, BindingResult br){
        if(br.hasErrors()){
            String error = br.getAllErrors().get(0).getDefaultMessage();
            return new BaseResponse<>(BaseResponseStatus.of(error));
        }

        try{
            int userId = jwtService.getUserIdx();
            return new BaseResponse<>(mypageService.checkLogInInfo(getCheckUserReq, userId));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 프로필 이미지 수정 API
     * @param patchProfileImgReq
     * @return
     */
    @PatchMapping("/myInfo/profileImg")
    public BaseResponse<String> modifyProfileImg(@ModelAttribute PatchProfileImgReq patchProfileImgReq) {
        try{
            int userId = jwtService.getUserIdx();
            return new BaseResponse<>(mypageService.modifyProfileImg(patchProfileImgReq, userId));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 내가 작성한 글 조회 API
     *
     * @return boardList
     */
    @ApiOperation(value = "내가 작성한 글 조회", notes = "페이징 처리, (default)page=1, size=15")
    @GetMapping("/board")
    public BaseResponse<List<Board>> getMyWriteBoardList(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value="size", defaultValue = "15") int size){
        try{
            int userId = jwtService.getUserIdx();
            List<Board> boardList = mypageService.getMyWriteBoardList(userId, page, size);
            return new BaseResponse<>(boardList);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 내가 작성한 글 총 게시글 수 조회 API
     *
     * @return count
     */
    @ApiOperation(value = "내가 작성한 글 총 게시글 수 조회")
    @GetMapping("/board/count")
    public BaseResponse<Integer> getMyWriteBoardCount(){
        try{
            return new BaseResponse<>(mypageService.getMyWriteBoardCount());
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 내가 좋아요한 글 조회 API
     *
     * @return boardList
     */
    @ApiOperation(value = "내가 좋아요한 글 조회", notes = "페이징 처리, (default)page=1, size=15")
    @GetMapping("/like")
    public BaseResponse<List<Board>> getMyLikeBoardList(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value="size", defaultValue = "15") int size){
        try{
            int userId = jwtService.getUserIdx();
            List<Board> boardList = mypageService.getMyLikeBoardList(userId, page, size);
            return new BaseResponse<>(boardList);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 내가 좋아요한 글 총 게시글 수 조회 API
     *
     * @return count
     */
    @ApiOperation(value = "내가 좋아요한 글 총 게시글 수 조회")
    @GetMapping("/like/count")
    public BaseResponse<Integer> getMyLikeBoardCount(){
        try{
            return new BaseResponse<>(mypageService.getMyLikeBoardCount());
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
