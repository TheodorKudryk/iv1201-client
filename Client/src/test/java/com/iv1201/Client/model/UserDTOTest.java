package com.iv1201.Client.model;

import com.iv1201.client.model.UserDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author leohj
 */
public class UserDTOTest {
    
    private UserDTO instance;
    
    @Before
    public void setUp() {
        this.instance = new UserDTO("username", "password", "email");
    }
    
    @After
    public void tearDown() {
        this.instance = null;
    }

    /**
     * Test of getUsername method, of class UserDTO.
     */
    @Test
    public void testGetUsernameTest() {
        String expResult = "username";
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPassword method, of class UserDTO.
     */
    @Test
    public void testGetPassword() {
        String expResult = "password";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEmail method, of class UserDTO.
     */
    @Test
    public void testGetEmail() {
        String expResult = "email";
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }
    
}
