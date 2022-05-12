package challengers.findog.src.board;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.config.BaseResponseStatus;
import challengers.findog.src.board.model.*;
import challengers.findog.src.comment.model.GetCommentRes;
import challengers.findog.utils.JwtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

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
    @ApiOperation(value = "게시글 작성", notes = "body를 포함해서 post request를 보내면 postId와 userId를 리턴")
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
    @ApiOperation(value = "게시글 수정", notes = "body값을 모두 포함해서 request 보내야함")
    @ApiImplicitParam(name = "postId", value = "게시글 ID", required = true, dataType = "int")
    @PatchMapping("/update/{postId}")
    public BaseResponse<String> updateBoard(@PathVariable("postId") int postId, @Valid @ModelAttribute PatchBoardReq patchBoardReq, BindingResult br) {
        if (br.hasErrors()) {
            String error = br.getAllErrors().get(0).getDefaultMessage();
            return new BaseResponse<>(BaseResponseStatus.of(error));
        }
        try {
            int userId = jwtService.getUserIdx();
            if (userId != patchBoardReq.getUserId()) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            boardService.updateBoard(userId, postId, patchBoardReq);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        String result = "게시글이 수정되었습니다.";
        return new BaseResponse<>(result);
    }

    /**
     * 게시글 삭제 API
     *
     * @param :postId
     * @return 삭제완료 메세지
     */
    @ApiOperation(value = "게시글 삭제", notes = "유저 검증 후 존재하는 게시글에 대해서 해당 게시글, 이미지, 댓글을 모두 삭제")
    @ApiImplicitParam(name = "postId", value = "게시글 ID", required = true, dataType = "int")
    @DeleteMapping("/{postId}")
    public BaseResponse<String> deleteBoard(@PathVariable("postId") int postId, @Valid @ModelAttribute DeleteBoardReq deleteBoardReq) {
        try {
            //유저 권한 검증
            int userId = jwtService.getUserIdx();
            if (userId != deleteBoardReq.getUserId()) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //게시글 삭제
            boardService.deleteBoard(postId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        String result = "게시글이 삭제되었습니다.";
        return new BaseResponse<>(result);
    }

    /**
     * 해당 게시글 조회 API
     *
     * @param :postId
     * @return getBoardRes
     */
    @ApiOperation(value = "해당 게시글 조회", notes = "해당 게시글을 포함한 이미지, 댓글을 모두 조회")
    @ApiImplicitParam(name = "postId", value = "게시글 ID", required = true, dataType = "int")
    @GetMapping("/{postId}")
    public BaseResponse<GetBoardRes> getBoard(@PathVariable("postId") int postId) {
        try {
            int userId = jwtService.getUserIdx();
            GetBoardRes getBoardRes = boardService.getBoard(postId, userId);
            return new BaseResponse<>(getBoardRes);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 전체 게시글 조회 API
     *
     * @return boardList
     */
    @ApiOperation(value = "게시물 조회", notes = "페이징 처리")
    @GetMapping("")
    public BaseResponse<List<Board>> getBoardList(){
        try{
            List<Board> boardList = boardService.getBoardList();
            return new BaseResponse<>(boardList);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}