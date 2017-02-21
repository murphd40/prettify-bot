package integration;

import java.io.IOException;

import app.resources.MessageTypes;
import app.workspace.model.WebhookEvent;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

public class MyTest extends BaseIntegrationTest{

    @Test
    public void myTest() throws IOException {

        WebhookEvent event = new WebhookEvent();
        event.setContent("@prettify {\"key\": \"value\"}");
        event.setType(MessageTypes.MESSAGE_CREATED);
        Call<Void> call = testRequestService.webhookRequest(event);

        Response<Void> response = call.execute();

        int x = 5;

    }

    @Test
    public void debugTest() throws IOException {

        WebhookEvent event = new WebhookEvent();
        event.setContent("@prettify mutation {\n" +
            "createSpace(\n" +
            "input: {\n" +
            "title: \"title\"\n" +
            "members: []\n" +
            ")\n" +
            "}");
        event.setType(MessageTypes.MESSAGE_CREATED);
        Call<Void> call = testRequestService.webhookRequest(event);

        Response<Void> response = call.execute();

        int x = 5;

    }

}
