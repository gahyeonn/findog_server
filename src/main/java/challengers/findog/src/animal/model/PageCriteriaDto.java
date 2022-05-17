package challengers.findog.src.animal.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageCriteriaDto {
    private int totalPostCount; //전체 동물공고 개수
    private int totalPage; //전체 페이지 수
    private int nowPage; //현재 페이지
    private int pageSize; //해당 페이지에 표시할 동물 공고 개수
}
