package cloud.benchflow.deploymentmanager.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {
	
	private int code;
	private String message;
	private String description;
	
	/**
	 * @return the code
	 */
	@JsonProperty
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	@JsonProperty
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the description
	 */
	@JsonProperty
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
