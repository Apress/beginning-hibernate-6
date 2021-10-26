package ch12;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class Main {
  @Bean
  LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setConfigLocation(new ClassPathResource("/hibernate.cfg.xml"));

    return sessionFactory;
  }

  @Bean
  public PlatformTransactionManager hibernateTransactionManager() {
    HibernateTransactionManager transactionManager
      = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory().getObject());
    return transactionManager;
  }

  @Bean
  PostManager postManager(SessionFactory factory) {
    return new HibernatePostManager(factory);
  }

  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(Main.class);
    ApplicationContext context =
      new AnnotationConfigApplicationContext(Main.class);

    PostManager postManager = context.getBean(PostManager.class);
    logger.info(postManager.toString());
    postManager.savePost("foo", "bar");
    logger.info(postManager.getPosts().toString());
  }
}
