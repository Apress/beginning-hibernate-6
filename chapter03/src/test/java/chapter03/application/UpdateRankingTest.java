package chapter03.application;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class UpdateRankingTest {
  RankingService service = new HibernateRankingService();
  static final String SCOTT = "Scotball Most";
  static final String GENE = "Gene Showrama";
  static final String CEYLON = "Ceylon";

  @Test
  public void updateExistingRanking() {
    service.addRanking(GENE, SCOTT, CEYLON, 6);
    assertEquals(service.getRankingFor(GENE, CEYLON), 6);
    service.updateRanking(GENE, SCOTT, CEYLON, 7);
    assertEquals(service.getRankingFor(GENE, CEYLON), 7);
  }

  @Test
  public void updateNonexistentRanking() {
    assertEquals(service.getRankingFor(SCOTT, CEYLON), 0);
    service.updateRanking(SCOTT, GENE, CEYLON, 7);
    assertEquals(service.getRankingFor(SCOTT, CEYLON), 7);
  }
}
