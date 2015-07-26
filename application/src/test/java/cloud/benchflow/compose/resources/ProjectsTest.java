package cloud.benchflow.compose.resources;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import cloud.benchflow.compose.BenchflowComposeApplication;
import cloud.benchflow.compose.configurations.BenchflowComposeConfiguration;
import cloud.benchflow.compose.configurations.DockerConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.dropwizard.testing.junit.ResourceTestRule;

public class ProjectsTest {

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();
	
	//Run the service (useful mainly for integration testing)
//	@ClassRule
//    public static final DropwizardAppRule<BenchflowComposeConfiguration> RULE =
//            new DropwizardAppRule<BenchflowComposeConfiguration>(BenchflowComposeApplication.class, ResourceHelpers.resourceFilePath("configuration.yaml"));
	
	//TODO: add testing of the methods that calls docker-compose, by stubbing the service interaction

//	@Test
//	public void testDeploymentDescriptor() {
		
//		String folder = testFolder.newFolder("experimentId").getAbsolutePath();

//		ResourceTestRule resources = ResourceTestRule.builder()
//				.addResource(new Projects(folder, null))
//				.build();

		//TODO: add testing of correct upload of files
//		assertThat(resources.client().target("/projects/experimentId/deploymentDescriptor").request())

//	}

//	@Test
//	public void testUp() {
		
		//TODO: the following is custom testing code, implement the real one!
		
//	}
}
