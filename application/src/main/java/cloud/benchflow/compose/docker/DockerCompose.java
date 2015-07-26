package cloud.benchflow.compose.docker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;

import cloud.benchflow.compose.configurations.DockerConfiguration;

public class DockerCompose {

	private DockerConfiguration configuration;

	public DockerCompose(DockerConfiguration configuration){

		this.configuration = configuration;
	}

	public String up(String experimentId) {

		Path dockerComposeExec = Paths.get(configuration.dockerComposeFolder,"docker-compose");

		CommandLine cmdLine = new CommandLine(dockerComposeExec.toString());

		//		cmdLine.addArgument("/p");
		//		cmdLine.addArgument("/h");
		//		cmdLine.addArgument("${file}");
		//		cmdLine.setSubstitutionMap(map);

		DefaultExecutor executor = new DefaultExecutor();

		ByteArrayOutputStream out=new ByteArrayOutputStream();
		ByteArrayOutputStream err=new ByteArrayOutputStream();

		PumpStreamHandler handler=new PumpStreamHandler(out,err);

		executor.setStreamHandler(handler);
		executor.setExitValue(1); //The process always exit with 1

		//kills a run-away process after sixty seconds.
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);

		executor.setWatchdog(watchdog);

		int exitValue = 0;
		
		try {
			
			
			exitValue = executor.execute(cmdLine);
			

		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InputStream stdout=out.size() == 0 ? null : new ByteArrayInputStream(out.toByteArray());
		InputStream stderr=err.size() == 0 ? null : new ByteArrayInputStream(err.toByteArray());
//		String outStr = IOUtils.toString(stdout, "UTF-8"); 
		String errStr = null;
		try {
			errStr = IOUtils.toString(stderr, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(exitValue);
//		System.out.println(outStr);
		System.out.println(errStr);


		return experimentId;

	}

}
