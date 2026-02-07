package ca.bakuryn.bugtracker.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ca.bakuryn.bugtracker.config.TestPersistenceConfig;
import ca.bakuryn.bugtracker.persistence.entity.Bug;
import ca.bakuryn.bugtracker.persistence.entity.BugSeverity;
import ca.bakuryn.bugtracker.persistence.entity.BugStatus;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPersistenceConfig.class)
@Transactional
@Sql("/sql/init-db-data.sql")
public class BugRepositoryTest {

  @Autowired
  private BugRepository bugRepository;

  @Test
  public void shouldFindById() {
    Optional<Bug> bug = bugRepository.findById(1L);

    assertThat(bug).isPresent();
    assertThat(bug.get().getTitle()).isEqualTo("Critical Error");
  }

  @Test
  public void shouldReturnEmptyOptionalWhenIdNotFound() {
    Optional<Bug> bug = bugRepository.findById(999L);

    assertThat(bug).isEmpty();
  }

  @Test
  public void shouldCheckExistence() {
    assertThat(bugRepository.existsById(1L)).isTrue();
    assertThat(bugRepository.existsById(999L)).isFalse();
  }

  @Test
  public void shouldSaveNewBug() {
    Bug newBug = new Bug();
    newBug.setTitle("New Test Bug");
    newBug.setSeverity(BugSeverity.LOW);
    newBug.setStatus(BugStatus.OPEN);

    Bug saved = bugRepository.save(newBug);

    newBug.setId(saved.getId());
    assertThat(saved).isEqualTo(newBug);
    assertThat(bugRepository.findById(saved.getId()).get()).isEqualTo(newBug);
  }

  @Test
  public void shouldUpdateExistingBug() {
    Bug existing = bugRepository.findById(1L).get();
    existing.setTitle("Updated Title");

    Bug updated = bugRepository.save(existing);

    assertThat(updated.getTitle()).isEqualTo("Updated Title");
    assertThat(bugRepository.findById(1L).get().getTitle()).isEqualTo("Updated Title");
  }

  @Test
  public void shouldFindAllBugs() {
    List<Bug> allBugs = bugRepository.findAll();

    assertThat(allBugs).hasSize(5);
  }

  @Test
  public void shouldFindAllCriticalBugs() {
    List<Bug> criticalBugs = bugRepository.findByFilter(BugSeverity.CRITICAL);

    assertThat(criticalBugs).hasSize(2);
    assertThat(criticalBugs).extracting(Bug::getTitle)
        .containsExactlyInAnyOrder("Critical Error", "Another Critical");
  }

  @Test
  public void shouldFindAll() {
    List<Bug> result = bugRepository.findByFilter(null);

    assertThat(result).hasSize(5);
  }
}
