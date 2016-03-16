package cloud.benchflow.compose.docker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;

import cloud.benchflow.compose.configurations.DockerConfiguration;

public class DockerComposeWrapper {

	private DockerConfiguration configuration;

	public DockerComposeWrapper(DockerConfiguration configuration){

		this.configuration = configuration;
	}
	
	// TODO: for now I direclty force the pull, this should be a specified
	// parameter
	public String pull(String projectFolder, String projectName) {

		// get the docker-compose bin path
		Path dockerComposeExec = Paths.get(configuration.dockerComposeFolder, "docker-compose");

		// build the docker-compose command line
		CommandLine cmdLine = new CommandLine(dockerComposeExec.toString());

		cmdLine.addArgument("--verbose"); //TODO: testing, remove
		cmdLine.addArgument("pull");

		// build the env variables map we want to pass to docker-compose
		Map<String, String> env = new HashMap<String, String>();

		env.put("DOCKER_HOST", configuration.getDockerEndpoint());
		// TODO: remove, currenlty disabled for testing purposes
		// env.put("DOCKER_TLS_VERIFY",configuration.getDockerTLSVerify());
		// TODO: remove, currenlty disabled for testing purposes
		// env.put("DOCKER_CERT_PATH", configuration.getCertificatesFolder());
		env.put("COMPOSE_FILE", Paths.get(projectFolder, "docker-compose.yml").toAbsolutePath().toString());
		env.put("COMPOSE_PROJECT_NAME", projectName);

		// TODO: the following is similar to the previous method, refactor it
		// extracting the method

		// execute docker compose
		DefaultExecutor executor = new DefaultExecutor();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();

		PumpStreamHandler handler = new PumpStreamHandler(out, err);

		executor.setStreamHandler(handler);
		// executor.setExitValue(0); //The process always exit with 0

		// kills a run-away process after 10 minutes (the pull can be a long process, dependending on the netwrok connection of the SUT servers).
		ExecuteWatchdog watchdog = new ExecuteWatchdog(600000);

		executor.setWatchdog(watchdog);

		int exitValue = 0;

		// TODO: improve docker-compose error handling
		try {

			System.out.println(cmdLine.toString());
			exitValue = executor.execute(cmdLine, env);

			//
		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InputStream stdout = out.size() == 0 ? null : new ByteArrayInputStream(out.toByteArray());
		InputStream stderr = err.size() == 0 ? null : new ByteArrayInputStream(err.toByteArray());
		String outStr = null;
		String errStr = null;
		try {
			if (stderr != null)
				errStr = IOUtils.toString(stderr, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (stdout != null)
				outStr = IOUtils.toString(stdout, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(exitValue);
		System.out.println("Out:");
		System.out.println(outStr);
		System.out.println("Err:");
		System.out.println(errStr);

		// Attach to logs to retrieve the logs and exit (when to exit???)

		return projectFolder;

	}

	public String up(String projectFolder, String projectName) {

		//get the docker-compose bin path
		Path dockerComposeExec = Paths.get(configuration.dockerComposeFolder,"docker-compose");

		//build the docker-compose command line
		CommandLine cmdLine = new CommandLine(dockerComposeExec.toString());

		cmdLine.addArgument("--verbose"); //TODO: testing, remove
		cmdLine.addArgument("up");
		//Recreate containers even if their configuration and image haven't changed
		cmdLine.addArgument("--force-recreate");
		cmdLine.addArgument("-d");

		//build the env variables map we want to pass to docker-compose
		Map<String,String> env = new HashMap<String,String>();

		env.put("DOCKER_HOST", configuration.getDockerEndpoint());
		//TODO: remove, currenlty disabled for testing purposes
//		env.put("DOCKER_TLS_VERIFY",configuration.getDockerTLSVerify());
//		env.put("DOCKER_CERT_PATH", configuration.getCertificatesFolder());
		env.put("COMPOSE_FILE", Paths.get(projectFolder,"docker-compose.yml").toAbsolutePath().toString());
		env.put("COMPOSE_PROJECT_NAME", projectName);

		//execute docker compose
		DefaultExecutor executor = new DefaultExecutor();

		ByteArrayOutputStream out=new ByteArrayOutputStream();
		ByteArrayOutputStream err=new ByteArrayOutputStream();

		PumpStreamHandler handler=new PumpStreamHandler(out,err);

		executor.setStreamHandler(handler);
		//		executor.setExitValue(0); //The process always exit with 0

		//kills a run-away process after sixty seconds.
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);

		executor.setWatchdog(watchdog);

		int exitValue = 0;

		//TODO: improve docker-compose error handling
		try {

			System.out.println(cmdLine.toString());
			exitValue = executor.execute(cmdLine,env);

			//
		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InputStream stdout=out.size() == 0 ? null : new ByteArrayInputStream(out.toByteArray());
		InputStream stderr=err.size() == 0 ? null : new ByteArrayInputStream(err.toByteArray());
		String outStr = null; 
		String errStr = null;
		try {
			if(stderr!=null)
				errStr = IOUtils.toString(stderr, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			if(stdout!=null)
				outStr = IOUtils.toString(stdout, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(exitValue);
		System.out.println("Out:");
		System.out.println(outStr);
		System.out.println("Err:");
		System.out.println(errStr);

		//Attach to logs to retrieve the logs and exit (when to exit???)
		//TODO: remove, currenlty disabled for testing purposes
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return projectFolder;

	}

	public String run(String projectFolder, String projectName, String service) {

		//get the docker-compose bin path
		Path dockerComposeExec = Paths.get(configuration.dockerComposeFolder,"docker-compose");

		//build the docker-compose command line
		CommandLine cmdLine = new CommandLine(dockerComposeExec.toString());

		cmdLine.addArgument("--verbose"); //TODO: testing, remove
		cmdLine.addArgument("run");
		cmdLine.addArgument(service);

		//build the env variables map we want to pass to docker-compose
		Map<String,String> env = new HashMap<String,String>();

		env.put("DOCKER_HOST", configuration.getDockerEndpoint());
		//TODO: remove, currenlty disabled for testing purposes
//		env.put("DOCKER_TLS_VERIFY",configuration.getDockerTLSVerify());
		//TODO: remove, currenlty disabled for testing purposes
//		env.put("DOCKER_CERT_PATH", configuration.getCertificatesFolder());
		env.put("COMPOSE_FILE", Paths.get(projectFolder,"docker-compose.yml").toAbsolutePath().toString());
		env.put("COMPOSE_PROJECT_NAME", projectName);

		//execute docker compose
		DefaultExecutor executor = new DefaultExecutor();

		ByteArrayOutputStream out=new ByteArrayOutputStream();
		ByteArrayOutputStream err=new ByteArrayOutputStream();

		PumpStreamHandler handler=new PumpStreamHandler(out,err);

		executor.setStreamHandler(handler);
		//		executor.setExitValue(0); //The process always exit with 0

		//kills a run-away process after sixty seconds.
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);

		executor.setWatchdog(watchdog);

		int exitValue = 0;

		//TODO: improve docker-compose error handling
		try {

			System.out.println(cmdLine.toString());
			exitValue = executor.execute(cmdLine,env);

			//
		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InputStream stdout=out.size() == 0 ? null : new ByteArrayInputStream(out.toByteArray());
		InputStream stderr=err.size() == 0 ? null : new ByteArrayInputStream(err.toByteArray());
		String outStr = null; 
		String errStr = null;
		try {
			if(stderr!=null)
				errStr = IOUtils.toString(stderr, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			if(stdout!=null)
				outStr = IOUtils.toString(stdout, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(exitValue);
		System.out.println("Out:");
		System.out.println(outStr);
		System.out.println("Err:");
		System.out.println(errStr);

		//Attach to logs to retrieve the logs and exit (when to exit???)


		return projectFolder;

	}

	//TODO: for now I direclty force the removal, this should be a specified parameter
	public String rm(String projectFolder, String projectName) {

		//get the docker-compose bin path
		Path dockerComposeExec = Paths.get(configuration.dockerComposeFolder,"docker-compose");

		//build the docker-compose command line
		CommandLine cmdLine = new CommandLine(dockerComposeExec.toString());

		//		cmdLine.addArgument("--verbose"); //TODO: testing, remove
		cmdLine.addArgument("rm");
		cmdLine.addArgument("-f");
		cmdLine.addArgument("-v");

		//build the env variables map we want to pass to docker-compose
		Map<String,String> env = new HashMap<String,String>();

		env.put("DOCKER_HOST", configuration.getDockerEndpoint());
		//TODO: remove, currenlty disabled for testing purposes
//		env.put("DOCKER_TLS_VERIFY",configuration.getDockerTLSVerify());
		//TODO: remove, currenlty disabled for testing purposes
//		env.put("DOCKER_CERT_PATH", configuration.getCertificatesFolder());
		env.put("COMPOSE_FILE", Paths.get(projectFolder,"docker-compose.yml").toAbsolutePath().toString());
		env.put("COMPOSE_PROJECT_NAME", projectName);

		//TODO: the following is similar to the previous method, refactor it extracting the method

		//execute docker compose
		DefaultExecutor executor = new DefaultExecutor();

		ByteArrayOutputStream out=new ByteArrayOutputStream();
		ByteArrayOutputStream err=new ByteArrayOutputStream();

		PumpStreamHandler handler=new PumpStreamHandler(out,err);

		executor.setStreamHandler(handler);
		//		executor.setExitValue(0); //The process always exit with 0

		//kills a run-away process after sixty seconds.
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);

		executor.setWatchdog(watchdog);

		int exitValue = 0;

		//TODO: improve docker-compose error handling
		try {

			System.out.println(cmdLine.toString());
			exitValue = executor.execute(cmdLine,env);

			//
		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InputStream stdout=out.size() == 0 ? null : new ByteArrayInputStream(out.toByteArray());
		InputStream stderr=err.size() == 0 ? null : new ByteArrayInputStream(err.toByteArray());
		String outStr = null; 
		String errStr = null;
		try {
			if(stderr!=null)
				errStr = IOUtils.toString(stderr, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			if(stdout!=null)
				outStr = IOUtils.toString(stdout, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(exitValue);
		System.out.println("Out:");
		System.out.println(outStr);
		System.out.println("Err:");
		System.out.println(errStr);

		//Attach to logs to retrieve the logs and exit (when to exit???)


		return projectFolder;

	}

	//TODO: for now I direclty force the removal, this should be a specified parameter
	public String stop(String projectFolder, String projectName) {

		//get the docker-compose bin path
		Path dockerComposeExec = Paths.get(configuration.dockerComposeFolder,"docker-compose");

		//build the docker-compose command line
		CommandLine cmdLine = new CommandLine(dockerComposeExec.toString());

		//			cmdLine.addArgument("--verbose"); //TODO: testing, remove
		cmdLine.addArgument("stop");

		//build the env variables map we want to pass to docker-compose
		Map<String,String> env = new HashMap<String,String>();

		env.put("DOCKER_HOST", configuration.getDockerEndpoint());
		//TODO: remove, currenlty disabled for testing purposes
//		env.put("DOCKER_TLS_VERIFY",configuration.getDockerTLSVerify());
		//TODO: remove, currenlty disabled for testing purposes
//		env.put("DOCKER_CERT_PATH", configuration.getCertificatesFolder());
		env.put("COMPOSE_FILE", Paths.get(projectFolder,"docker-compose.yml").toAbsolutePath().toString());
		env.put("COMPOSE_PROJECT_NAME", projectName);

		//TODO: the following is similar to the previous method, refactor it extracting the method

		//execute docker compose
		DefaultExecutor executor = new DefaultExecutor();

		ByteArrayOutputStream out=new ByteArrayOutputStream();
		ByteArrayOutputStream err=new ByteArrayOutputStream();

		PumpStreamHandler handler=new PumpStreamHandler(out,err);

		executor.setStreamHandler(handler);
		//			executor.setExitValue(0); //The process always exit with 0

		//kills a run-away process after sixty seconds.
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);

		executor.setWatchdog(watchdog);

		int exitValue = 0;

		//TODO: improve docker-compose error handling
		try {

			System.out.println(cmdLine.toString());
			exitValue = executor.execute(cmdLine,env);

			//
		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InputStream stdout=out.size() == 0 ? null : new ByteArrayInputStream(out.toByteArray());
		InputStream stderr=err.size() == 0 ? null : new ByteArrayInputStream(err.toByteArray());
		String outStr = null; 
		String errStr = null;
		try {
			if(stderr!=null)
				errStr = IOUtils.toString(stderr, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			if(stdout!=null)
				outStr = IOUtils.toString(stdout, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(exitValue);
		System.out.println("Out:");
		System.out.println(outStr);
		System.out.println("Err:");
		System.out.println(errStr);

		//Attach to logs to retrieve the logs and exit (when to exit???)


		return projectFolder;

	}

	//TODO: for now I direclty force the removal, this should be a specified parameter
	public String stop(String projectFolder, String projectName, String service) {

		//get the docker-compose bin path
		Path dockerComposeExec = Paths.get(configuration.dockerComposeFolder,"docker-compose");

		//build the docker-compose command line
		CommandLine cmdLine = new CommandLine(dockerComposeExec.toString());

		cmdLine.addArgument("--verbose"); //TODO: testing, remove
		cmdLine.addArgument("stop");
		cmdLine.addArgument(service);

		//build the env variables map we want to pass to docker-compose
		Map<String,String> env = new HashMap<String,String>();

		env.put("DOCKER_HOST", configuration.getDockerEndpoint());
		//TODO: remove, currenlty disabled for testing purposes		
//		env.put("DOCKER_TLS_VERIFY",configuration.getDockerTLSVerify());
		//TODO: remove, currenlty disabled for testing purposes
//		env.put("DOCKER_CERT_PATH", configuration.getCertificatesFolder());
		env.put("COMPOSE_FILE", Paths.get(projectFolder,"docker-compose.yml").toAbsolutePath().toString());
		env.put("COMPOSE_PROJECT_NAME", projectName);

		//TODO: the following is similar to the previous method, refactor it extracting the method

		//execute docker compose
		DefaultExecutor executor = new DefaultExecutor();

		ByteArrayOutputStream out=new ByteArrayOutputStream();
		ByteArrayOutputStream err=new ByteArrayOutputStream();

		PumpStreamHandler handler=new PumpStreamHandler(out,err);

		executor.setStreamHandler(handler);
		//					executor.setExitValue(0); //The process always exit with 0

		//kills a run-away process after sixty seconds.
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);

		executor.setWatchdog(watchdog);

		int exitValue = 0;

		//TODO: improve docker-compose error handling
		try {

			System.out.println(cmdLine.toString());
			exitValue = executor.execute(cmdLine,env);

			//
		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InputStream stdout=out.size() == 0 ? null : new ByteArrayInputStream(out.toByteArray());
		InputStream stderr=err.size() == 0 ? null : new ByteArrayInputStream(err.toByteArray());
		String outStr = null; 
		String errStr = null;
		try {
			if(stderr!=null)
				errStr = IOUtils.toString(stderr, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			if(stdout!=null)
				outStr = IOUtils.toString(stdout, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(exitValue);
		System.out.println("Out:");
		System.out.println(outStr);
		System.out.println("Err:");
		System.out.println(errStr);

		//Attach to logs to retrieve the logs and exit (when to exit???)


		return projectFolder;

	}

	public String port(final String projectFolder, final String projectName,
                       final String serviceName, final int privatePort) {

        //get the docker-compose bin path
        Path dockerComposeExec = Paths.get(configuration.dockerComposeFolder,"docker-compose");

        //build the docker-compose command line
        CommandLine cmdLine = new CommandLine(dockerComposeExec.toString());

        cmdLine.addArgument("port");
        cmdLine.addArgument(serviceName);
        cmdLine.addArgument(Integer.toString(privatePort));

        //build the env variables map we want to pass to docker-compose
        Map<String,String> env = new HashMap<String,String>();

        env.put("DOCKER_HOST", configuration.getDockerEndpoint());
        //TODO: remove, currenlty disabled for testing purposes
//		env.put("DOCKER_TLS_VERIFY",configuration.getDockerTLSVerify());
        //TODO: remove, currenlty disabled for testing purposes
//		env.put("DOCKER_CERT_PATH", configuration.getCertificatesFolder());
        env.put("COMPOSE_FILE", Paths.get(projectFolder,"docker-compose.yml").toAbsolutePath().toString());
        env.put("COMPOSE_PROJECT_NAME", projectName);

        //execute docker compose
        DefaultExecutor executor = new DefaultExecutor();

        ByteArrayOutputStream out=new ByteArrayOutputStream();
        ByteArrayOutputStream err=new ByteArrayOutputStream();

        PumpStreamHandler handler=new PumpStreamHandler(out,err);

        executor.setStreamHandler(handler);
        //					executor.setExitValue(0); //The process always exit with 0

        //kills a run-away process after sixty seconds.
        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);

        executor.setWatchdog(watchdog);

        int exitValue = 0;

        //TODO: improve docker-compose error handling
        try {

            System.out.println(cmdLine.toString());
            exitValue = executor.execute(cmdLine,env);

            //
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        InputStream stdout=out.size() == 0 ? null : new ByteArrayInputStream(out.toByteArray());
        InputStream stderr=err.size() == 0 ? null : new ByteArrayInputStream(err.toByteArray());
        String outStr = null;
        String errStr = null;
        try {
            if(stderr!=null)
                errStr = IOUtils.toString(stderr, "UTF-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            if(stdout!=null)
                outStr = IOUtils.toString(stdout, "UTF-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(exitValue);
        System.out.println("Out:");
        System.out.println(outStr);
        System.out.println("Err:");
        System.out.println(errStr);

        //Attach to logs to retrieve the logs and exit (when to exit???)
        return outStr; //outStr should be the private port printed to stdout, right?
    }
}
