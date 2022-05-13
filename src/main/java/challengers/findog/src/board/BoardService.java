package challengers.findog.src.board;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.src.board.model.*;
import challengers.findog.src.comment.CommentRepository;
import challengers.findog.src.comment.model.Comment;
import challengers.findog.src.comment.model.GetCommentRes;
import challengers.findog.utils.s3Component.FileControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static challengers.findog.config.BaseResponseStatus.*;
import static challengers.findog.utils.ValidationRegex.isRegexImage;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final FileControlService fileControlService;

    //게시글 작성
    @Transactional(rollbackFor = Exception.class)
    public PostBoardRes createBoard(PostBoardReq postBoardReq) throws BaseException {
        try {
            int postId;
            String title = postBoardReq.getTitle();
            int category = postBoardReq.getCategory();
            String content = postBoardReq.getContent();
            ArrayList<MultipartFile> imgFiles = postBoardReq.getImgFiles();
            if (title.isEmpty() || title.isBlank()) {
               throw new BaseException(EMPTY_TITLE);
            }
            if (category == 0 || category > 4) {
                throw new BaseException(EMPTY_CATEGORY);
            }
            if (content.isEmpty() || content.isBlank()) {
                throw new BaseException(EMPTY_CONTENT);
            }
            try {
                postId = boardRepository.createBoard(postBoardReq);
            } catch (Exception e) {
                throw new Exception(Arrays.toString(e.getStackTrace()));
            }

            if (postBoardReq.getImgFiles() != null) { //imgFile 존재하면
                if (imgFiles.get(0).getOriginalFilename() != null && !postBoardReq.getImgFiles().get(0).getOriginalFilename().isBlank()) { //imgFile 존재하면
                    try {
                        for (MultipartFile img : imgFiles) {
                            if (!isRegexImage(img.getOriginalFilename())) {
                                throw new BaseException(INVALID_IMAGEFILEEXTENTION);
                            }
                            String imgUrl = fileControlService.uploadImage(img); //return값이 url
                            int result = boardRepository.createBoardPhoto(postId, imgUrl);
                            if (result == 0)
                                throw new BaseException(FAIL_UPLOAD_IMAGES);
                        }
                    } catch (Exception e) {
                        throw new BaseException(FAIL_UPLOAD_IMAGES);
                    }
                }
            }
            return new PostBoardRes(postId, postBoardReq.getUserId());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //게시글 수정
    @Transactional(rollbackFor = Exception.class)
    public void updateBoard(int userId, int postId, PatchBoardReq patchBoardReq) throws BaseException {
        try {
            boardRepository.updateBoard(userId, postId, patchBoardReq);

            if (patchBoardReq.getImgFiles() != null) { //req에 수정할 imgFile 존재하면
                List<String> arr = boardRepository.checkImg(postId); //기존 저장된 img 유무 확인
                if (arr.size() != 0) { //기존에 저장된 사진이 한 개 이상 있을 때
                    for (String url : arr)  //s3에서 delete
                        fileControlService.deleteImage(url);
                    try {
                        boardRepository.deleteImg(postId); //서버에서 delete
                    } catch (Exception e) {
                        throw new BaseException(FAIL_DELETE_IMAGES);
                    }
                }
                for (int i = 0; i < patchBoardReq.getImgFiles().size(); i++) { //새로운 사진 저장
                    MultipartFile img = patchBoardReq.getImgFiles().get(i);
                    if (!isRegexImage(img.getOriginalFilename())) {
                        throw new BaseException(INVALID_IMAGEFILEEXTENTION);
                    }
                    String imgUrl = fileControlService.uploadImage(img); //return값이 url
                    int res = boardRepository.createBoardPhoto(postId, imgUrl);
                    if (res == 0)
                        throw new BaseException(FAIL_UPLOAD_IMAGES);
                }
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //게시글 삭제, 해당 게시글 댓글 함께 삭제
    @Transactional(rollbackFor = Exception.class)
    public void deleteBoard(int postId) throws BaseException {
        //todo 댓글 삭제
        //이미지 삭제
        List<String> arr = boardRepository.checkImg(postId); //기존 저장된 img 유무 확인
        if (arr.size() != 0) { //기존에 저장된 사진이 한 개 이상 있을 때
            for (String url : arr)  //s3에서 delete
                fileControlService.deleteImage(url);
            try {
                boardRepository.deleteImg(postId); //서버에서 delete
            } catch (Exception e) {
                throw new BaseException(FAIL_DELETE_IMAGES);
            }
        }
        //게시글 삭제
        int result = boardRepository.deleteBoard(postId);
        if (result == 0) {
            throw new BaseException(FAIL_DELETE_BOARD);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public GetBoardRes getBoard(int postId, int userId) throws BaseException {
        List<String> imgList;
        List<Comment> commentList;
        try {
            //조회수- hits
            boardRepository.viewCount(postId);
            //게시글 조회- userId, nickname, userImgUrl, title, category, thumbnail, content, imgUrl, postCreateAt, likeCount, commentCount
            Board board = boardRepository.getBoard(postId);
            try {
                //사진 조회- imgList
                imgList = boardRepository.getBoardImage(postId);
            } catch (Exception e) {
                throw new BaseException(FAIL_GET_BOARD_IMAGE);
            }
            try {
                //todo 게시글 댓글 조회- commentList
//                commentList = commentRepository.getCommentList(postId);
            } catch (Exception e) {
                throw new BaseException(FAIL_GET_BOARD_COMMENTLIST);
            }
            //사용자 좋아요- userLiked
            boolean userLiked = false;
            int liked = boardRepository.userLiked(postId, userId);
            if(liked > 0)
                userLiked = true;

            return new GetBoardRes(board, imgList, userLiked);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Board> getBoardList(int page, int size) throws BaseException {
        try {
            return boardRepository.getBoardList(page, size);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

