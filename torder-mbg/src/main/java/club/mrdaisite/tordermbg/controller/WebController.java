package club.mrdaisite.tordermbg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dai
 */
@RestController
public class WebController {
    @GetMapping("/mbg")
    public String index() {
        return "mbg";
    }
}
