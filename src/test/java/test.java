import com.fasterxml.jackson.databind.JsonNode;
import core.runnable.SpaceFact;

import java.io.IOException;
import java.net.URISyntaxException;

public class test {

    static int counter = 0;

    public static void main(String[] args) throws InterruptedException, URISyntaxException, IOException {
        JsonNode fact = SpaceFact.getSpaceFact();
        System.out.println(fact.asText("title"));
        System.out.println(fact.asText("url"));
    }
}
