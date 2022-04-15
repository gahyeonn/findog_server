package challengers.findog.src.comment;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.config.BaseResponseStatus;
import challengers.findog.src.comment.model.Comment;
import challengers.findog.src.comment.model.DeleteCommentRes;
import challengers.findog.src.comment.model.GetCommentRes;
import challengers.findog.src.comment.model.PostCommentReq;
import challengers.findog.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static challengers.findog.config.BaseResponseStatus.INVALID_USER_JWT;

@Api(tags = "Comment")
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final JwtService jwtService;

    /**
     * 댓글 & 대댓글 등록 API
     * @param postCommentReq
     * @param br
     * @return
     */
    @ApiOperation(value = "댓글 & 대댓글 등록", notes = "댓글의 경우 parentCommentId = 0, 대댓글의 경우 parentCommentId를 대댓글을 달 댓글의 id로 설정. JWT 토큰 필수\n Return :  게시글의 댓글 리스트")
    @PostMapping("")
    public BaseResponse<List<GetCommentRes>> createComment(@Valid @RequestBody PostCommentReq postCommentReq, BindingResult br){
        if(br.hasErrors()){
            String errorName = br.getAllErrors().get(0).getDefaultMessage();
            return new BaseResponse<>(BaseResponseStatus.of(errorName));
        }

        try{
            int userId = jwtService.getUserIdx();
            List<GetCommentRes> commentList = commentService.createComment(userId ,postCommentReq);
            return new BaseResponse<>(commentList);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 댓글 조회 기능 API
     * @param postId
     * @return
     */
    @ApiOperation(value = "댓글 조회", notes = "해당 게시글의 모든 댓글 조회")
    @ApiImplicitParam(name = "postId", value = "게시글 ID", required = true, dataType = "int")
    @GetMapping("")
    public BaseResponse<List<GetCommentRes>> getCommentList(@RequestParam int postId){
        try{
            List<GetCommentRes> commentList = commentService.getCommentList(postId);
            return new BaseResponse<>(commentList);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 댓글 수정 API
     * @param comment
     * @return
     */
    @ApiOperation(value = "댓글 수정", notes = "JWT token 필요")
    @PatchMapping("")
    public BaseResponse<List<GetCommentRes>> modifyComment(@RequestBody Comment comment){
        try{
            int userId = jwtService.getUserIdx();
            if(userId != comment.getUserId()){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetCommentRes> commentList = commentService.modifyComment(comment);
            return new BaseResponse<>(commentList);

        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 댓글 삭제 API
     * 대댓글이 있는 댓글인 경우는 삭제하지 않고 commentStatus = 'deleted'로 변경
     * @param postId
     * @param deleteCommentRes
     * @return
     */
    @ApiOperation(value = "댓글 삭제", notes = "JWT token 필요 , 대댓글이 있는 댓글은 commentStatus = 'deleted'로 변경")
    @ApiImplicitParam(name = "postId", value = "게시글 ID", required = true, dataType = "int", paramType = "path")
    @DeleteMapping("/{postId}")
    public BaseResponse<List<GetCommentRes>> deleteComment(@PathVariable("postId") int postId, @RequestBody DeleteCommentRes deleteCommentRes) {
        try {
            int userId = jwtService.getUserIdx();
            if (userId != deleteCommentRes.getUserId()) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetCommentRes> commentList = commentService.deleteComment(postId, deleteCommentRes);
            return new BaseResponse<>(commentList);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
