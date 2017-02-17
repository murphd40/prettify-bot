package util;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import app.workspace.WorkspaceService;
import app.workspace.model.Message;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class MockWorkspaceService implements WorkspaceService {

    private List<Message> interceptedMessages;

    public MockWorkspaceService(List<Message> interceptedMessages) {
        this.interceptedMessages = interceptedMessages;
    }

    @Override
    public Call<Message> createMessage(@Header("Authorization") String authToken, @Path("spaceId") String spaceId, @Body Message message) {
        Optional.ofNullable(interceptedMessages).ifPresent(list -> list.add(message));
        return new AbstractMockCall<Message>() {
            @Override
            public Response<Message> execute() throws IOException {
                return Response.success(message);
            }
        };
    }

}
