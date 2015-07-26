package cloud.benchflow.compose.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import cloud.benchflow.compose.configurations.BenchflowComposeConfiguration;

public class BenchflowComposeModule extends AbstractModule {

	@Override
	protected void configure() {
		// TODO Auto-generated method stub

	}
	
	@Provides
    @Named("projectFolder")
    public String provideProjectFolder(BenchflowComposeConfiguration configuration) {
        return configuration.getProjectFolder();
    }

}
