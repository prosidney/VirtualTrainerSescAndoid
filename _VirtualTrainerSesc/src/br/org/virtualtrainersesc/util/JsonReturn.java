package br.org.virtualtrainersesc.util;

import java.io.Serializable;
import java.util.List;

public class JsonReturn implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Object> data;

	public JsonReturn() {
	}
	
	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}
}
