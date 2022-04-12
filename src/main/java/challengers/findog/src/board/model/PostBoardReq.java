package challengers.findog.src.board.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import java.util.ArrayList;

import static challengers.findog.config.Constant.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBoardReq {

    private int userId;

    @NotBlank(message = "EMPTY_TITLE")
    private String title;

    @Positive
    private int category;

    @NotBlank(message = "EMPTY_CONTENT")
    private String content;

    private ArrayList<MultipartFile> imgFiles;
}


