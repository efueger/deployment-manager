/**
 * 
 */
package cloud.benchflow.deploymentmanager.configurations;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import io.dropwizard.Configuration;

/**
 * @author vincenzoferme
 *
 */
public class BenchflowDeploymentManagerConfiguration extends Configuration {

	@NotEmpty
	public String projectFolder;
	
	@Valid
    @NotNull
    private DockerConfiguration docker = new DockerConfiguration();
	
	public String getProjectFolder() {
		return projectFolder;
	}

	public void setProjectFolder(String projectFolder) {
		this.projectFolder = projectFolder;
	}

	/**
	 * @return the docker
	 */
	public DockerConfiguration getDocker() {
		return docker;
	}

	/**
	 * @param docker the docker to set
	 */
	public void setDocker(DockerConfiguration docker) {
		this.docker = docker;
	}

}
