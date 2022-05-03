package com.base;
import com.clivern.wit.api.App;
import com.clivern.wit.api.Entity;
import com.clivern.wit.api.Message;
import com.clivern.wit.api.endpoint.AppEndpoint;
import com.clivern.wit.api.endpoint.EntityEndpoint;
import com.clivern.wit.api.endpoint.MessageEndpoint;
import com.clivern.wit.exception.DataNotFound;
import com.clivern.wit.exception.DataNotValid;
import com.clivern.wit.util.Config;
import com.clivern.wit.Wit;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import static com.base.AtlasBrain.weatherProgram;

public class dateCoverter{
    static String realDate = "";
    public static String getRealDate() {
        return realDate;
    }


    public static void date(String date) throws Exception {
        Config config = new Config();
        config.loadPropertiesFile("/Users/ahmedsaheed/Desktop/Desktop/Griffith/real_BOT/atlas-module/config.properties");
        config.configLogger();
        Wit wit = new Wit(config);
        App getApp = new App(AppEndpoint.GET);
        Entity entity = new Entity(EntityEndpoint.GET_ENTITY);
        entity.setEntityId("datetime");
        Message message = new Message(MessageEndpoint.GET);
        message.setQ(date);
        String result = "";
        String error = "";
        String answer = "";

        if (wit.send(entity)) {
            result = wit.getResponse();
            //System.out.println("Result:" + result);
        } else {
            error = wit.getError();
            System.out.println("Wit Error : " + error);
        }

        if (wit.send(message)) {
            answer = wit.getResponse();
            //System.out.println(answer);
        } else {
            System.out.println("Wit Error : "+wit.getError());
        }
        JSONObject jsonObject = new JSONObject(answer);
        JSONObject entities = jsonObject.getJSONObject("entities");
        JSONArray datetime = entities.getJSONArray("datetime");
        String val = "";
        for (int i = 0; i < datetime.length(); i++) {
            JSONObject subArray = datetime.getJSONObject(i);
            if(subArray.has("value")){
                val = (String) subArray.get("value");
            }else{
                googleTextToSpeech.TTS("I can't resolve that date, let's try again");
                System.out.println("Atlas: I can't resolve that date, let's try again");
                weatherProgram();
            }

        }
        realDate = val.substring(0, val.length() - 19);
    }

}