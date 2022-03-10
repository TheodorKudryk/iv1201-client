package com.iv1201.client.integration;

import com.iv1201.client.model.ApplicationDTO;
import com.iv1201.client.model.Competence;
import com.iv1201.client.model.Person;
import com.iv1201.client.model.UserDTO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject; 

/**
 *
 * @author leohj
 */
public class DBHandler {
    private static HashMap<String, Integer> map = new HashMap<String, Integer>(){{put("sv", 1); put("en", 0);}};
    private static HashMap<String, Person> users = new HashMap<String, Person>(){};
    
    
    private static StringBuilder dbAPICallPostAuth(String urlString, String body) throws ConnectException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            StringBuilder content;
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
                os.flush();
                os.close();
            }
            
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            content = new StringBuilder();
            while ((line = input.readLine()) != null) {
                // Append each line of the response and separate them
                content.append(line);
                content.append(System.lineSeparator());
            }
            connection.disconnect();

            return content;
        } catch(ConnectException ex){
            throw ex;
        } catch (IOException ex) {
           return null;
        }
    }
    
    private static StringBuilder dbAPICallPost(String urlString, String body, String token) {
        StringBuilder content;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            if (!"".equals(token)){
                connection.setRequestProperty("Authorization", "Bearer "+token);
            }
                
            JSONObject myJsonObj = new JSONObject(body);
            connection.setDoOutput(true);
            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = myJsonObj.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
                os.flush();
                os.close();
            }
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            content = new StringBuilder();
            while ((line = input.readLine()) != null) {
                // Append each line of the response and separate them
                content.append(line);
                content.append(System.lineSeparator());
            }
            connection.disconnect();
            System.out.println(content);
            return content;
        } catch (Exception ex) {
            content = new StringBuilder();
            return content.append(ex);
        }
    }
    
    private static StringBuilder dbAPICallGet(String urlString, String token) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            if (token != "")
                connection.setRequestProperty("Authorization", "Bearer "+token);
            StringBuilder content;
            
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            content = new StringBuilder();
            while ((line = input.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }

            connection.disconnect();
            return content;
        } catch (Exception ex) {
            System.out.println("Error in dbAPICall()");
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Checks the username and password with the database handler and if they fit
     * get an access token that allows the user access
     * @param username the username that the user is trying to log in with
     * @param password the password that the user is trying to log in with
     * @return info about the user, their role and a access token
     * @throws ConnectException if there is no connection to the databasehandler
     */
    public static Person validateLogin(String username, String password) throws ConnectException {
        String body = "username="+username+"&password="+password;
        StringBuilder content = dbAPICallPostAuth("http://localhost:8081/login", body);
        if (content == null)
            return null;
         
        JSONObject myJsonObj = new JSONObject(content.toString());
        StringBuilder contentUser = dbAPICallGet("http://localhost:8081/user/"+username, myJsonObj.getString("access_Token"));
        JSONObject myJsonObj2 = new JSONObject(contentUser.toString());
        Person person = new Person(myJsonObj2.getInt("id"),myJsonObj2.getString("name"),myJsonObj.getString("access_Token"), myJsonObj2.getJSONObject("role").getString("name"));
        users.put(username, person);
        return person;
    }
    
    /**
     * This checks if the email that was sent is valid and the account is valid 
     * to be reset
     * @param email the email that is sent by the user
     * @return the message from the server
     * @throws ConnectException if there is no connection to the database
     */
    public static String validateEmail(String email) throws ConnectException{
        String body = "email="+email;
        StringBuilder content = dbAPICallPostAuth("http://localhost:8081/resetAccount/getToken", body);
        if (content == null)
            return null;
        return content.toString();
    }
    
    /**
     * Checks if the token sent exist in the database
     * @param token the token sent from the user
     * @return the message from the server
     * @throws ConnectException if there is no connection to the database
     */
    public static String validateToken(String token) throws ConnectException{
        String body = "token="+token;
        StringBuilder content = dbAPICallPostAuth("http://localhost:8081/resetAccount/validateToken", body);
        if (content == null)
            return null;
        return content.toString();
    }
    
    /**
     * Updates the user with the new username and password
     * @param user an user class with the new username and password
     * @param token the token that is used to get the right server
     * @return a message from the server and null if there has been a problem 
     * @throws ConnectException if there is no connection to the database
     */
    public static String updateUser(UserDTO user, String token) throws ConnectException{
        String body = "username="+user.getUsername()+"&password="+user.getPassword()+"&token="+token;
        StringBuilder content = dbAPICallPostAuth("http://localhost:8081/resetAccount/updateAccount", body);
        if (content == null)
            return null;
        return content.toString();
    }

    
    public static String applications(String Username){
        Person person = users.get(Username);
        StringBuilder content = dbAPICallGet("http://localhost:8081/applications/" + person.getId(), person.getToken());
        return content.toString();
    }
    
    /**
     * Sends an application to the database handler 
     * @param application contains all the info about the application
     * @param Username the username of the logged in user
     * @return message from the server
     * @throws ConnectException if there was no connection to the database handler
     */
    public static String application(ApplicationDTO application, String Username) throws ConnectException{
        Person person = users.get(Username);
        String body = "{"
                + "'person_id': '" + person.getId() + "',"
                + "'competence_id': '" + application.getCompetence() + "',"
                + "'years_of_experience': '" + application.getExperience() + "'"
                + "}";
        StringBuilder content = dbAPICallPost("http://localhost:8081/addProfile", body, person.getToken());
        if (!content.toString().contains("OK"))
            return content.toString();
        body = "{"
                + "'person_id': '" + person.getId() + "',"
                + "'from_date': '" + application.getStart() + "',"
                + "'to_date': '" + application.getEnd() + "'"
                + "}";
        
        content = dbAPICallPost("http://localhost:8081/addAvailability", body, person.getToken());
        return content.toString();
    }
    
    /**
     * Get the list of available competences and returns the sublist that contains
     * the names of the competences that fits the users language
     * @param language
     * @return 
     */
    public static List<Competence> loadCompetence(String language) {
        List<Competence> competenceList = new ArrayList<Competence>();
        StringBuilder content = dbAPICallGet("http://localhost:8081/competences", "");
        JSONArray myJsonArray = new JSONArray(content.toString());
        int j = 0;
        try{
            j = map.get(language)*(myJsonArray.length()/map.size());
        }catch (Exception e){
        }
        for(int i = 0; i < (myJsonArray.length()/map.size()); i++){
            competenceList.add(new Competence(myJsonArray.getJSONObject(i).getInt("id"), myJsonArray.getJSONObject(i+j).getString("name")));
        }
        
        return competenceList;
    }
        
}