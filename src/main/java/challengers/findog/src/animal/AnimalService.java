package challengers.findog.src.animal;

import challengers.findog.config.BaseException;
import challengers.findog.config.BaseResponse;
import challengers.findog.src.animal.model.Animal;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public String insertAnimalPost(StringBuilder sb) throws BaseException{

        try {
            JSONParser parser	= new JSONParser();
            JSONObject obj 		= (JSONObject)parser.parse(sb.toString());
            JSONObject response = (JSONObject)obj.get("response");
            JSONObject body 	= (JSONObject)response.get("body");
            JSONObject items 	= (JSONObject)body.get("items");
            JSONArray item 	= (JSONArray) items.get("item");

            for (int i=0; i<item.size(); i++) {
                JSONObject data = (JSONObject) item.get(i);

                String desertionNo  = (String) data.get("desertionNo").toString();
                String filename     = (String) data.get("filename").toString();
                String happenDt 	= changeDateForm(data.get("happenDt").toString());
                String happenPlace	= (String) data.get("happenPlace").toString();
                String kindCd	    = (String) data.get("kindCd").toString();
                String colorCd 		= (String) data.get("colorCd").toString();
                String age		    = (String) data.get("age").toString();
                String weight 		= (String) data.get("weight").toString();
                String noticeNo	    = (String) data.get("noticeNo").toString();
                String noticeSdt	= changeDateForm(data.get("noticeSdt").toString());
                String noticeEdt	= changeDateForm(data.get("noticeEdt").toString());
                String popfile		= (String) data.get("popfile").toString();
                String processState	= (String) data.get("processState").toString();
                String sexCd	    = (String) data.get("sexCd").toString();
                String neuterYn		= (String) data.get("neuterYn").toString();
                String specialMark	= (String) data.get("specialMark").toString();
                String careNm		= (String) data.get("careNm").toString();
                String careTel		= (String) data.get("careTel").toString();
                String careAddr		= (String) data.get("careAddr").toString();

                Animal animal = new Animal(0, desertionNo, filename, happenDt, happenPlace, kindCd, colorCd, age, weight, noticeNo, noticeSdt, noticeEdt, popfile, processState, sexCd, neuterYn, specialMark, careNm, careTel, careAddr);
                animalRepository.createAnimal(animal);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "불러온 유기동물 공고를 성공적으로 저장하였습니다.";
    }

    private String changeDateForm(String date){
        StringBuffer sb = new StringBuffer();

        sb.append(date);
        sb.insert(4, "-");
        sb.insert(7, "-");
        return sb.toString();
    }
}
