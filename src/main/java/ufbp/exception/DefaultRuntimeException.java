package ufbp.exception;

/**
 * 封装异常父类
 * 
 * @author 小磊子
 *
 */
public class DefaultRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -1784554631066835359L;
	protected String message;// 异常信息
	protected String errorType;// 异常类别
	protected String elementName;// 元素名称

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public DefaultRuntimeException(String errorType, String elementName) {
		super();
		this.errorType = errorType;
		this.elementName = elementName;
		if (elementName != null) {
			this.message = errorType + " [" + elementName + "]";
		} else {
			this.message = errorType;
		}
	}

	public DefaultRuntimeException(String message) {
		super();
		this.message = message;
	}
}
