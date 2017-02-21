package unit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import app.workspace.format.Prettifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;

public class FormatterTest {

    @Test
    public void defaultStyleSimpleTest() {
        String input = "not json";

        String result = Prettifier.prettify(input, Prettifier.Style.unknown);

        assertThat(result, is(input));
    }

    @Test
    public void defaultStyleJsonTest() {
        String input = "{\"key1\":\"value1\"            \n\n\n\n,\"inner\":{\"innerKey1\":\"innerValue1\"},\"list\":[{\"entry\":\"1\"},{\"entry\":\"2\"}]}";

        String result = Prettifier.prettify(input, Prettifier.Style.unknown);

//        assertThat(result, is(input));
    }

    @Ignore
    @Test
    public void jsonTest() {
        String input = "{\"key1\":\"value1\",\"inner\":{\"innerKey1\":\"innerValue1\"},\"list\":[{\"entry\":\"1\"},{\"entry\":\"2\"}]}";

        String result = Prettifier.prettify(input, Prettifier.Style.json);

        String expected =
            "{\r\n" +
            "  \"key1\" : \"value1\",\r\n" +
            "  \"inner\" : {\r\n" +
            "    \"innerKey1\" : \"innerValue1\"\r\n" +
            "  },\r\n" +
            "  \"list\" : [ {\r\n" +
            "    \"entry\" : \"1\"\r\n" +
            "  }, {\r\n" +
            "    \"entry\" : \"2\"\r\n" +
            "  } ]\r\n" +
            "}";

        assertThat(result, is(expected));
    }

    @Test
    public void jsonWithExtraTest() {

        String input = "data {\n" +
            "\t\"key1\": \"value1\",\n" +
            "\t\"key2\": \"value2\"\n" +
            "} hello";

        String result = Prettifier.prettify(input, Prettifier.Style.json);

//        assertThat(result, is("Failed to parse text. Invalid JSON."));

    }

}
