package auth.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bayerl.auth.models.User;
import org.junit.jupiter.api.Test;

public class UserTest {
    
    @Test
    public void testUserCreation() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setPassword("password123");

        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Test User", user.getName());
        assertEquals("password123", user.getPassword());
    }
}