package cloud.benchflow.compose.responses;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;

import cloud.benchflow.compose.responses.Up;
import io.dropwizard.jackson.Jackson;

public class UpTest {

	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final Up up = new Up("OK", "Sample Logs");

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/up.json"), Up.class));

        assertThat(MAPPER.writeValueAsString(up)).isEqualTo(expected);
    }
    
    @Test
    public void deserializesFromJSON() throws Exception {
    	final Up up = new Up("OK", "Sample Logs");
    	
        assertThat(MAPPER.readValue(fixture("fixtures/up.json"), Up.class)).isEqualTo(up);
    }
	
}
