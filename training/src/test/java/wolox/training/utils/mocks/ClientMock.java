package wolox.training.utils.mocks;

import org.apache.commons.lang3.RandomStringUtils;
import wolox.training.models.Book;
import wolox.training.models.Client;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class ClientMock {
    public static Client createClient(Map parameters) {
        Client client = new Client();
        String username = (parameters.get("username") == null) ? RandomStringUtils.randomAlphabetic(10) : (String) parameters.get("username");
        LocalDate birthdate = (parameters.get("birthdate") == null) ? LocalDate.now() : (LocalDate) parameters.get("birthdate");
        client.setUsername(username);
        client.setPassword(RandomStringUtils.randomAlphabetic(20));
        client.setBirthdate(birthdate);
        return  client;
    }

    public static Client createClient() {
        Client client = new Client();
        client.setUsername(RandomStringUtils.randomAlphabetic(10));
        client.setPassword(RandomStringUtils.randomAlphabetic(20));
        client.setBirthdate(LocalDate.now());
        return  client;
    }
}
