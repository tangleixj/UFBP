package ufbp.exception;

/**
 * �����쳣
 * 
 * @author С����
 *
 */
public class ServiceException extends DefaultRuntimeException {
	public static final String PARAMETER_IS_NULL = "����Ϊ��";

	public ServiceException(String errorType, String elementName) {
		super(errorType, elementName);
		this.message = "����ִ���г����쳣:" + message;
	}
}
