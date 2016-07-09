package cloud.benchflow.deploymentmanager.resources;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.exec.ExecuteException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import cloud.benchflow.deploymentmanager.configurations.DockerConfiguration;
import cloud.benchflow.deploymentmanager.docker.DockerComposeUtils;

public class ProjectsTest {

	private static final DockerConfiguration configuration = new DockerConfiguration();
	private TemporaryFolder testFolder = new TemporaryFolder();
	private String sourceFolder = "/projects/examples/camunda";
	private Projects project;
	
	//TODO: add testing of the methods that calls docker-compose, by stubbing the service interaction with actual REST call, and not direclty calling the method of the resource
	
	//TODO: improve, currently I direclty call the methods I'm interested to test
	@Before
	public void setup() throws IOException {

		DockerComposeUtils.configure(configuration);
		
		testFolder.create();
		
		//Create a test project
		project = new Projects(testFolder.getRoot().toString(), configuration);
		
	}

	@After
	public void tearDown() { 
		testFolder.delete();
	}
	
	@Test
	public void testDeploymentDescriptor() {
		
		String experimentId = "wordpress";
		
		InputStream dockerCompose = getClass().getResourceAsStream(sourceFolder + "/docker-compose.yml");

		project.deploymentDescriptor(experimentId, dockerCompose);

	}

	
	@Test
	public void testRm() throws ExecuteException, IOException {
		
		String experimentId = "wordpress";
		
		project.rm(experimentId);

	}
	
	@Test
	public void testRmSameResourceTwice() throws ExecuteException, IOException {
		
		String experimentId = "wordpress";
		
		project.rm(experimentId);
		
		project.rm(experimentId);

	}
}
