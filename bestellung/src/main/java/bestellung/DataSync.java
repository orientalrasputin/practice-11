package bestellung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class DataSync {
  private Long lastId = 0L;
  RestTemplate rt =  new RestTemplate();
  final String URL = "http://localhost:8081/geliefert?startAt={id}";

  @Autowired
  BestellungRepository repository;

  @Scheduled(fixedRate = 1000)
  public void syncData() {
    ResponseEntity<Long[]> result = rt.getForEntity(URL, Long[].class, lastId);

    Long[] lieferungen = result.getBody();

    if (lieferungen != null) {
      for(Long id: lieferungen) {
        Bestellung bestellung = repository.findById(id);

        System.out.println(bestellung);
        if (bestellung == null) {
          continue;
        }
        bestellung.verschickt();
        repository.save(bestellung);

        lastId = bestellung.getId() + 1;
      }
    }
  }
}
