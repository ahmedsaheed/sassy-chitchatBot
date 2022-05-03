import com.base.AtlasBrain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtlasBrainTest {

    @Test
    void get_response1() {
        String atlasAnswer = AtlasBrain.get_response("how are you?");
        assertEquals("I am doing fine, and you?", atlasAnswer);
    }
    @Test
    void get_response2() {
        String atlasAnswer = AtlasBrain.get_response("what is your name?");
        assertEquals("My name is Atlas!", atlasAnswer);
    }
    @Test
    void get_response3() {
        String atlasAnswer = AtlasBrain.get_response("Where you from?");
        assertEquals("I consider myself irish", atlasAnswer);
    }


}