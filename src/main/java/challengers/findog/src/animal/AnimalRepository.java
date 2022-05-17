package challengers.findog.src.animal;

import challengers.findog.src.animal.model.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AnimalRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //유기동물 공고 저장
    public int createAnimal(Animal animal){
        String query = "insert into Animal(desertionNo, filename, happenDt, happenPlace, kindCd, colorCd, age, weight, noticeNo, noticeSdt, noticeEdt, popfile, processState, sexCd, neuterYn, specialMark, careNm, careTel, careAddr) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = new Object[]{
                animal.getDesertionNo(),
                animal.getFilename(),
                animal.getHappenDt(),
                animal.getHappenPlace(),
                animal.getKindCd(),
                animal.getColorCd(),
                animal.getAge(),
                animal.getWeight(),
                animal.getNoticeNo(),
                animal.getNoticeSdt(),
                animal.getNoticeEdt(),
                animal.getPopfile(),
                animal.getProcessState(),
                animal.getSexCd(),
                animal.getNeuterYn(),
                animal.getSpecialMark(),
                animal.getCareNm(),
                animal.getCareTel(),
                animal.getCareAddr()
        };
        return jdbcTemplate.update(query, params);
    }

}
