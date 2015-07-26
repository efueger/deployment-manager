package cloud.benchflow.compose.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Up {
	
	private String status;
	private String logs;
	
	public Up (String status, String logs) {
		this.status = status;
		this.logs = logs;
	}

	/**
	 * @return the status
	 */
	@JsonProperty
	public String getStatus() {
		return status;
	}

	/**
	 * @return the logs
	 */
	@JsonProperty
	public String getLogs() {
		return logs;
	}
	

}
