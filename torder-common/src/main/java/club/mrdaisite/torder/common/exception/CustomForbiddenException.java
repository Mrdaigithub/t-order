package club.mrdaisite.torder.common.exception;

/**
 * 自定义403异常
 *
 * @author dai
 * @date 2019/03/25
 */
public class CustomForbiddenException extends Exception {
    public CustomForbiddenException(String message) {
        super(message);
    }
}
