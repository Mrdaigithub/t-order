package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.torderadmin.exception.CustomInternalException;
import club.mrdaisite.torder.torderadmin.util.ErrorCodeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * torder
 *
 * @author dai
 * @date 2019/04/24
 */
@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public void handleError(HttpServletRequest request) throws CustomInternalException {
        new ErrorCodeUtils(5000000).throwInternalException();
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
