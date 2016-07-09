package cloud.benchflow.deploymentmanager.configurations;

import org.hibernate.validator.constraints.NotEmpty;

public class DockerConfiguration {
	
	//TODO: add more strict validation
	
	@NotEmpty
	public String dockerComposeFolder;
	@NotEmpty
	public String certificatesFolder;
	@NotEmpty
	public String dockerEndpoint;
	@NotEmpty
	public String dockerTLSVerify;

	/**
	 * @return the dockerComposeFolder
	 */
	public String getDockerComposeFolder() {
		return dockerComposeFolder;
	}

	/**
	 * @param dockerComposeFolder the dockerComposeFolder to set
	 */
	public void setDockerComposeFolder(String dockerComposeFolder) {
		this.dockerComposeFolder = dockerComposeFolder;
	}

	/**
	 * @return the certificatesFolder
	 */
	public String getCertificatesFolder() {
		return certificatesFolder;
	}

	/**
	 * @param certificatesFolder the certificatesFolder to set
	 */
	public void setCertificatesFolder(String certificatesFolder) {
		this.certificatesFolder = certificatesFolder;
	}

	/**
	 * @return the dockerEndpoint
	 */
	public String getDockerEndpoint() {
		return dockerEndpoint;
	}

	/**
	 * @param dockerEndpoint the dockerEndpoint to set
	 */
	public void setDockerEndpoint(String dockerEndpoint) {
		this.dockerEndpoint = dockerEndpoint;
	}

	/**
	 * @return the dockerTLSVerify
	 */
	public String getDockerTLSVerify() {
		return dockerTLSVerify;
	}

	/**
	 * @param dockerTLSVerify the dockerTLSVerify to set
	 */
	public void setDockerTLSVerify(String dockerTLSVerify) {
		this.dockerTLSVerify = dockerTLSVerify;
	}
	
}