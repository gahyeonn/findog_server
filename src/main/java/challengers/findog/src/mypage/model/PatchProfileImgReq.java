package challengers.findog.src.mypage.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PatchProfileImgReq {
    private MultipartFile newProfileImg;
    private int deleteFlag;
    private String originProfileImgUrl; //삭제할 이미지 => 기존 프로필
}
