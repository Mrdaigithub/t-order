package club.mrdaisite.torder.common.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * CommonResult
 *
 * @author dai
 * @date 2019/05/05
 */
public class CommonResult {
    private int code;
    private String message;
    private Object data;

    /**
     * 普通成功返回
     *
     * @param data 获取的数据
     * @return 返回的数据
     */
    public ResponseEntity<CommonResult> success(Object data) {
        setCode(HttpStatus.OK.value());
        setMessage("操作成功");
        setData(data);
        return new ResponseEntity<CommonResult>(this, HttpStatus.OK);
    }

    /**
     * 无效请求返回
     *
     * @param message 获取的数据
     * @return 返回的数据
     */
    public ResponseEntity<CommonResult> badRequest(String message) {
        setCode(HttpStatus.BAD_REQUEST.value());
        setMessage(message != null ? message : "无效的请求");
        setData(null);
        return new ResponseEntity<CommonResult>(this, HttpStatus.BAD_REQUEST);
    }

    /**
     * 认证错误时返回数据
     *
     * @param message 自定义错误消息
     * @return 认证错误时返回结果
     */
    public ResponseEntity<CommonResult> unauthorized(String message) {
        setCode(HttpStatus.UNAUTHORIZED.value());
        setMessage(message != null ? message : "认证错误");
        setData(null);
        return new ResponseEntity<CommonResult>(this, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 无权限返回数据
     *
     * @param message 自定义错误消息
     * @return 无权限时返回结果
     */
    public ResponseEntity<CommonResult> forbidden(String message) {
        setCode(HttpStatus.FORBIDDEN.value());
        setMessage(message != null ? message : "为获取相关权限");
        setData(null);
        return new ResponseEntity<CommonResult>(this, HttpStatus.FORBIDDEN);
    }

    /**
     * 找不到资源返回数据
     *
     * @param message 自定义错误消息
     * @return 找不到资源时返回结果
     */
    public ResponseEntity<CommonResult> notFound(String message) {
        setCode(HttpStatus.NOT_FOUND.value());
        setMessage(message != null ? message : "找不到资源");
        setData(null);
        return new ResponseEntity<CommonResult>(this, HttpStatus.NOT_FOUND);
    }

    /**
     * 无效的请求方法返回数据
     *
     * @param message 自定义错误消息
     * @return 无效的请求方法时返回结果
     */
    public ResponseEntity<CommonResult> methodNotAllowed(String message) {
        setCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        setMessage(message != null ? message : "无效的请求方法");
        setData(null);
        return new ResponseEntity<CommonResult>(this, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 服务器错误返回数据
     *
     * @param message 自定义错误消息
     * @return 服务器错误时返回结果
     */
    public ResponseEntity<CommonResult> internalServerError(String message) {
        setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        setMessage(message != null ? message : "服务器错误");
        setData(null);
        return new ResponseEntity<CommonResult>(this, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
