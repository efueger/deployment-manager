package cloud.benchflow.deploymentmanager.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import cloud.benchflow.deploymentmanager.configurations.BenchflowDeploymentManagerConfiguration;
import cloud.benchflow.deploymentmanager.configurations.DockerConfiguration;

public class DockerModule extends AbstractModule {

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		
	}
	
	@Provides
    @Named("docker")
    public DockerConfiguration provideDocker(BenchflowDeploymentManagerConfiguration configuration) {
        return configuration.getDocker();
    }

}
