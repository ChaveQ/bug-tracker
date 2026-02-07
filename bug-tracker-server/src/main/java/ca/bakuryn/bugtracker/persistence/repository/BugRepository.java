package ca.bakuryn.bugtracker.persistence.repository;

import ca.bakuryn.bugtracker.persistence.entity.Bug;
import ca.bakuryn.bugtracker.persistence.entity.BugSeverity;
import java.util.List;
import java.util.Optional;

public interface BugRepository {

  List<Bug> findAll();
  Optional<Bug> findById(Long id);
  boolean existsById(Long id);
  Bug save(Bug bug);
  List<Bug> findByFilter(BugSeverity severity);
}
