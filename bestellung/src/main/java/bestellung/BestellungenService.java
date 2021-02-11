package bestellung;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class BestellungenService {

  @Value("${lieferung.endpoint}")
  private String endpoint;

  @Autowired
  BestellungQueue queue;

  @Async
  public void liefern(Long id) {
    // senden
    try {
      Unirest
          .post(endpoint)
          .field("id", id)
          .asString();
    } catch (UnirestException e) {
      e.printStackTrace();
    }
  }

  private final BestellungRepository repository;

  public BestellungenService(BestellungRepository repository) {
    this.repository = repository;
  }

  public Collection<Bestellung> alleBestellungen() {
    return repository.findAll();
  }

  public void neueBestellungFuer(String kunde) {
    Bestellung bestellung = new Bestellung(kunde);
    repository.save(bestellung);

    // Hier muss das andere SCS asynchron informiert werden
    // Achten Sie darauf, dass die Nachricht an das andere System niemals verloren geht
    queue.add(bestellung);

    // Probieren Sie aus, das andere System vorher zu deaktivieren.
    // Wenn es reaktiviert wird, mussen die fehlenden Nachrichten übertragen werden.

  }

}
