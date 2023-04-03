package servicies;

import com.google.gson.Gson;
import entity.Todo;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class TodoService {
    private String baseUrl;
    private String todoEndpoint;

    private HttpClient httpClient;
    private Gson gson;

    public TodoService() {
        try {
            this.loadAPIProperties();
            this.setupHTTPClient();
        } catch (ConfigurationException e) {
            // we can catch different exceptions and do something else for each of them
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createTodo(Todo todo) throws Exception {
        // convert Todo object to JSON data type
        String requestBody = this.gson.toJson(todo);

        // create http request using httpRequest builder
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(this.todoEndpoint)) // specify the address to send the request
                .timeout(Duration.ofSeconds(30)) // how long should it wait for response from address / server / api
                .header("Content-Type", "application/json") // what configurations / settings are required from server are provided in header
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)) // the type og http request is specified e.g get post delete put etc. and the data if required is also provided
                .build(); // the final copy of the request is compiled and ready for sending
        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString()); // the request is sent and response is returned and stored in object

        Todo createdTodoItem = gson.fromJson(response.body(), Todo.class); // we try to extract the response json / body from the rresponse object

        if (createdTodoItem == null || createdTodoItem.get_id() == null) { // we check if the response was converted properly back to java object; thiis could also mean that everything was ok and API returned what we expect in case where it returns data
            throw new Exception("Failed to create todo item with code: " + response.statusCode());
        }

        if (response.statusCode() != 201){ // we can also check the status code
            throw new Exception("Request failed, API respond with code: " + response.statusCode());
        }
    }

    private void setupHTTPClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    private void loadAPIProperties() throws ConfigurationException {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
        propertiesConfiguration.load("application.properties");
        this.baseUrl = propertiesConfiguration.getString("api.baseUrl");
        this.todoEndpoint = this.baseUrl + propertiesConfiguration.getString("api.todoEndpoint");
    }
}
