package club.mrdaisite.torderportal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dai
 */
@RestController
public class WebController {
    @GetMapping("/portal")
    public String index() {
        return "portal";
    }
}
