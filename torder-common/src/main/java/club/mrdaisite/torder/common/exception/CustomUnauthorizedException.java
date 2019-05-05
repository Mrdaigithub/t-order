package club.mrdaisite.torder.common.exception;

/**
 * 自定义401异常
 *
 * @author dai
 * @date 2019/03/25
 */
public class CustomUnauthorizedException extends Exception {
    public CustomUnauthorizedException(String message) {
        super(message);
    }
}
