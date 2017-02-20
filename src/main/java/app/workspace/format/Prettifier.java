package app.workspace.format;

import static java.lang.Integer.max;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class Prettifier {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public enum Style {
        json(Prettifier::prettifyJson),
        unknown(Prettifier::prettifyDefault);

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
            StringBuilder builder = new StringBuilder();

            int jsonStart = StringUtils.indexOf(input, '{');
            int jsonEnd = StringUtils.lastIndexOf(input, '}');
            String json = input.substring(jsonStart, jsonEnd);
            Object jsonObj = OBJECT_MAPPER.readValue(json, Object.class);

            builder.append(input.substring(0, jsonStart));
            builder.append(OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj));
            builder.append(input.substring(jsonEnd));

            output = builder.toString();
        } catch (IOException e) {
            output = prettifyDefault(input);
        }

        return output;
    }

    private static String prettifyDefault(String input) {

        StringBuilder builder = new StringBuilder();

        char[] chars = input.toCharArray();
        int indent = 0;

        int lineStart = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (ArrayUtils.contains(NEW_LINE_PREFIXES, c)) {
                // add this line to string builder
                String line = input.substring(lineStart, i+1).trim();
                builder.append(indent(indent)).append(line).append("\r\n");

                // manage indents
                if (ArrayUtils.contains(INCREASE_INDENT_CHARS, c)) {
                    indent += 1;
                }

                lineStart = i+1;
            } else if (ArrayUtils.contains(NEW_LINE_SUFFIXES, c)) {
                // add this line to string builder
                String line = input.substring(lineStart, i).trim();
                builder.append(indent(indent)).append(line).append("\r\n");

                if (ArrayUtils.contains(DECREASE_INDENT_CHARS, c)) {
                    indent = max(0, indent - 1);
                }

                lineStart = i;
            }
        }
        // add final line
        builder.append(indent(indent)).append(input.substring(lineStart));

        return builder.toString().trim();

    }

    private static String indent(int indent) {
        return IntStream.range(0, indent).mapToObj(i -> "  ").reduce("", String::concat);
    }

    private static final char[] INCREASE_INDENT_CHARS = {'{', '['};
    private static final char[] DECREASE_INDENT_CHARS = {'}', ']'};
    private static final char[] NEW_LINE_PREFIXES = {'{', '[', ','};
    private static final char[] NEW_LINE_SUFFIXES = {'}', ']'};

}
