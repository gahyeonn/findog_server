package challengers.findog.src.board;

import challengers.findog.config.BaseException;
import challengers.findog.config.secret.Secret;
import challengers.findog.src.board.model.PatchBoardReq;
import challengers.findog.src.board.model.PostBoardReq;
import challengers.findog.src.board.model.PostBoardRes;
import challengers.findog.src.user.model.User;
import challengers.findog.utils.AES128;
import challengers.findog.utils.s3Component.FileControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static challengers.findog.config.BaseResponseStatus.*;
import static challengers.findog.utils.ValidationRegex.isRegexImage;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileControlService fileControlService;

    //게시글 작성
    @Transactional(rollbackFor = Exception.class)
    public PostBoardRes createBoard(PostBoardReq postBoardReq) throws BaseException {
        try {
            int postId = boardRepository.createBoard(postBoardReq);

            if (postBoardReq.getImgFiles() != null) { //imgFile 존재하면
                for (int i = 0; i < postBoardReq.getImgFiles().size(); i++) {
                    MultipartFile img = postBoardReq.getImgFiles().get(i);
                    if (!isRegexImage(img.getOriginalFilename())) {
                        throw new BaseException(INVALID_IMAGEFILEEXTENTION);
                    }
                    String imgUrl = fileControlService.uploadImage(img); //return값이 url
                    int result = boardRepository.createBoardPhoto(postId, imgUrl);
                    if(result == 0)
                        throw new BaseException(FAIL_UPLOAD_IMAGES);
                }
            }
            return new PostBoardRes(postId, postBoardReq.getUserId());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //게시글 수정
    public void updateBoard(int userId, int postId, PatchBoardReq patchBoardReq) throws BaseException {
        try {
            int result = boardRepository.updateBoard(userId, postId, patchBoardReq);
            if(result == 0) {
                throw new BaseException(FAIL_UPDATE_BOARD);
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

