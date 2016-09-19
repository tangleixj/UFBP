package ufbp.exception;

/**
 * 服务异常
 * 
 * @author 小磊子
 *
 */
public class ServiceException extends DefaultRuntimeException {
	public static final String PARAMETER_IS_NULL = "参数为空";

	public ServiceException(String errorType, String elementName) {
		super(errorType, elementName);
		this.message = "服务执行中出现异常:" + message;
	}
}
