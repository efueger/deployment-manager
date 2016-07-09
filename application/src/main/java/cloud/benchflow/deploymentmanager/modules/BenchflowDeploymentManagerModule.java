package cloud.benchflow.deploymentmanager.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import cloud.benchflow.deploymentmanager.configurations.BenchflowDeploymentManagerConfiguration;

public class BenchflowDeploymentManagerModule extends AbstractModule {

	@Override
	protected void configure() {
		// TODO Auto-generated method stub

	}
	
	@Provides
    @Named("projectFolder")
    public String provideProjectFolder(BenchflowDeploymentManagerConfiguration configuration) {
        return configuration.getProjectFolder();
    }

}
