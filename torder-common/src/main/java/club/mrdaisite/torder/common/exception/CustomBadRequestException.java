package club.mrdaisite.torder.common.exception;

/**
 * 自定义400异常
 *
 * @author dai
 * @date 2019/03/25
 */
public class CustomBadRequestException extends Exception {
    public CustomBadRequestException(String message) {
        super(message);
    }
}
