import java.io.FileReader;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class bcrypt-check {

    private static final String JSON_FILE = "bcrypt.json";

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java bcrypt-check <username> <password>");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(JSON_FILE));
            JSONArray usersArray = (JSONArray) obj;

            boolean userFound = false;

            // Iterate over JSON array to find matching username
            for (Object userObj : usersArray) {
                JSONObject user = (JSONObject) userObj;
                String storedUsername = (String) user.get("username");
                String storedHashedPassword = (String) user.get("password");
                String salt = (String) user.get("salt");

                if (storedUsername.equals(username)) {
                    // Check if password matches hashed password
                    if (BCrypt.checkpw(password, storedHashedPassword)) {
                        userFound = true;
                        break;
                    }
                }
            }

            if (userFound) {
                System.out.println("User '" + username + "' found in " + JSON_FILE);
            } else {
                System.out.println("User '" + username + "' not found in " + JSON_FILE + " or password does not match.");
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
