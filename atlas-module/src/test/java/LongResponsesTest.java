import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LongResponsesTest {

    @Test
    void unknown() {
        //given
        String[] response = {"Could you please re-phrase that?",
                "...","Sounds about right","What does that mean?"};
        //while
        int rnd = new Random().nextInt(response.length);
        System.out.println(response[rnd]);
        //assert
        assertNotNull(response[rnd]);
        assertTrue(response[rnd].equals(response[0]) ||
                            response[rnd].equals(response[1]) ||
                            response[rnd].equals(response[2]) ||
                            response[rnd].equals(response[3]));
    }
}