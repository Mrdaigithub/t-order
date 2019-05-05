package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.common.exception.CustomInternalException;
import club.mrdaisite.torder.torderadmin.util.ErrorCodeUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * ErrorController
 *
 * @author dai
 * @date 2019/04/24
 */
@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public void handleError(HttpServletRequest request) throws CustomInternalException {
        throw new CustomInternalException(new ErrorCodeUtils(5000000).getEMessage());
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
