package lieferung;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class LieferungService {

  private final static String QUEUE_NAME = "sendBestellung";
  private final LieferungenRepository repository;

  public LieferungService(LieferungenRepository repository) {
    this.repository = repository;
  }

// Wir simulieren die Lieferung durch eine zeitliche Verzögerung
  @Async
  public void anfrage(Long id){

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    factory.setUsername("groot");
    factory.setPassword("iamgroot");

    try{
      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();

      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

      DeliverCallback deliverCallback = (consumerTag, delivery) -> {
      HttpResponse receive = Unirest.get

      };
      channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    } catch (TimeoutException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    repository.save(id);
  }

  public int anzahl() {
    return repository.count();
  }
}
