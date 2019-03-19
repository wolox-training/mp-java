package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.OpenLibraryService;
import wolox.training.utils.Serializer;
import wolox.training.utils.mocks.BookMock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private OpenLibraryService openLibraryService;

    @Autowired
    private ObjectMapper objectMapper;

    private final  static String BASE_URL = "/api/books/";

    @Test
    public void givenBooks_whenGetBooks_thenReturnJsonArray()
            throws Exception {

        Book book = BookMock.createBook();

        List<Book> allBook = Arrays.asList(book);

        given(bookRepository.findAll()).willReturn(allBook);

        mvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(book.getTitle())));
    }

    @Test
    public void givenBook_whenGetBookById_thenReturnJsonObject()
            throws Exception {

        Book book = BookMock.createBook();

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));

        String url = "/api/books/" + book.getId();

        ResultActions resultActions = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Book response = objectMapper.readValue(contentAsString, Book.class);
        assertThat(response.equals(book));

    }

    @Test
    public void givenBook_whenGetBookById_throwNotFoundError() throws Exception {


        Book book = BookMock.createBook();

        when(bookRepository.findById(book.getId()))
                .thenThrow(new BookNotFoundException());
        String url = BASE_URL + book.getId();

        ResultActions resultActions = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenBook_whenGetBookByIsbn_thenReturnJsonObjectFromLocalDB()
            throws Exception {

        Book book = BookMock.createBook();

        given(bookRepository.findByIsbn(book.getIsbn())).willReturn(book);

        String url = BASE_URL + "/isbn/"+book.getIsbn();

        ResultActions resultActions = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Book response = objectMapper.readValue(contentAsString, Book.class);
        assertThat(response.equals(book));

    }

    @Test
    public void givenBook_whenGetBookByIsbn_thenReturnJsonObjectFromExternalApi()
            throws Exception {

        Book book = BookMock.createBook();

        given(bookRepository.findByIsbn(book.getIsbn())).willReturn(null);

        given(openLibraryService.bookInfo(book.getIsbn())).willReturn(book);

        String url = BASE_URL + "/isbn/"+book.getIsbn();

        ResultActions resultActions = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Book response = objectMapper.readValue(contentAsString, Book.class);
        assertThat(response.equals(book));

    }

    @Test
    public void givenBook_whenGetBookByIsbn_throwNotFoundError() throws Exception {

        Book book = BookMock.createBook();

        given(bookRepository.findByIsbn(book.getIsbn())).willReturn(null);
        given(openLibraryService.bookInfo(book.getIsbn())).willReturn(null);

        when(bookRepository.findById(book.getId()))
                .thenThrow(new BookNotFoundException());
        String url = BASE_URL + book.getId();

        ResultActions resultActions = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenBook_whenCreateBook_thenReturnJsonObject()
            throws Exception {

        Book book = BookMock.createBook();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(book );

        given(bookRepository.save(book)).willReturn(book);


        String url = BASE_URL;

        ResultActions resultActions = mvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Book response = objectMapper.readValue(contentAsString, Book.class);
        assertThat(response.equals(book));

    }

    @Test
    public void givenBook_whenDeleteBook_thenReturnJsonObject()
            throws Exception {

        Book book = BookMock.createBook();

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));

        doNothing().when(bookRepository).deleteById(book.getId());

        String url = "/api/books/" + book.getId();

        ResultActions resultActions = mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        assertThat(contentAsString.isEmpty());

    }

    @Test
    public void givenBook_whenDeleteBook_throwNotFoundError() throws Exception {


        Book book = BookMock.createBook();

        when(bookRepository.findById(book.getId()))
                .thenThrow(new BookNotFoundException());

        String url = "/api/books/" + book.getId();

        ResultActions resultActions = mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void givenBook_whenUpdateBook_thenReturnJsonObject()
            throws Exception {

        Book book = BookMock.createBook();
        String requestJson = Serializer.serializeObject(book);
        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));
        given(bookRepository.save(book)).willReturn(book);

        String url = "/api/books/" + book.getId();

        ResultActions resultActions = mvc.perform(put(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Book response = objectMapper.readValue(contentAsString, Book.class);

        assertThat(response.equals(book));

    }

    @Test
    public void givenBook_whenUpdateBook_throwNotFoundError() throws Exception {


        Book book = BookMock.createBook();
        String requestJson = Serializer.serializeObject(book);

        when(bookRepository.findById(book.getId()))
                .thenThrow(new BookNotFoundException());

        String url = "/api/books/" + book.getId();

        ResultActions resultActions = mvc.perform(put(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isNotFound());

    }

    @Test
    public void givenBook_whenUpdateBook_throwBookIdMismatchError() throws Exception {


        Book book = BookMock.createBook();
        String requestJson = Serializer.serializeObject(book);



        String url = "/api/books/" + (book.getId() + 1);

        ResultActions resultActions = mvc.perform(put(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isBadRequest());

    }


}
