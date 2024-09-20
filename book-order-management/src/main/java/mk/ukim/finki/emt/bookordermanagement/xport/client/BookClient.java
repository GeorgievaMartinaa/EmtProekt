package mk.ukim.finki.emt.bookordermanagement.xport.client;

import mk.ukim.finki.emt.bookordermanagement.domain.valueObjects.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

//pristapuva resursi od book katalogot- zima gi  knigite so se u data init za book
@Service
public class BookClient {
    //pravi povici do nadvoresen resurs
    private  final RestTemplate restTemplate;
    private final String serverUrl;

    public BookClient(@Value("${app.book.url}") String serverUrl) {
        this.serverUrl = serverUrl;
        this.restTemplate= new RestTemplate();
        var requestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri(){
        return UriComponentsBuilder.fromUriString(this.serverUrl);
    }

    public List<Book> findAll(){
        try {
           return restTemplate.exchange(uri().path("/api/book").build().toUri(),
                   HttpMethod.GET,
                   null,
                   new ParameterizedTypeReference<List<Book>>(){
            }).getBody();
        } catch (Exception e) {
            return  Collections.emptyList();
        }
    }
}
