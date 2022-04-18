import java.util.Random;
public class LongResponses {
    static String eating = "I don't like eating anything because I'm a bot obviously!";
    static String who = "I am Atlas! Your personal bot. How may I help you today?";
    static String joke = "How many programmers does it take to screw in a light bulb? None. It's a hardware problem.";
    public static String unknown(){
        String[] response = {"Could you please re-phrase that?",
                "...","Sounds about right","What does that mean?"};
        int rnd = new Random().nextInt(response.length);
        return response[rnd];
    }
}
