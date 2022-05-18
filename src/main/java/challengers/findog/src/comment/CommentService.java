package challengers.findog.src.comment;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponseStatus;
import challengers.findog.src.animal.model.PageCriteriaDto;
import challengers.findog.src.comment.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static challengers.findog.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    //댓글 등록
    @Transactional
    public List<GetCommentRes> createComment(int userId, PostCommentReq postCommentReq) throws BaseException{
        try{
            if(commentRepository.createComment(userId, postCommentReq) == 0){
                throw new BaseException(DATABASE_ERROR);
            }

            return getCommentList(postCommentReq.getPostId());
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //날짜 형식 변경
    public String changeDateFormat(String date){
        String newDate = date.substring(0, date.length()-3);
        return newDate.replace('-', '.');
    }

    //댓글 조회
    public List<GetCommentRes> getCommentList(int postId) throws BaseException{
        try{
            List<GetCommentRes> commentList = commentRepository.getCommentList(postId);

            for(GetCommentRes comment : commentList){
                comment.setCommentUpdateAt(changeDateFormat(comment.getCommentUpdateAt()));
            }
            return commentList;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //댓글 수정
    @Transactional
    public List<GetCommentRes> modifyComment(Comment comment) throws BaseException{
        try{
            if(commentRepository.modifyComment(comment) == 0){
                throw new BaseException(FAIL_MODIFY_COMMENT);
            }

            return getCommentList(comment.getPostId());
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //댓글 삭제
    public List<GetCommentRes> deleteComment(int postId, DeleteCommentRes deleteCommentRes) throws BaseException{
        try{
            if(commentRepository.checkParentComment(deleteCommentRes.getCommentId()) == 0){
                if(commentRepository.deleteComment(deleteCommentRes.getCommentId()) == 0){
                    throw new BaseException(FAIL_DELETE_COMMENT);
                }
            }
            else{
                if(commentRepository.deleteParentComment(deleteCommentRes.getCommentId()) == 0){
                    throw new BaseException(FAIL_DELETE_COMMENT);
                }
            }

            return getCommentList(postId);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //내가 작성한 댓글 조회
    public GetMyCommentsRes getMyCommentList(int userId, int page, int size) throws BaseException {
        try{
            List<MyCommentDto> commentList = commentRepository.getMyCommentList(userId, page, size);
            int totalCount = commentRepository.getMyCommentTotalCount(userId);
            int totalPage = (totalCount % size != 0) ? totalCount / size + 1 : totalCount / size;

            PageCriteriaDto pageCriteriaDto = new PageCriteriaDto(totalCount, totalPage, page, size);
            return new GetMyCommentsRes(pageCriteriaDto, commentList);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
