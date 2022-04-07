package challengers.findog.src.comment;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.config.BaseResponseStatus;
import challengers.findog.src.comment.model.Comment;
import challengers.findog.src.comment.model.GetCommentRes;
import challengers.findog.src.comment.model.PostCommentReq;
import challengers.findog.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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
}
