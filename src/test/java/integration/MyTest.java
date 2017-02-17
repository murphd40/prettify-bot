package integration;

import java.io.IOException;

import app.workspace.model.WebhookEvent;
import org.junit.Ignore;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

@Ignore
public class MyTest extends BaseIntegrationTest{

    @Test
    public void myTest() throws IOException {

        WebhookEvent event = new WebhookEvent();
        event.setContent("content");
        Call<Void> call = testRequestService.webhookRequest(event);

        Response<Void> response = call.execute();

        int x = 5;

    }

}
