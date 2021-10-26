package ch12;

import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class HibernatePostManager implements PostManager {
  @Inject
  Session session;

  @Transactional
  @Override
  public Post savePost(Post post) {
    post.setCreatedAt(new Date());
    session.save(post);
    return post;
  }

  @Override
  @Transactional
  public List<Post> getPosts() {
    TypedQuery<Post> postQuery = session
      .createQuery(
        "select p from Post p order by p.createdAt desc",
        Post.class
      );
    postQuery.setMaxResults(20);
    return postQuery.getResultList();
  }
}
