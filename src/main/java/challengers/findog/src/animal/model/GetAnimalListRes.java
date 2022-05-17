package challengers.findog.src.animal.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAnimalListRes {
    private PageCriteriaDto pageCriteria;
    private List<AnimalSimpleDto> animalSimpleInfo;
}
