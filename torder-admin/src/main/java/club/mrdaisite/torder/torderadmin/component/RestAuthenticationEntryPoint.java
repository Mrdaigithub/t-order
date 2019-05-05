package club.mrdaisite.torder.torderadmin.component;

import club.mrdaisite.torder.common.util.ErrorCodeUtils;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当访问接口没有权限时，自定义的返回结果
 *
 * @author dai
 * @date 2019/03/22
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.put("code", Convert.toStr(HttpStatus.UNAUTHORIZED.value()));
        jsonObject.put("message", new ErrorCodeUtils(4030000).getEMessage());
        StaticLog.error(jsonObject.toStringPretty());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().println(jsonObject.toString());
        httpServletResponse.getWriter().flush();
    }
}
