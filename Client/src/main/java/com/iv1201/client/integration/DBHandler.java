package com.iv1201.client.integration;

import com.iv1201.client.model.ApplicationDTO;
import com.iv1201.client.model.Competence;
import com.iv1201.client.model.Person;
import com.iv1201.client.model.UserDTO;
import java.io.BufferedReader;
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
        } catch (Exception ex) {
            System.out.println("Error in dbAPICallPostAuth()");
            ex.printStackTrace();
        }
        return null;

    }
    
    private static StringBuilder dbAPICallPost(String urlString, String body, String token) {
        
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            StringBuilder content;
            connection.setRequestProperty("Accept", "application/json");
            if (token != ""){
                connection.setRequestProperty("Authorization", "Bearer "+token);
                System.out.println("token hänger med.");
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
            System.out.println("check 2");
            return content;
        } catch (Exception ex) {
            System.out.println("Error in dbAPICallPost()");
            ex.printStackTrace();
        }
        return null;

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
    
    public static Person validateLogin(String username, String password) throws ConnectException {
        String body = "username="+username+"&password="+password;
        StringBuilder content = dbAPICallPostAuth("https://com-iv1201-server.herokuapp.com/login", body);
        if (content == null)
            return null;
         
        JSONObject myJsonObj = new JSONObject(content.toString());
        StringBuilder contentUser = dbAPICallGet("https://com-iv1201-server.herokuapp.com/user/"+username, myJsonObj.getString("access_Token"));
        JSONObject myJsonObj2 = new JSONObject(contentUser.toString());
        Person person = new Person(myJsonObj2.getInt("id"),myJsonObj2.getString("name"),myJsonObj.getString("access_Token"), myJsonObj2.getJSONObject("role").getString("name"));
        users.put(username, person);
        System.out.println(myJsonObj.getString("access_Token"));
        return person;
    }

    public static String validateEmail(String email) throws ConnectException{
        String body = "email="+email;
        StringBuilder content = dbAPICallPostAuth("https://com-iv1201-server.herokuapp.com/resetAccount/getToken", body);
        if (content == null)
            return null;
        System.out.println(content.toString());
        return content.toString();
    }
    
    public static String validateToken(String token) throws ConnectException{
        String body = "token="+token;
        System.out.println("token"+token);
        StringBuilder content = dbAPICallPostAuth("https://com-iv1201-server.herokuapp.com/resetAccount/validateToken", body);
        if (content == null)
            return null;
        System.out.println(content.toString());
        return content.toString();
    }
    
    public static String updateUser(UserDTO user, String token) throws ConnectException{
        String body = "username="+user.getUsername()+"&password="+user.getPassword()+"&token="+token;
//        String body = "{"
//                + "'username': '" + user.getUsername() + "',"
//                + "'password': '" + user.getPassword() + "',"
//                + "'token': '" + token + "'"
//                + "}";
        StringBuilder content = dbAPICallPostAuth("https://com-iv1201-server.herokuapp.com/resetAccount/updateAccount", body);
        if (content == null)
            return null;
        System.out.println(content.toString());
        return content.toString();
    }
    
    public static void updateUser(UserDTO user){
        Person person = users.get(user.getUsername());
        String body = "{"
                + "'username': '" + user.getUsername() + "',"
                + "'password': '" + user.getPassword() + "',"
                + "'email': '" + user.getEmail() + "'"
                + "}";
        dbAPICallPost("https://com-iv1201-server.herokuapp.com/updateuser", body, person.getToken());
    }
    
    public static String loadApplications(String Username){
        Person person = users.get(Username);
        StringBuilder content = dbAPICallGet("https://com-iv1201-server.herokuapp.com/applications/" + person.getId(), person.getToken());
        System.out.println("applications: " + content.toString());
        if(!content.toString().contains("person_id"))
            return null;
        return content.toString();
    }
    
    public static void sendApplication(ApplicationDTO application, String Username) throws ConnectException{
        Person person = users.get(Username);
        System.out.println("check 1: " + person.getToken());
        String body = "{"
                + "'person_id': '" + person.getId() + "',"
                + "'competence_id': '" + application.getCompetence() + "',"
                + "'years_of_experience': '" + application.getExperience() + "'"
                + "}";
        
        dbAPICallPost("https://com-iv1201-server.herokuapp.com/addProfile", body, person.getToken());
        body = "{"
                + "'person_id': '" + person.getId() + "',"
                + "'from_date': '" + application.getStart() + "',"
                + "'to_date': '" + application.getEnd() + "'"
                + "}";
        
        System.out.println("start: " +application.getStart()+ ", end: " + application.getEnd());
        dbAPICallPost("https://com-iv1201-server.herokuapp.com/addAvailability", body, person.getToken());
        
    }
    
    
    public static List<Competence> loadCompetence(String language) {
        List<Competence> competenceList = new ArrayList<Competence>();
        StringBuilder content = dbAPICallGet("https://com-iv1201-server.herokuapp.com/competences", "");
        JSONArray myJsonArray = new JSONArray(content.toString());
        int j = 0;
        try{
            j = map.get(language)*(myJsonArray.length()/map.size());
        }catch (Exception e){
        }
        for(int i = 0; i < (myJsonArray.length()/map.size()); i++){
            competenceList.add(new Competence(myJsonArray.getJSONObject(i+j).getInt("id"), myJsonArray.getJSONObject(i+j).getString("name")));
        }
        
        return competenceList;
    }
        
}