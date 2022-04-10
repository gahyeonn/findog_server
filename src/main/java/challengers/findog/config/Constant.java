package challengers.findog.config;

//프로젝트 내 공통 사용 상수
public class Constant {
    public static final String passwordRegex = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$";//8~20자 특문 1개 숫자 1개
    public static final String nicknameRegex ="^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]{1,15}+$";
    public static final String phoneNumRegex = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$";
}
