package cloud.benchflow.compose;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import cloud.benchflow.compose.configurations.BenchflowComposeConfiguration;
import cloud.benchflow.compose.modules.BenchflowComposeModule;
import cloud.benchflow.compose.modules.DockerModule;
import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class BenchflowComposeApplication extends Application<BenchflowComposeConfiguration> {

	public static void main(String[] args) throws Exception {
		new BenchflowComposeApplication().run(args);
	}

	@Override
	public String getName() {
		return "benchflow-compose";
	}

	@Override
	public void initialize(Bootstrap<BenchflowComposeConfiguration> bootstrap) {
		
		//We use this plugin to manage environment variables. It's more powerful that the method provided by Dropwizard
		bootstrap.addBundle(new TemplateConfigBundle());
		
		//Configuration injector
		GuiceBundle<BenchflowComposeConfiguration> guiceBundle = GuiceBundle.<BenchflowComposeConfiguration>builder()
				.enableAutoConfig("cloud.benchflow.compose")
                .modules(new BenchflowComposeModule(), new DockerModule())
                .build();
		
        bootstrap.addBundle(guiceBundle);
	
	}

	@Override
	public void run(BenchflowComposeConfiguration configuration, Environment environment) throws Exception {
		
		environment.jersey().register(MultiPartFeature.class);  

	}

}
