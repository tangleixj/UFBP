package ufbp.exception;

/**
 * 加载配置文件异常
 * 
 * @author 小磊子
 *
 */
public class LoadConfigFileException extends RuntimeException {
	private static final long serialVersionUID = 72199283619005504L;

	public static enum ERROR_TYPE {// 定义异常类别枚举集合
		FILE_NOT_FOUND("文件找不到"), MISS_ELEMENT("缺少元素"), TYPE_MISMATCH("类型不匹配");
		private String value;

		private ERROR_TYPE(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	private ERROR_TYPE errorType;// 异常类别
	private String elementName;
	private String message;// 异常信息

	public ERROR_TYPE getErrorType() {
		return errorType;
	}

	public void setErrorType(ERROR_TYPE errorType) {
		this.errorType = errorType;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LoadConfigFileException(ERROR_TYPE errorType, String elementName) {
		super(errorType + "[" + elementName + "]");
		this.message = "配置错误，" + errorType.getValue() + "[" + elementName + "]";
	}

	public LoadConfigFileException() {
		super();
	}
}
