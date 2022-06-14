package sv.edu.udb.www.transversal;

public class Response<T> {

	private T Data;
	private boolean IsSuccess = true;
	private boolean IsWarning = true;
	private String Message = "";
	
	
	public boolean isIsSuccess() {
		return IsSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		IsSuccess = isSuccess;
	}
	public boolean isIsWarning() {
		return IsWarning;
	}
	public void setIsWarning(boolean isWarning) {
		IsWarning = isWarning;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public T getData() {
		return Data;
	}
	public void setData(T data) {
		Data = data;
	}
	
	
	
	
	
}
