import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList <String> listStrings = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("SearchList: %d", listStrings);
        } else if (url.getPath().equals("/add")) {
            String[] str=url.getQuery().split("=");
            if (str[0].equals("s")){
                listStrings.add(str[1]);
            }
            return String.format("String added!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] searched = url.getQuery().split("=");
                if (listStrings.contains(searched)) {
                    return String.format("Number increased by %s! It's now %d", searched[1], listStrings);
                }
            }
            return "404 Not Found!";
        }
    }
}

class NumberServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
