package cloud.benchflow.compose.resources;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import cloud.benchflow.compose.configurations.DockerConfiguration;
import cloud.benchflow.compose.docker.DockerComposeWrapper;
import cloud.benchflow.compose.exceptions.FileCreationException;
import cloud.benchflow.compose.responses.Rm;
import cloud.benchflow.compose.responses.Stop;
import cloud.benchflow.compose.responses.Up;

@Path("projects")
@Produces(MediaType.APPLICATION_JSON)
public class Projects {

	private String projectFolder = null;
	private DockerConfiguration dockerConf;

	@Inject
	public Projects(@Named("projectFolder") String projectFolder, @Named("docker") DockerConfiguration dockerConf) {
		this.projectFolder = projectFolder;
		this.dockerConf = dockerConf;
	}

	/**
	 * Save a deployment descriptor of a multi-container application described in a docker-compose.yml and in a benchflow-compose.yml
	 * 
	 * 
	 * @param experimentId
	 *     
	 * @param entity
	 *     docker_compose_file:  - docker-compose.yml file<br/>
	 *     benchflow_compose_file:  - benchflow-compose.yml file<br/>
	 *     
	 */
	@PUT
	@Path("{experimentId}/deploymentDescriptor")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public void deploymentDescriptor(@PathParam("experimentId") String experimentId, 
			@FormDataParam("docker_compose_file") final InputStream dockerComposeStream,  
			@FormDataParam("benchflow_compose_file") final InputStream benchflowComposeStream) {

		//TODO: validate files

		//Create project directory if not exists
		createExperimentIdDirectory(experimentId);

		//Save files in the experimentId folder
		saveFileFromInputStream(experimentId, dockerComposeStream, "docker-compose.yml");
		saveFileFromInputStream(experimentId, benchflowComposeStream, "benchflow-compose.yml");
		
		//TODO: Generate the transformed docker-compose.yml by using the abstractions offered by benchflow-compose


	}


	/**
	 * Deploys a multi-container application described in a docker-compose.yml and in a benchflow-compose.yml
	 * 
	 * 
	 * @param projectnameid
	 * @throws IOException 
	 * @throws ExecuteException 
	 *     
	 */
	@PUT
	@Path("{experimentId}/up") 
	@Produces(MediaType.APPLICATION_JSON)
	public Up up(@PathParam("experimentId") String experimentId) throws ExecuteException, IOException {
		
		//TODO: implement
		//Verify that the experimentId folder exists
		
		//TODO: enable also benchflow-compose, for now I'm testing using what the output of the benchflow-compose abstraction should be
		//should be on the docker-compose.yml file
		
		//Call the DockerCompose wrapper to run the up method
		java.nio.file.Path projectFolder = Paths.get(this.projectFolder.toString(),experimentId);
		DockerComposeWrapper dockerCompose = new DockerComposeWrapper(this.dockerConf);
		dockerCompose.up(projectFolder.toString(),experimentId);

		//TODO: correclty handle the response
		return null;
	}
	
	//TODO: add the run command API
	
	//TODO: document
	@PUT
	@Path("{experimentId}/stop") 
	@Produces(MediaType.APPLICATION_JSON)
	public Stop stop(@PathParam("experimentId") String experimentId) throws ExecuteException, IOException {

		//TODO: implement
		//Verify that the experimentId folder exists

		//Call the DockerCompose wrapper to run the stop method
		java.nio.file.Path projectFolder = Paths.get(this.projectFolder.toString(),experimentId);
		DockerComposeWrapper dockerCompose = new DockerComposeWrapper(this.dockerConf);
		dockerCompose.stop(projectFolder.toString(),experimentId);

		return null;
	}
	
	//TODO: document
	@PUT
	@Path("{experimentId}/rm") 
	@Produces(MediaType.APPLICATION_JSON)
	public Rm rm(@PathParam("experimentId") String experimentId) throws ExecuteException, IOException {
		
		//TODO: implement
		//Verify that the experimentId folder exists

		//Call the DockerCompose wrapper to run the rm method
		
		//The services have to be stopped before removal
		stop(experimentId);
		
		java.nio.file.Path projectFolder = Paths.get(this.projectFolder.toString(),experimentId);
		DockerComposeWrapper dockerCompose = new DockerComposeWrapper(this.dockerConf);
		dockerCompose.rm(projectFolder.toString(),experimentId);

		return null;
	}

	/**
	 * 
	 * Creates a directory in the project folder if not exists
	 * 
	 * @param experimentId
	 */
	private void createExperimentIdDirectory(String experimentId) {
		java.nio.file.Path experimentIdFolder = Paths.get(projectFolder,experimentId);

		if (!Files.exists(experimentIdFolder)) {
			try {
				Files.createDirectories(experimentIdFolder);
			} catch (IOException e) {
				throw new FileCreationException("Unable to create the folder to store the deployment descriptor");
			}
		}

	}

	/**
	 * 
	 * Create a file from an InputStream
	 * 
	 * @param experimentId
	 * @param dataStream
	 * @param fileName
	 */
	private void saveFileFromInputStream(String experimentId, final InputStream dataStream, String fileName) {
		java.nio.file.Path outputFile = Paths.get(projectFolder,experimentId,fileName);

		try {
			//Creates file if not exists
			if(!Files.exists(outputFile)){
				Files.createFile(outputFile);
			}
			//Save dataStream on fileName (overwriting the content of the file, if already present: PUT behaviour)
			OutputStream out = new FileOutputStream(outputFile.toFile());
			IOUtils.copy(dataStream, out);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileCreationException("Unable to save the " + fileName + " file");
		}			

	}

}

//TODO, use the following and document in root project documentation
//HTTP status code
//
//HTTP defines a bunch of meaningful status codes that can be returned from your API. These can be leveraged to help the API consumers route their responses accordingly. I've curated a short list of the ones that you definitely should be using:
//       
//200 OK - Response to a successful GET, PUT, PATCH or DELETE. Can also be used for a POST that doesn't result in a creation.
//201 Created - Response to a POST that results in a creation. Should be combined with a Location header pointing to the location of the new resource
//204 No Content - Response to a successful request that won't be returning a body (like a DELETE request)
//304 Not Modified - Used when HTTP caching headers are in play
//400 Bad Request - The request is malformed, such as if the body does not parse
//401 Unauthorized - When no or invalid authentication details are provided. Also useful to trigger an auth popup if the API is used from a browser
//403 Forbidden - When authentication succeeded but authenticated user doesn't have access to the resource
//404 Not Found - When a non-existent resource is requested
//405 Method Not Allowed - When an HTTP method is being requested that isn't allowed for the authenticated user
//410 Gone - Indicates that the resource at this end point is no longer available. Useful as a blanket response for old API versions
//415 Unsupported Media Type - If incorrect content type was provided as part of the request
//422 Unprocessable Entity - Used for validation errors
//429 Too Many Requests - When a request is rejected due to rate limiting

