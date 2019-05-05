package club.mrdaisite.torder.common.exception;

/**
 * 自定义405异常
 *
 * @author dai
 * @date 2019/03/25
 */
public class CustomMethodNotAllowedException extends Exception {
    public CustomMethodNotAllowedException(String message) {
        super(message);
    }
}
