package bestellung;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

@Component
public class BestellungQueue {
  private Queue<Bestellung> queue = new LinkedList<>();

  public void add(Bestellung b) {
    queue.add(b);
  }

  @Scheduled(fixedDelay = 1000)
  public void send() {
    Iterator<Bestellung> itr = queue.iterator();

    while(itr.hasNext()) {
      try {
        Bestellung bestellung = itr.next();

        Unirest
            .post("http://localhost:8081/liefern")
            .field("id", bestellung.getId())
            .asString();

        itr.remove();
      } catch (UnirestException e) {
        e.printStackTrace();
      }
    }
  }
}
