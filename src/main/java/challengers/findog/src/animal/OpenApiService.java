package challengers.findog.src.animal;

import challengers.findog.config.BaseException;
import challengers.findog.src.animal.model.Animal;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenApiService {

    //유기 동물 공고 디비 저장
    public List<String> getAnimalImgList(StringBuilder sb) throws BaseException {
        List<String> imgList = new ArrayList<String>();

        try {
            JSONParser parser	= new JSONParser();
            JSONObject obj 		= (JSONObject)parser.parse(sb.toString());
            JSONObject response = (JSONObject)obj.get("response");
            JSONObject body 	= (JSONObject)response.get("body");
            JSONObject items 	= (JSONObject)body.get("items");
            JSONArray item 	= (JSONArray) items.get("item");

            for (int i=0; i<item.size(); i++) {
                JSONObject data = (JSONObject) item.get(i);

                imgList.add(data.get("popfile").toString());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return imgList;
    }
}
