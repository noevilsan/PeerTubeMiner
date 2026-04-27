package aiss.peertubeminer.service;

import aiss.peertubeminer.model.peertube.OwnerAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PeertubeServiceTest {

    @Autowired
    PeertubeService service;

    @Test
    @DisplayName("Probar que recupera la cuenta de Framasoft")
    void getAccountTest() {
        OwnerAccount account = service.getAccount("Framasoft");
        assertNotNull(account, "La cuenta no debería ser nula");
        assertEquals("Framasoft", account.getName(), "El nombre debería coincidir");
        System.out.println(account.toString());
    }
}