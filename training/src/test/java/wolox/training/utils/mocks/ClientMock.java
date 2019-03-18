package wolox.training.utils.mocks;

import org.apache.commons.lang3.RandomStringUtils;
import wolox.training.models.Book;
import wolox.training.models.Client;

import java.time.LocalDate;
import java.util.Random;

public final class ClientMock {
    public static Client createClient() {
        Client client = new Client();
        client.setUsername(RandomStringUtils.randomAlphabetic(10));
        client.setBirthdate(LocalDate.now());
        return  client;
    }
}
