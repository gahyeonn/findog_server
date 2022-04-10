package challengers.findog.src.board;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.config.BaseResponseStatus;
import challengers.findog.src.board.model.PostBoardReq;
import challengers.findog.src.board.model.PostBoardRes;
import challengers.findog.src.mypage.MypageService;
import challengers.findog.src.mypage.model.PatchUserInfoReq;
import challengers.findog.src.user.model.PostSignUpRes;
import challengers.findog.src.user.model.User;
import challengers.findog.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static challengers.findog.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    /**
     * 게시글작성 API
     *
     * @return
     */
    @PostMapping("/post")
    public BaseResponse<PostBoardRes> createBoard(@Valid @ModelAttribute PostBoardReq postBoardReq, BindingResult br) {
        if (br.hasErrors()) {
            String error = br.getAllErrors().get(0).getDefaultMessage();
            return new BaseResponse<>(BaseResponseStatus.of(error));
        }

        try {
            PostBoardRes postBoardRes = boardService.createBoard(postBoardReq);
            return new BaseResponse<>(postBoardRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}