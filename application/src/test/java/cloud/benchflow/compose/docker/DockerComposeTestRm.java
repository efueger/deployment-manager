package cloud.benchflow.compose.docker;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import cloud.benchflow.compose.configurations.DockerConfiguration;

public class DockerComposeTestRm {

	private static final DockerConfiguration configuration = new DockerConfiguration();
	private TemporaryFolder testFolder = new TemporaryFolder();
	private String sourceFolder = "/projects/examples/wordpress";
	private String projectName = "wordpress";

	@Before
	public void setup() throws IOException {

		DockerComposeUtils.configure(configuration);

		testFolder.create();
		
		DockerComposeUtils.deployProject(configuration, sourceFolder, testFolder.getRoot().toString(), projectName);

	}
	
	@After
	public void tearDown() { 
		testFolder.delete();
	}

	@Test
	public void testUp() {

		DockerComposeWrapper dockerCompose = new DockerComposeWrapper(configuration);

		//Need to stop the service before removal
		dockerCompose.stop(testFolder.getRoot().getAbsolutePath() + File.separator + projectName, projectName);
		dockerCompose.rm(testFolder.getRoot().getAbsolutePath() + File.separator + projectName, projectName);


	}

}
