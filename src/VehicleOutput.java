import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.*;
import java.util.List;
import java.lang.reflect.Type;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/rentalcars")
public class VehicleOutput {
    private List<Vehicle> vehicleList;

    public VehicleOutput() throws FileNotFoundException {
        Gson gson = new Gson();
        //BufferedReader bufferedReader;
        //bufferedReader = new BufferedReader(new FileReader("web\\WEB-INF\\src\\vehicles.json"));
        //InputStream inputStream = new FileInputStream("web\\WEB-INF\\src\\vehicles.json");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("vehicles.json")));
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(bufferedReader).getAsJsonObject().getAsJsonObject("Search").getAsJsonArray("VehicleList");
        Type listType = new TypeToken<List<Vehicle>>(){}.getType();
        //Converts the JSON array to a list of Vehicle objects
        vehicleList = gson.fromJson(jsonArray, listType);
    }

    // The Java method will process HTTP GET requests
    @GET
    @Path("/price")
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String getPriceOutput() {
        // Return some cliched textual content
        return vehicleList.get(0).getName();
    }
}