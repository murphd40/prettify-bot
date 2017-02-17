package app.workspace.format;

import java.io.IOException;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Prettifier {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public enum Style {
        json(Prettifier::prettifyJson),
        unknown(Function.identity());

        private Function<String, String> converter;

        Style(Function<String, String> converter) {
            this.converter = converter;
        }

        private String format(String input) {
            return converter.apply(input);
        }
    }

    public static String prettify(final String content, Style style) {
        return style.format(content);
    }

    private static String prettifyJson(String input) {

        String output;

        try {
            Object json = OBJECT_MAPPER.readValue(input, Object.class);
            output = OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (IOException e) {
            output = "Failed to parse text. Invalid JSON.";
        }

        return output;
    }

}
