package bestellung;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class BestellungenService {


  @Value("${lieferung.endpoint}")
  private String endpoint;

  private final static String BESTELLUNG_SENT = "sendBestellung";

  private final BestellungRepository repository;

  public BestellungenService(BestellungRepository repository) {
    this.repository = repository;
  }

  public Collection<Bestellung> alleBestellungen() {
    return repository.findAll();
  }

  public void neueBestellungFuer(String kunde){
    Bestellung bestellung = new Bestellung(kunde);
    repository.save(bestellung);

    // Hier muss das andere SCS asynchron informiert werden
    // Achten Sie darauf, dass die Nachricht an das andere System niemals verloren geht
    ConnectionFactory factory = new ConnectionFactory();

    factory.setHost("localhost");
    factory.setUsername("groot");
    factory.setPassword("iamgroot");

    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {
      channel.queueDeclare(BESTELLUNG_SENT, false, false, false, null);
      HttpResponse response = Unirest.post
      channel.basicPublish("", BESTELLUNG_SENT, null, null);


    } catch (TimeoutException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }


    // Probieren Sie aus, das andere System vorher zu deaktivieren. 
    // Wenn es reaktiviert wird, mussen die fehlenden Nachrichten übertragen werden.

  }




}
