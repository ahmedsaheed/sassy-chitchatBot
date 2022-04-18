import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Objects;


public class WeatherApiNew {
    //Clothes arrays
    private final String[] humid ={"Hoodies","Pants","Beanie", "Swaters"};
    private final String[] warm ={"Tees","Short","Sunglasses", "Flip Flops"};
    private final String[] cold ={"Puffers","Gloves","Jackets", "Scarfs"};

    //method to make api requests
    public void getTemperatureForecast(String city, String country, String date) throws IOException {
        try {
            //preparing API request with desired arguments as city, country
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://community-open-weather-map.p.rapidapi.com/forecast?q="+city+"%2C"+country+"&units=metric")
                    .get()
                    .addHeader("X-RapidAPI-Key", "2c968d519bmsh1dc87eba6dd9971p14bd6ajsn8985feabd3ae")
                    .build();

            //stores responses as a string, then calls analysing method on it
            Response response = client.newCall(request).execute();
            String responseString = Objects.requireNonNull(response.body()).string();
            analysingResponse(responseString,city,date);

        }catch (Exception e) {
            //If any exception happens during api call, makes user try it again
            googleTextToSpeech.TTS( "Invalid city input, let's try again!");
            System.out.println("Atlas: Invalid city Input, Let's try again!");
            System.out.print("Atlas: ");
            AtlasBrain.weatherProgram();
        }
    }


    //method to convert API response string to JSON, then extract necessary data
    public void analysingResponse(String responseString, String city, String userDate) throws IOException {
        boolean dateFound = false;

        //turns API response string into JsonObject, then gets array with 39 forecast subArrays
        JSONObject jsonObject = new JSONObject(responseString);
        JSONArray listArray39 = jsonObject.getJSONArray("list");

        //loop through each of 39 arrays checking for user date of travel
        for (int i = 0; i <listArray39.length() ; i++) {
            JSONObject subArray =  listArray39.getJSONObject(i);
            String dateInSubArray = (String)subArray.get("dt_txt");
            //if date in array matches user input date. We found the correct date to forecast temperature
            if (dateInSubArray.contains(userDate)){
                dateFound = true;
                JSONObject mainTempArray = subArray.getJSONObject("main");
                Double temperature = (Double) mainTempArray.get("temp");
                String dates = dateInSubArray.substring(0,dateInSubArray.length()-9);
                googleTextToSpeech.TTS("The temperature will be "+temperature+"°C in "+city+" on "+dates);
                System.out.println("Atlas: The temperature will be "+temperature+"°C in "+city+" on "+dates);
                cloth(temperature, humid, warm, cold);
                break;
            }
        }
        //If date wasn't found in any of 39subArrays, output invalid date message
        if (!dateFound){
            googleTextToSpeech.TTS("Invalid date input, i can only get the forecast 5 days from today for " +city+". Let's try again");
            System.out.println("Invalid date input, i can only get the forecast 5 days from today for " +city+". Let's try again");
            AtlasBrain.weatherProgram();
        }
    }

    //method to output proper set of clothes for appropriate temperature.
    // Triggered in analysingResponse() when correct date is found
    static void cloth(Double temp, String[] humid, String[] warm, String[] cold) throws IOException {
        googleTextToSpeech.TTS("You should take this clothes in your baggage: ");
        System.out.print("Atlas: You should take this clothes in your baggage: ");
        if(temp > 15 && temp < 22){
            System.out.println("☀");
            for(String i : humid){
                googleTextToSpeech.TTS(i+", ");
                System.out.print(i+ ", ");
            }
        }
        else if(temp > 22){
            System.out.println("⛅");
            for(String i : warm){
                googleTextToSpeech.TTS(i+", ");
                System.out.print(i + ", ");
            }
        }else{
            System.out.println("❄️️");
            for(String i : cold){
                googleTextToSpeech.TTS(i+", ");
                System.out.print(i + ", ");
            }
        }
        System.out.println("");
    }


}
