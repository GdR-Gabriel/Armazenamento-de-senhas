import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class textoclaro-check {

    private static final String JSON_FILE = "textoclaro.json";

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java textoclaro-check <username> <password>");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(JSON_FILE));
            JSONArray usersArray = (JSONArray) obj;

            boolean userFound = false;

            // Iterate over JSON array to find matching username and password
            for (Object userObj : usersArray) {
                JSONObject user = (JSONObject) userObj;
                String storedUsername = (String) user.get("username");
                String storedPassword = (String) user.get("password");

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    userFound = true;
                    break;
                }
            }

            if (userFound) {
                System.out.println("User '" + username + "' found in " + JSON_FILE);
            } else {
                System.out.println("User '" + username + "' not found in " + JSON_FILE);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
