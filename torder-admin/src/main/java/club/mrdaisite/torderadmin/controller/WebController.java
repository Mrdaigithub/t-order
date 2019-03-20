package club.mrdaisite.torderadmin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dai
 */
@RestController
public class WebController {
    @GetMapping("/admin")
    public String index() {
        return "admin";
    }
}
