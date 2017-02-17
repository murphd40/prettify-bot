package util;

import javax.ws.rs.core.Response;

import app.workspace.model.Message;
import app.workspace.model.WebhookEvent;
import retrofit2.Call;
import retrofit2.http.Body;
//import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
//import retrofit2.http.Path;

public interface TestRequestService {

    @Headers({"Content-Type: application/json"})
    @POST("/webhook")
    Call<Void> webhookRequest(@Body WebhookEvent webhookEvent);

}
