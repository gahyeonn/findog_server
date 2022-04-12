package challengers.findog.src.board;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.config.BaseResponseStatus;
import challengers.findog.src.board.model.PatchBoardReq;
import challengers.findog.src.board.model.PostBoardReq;
import challengers.findog.src.board.model.PostBoardRes;
import challengers.findog.src.mypage.MypageService;
import challengers.findog.src.mypage.model.PatchUserInfoReq;
import challengers.findog.src.user.model.PatchLeaveReq;
import challengers.findog.src.user.model.PostSignUpRes;
import challengers.findog.src.user.model.User;
import challengers.findog.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Patch;
import javax.validation.Valid;

import static challengers.findog.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;
    private final JwtService jwtService;

    /**
     * 게시글 작성 API
     *
     * @return PostBoardRes
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

    /**
     * 게시글 수정 API
     *
     * @param :postId
     * @return 수정완료 메세지
     */
    @PatchMapping("/update/{postId}")
    public BaseResponse<String> updateBoard(@PathVariable("postId") int postId, @Valid @ModelAttribute PatchBoardReq patchBoardReq, BindingResult br) {
        if (br.hasErrors()) {
            String error = br.getAllErrors().get(0).getDefaultMessage();
            return new BaseResponse<>(BaseResponseStatus.of(error));
        }

        try {
            int userIdxByJwt = jwtService.getUserIdx();
            boardService.updateBoard(userIdxByJwt, postId, patchBoardReq);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        String result = "회원정보가 수정되었습니다.";
        return new BaseResponse<>(result);

    }
}