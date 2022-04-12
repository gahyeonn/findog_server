package challengers.findog.src.board.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;

@Data
public class PatchBoardReq {

    @NotBlank(message = "EMPTY_TITLE")
    private String title;

    @Positive
    private int category;

    @NotBlank(message = "EMPTY_CONTENT")
    private String content;

    private ArrayList<MultipartFile> imgFiles;
}
