package lieferung;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LieferungService {

  private final LieferungenRepository repository;

  public LieferungService(LieferungenRepository repository) {
    this.repository = repository;
  }

// Wir simulieren die Lieferung durch eine zeitliche Verzögerung
  @Async
  public void anfrage(Long id) {
    try {
      Thread.sleep((long) (Math.random() * 5000 + 1000));
    } catch (InterruptedException e) {

    }
    repository.save(id);
  }

  public int anzahl() {
    return repository.count();
  }

  public List<Long> alleAbId(Long id) {
    return repository.findLieferungenGreaterThan(id);
  }
}
