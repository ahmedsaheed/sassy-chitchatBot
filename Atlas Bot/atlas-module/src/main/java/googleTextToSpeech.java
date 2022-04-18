import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.texttospeech.v1beta1.*;
import com.google.protobuf.ByteString;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.*;


/*
################################################################
STAGE: CONSTRUCTION COMPLETE!!!!
Here we have our Google texts to speech running, yay!!!
the authExplicit() helps us get the services from the Google console,
while the TTS() is where the main work is done.
################################################################
 */
public class googleTextToSpeech {
    static void authExplicit() throws IOException {
        //get the google cloud service account, get the API json file & complete the configuration 
    }
    public static ByteString TTS(String word) throws IOException {
        ByteString audioContents = null;
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder().setText(word).build();

            // Build the voice request
            VoiceSelectionParams voice =
                    VoiceSelectionParams.newBuilder()
                            .setLanguageCode("en-US") // languageCode = "en_us"
                            .setSsmlGender(SsmlVoiceGender.FEMALE) // ssmlVoiceGender = SsmlVoiceGender.FEMALE
                            .build();

            // Select the type of audio file you want returned
            AudioConfig audioConfig =
                    AudioConfig.newBuilder()
                            .setAudioEncoding(AudioEncoding.MP3) // MP3 audio.
                            .build();

            // Perform the text-to-speech request
            SynthesizeSpeechResponse response =
                    textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // Get the audio contents from the response
            audioContents = response.getAudioContent();

            // Write the response to the output file.
            BufferedInputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(audioContents.toByteArray()));
            Player player = new Player(inputStream);
            player.play();


        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
        return audioContents;
    }
}
