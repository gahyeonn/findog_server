package challengers.findog.src.comment.model;

import challengers.findog.src.animal.model.PageCriteriaDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetMyCommentsRes {
    private PageCriteriaDto pageCriteriaDto;
    private List<MyCommentDto> commentList;
}
