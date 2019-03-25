package wolox.training.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.web.util.UriComponentsBuilder;
import wolox.training.mappers.BookMapper;
import wolox.training.models.Book;
import wolox.training.utils.ParameterStringBuilder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Component
public class OpenLibraryService {
    private final  static String BASE_URL = "https://openlibrary.org/api/books";

    @Autowired
    private BookMapper bookMapper = new BookMapper();



    public Book bookInfo(String isbn) {
        Book book = null;
        try {

            Map<String, String> parameters = new HashMap<>();
            parameters.put("bibkeys", "ISBN:"+ isbn);
            parameters.put("format", "json");
            parameters.put("jscmd", "data");

            URL obj = new URL(BASE_URL + ParameterStringBuilder.getParamsString(parameters));

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            ;


            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + BASE_URL);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());

            book = bookMapper.mapJsonResponse(response, isbn);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }
}
