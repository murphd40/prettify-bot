package integration;

import java.util.ArrayList;
import java.util.List;

import app.WorkspaceApplication;
import app.WorkspaceConfiguration;
import app.workspace.WorkspaceService;
import app.workspace.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.setup.Environment;
import okhttp3.HttpUrl;
import org.junit.Before;
import org.junit.BeforeClass;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import util.MockWorkspaceService;
import util.TestRequestService;

public abstract class BaseIntegrationTest {

    protected TestRequestService testRequestService;

    protected static List<Message> interceptedMessages = new ArrayList<>();

    @BeforeClass
    public static void startServer() throws Exception {
        try {
            new WorkspaceApplication(){
                @Override
                protected void initServices(WorkspaceConfiguration config, Environment environment) {
                    super.initServices(config, environment);
                    super.workspaceService = new MockWorkspaceService(interceptedMessages);
                }
            }.run("server", "config.yml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public final void baseInit() {
        ObjectMapper mapper = new ObjectMapper();
        HttpUrl baseUrl = new HttpUrl.Builder().host("localhost").port(8080).scheme("http").build();
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(JacksonConverterFactory.create(mapper)).baseUrl(baseUrl).build();

        testRequestService = retrofit.create(TestRequestService.class);
    }

}
