package cloud.benchflow.deploymentmanager;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import cloud.benchflow.deploymentmanager.configurations.BenchflowDeploymentManagerConfiguration;
import cloud.benchflow.deploymentmanager.modules.BenchflowDeploymentManagerModule;
import cloud.benchflow.deploymentmanager.modules.DockerModule;
import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class BenchflowDeploymentManagerApplication extends Application<BenchflowDeploymentManagerConfiguration> {

	public static void main(String[] args) throws Exception {
		new BenchflowDeploymentManagerApplication().run(args);
	}

	@Override
	public String getName() {
		return "benchflow-deployment-manager";
	}

	@Override
	public void initialize(Bootstrap<BenchflowDeploymentManagerConfiguration> bootstrap) {
		
		//We use this plugin to manage environment variables. It's more powerful that the method provided by Dropwizard
		bootstrap.addBundle(new TemplateConfigBundle());
		
		//Configuration injector
		GuiceBundle<BenchflowDeploymentManagerConfiguration> guiceBundle = GuiceBundle.<BenchflowDeploymentManagerConfiguration>builder()
				.enableAutoConfig("cloud.benchflow.deploymentmanager")
                .modules(new BenchflowDeploymentManagerModule(), new DockerModule())
                .build();
		
        bootstrap.addBundle(guiceBundle);
	
	}

	@Override
	public void run(BenchflowDeploymentManagerConfiguration configuration, Environment environment) throws Exception {
		
		environment.jersey().register(MultiPartFeature.class);  

	}

}
