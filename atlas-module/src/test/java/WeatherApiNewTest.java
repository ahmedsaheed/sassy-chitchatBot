import com.base.AtlasBrain;
import com.base.googleTextToSpeech;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class WeatherApiNewTest {
    //given....
    String city = "london";
    String country = "UK";
    String KEY = "2c968d519bmsh1dc87eba6dd9971p14bd6ajsn8985feabd3ae";
    String name = "X-RapidAPI-Key";
    LocalDate date = java.time.LocalDate.now();
    private final String[] humid = {"Hoodies", "Pants", "Beanie", "Swaters"};
    private final String[] warm = {"Tees", "Short", "Sunglasses", "Flip Flops"};
    private final String[] cold = {"Puffers", "Gloves", "Jackets", "Scarfs"};


    @Test
    void getTemperatureForecast() {
        //while
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://community-open-weather-map.p.rapidapi.com/forecast?q=" + city + "%2C" + country + "&units=metric")
                    .get()
                    .addHeader(name, KEY)
                    .build();
            Response response = client.newCall(request).execute();
            String responseString = Objects.requireNonNull(response.body()).string();


            /*
            Assert that both city,country & response string are not empty
            Also assert that response is successful else handle with catch block
             */
            assertFalse(city.isEmpty());
            assertFalse(country.isEmpty());
            assertNotNull(responseString);
            assertTrue(response.isSuccessful());
            analysingResponse(responseString);
        } catch (Exception e) {
            /*
             *Catch handles exception and make sure it's caught
             *Make sure that if there's an exception, bot log error to user
             *Error can't be empty
             */
            assertNotEquals("", e.getMessage());
            System.out.println(e.getMessage());
            System.out.println();
            //TODO: Add the remaining once tested.
        }

    }

    /*
    We ignore here because of response parameter can only be gotten from getTemperatureForecast
    So therefore the test is executed while when called in  getTemperatureForecast() method.
     */
    @Ignore
    void analysingResponse(String response) throws Exception {
        boolean dateFound = false;
        //turns API response string into JsonObject, then gets array with 39 forecast subArrays
        JSONObject jsonObject = new JSONObject(response);
        JSONArray listArray39 = jsonObject.getJSONArray("list");

        for (int i = 0; i < listArray39.length(); i++) {
            JSONObject subArray = listArray39.getJSONObject(i);
            String dateInSubArray = (String) subArray.get("dt_txt");
            //if date in array matches user input date. We found the correct date to forecast temperature
            if (dateInSubArray.contains(date.toString())) {
                dateFound = true;
                JSONObject mainTempArray = subArray.getJSONObject("main");
                Double temperature = (Double) mainTempArray.get("temp");
                googleTextToSpeech.TTS("The temperature will be " + temperature + "°C in " + city + " on " + dateInSubArray);
                System.out.println("Atlas: The temperature will be " + temperature + "°C in " + city + " on " + dateInSubArray);
                cloth(temperature);
                break;
            }

            //assert
            assertNotNull(jsonObject);
            assertEquals(40, listArray39.length());
            assertTrue(dateFound);//if not found handle with if block below
        }
        //If date wasn't found in any of 39subArrays, output invalid date message
        if (!dateFound) {
            googleTextToSpeech.TTS("Invalid date input, maximum 5 days ahead forecast. Let's try again");
            System.out.println("Invalid date input, maximum 5 days ahead forecast");
            AtlasBrain.weatherProgram();
        }


    }

    @Ignore
     /*
    We ignore here because temperature parameter can only be gotten from analysingResponse()
    So therefore the cloth() test is simultaneously executed  when called in  analysingResponse() method.
     */
    void cloth(Double temperature) throws IOException {
        //while
        googleTextToSpeech.TTS("You should take this clothes in your baggage: ");
        System.out.print("Atlas: You should take this clothes in your baggage: ");
        if (temperature > 15 && temperature < 22) {
            for (String i : humid) {
                googleTextToSpeech.TTS(i + ", ");
                System.out.print(i + ", ");
            }
        } else if (temperature > 22) {
            for (String i : warm) {
                googleTextToSpeech.TTS(i + ", ");
                System.out.print(i + ", ");
            }
        } else {
            for (String i : cold) {
                googleTextToSpeech.TTS(i + ", ");
                System.out.print(i + ", ");
            }
        }
        //assert
        assertNotNull(temperature);
        assertNotNull(humid);
        assertNotNull(warm);
        assertNotNull(cold);
    }
}
