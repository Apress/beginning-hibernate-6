package chapter09;

import chapter09.model.Product;
import org.hibernate.query.Query;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class QueryTest extends TestBase {
  @Test
  public void testProjection() {
    Query query = session.getNamedQuery("supplier.findAverage");
    List<Object[]> suppliers = query.list();
    for (Object[] o : suppliers) {
      System.out.println(Arrays.toString(o));
    }
    assertEquals(suppliers.size(), 2);
  }

  @Test
  public void testLikeQuery() {
    Query<Product> query = session.createQuery(
        "from Product p "
            + " where p.price > :minprice"
            + " and p.description like :desc", Product.class);
    query.setParameter("desc", "Mou%");
    query.setParameter("minprice", 15.0);
    List<Product> products = query.list();
    assertEquals(products.size(), 1);
  }

  @DataProvider
  Object[][] queryTypesProvider() {
    return new Object[][]{
        {"Supplier", 2},
        {"Product", 5},
        {"Software", 2},
    };
  }

  @Test(dataProvider = "queryTypesProvider")
  public void testQueryTypes(String type, Integer count) {
    Query query = session.createQuery("from " + type);
    List list = query.list();
    assertEquals(list.size(), count.intValue());
  }

  @Test
  public void searchForProduct() {
    Query query = session.getNamedQuery("product.searchByPhrase");
    query.setParameter("text", "%Mou%");
    List<Product> products = query.list();
    assertEquals(products.size(), 3);
  }
}
