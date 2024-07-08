import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class textoclaro-add {

    private static final String JSON_FILE = "textoclaro.json";

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java textoclaro-add <username> <password>");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        JSONObject newUser = new JSONObject();
        newUser.put("username", username);
        newUser.put("password", password);

        try {
            File file = new File(JSON_FILE);
            JSONArray usersArray;

            if (file.exists()) {
                // Read existing JSON file
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new FileReader(JSON_FILE));
                usersArray = (JSONArray) obj;
            } else {
                // Create new JSON file
                usersArray = new JSONArray();
            }

            // Add new user to JSON array
            usersArray.add(newUser);

            // Write JSON array to file
            FileWriter fileWriter = new FileWriter(JSON_FILE);
            fileWriter.write(usersArray.toJSONString());
            fileWriter.close();

            System.out.println("User '" + username + "' added to " + JSON_FILE);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
