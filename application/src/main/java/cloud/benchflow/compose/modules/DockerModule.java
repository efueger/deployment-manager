package cloud.benchflow.compose.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import cloud.benchflow.compose.configurations.BenchflowComposeConfiguration;
import cloud.benchflow.compose.configurations.DockerConfiguration;

public class DockerModule extends AbstractModule {

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		
	}
	
	@Provides
    @Named("docker")
    public DockerConfiguration provideDocker(BenchflowComposeConfiguration configuration) {
        return configuration.getDocker();
    }

}
