package lieferung;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LieferungController {

  private final LieferungService lieferungen;

  public LieferungController(LieferungService lieferungen) {
    this.lieferungen = lieferungen;
  }

  @PostMapping("/liefern")
  public String liefern(Long id) {
    System.out.println(id);
    Assert.notNull(id,"Id für die Lieferung muss vorhanden sein");
    lieferungen.anfrage(id);
    String text = "Lieferung für " + id + " ist in Arbeit";
    System.out.println(text);
    return text;
  }

  @GetMapping("/geliefert")
  public List<Long> geliefert(@RequestParam("startAt") Long id) {
    System.out.println(lieferungen.alleAbId(id));
    return lieferungen.alleAbId(id);
  }

  @GetMapping("/count")
  public int count() {
    return lieferungen.anzahl();
  }


}
