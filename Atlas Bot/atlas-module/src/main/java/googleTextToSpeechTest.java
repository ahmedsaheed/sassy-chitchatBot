import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.protobuf.ByteString;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class googleTextToSpeechTest {

    @Test
    void authExplicit() {
        Storage x = StorageOptions.getDefaultInstance().getService();
        assertNotNull(x);
    }

    @Test
     void TTS() throws IOException, JavaLayerException {
        authExplicit();
        String TEXT = "hey";
        ByteString audioContents = googleTextToSpeech.TTS(TEXT);
        BufferedInputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(audioContents.toByteArray()));
        Player play = new Player(inputStream);
        // Assert

        assertFalse(audioContents.isEmpty());
        assertNotNull(inputStream);
        assertNotNull(play);
    }
}