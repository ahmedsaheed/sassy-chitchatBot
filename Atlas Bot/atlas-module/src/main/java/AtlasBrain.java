import java.io.IOException;
import java.util.*;

public class AtlasBrain {
    //3 first methods do all casual chatting
    private static int message_probability(String[] user_message, String[] recognised_words, boolean single_response, String[] required_words){
        int message_certainty = 0;
        boolean has_required_words = true;

        //for each word in user message
        for (String word : user_message){
            //check if word is recognised
            for (String recognised_word : recognised_words) {
                if (recognised_word.equals(word)) {
                    message_certainty++;
                }
            }
        }

        //calculates the percentages of recognised words in a user message
        float percentage = (float)message_certainty / (float)recognised_words.length;

        //for each word in required words
        for(String word : required_words){
            boolean foundRequiredWord = false;
            for (String s : user_message) {
                //if word not in user message leave variable as false
                if (s.equals(word)) {
                    foundRequiredWord = true;
                }
            }

            //if user messages not contains required words
            if (!foundRequiredWord){
                //missing required word, prevents wrongly matching different sentences
                has_required_words = false;
                break;
            }
        }

        //check for matches
        if(has_required_words || single_response){
            return (int)(percentage*100);
        } else return 0;

    }
    private static String check_all_messages(String[] message){
        // creating a My HashTable Dictionary
        Hashtable<String, Integer> highest_prob_list = new Hashtable<String, Integer>();

        //inner class
        class InnerClass{
            public  void response(String bot_response, String[] list_of_words, boolean single_response, String[] required_words){
                highest_prob_list.put(bot_response,message_probability(message, list_of_words,single_response,required_words));
            }
        }

        InnerClass inner = new InnerClass();
        //responses----------------------------------------------------------
        //greetings
        inner.response("Hi there!", new String[]{"hi", "hello", "hii","heyo","hola"}, true, new String[]{""});
        inner.response("okay okay", new String[]{"ok","okok","okay"}, true, new String[]{""});
        inner.response("I am doing fine, and you?", new String[]{"how","are","you","doing"}, false, new String[]{"how"});
        inner.response("My name is Atlas!", new String[]{"what", "is", "your", "name"}, false, new String[]{"your","name"});
        inner.response(LongResponses.who, new String[]{"who", "are", "you"}, false, new String[]{"you","who"});
        inner.response("My life restarts everytime you run the program", new String[]{"how", "old","are","you"}, false, new String[]{"you","how","old"});
        inner.response("I am inside your computer", new String[]{"where","are","you"}, false, new String[]{"where","are","you"});
        inner.response("I consider myself irish", new String[]{"where","are","you","from"}, false, new String[]{"where","you","from"});
        inner.response("I am a full time chatterbot", new String[]{"what","is","your","job","work"}, true, new String[]{""});
        //polite
        inner.response("You are welcome", new String[]{"thanks", "thank","tks", "you"}, true, new String[]{""});
        inner.response("I love you too!", new String[]{"i", "love","you"}, false, new String[]{"love","you"});
        inner.response("Thank you!", new String[]{"you","are","smart","genius","clever","nice","pretty","beautiful","fine","cool","helpful"},false, new String[]{"you"});
        //favorites
        inner.response("my favorite colour is black", new String[]{"black","color", "colour","white", "red", "yellow", "blue", "green"},true, new String[]{""});
        inner.response(LongResponses.eating, new String[]{"what", "do", "you","like","eating","food","favorite"}, false, new String[]{"eating"});
        inner.response("I like animals! Dogs are my favorites", new String[]{"you","like","dog","dogs","cats","cats","animal","animals",}, true, new String[]{""});
        //joke
        inner.response(LongResponses.joke, new String[]{"joke","tell","can","you"}, false, new String[]{"joke"});
        inner.response("hahahhahaha", new String[]{"haha","hahaha","hahahah","hahah"}, true, new String[]{""});
        //bunch same type
        inner.response("That's very positive", new String[]{"smart","genius","clever","nice","pretty","beautiful","fine","cool","helpful","good"}, true, new String[]{""});
        inner.response("That doesn't sound good", new String[]{"bad","dumb","stupid","sad","terrible","awful","you","bad"}, true, new String[]{""});
        //creators
        inner.response("my creators! I like them", new String[]{"mauricio", "anibe", "ahmed", "creators"}, true, new String[]{""});

        //return key String of highest value
        String bestMatch = Collections.max(highest_prob_list.entrySet(), Map.Entry.comparingByValue()).getKey();

        //if the best match didn't achieve not even 1 percent of accuracy, return unknown
        if (highest_prob_list.get(bestMatch)<1){
            return LongResponses.unknown();
        }else return bestMatch;

    }
    public static String get_response(String userMessage) {
        String sanitize_message = userMessage.toLowerCase().trim().replaceAll("[^a-z\\s0-9]","");
        String[] split_message = sanitize_message.split(" ");
        return check_all_messages(split_message);
    }
    public static void weatherProgram() throws IOException {
        Main.location  = new ArrayList<>();
        Main.apiCall = new WeatherApiNew();
        //2 scanners necessary to fix bug when scanner next line will skip one line after a scan.nexInt
        Scanner scanner = new Scanner(System.in);
        int locationCount = 0;

        googleTextToSpeech.TTS("How many cities would you like to check");
        System.out.print("How many cities would you like to check?\nYou: ");
        //checks if user input is a number
        while (true) {
            try {
                locationCount = scanner.nextInt();
                break;
            }
            catch (InputMismatchException e) {
                googleTextToSpeech.TTS("Invalid input. Please reenter a valid number ");
                System.out.print("Atlas: Invalid input. Please reenter a valid number \nyou: ");
                scanner.nextLine();
            }
        }


        if(locationCount > 5){
            googleTextToSpeech.TTS(" I can't search more than 5 cities at a time. Let's try again");
            System.out.println("Atlas: I can't search more than 5 cities at a time. Let's try again");
            weatherProgram();
        }else {
            for (int i = 1; i <= locationCount; i++) {
                Scanner sc2 = new Scanner(System.in);
                googleTextToSpeech.TTS("Please Enter city "+i);

                System.out.print("Atlas: Please Enter city " +i+ "\nYou: ");
                String city = sc2.nextLine();
                googleTextToSpeech.TTS("Please Enter country code for" + city);
                System.out.print("Atlas: Please Enter country code for " + city + "\nYou: ");
                String code = sc2.next();
                googleTextToSpeech.TTS("Please Enter date of arrival to " + city);
                System.out.print("Atlas: Please Enter date of arrival (YYYY-MM-DD) to " + city + "\nYou: ");
                String date = sc2.next();
                Main.location.add(city);
                Main.location.add(code);
                Main.location.add(date);
            }


            for (int i = 0; i < Main.location.size(); i += 3) {
                Main.apiCall.getTemperatureForecast(Main.location.get(i), Main.location.get(i + 1), Main.location.get(i + 2));
            }

        }
    }
    public static void doJob() throws IOException {
        Scanner sc = new Scanner(System.in);
        googleTextToSpeech.TTS("Hello there, I am Atlas, we could have a conversation casually, i could also look up the weather for you. To do so just type in either 'weather', 'forecast' or 'temperature'. If you want to end the conversation use 'q' or 'exit' ");
        System.out.println("Atlas: Hello there \uD83D\uDC4B I am Atlas, we could have a conversation casually, \nI could also look up the weather for you. To do so just type in either 'weather', 'forecast' or 'temperature'.\nIf you want to end the conversation use 'q' or 'exit' \n");
        System.out.print("You: ");
        //infinite loop for chatting
        while(true){
            String userMessage = sc.nextLine();
            System.out.print("Atlas: ");
            //3 keywords to interrupt casual chat and start weather forecast program
            if(userMessage.equalsIgnoreCase("q") || userMessage.equalsIgnoreCase("exit")){
                System.out.println("GoodBye");
                System.exit(0);
            }else if (!(userMessage.toLowerCase().contains("weather")||userMessage.toLowerCase().contains("forecast")||userMessage.toLowerCase().contains("temperature"))){
                googleTextToSpeech.TTS(AtlasBrain.get_response(userMessage));
                System.out.println(AtlasBrain.get_response(userMessage));
            }
            else{
                AtlasBrain.weatherProgram();
            }
            System.out.print("you: ");
        }
    }
}

