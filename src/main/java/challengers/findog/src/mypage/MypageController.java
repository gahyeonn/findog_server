package challengers.findog.src.mypage;

import challengers.findog.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final JwtService jwtService;


}
