package net.yasion.common.support.common.handle;

public class ReturnValueHandle<T> {

	private Boolean result;
	private T returnVal;
	private String message;
	private Exception exception;

	public ReturnValueHandle() {
		super();
	}

	public ReturnValueHandle(Boolean result, T returnVal, String message, Exception exception) {
		super();
		this.result = result;
		this.returnVal = returnVal;
		this.message = message;
		this.exception = exception;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public T getReturnVal() {
		return returnVal;
	}

	public void setReturnVal(T returnVal) {
		this.returnVal = returnVal;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
}
