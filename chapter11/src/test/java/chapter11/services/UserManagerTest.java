package chapter11.services;

import chapter11.model.User;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class UserManagerTest extends TestBase {
  @DataProvider
  Object[][] users() {
    return new Object[][]{
      {"ts", false},
      {"jbo", true}
    };
  }

  @Test(dataProvider = "users")
  void addUsersTest(String userName, boolean active) {
    services.addUser(userName);
    User user = services.findUserByName(userName);
    assertEquals(user.isActive(), true);

    if (!active) {
      user.setActive(active);
      services.updateUser(user);
      user = services.findUserByName(userName);
      assertEquals(user.isActive(), active);
    }
  }
}
