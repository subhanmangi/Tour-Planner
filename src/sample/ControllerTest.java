package sample;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void getConnectionTest() {

        Connection c = Controller.getConnection();
        assertEquals(c,Controller.getConnection());
    }
}