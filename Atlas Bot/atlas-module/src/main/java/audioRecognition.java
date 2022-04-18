/*
**************************************CURRENTLY NOT IN USE************************************
*******************THE ALGORITHM WORKS FINE, UNCOMMENT TO TRY IT OUT**************************
 */
//import org.vosk.LibVosk;
//import org.vosk.LogLevel;
//import org.vosk.Model;
//import org.vosk.Recognizer;
//import javax.sound.sampled.*;
//import java.io.*;
//
///*
//########################################################################################
//CURRENT STAGE: COMPLETED 100%
//
//This is a trial of vosk audio recognition,
//Our aim is to collect speech from the pc's microphone & convert to text...
//Currently, this method works fine.
//Find some more details below
//1. Capturing Audio: https://docs.oracle.com/javase/tutorial/sound/capturing.html
//2. Vosk Model: https://alphacephei.com/vosk/
//3. Vosk demo github: https://github.com/alphacep/vosk-api/tree/master/java
//
//########################################################################################
//
// */
//public class audioRecognition {
//    static String result;
//
//    public static String getResult() {
//        return result;
//    }
//
//    public static void voice() throws IOException, UnsupportedAudioFileException {
//
//        //disable logs u can use LogLevel.DEBUG to debug
//        LibVosk.setLogLevel(LogLevel.WARNINGS);
//        //recognise input as audio format
//        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 60000, 16, 2, 4, 44100, false);
//        // add audio functionality
//        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
//        TargetDataLine microphone;
//        SourceDataLine speakers;
//
//        //our translation model
//        try ( Model model = new Model("atlas-module/model");
//              //recognises the audio
//             Recognizer recognizer = new Recognizer(model, 120000)) {
//            try {
//                //open the microphone line
//                microphone = (TargetDataLine) AudioSystem.getLine(info);
//                microphone.open(format);
//                microphone.start();
//
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                int numBytesRead;
//                int CHUNK_SIZE = 1024;
//                int bytesRead = 0;
//
//                DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
//                speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
//                speakers.open(format);
//                speakers.start();
//                byte[] b = new byte[4096];
//
//                while (bytesRead <= 800000) {
//                    numBytesRead = microphone.read(b, 0, CHUNK_SIZE);
//                    bytesRead += numBytesRead;
//                    if (recognizer.acceptWaveForm(b, numBytesRead)) {
//                        //System.out.println(recognizer.getResult().toString());
//                        result = recognizer.getResult();
//                        //System.out.println(audioRecognition.getResult());
//
//                    } else {
//                        //System.out.println(recognizer.getPartialResult());
//
//                    }
//                }
//
//                //System.out.println(recognizer.getFinalResult());
//                speakers.drain();
//                speakers.close();
//                microphone.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//}