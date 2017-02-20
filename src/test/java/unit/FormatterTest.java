package unit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import app.workspace.format.Prettifier;
import org.junit.Ignore;
import org.junit.Test;

public class FormatterTest {

    @Test
    public void unknownStringTest() {
        String input = "not json";

        String result = Prettifier.prettify(input, Prettifier.Style.unknown);

        assertThat(result, is(input));
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
    public void invalidJsonTest() {

        String input = "{\n" +
            "\t\"key1\": \"value1\"\n" +
            "\t\"key2\": \"value2\"\n" +
            "}";

        String result = Prettifier.prettify(input, Prettifier.Style.json);

        assertThat(result, is("Failed to parse text. Invalid JSON."));

    }

}