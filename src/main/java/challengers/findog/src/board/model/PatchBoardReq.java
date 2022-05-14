package challengers.findog.src.board.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;

@Data
public class PatchBoardReq {
    @ApiModelProperty(value = "제목", example = "게시글 제목입니다.", required = true)
    @NotBlank(message = "EMPTY_TITLE")
    private String title;

    @ApiModelProperty(value = "카테고리", example = "1", required = true)
    @Positive
    private Integer category;

    @ApiModelProperty(value = "지역", example = "1", required = true)
    @Positive
    private Integer region;

    @ApiModelProperty(value = "내용", example = "게시글 내용입니다.", required = true)
    @NotBlank(message = "EMPTY_CONTENT")
    private String content;

    @ApiModelProperty(value = "이미지파일", notes = "MultipartFile 형태, 다중선택 가능", required = true)
    private ArrayList<MultipartFile> imgFiles;
}