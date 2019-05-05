package club.mrdaisite.torder.common.exception;

/**
 * 自定义404异常
 *
 * @author dai
 * @date 2019/03/25
 */
public class CustomNotFoundException extends Exception {
    public CustomNotFoundException(String message) {
        super(message);
    }
}
