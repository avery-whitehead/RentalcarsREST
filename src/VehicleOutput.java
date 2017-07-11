import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.stream.Collectors;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/rentalcars")
public class VehicleOutput {
    private List<Vehicle> vehicleList;

    public VehicleOutput() throws FileNotFoundException {
        Gson gson = new Gson();
        //Special BufferedReader for reading from a web file
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("vehicles.json")));
        JsonParser jsonParser = new JsonParser();
        //Creates a JSON array out of the JSON file
        JsonArray jsonArray = jsonParser.parse(bufferedReader).getAsJsonObject().getAsJsonObject("Search").getAsJsonArray("VehicleList");
        Type listType = new TypeToken<List<Vehicle>>(){}.getType();
        //Converts the JSON array to a list of Vehicle objects
        vehicleList = gson.fromJson(jsonArray, listType);
    }

    /**
     *
     * @return
     */
    @GET
    @Path("/price")
    @Produces("text/plain")
    public String getPriceOutput() {
        String priceOutput = "";
        //Sorts the Vehicle list in ascending order by price
        vehicleList.sort(Comparator.comparing(Vehicle::getPrice));
        //Builds a list of the car prices in the appropriate format
        for (int i = 0; i < vehicleList.size(); i++) {
            priceOutput = priceOutput + (i + 1) + ". " + vehicleList.get(i).getName() + " - " + vehicleList.get(i).getPrice() + "\n";
        }
        return priceOutput;
    }

    @GET
    @Path("/sipp")
    @Produces("text/plain")
    public String getSippOutput() {
        String sippOutput = "";
        //Calculates and sets the SIPP specifications
        for (int i = 0; i < vehicleList.size(); i++) {
            vehicleList.get(i).setCarType(vehicleList.get(i).calculateCarType());
            vehicleList.get(i).setDoors(vehicleList.get(i).calculateDoors());
            vehicleList.get(i).setTransmission(vehicleList.get(i).calculateTransmission());
            vehicleList.get(i).setFuel(vehicleList.get(i).calculateFuel());
            vehicleList.get(i).setAirCon(vehicleList.get(i).calculateAirCon());
            //Builds the SIPP specifications in the appropriate format
            //Line breaks work in the page source but not the page display, don't know why
            sippOutput = sippOutput + (i + 1) + ". " + vehicleList.get(i).getName() + " - " + vehicleList.get(i).getSipp() +
                    " - " + vehicleList.get(i).getCarType() + " - " + vehicleList.get(i).getDoors() + " - " +
                    vehicleList.get(i).getTransmission() + " - " + vehicleList.get(i).getFuel() + " - " +
                    vehicleList.get(i).getAirCon() + "\n" ;
        }
        return sippOutput;
    }

    @GET
    @Path("/supplier")
    @Produces("text/plain")
    public String getSupplierOutput() {
        String supplierOutput = "";
        //Makes a set of the unique car types in the Vehicles list
        //Only six instead of the seven listed in the document. Could include the extra car types included in
        //the second SIPP letter, but then there would be more than seven.
        Set<String> carTypesSet = vehicleList.stream().map(Vehicle::getCarType).collect(Collectors.toSet());
        //Converts the set to an ArrayList for easy indexing
        ArrayList<String> carTypesArray = new ArrayList<>(carTypesSet);
        //Makes a temporary ArrayList for checking each car type
        ArrayList<Vehicle> tempArrayList = new ArrayList<>();
        //Makes another ArrayList for holding the highest rated cars
        ArrayList<Vehicle> maxArrayList = new ArrayList<>();
        //return carTypesArray.get(0);
        return "hi " + vehicleList.get(0).getCarType();

        //Finds the highest rated vehicle of each car type
        /*for (String aCarTypesArray : carTypesArray) {
            //Adds every vehicle of each car type to the temporary ArrayList
            for (Vehicle vehicle : vehicleList) {
                if (vehicle.getCarType().equals(aCarTypesArray)) {
                    tempArrayList.add(vehicle);
                }
            }
            //Gets the vehicle with the highest rating from the temporary ArrayList and adds it to the ArrayList
            maxArrayList.add(tempArrayList.stream().max(Comparator.comparing(Vehicle::getRating)).get());
            //Resets the temporary array for the next loop
            tempArrayList.clear();
        }
        //Sorts the max-rating ArrayList in descending order according to rating
        maxArrayList.sort(Comparator.comparing(Vehicle::getRating).reversed());
        //Prints a list of the car ratings in the appropriate format
        for (int i = 0; i < maxArrayList.size(); i++) {
            supplierOutput = supplierOutput + (i + 1) + ". " + maxArrayList.get(i).getName() + " - " + maxArrayList.get(i).getCarType() +
                    " - " + maxArrayList.get(i).getSupplier() + " - " + maxArrayList.get(i).getRating() + "\n";
        }
        return supplierOutput;
    }*/
    }
}