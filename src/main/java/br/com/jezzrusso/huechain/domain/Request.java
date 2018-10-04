package br.com.jezzrusso.huechain.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class Request<T> implements Serializable {

	private static final long serialVersionUID = -7776332469144269729L;

	@NotNull
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		if (!(obj instanceof Request)) {
			return false;
		}
		Request other = (Request) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "{Request:{data:" + data + "}}";
	}
}
