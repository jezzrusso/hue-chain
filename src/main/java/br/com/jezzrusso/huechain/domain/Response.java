package br.com.jezzrusso.huechain.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Response<T> implements Serializable {

	private static final long serialVersionUID = 2015420860768172726L;

	@JsonInclude(Include.NON_NULL)
	private T data;
	private List<String> errors = new ArrayList<>();

	public Response() {
		// Default constructor
	}

	public Response(T data) {
		this.data = data;
	}

	public Response(List<String> errors) {
		this.errors = errors;
	}

	public Response(T data, List<String> errors) {
		this.data = data;
		this.errors = errors;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((errors == null) ? 0 : errors.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Response)) {
			return false;
		}
		Response other = (Response) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		if (errors == null) {
			if (other.errors != null) {
				return false;
			}
		} else if (!errors.equals(other.errors)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "{Response:{data:" + data + ",errors:" + errors + "}}";
	}

}
