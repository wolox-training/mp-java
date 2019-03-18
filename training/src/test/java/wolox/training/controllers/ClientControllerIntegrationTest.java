package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import wolox.training.exceptions.ClientNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.Client;
import wolox.training.repositories.ClientRepository;
import wolox.training.repositories.ClientRepository;
import wolox.training.utils.Serializer;
import wolox.training.utils.mocks.BookMock;
import wolox.training.utils.mocks.ClientMock;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientRepository service;

    @Autowired
    private ObjectMapper objectMapper;

    private final  static String BASE_URL = "/api/clients/";

    @Test
    public void givenClients_whenGetClients_thenReturnJsonArray()
            throws Exception {

        Client client = ClientMock.createClient();

        List<Client> allClient = Arrays.asList(client);

        given(service.findAll()).willReturn(allClient);

        mvc.perform(get("/api/clients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(client.getUsername())));
    }

    @Test
    public void givenClient_whenGetClientById_thenReturnJsonObject()
            throws Exception {

        Client client = ClientMock.createClient();

        given(service.findById(client.getId())).willReturn(Optional.of(client));

        String url = BASE_URL + client.getId();

        ResultActions resultActions = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Client response = objectMapper.readValue(contentAsString, Client.class);
        assertThat(response.equals(client));

    }

    @Test
    public void givenClient_whenGetClientById_throwNotFoundError() throws Exception {

        Client client = ClientMock.createClient();

        when(service.findById(client.getId()))
                .thenThrow(new ClientNotFoundException());
        String url = BASE_URL + client.getId();

        ResultActions resultActions = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenClient_whenCreateClient_thenReturnJsonObject()
            throws Exception {

        Client client = ClientMock.createClient();
        String requestJson = Serializer.serializeObject(client);

        given(service.save(client)).willReturn(client);


        String url = "/api/clients";

        ResultActions resultActions = mvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Client response = objectMapper.readValue(contentAsString, Client.class);
        assertThat(response.equals(client));

    }

    @Test
    public void givenClient_whenAddBook_thenReturnJsonObject()
            throws Exception {

        Book book = BookMock.createBook();
        String requestJson = Serializer.serializeObject(book);
        Client client = ClientMock.createClient();
        given(service.findById(client.getId())).willReturn(Optional.of(client));



        String url = BASE_URL  + client.getId();

        ResultActions resultActions = mvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Client response = objectMapper.readValue(contentAsString, Client.class);
        assertThat(response.equals(client));

    }


    @Test
    public void givenClient_whenAddBook_throwNotFoundError() throws Exception {


        Client client = ClientMock.createClient();
        String requestJson = Serializer.serializeObject(client);

        when(service.findById(client.getId()))
                .thenThrow(new ClientNotFoundException());

        String url = BASE_URL + client.getId();

        ResultActions resultActions = mvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isNotFound());

    }

    @Test
    public void givenClient_whenDeleteClient_thenReturnJsonObject()
            throws Exception {

        Client client = ClientMock.createClient();

        given(service.findById(client.getId())).willReturn(Optional.of(client));

        doNothing().when(service).deleteById(client.getId());

        String url = BASE_URL + client.getId();

        ResultActions resultActions = mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        assertThat(contentAsString.isEmpty());

    }

    @Test
    public void givenClient_whenDeleteClient_throwNotFoundError() throws Exception {


        Client client = ClientMock.createClient();

        when(service.findById(client.getId()))
                .thenThrow(new ClientNotFoundException());

        String url = BASE_URL + client.getId();

        ResultActions resultActions = mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void givenClient_whenUpdateClient_thenReturnJsonObject()
            throws Exception {

        Client client = ClientMock.createClient();
        String requestJson = Serializer.serializeObject(client);

        given(service.findById(client.getId())).willReturn(Optional.of(client));

        given(service.save(client)).willReturn(client);

        String url = BASE_URL + client.getId();

        ResultActions resultActions = mvc.perform(put(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Client response = objectMapper.readValue(contentAsString, Client.class);

        assertThat(response.equals(client));

    }

    @Test
    public void givenClient_whenUpdateClient_throwNotFoundError() throws Exception {

        Client client = ClientMock.createClient();
        String requestJson = Serializer.serializeObject(client);

        given(service.findById(client.getId())).willReturn(Optional.of(client));

        when(service.findById(client.getId()))
                .thenThrow(new ClientNotFoundException());

        String url = BASE_URL + client.getId();

        ResultActions resultActions = mvc.perform(put(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isNotFound());

    }

    @Test
    public void givenClient_whenUpdateClient_throwClientIdMismatchError() throws Exception {


        Client client = ClientMock.createClient();
        String requestJson = Serializer.serializeObject(client);



        String url = BASE_URL + (client.getId() + 1);

        ResultActions resultActions = mvc.perform(put(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isBadRequest());

    }


}
