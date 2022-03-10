package com.iv1201.Client.integration;

import com.iv1201.client.integration.DBHandler;
import com.iv1201.client.model.ApplicationDTO;
import com.iv1201.client.model.Competence;
import com.iv1201.client.model.Person;
import com.iv1201.client.model.UserDTO;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author leohj
 */
public class DBHandlerTest {
    
    public DBHandlerTest() {
        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of validateLogin method, of class DBHandler.
     * @throws java.lang.Exception
     */
    @Test
    public void testValidateLoginRecruiter() throws Exception {
        String username = "JoelleWilkinson";
        String password = "LiZ98qvL8Lw";
        String expResult = "Joelle";
        Person resultPerson = DBHandler.validateLogin(username, password);
        assertEquals(expResult, resultPerson.getName());
    }
    
    /**
     * Test of validateLogin method, of class DBHandler.
     * @throws java.lang.Exception
     */
    @Test
    public void testValidateLoginApplicant() throws Exception {
        String username = "LeroyCrane";
        String password = "1234";
        String expResult = "Leroy";
        Person resultPerson = DBHandler.validateLogin(username, password);
        assertEquals(expResult, resultPerson.getName());
    }

    /**
     * Test of validateLogin method, of class DBHandler.
     * @throws java.lang.Exception
     */
    @Test
    public void testValidateLoginEmptyInputs() throws Exception {
        String username = "";
        String password = "";
        String expResult = null;
        Person resultPerson = DBHandler.validateLogin(username, password);
        assertEquals(expResult, resultPerson);
    }
    
    /**
     * Test of validateLogin method, of class DBHandler.
     * @throws java.lang.Exception
     */
    @Test
    public void testValidateLoginWrongInputs() throws Exception {
        String username = "wrongUsername";
        String password = "wrongPassword";
        String expResult = null;
        Person resultPerson = DBHandler.validateLogin(username, password);
        assertEquals(expResult, resultPerson);
    }
    
    /**
     * Test of validateEmail method, of class DBHandler.
     */
    @Test
    public void testValidateEmailBadlyFormatted() throws Exception {
        String email = "wrongEmail";
        String expResult = "invalid email";
        String result = DBHandler.validateEmail(email);
        assertEquals(expResult, result.trim());
    }
    
    /**
     * Test of validateEmail method, of class DBHandler.
     */
    @Test
    public void testValidateEmailNoSuchEmail() throws Exception {
        String email = "wrongEmail@hotmail.com";
        String expResult = "invalid email";
        String result = DBHandler.validateEmail(email);
        assertEquals(expResult, result.trim());
    }
    
    /**
     * Test of validateEmail method, of class DBHandler.
     */
    @Test
    public void testValidateEmailAlreadyBeenReset() throws Exception {
        String email = "l_crane118@finnsinte.se";
        String expResult = "already been reset";
        String result = DBHandler.validateEmail(email);
        assertEquals(expResult, result.trim());
    }
    
    /**
     * Test of validateEmail method, of class DBHandler.
     */
    @Test
    public void testValidateEmail() throws Exception {
        String email = "tucker-travis2407@finnsinte.se";
        String expResult = "ok";
        String result = DBHandler.validateEmail(email);
        assertEquals(expResult, result.trim());
    }

    /**
     * Test of validateToken method, of class DBHandler.
     */
    @Test
    public void testValidateToken() throws Exception {

    }

    /**
     * Test of updateUser method, of class DBHandler.
     */
    @Test
    public void testUpdateUser_UserDTO_String() throws Exception {

    }

    /**
     * Test of updateUser method, of class DBHandler.
     */
    @Test
    public void testUpdateUser_UserDTO() {

    }

    /**
     * Test of application method, of class DBHandler.
     */
    @Test
    public void testApplication() throws Exception {

    }

    /**
     * Test of loadCompetence method, of class DBHandler.
     */
    @Test
    public void testLoadCompetence() {

    }
    
}
