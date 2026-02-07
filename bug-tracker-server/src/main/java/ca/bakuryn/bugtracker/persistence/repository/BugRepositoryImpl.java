package ca.bakuryn.bugtracker.persistence.repository;

import ca.bakuryn.bugtracker.persistence.entity.Bug;
import ca.bakuryn.bugtracker.persistence.entity.BugSeverity;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BugRepositoryImpl implements BugRepository {

  private final SessionFactory sessionFactory;

  public BugRepositoryImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }


  @Override
  public List<Bug> findAll() {
    return getSession()
        .createQuery("from Bug", Bug.class)
        .list();
  }

  @Override
  public Optional<Bug> findById(Long id) {
    Bug bug = getSession().get(Bug.class, id);
    return Optional.ofNullable(bug);
  }

  @Override
  public boolean existsById(Long id) {
    Long count = getSession()
        .createQuery("select count(b.id) from Bug b where id = :id", Long.class)
        .setParameter("id", id)
        .uniqueResult();
    return count != null && count > 0;
  }

  @Override
  public Bug save(Bug bug) {
    Session session = getSession();
    if (bug.getId() == null) {
      session.persist(bug);
      return bug;
    } else {
      return (Bug) session.merge(bug);
    }
  }

  @Override
  public List<Bug> findByFilter(BugSeverity severity) {
    if (severity == null) {
      return findAll();
    }
    return getSession()
        .createQuery("from Bug b where b.severity = :sev", Bug.class)
        .setParameter("sev", severity)
        .list();
  }
}
