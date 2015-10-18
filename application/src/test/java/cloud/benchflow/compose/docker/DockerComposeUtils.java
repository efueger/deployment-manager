package cloud.benchflow.compose.docker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cloud.benchflow.compose.configurations.DockerConfiguration;
import cloud.benchflow.compose.resources.Projects;

public class DockerComposeUtils {
	
	public static void deployProject(DockerConfiguration configuration, String sourceFolder, String destFolder, String experimentId) {
		
		Projects project = new Projects(destFolder, configuration);
		
		InputStream dockerCompose = DockerComposeUtils.class.getResourceAsStream(sourceFolder + "/docker-compose.yml");
		InputStream benchflowCompose = DockerComposeUtils.class.getResourceAsStream(sourceFolder + "/benchflow-compose.yml");

		project.deploymentDescriptor(experimentId, dockerCompose, benchflowCompose);
	
	}

	public static void configure(DockerConfiguration configuration) {

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = DockerComposeUtils.class.getResourceAsStream("/configuration.properties");

			// load a properties file
			prop.load(input);
			
			// set properties
			configuration.setCertificatesFolder(prop.getProperty("CertificatesFolder"));
			configuration.setDockerComposeFolder(prop.getProperty("DockerComposeFolder"));
			configuration.setDockerEndpoint(prop.getProperty("DockerEndpoint"));
			configuration.setDockerTLSVerify(prop.getProperty("DockerTLSVerify"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
