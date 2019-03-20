package wolox.training.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import wolox.training.models.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BookMapper {
    private  ObjectMapper mapper = new ObjectMapper();
    public Book mapJsonResponse(StringBuffer response, String isbn ) throws IOException {
        JsonNode rootNode = mapper.readTree(response.toString());

        String genders = rootNode.findValue("subjects").findValue("name").textValue();
        String authors = rootNode.findValue("authors").findValue("name").textValue();
        String publishers = rootNode.findValue("publishers").findValue("name").textValue();
        Integer pages = new Integer(rootNode.findValue("number_of_pages").asInt());
        String title = rootNode.findValue("title").toString();
        String subtitle = rootNode.findValue("subtitle").toString();
        String year = rootNode.findValue("publish_date").toString();
        String image = rootNode.findValue("medium").toString();

        Book book = new Book();
        book.setAuthor(authors);
        book.setImage(image);
        book.setTitle(title);
        book.setSubtitle(subtitle);
        book.setGenre(genders);
        book.setIsbn(isbn);
        book.setPages(pages);
        book.setPublisher(publishers);
        book.setYear(year);

        return book;
    }
}
