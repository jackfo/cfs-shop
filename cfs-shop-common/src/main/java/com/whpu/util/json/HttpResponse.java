package com.whpu.util.json;

public class HttpResponse<T> {
	
	private int code;
	private String msg;
	private T data;
	
	/**
	 *  成功时候的调用
	 * */
	public static  <T> HttpResponse<T> success(T data){
		return new HttpResponse<T>(data);
	}
	
	/**
	 *  失败时候的调用
	 * */
	public static  <T> HttpResponse<T> error(CodeMsg codeMsg){
		return new HttpResponse<T>(codeMsg);
	}
	
	private HttpResponse(T data) {
		this.data = data;
	}
	
	private HttpResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	private HttpResponse(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
