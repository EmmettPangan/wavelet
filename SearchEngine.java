import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new WordHandler());
    }
}

class WordHandler implements URLHandler {
    ArrayList<String> words = new ArrayList<String>();
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "List of all words: " + words;
        } else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("word")) {
                words.add(parameters[1]);
                return String.format("%s added to the list of words!", parameters[1]);
            }
        } else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("word")) {
                ArrayList<String> matchedWords = new ArrayList<String>();
                for (String word : words) {
                    if (word.contains(parameters[1])) {
                        matchedWords.add(word);
                    }
                }
                return "List of matching words: " + matchedWords;
            }
        }
        return "404 Error";
    }
}