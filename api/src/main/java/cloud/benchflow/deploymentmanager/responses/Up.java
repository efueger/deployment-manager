package cloud.benchflow.deploymentmanager.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Up {
	
	private String status;
	private String logs;
	
	@SuppressWarnings("unused")
	private Up() {
        // Jackson deserialization
    }
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logs == null) ? 0 : logs.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Up other = (Up) obj;
		if (logs == null) {
			if (other.logs != null)
				return false;
		} else if (!logs.equals(other.logs))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
	

}
