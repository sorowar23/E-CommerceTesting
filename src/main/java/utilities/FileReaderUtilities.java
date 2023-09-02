package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class FileReaderUtilities {

    private FileReaderUtilities() {
    }

    public static String readTextFromFileInResource(final String file) {
        String fullText = "";
        BufferedReader reader = null;
        InputStreamReader oReader = null;

        try {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            final InputStream inputStream = classLoader.getResourceAsStream(file);
            oReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(oReader);
            final StringBuffer sbf = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sbf.append(line);
            }
            fullText = sbf.toString();
            reader.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (oReader != null) {
                    oReader.close();
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return fullText;
    }

    public static JsonNode readJSONFile(final String file)
    {
        JsonNode node = null;
        String fileContent = "";
        InputStreamReader oReader = null;
        BufferedReader bufferedReader = null;
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            final InputStream inputStream = classLoader.getResourceAsStream(file);

            oReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(oReader);

            String currentLine = "";
            while ((currentLine = bufferedReader.readLine()) != null)
            {
                fileContent = fileContent.concat(currentLine);
            }
            node = objectMapper.readTree(fileContent);
            bufferedReader.close();
            oReader.close();

        }
        catch (JsonMappingException e)
        {
            e.printStackTrace();
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (oReader != null) {
                    oReader.close();
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return node;
    }

    public static JsonNode readAllNodesFromJsonFile(final String filename) {
        final JsonNode node = readJSONFile(filename);
        return node;
    }

    public static String readTextFromFile(final String file) {
        String fullText = "";

        return fullText;
    }
}
