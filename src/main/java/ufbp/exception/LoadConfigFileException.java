package ufbp.exception;

/**
 * ���������ļ��쳣
 * 
 * @author С����
 *
 */
public class LoadConfigFileException extends RuntimeException {
	private static final long serialVersionUID = 72199283619005504L;

	public static enum ERROR_TYPE {// �����쳣���ö�ټ���
		FILE_NOT_FOUND("�ļ��Ҳ���"), MISS_ELEMENT("ȱ��Ԫ��"), TYPE_MISMATCH("���Ͳ�ƥ��");
		private String value;

		private ERROR_TYPE(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	private ERROR_TYPE errorType;// �쳣���
	private String elementName;
	private String message;// �쳣��Ϣ

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
		this.message = "���ô���" + errorType.getValue() + "[" + elementName + "]";
	}

	public LoadConfigFileException() {
		super();
	}
}
