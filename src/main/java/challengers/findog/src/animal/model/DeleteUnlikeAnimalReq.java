package challengers.findog.src.animal.model;

import lombok.Data;

import java.util.List;

@Data
public class DeleteUnlikeAnimalReq {
    List<Integer> animalIdList;
}
