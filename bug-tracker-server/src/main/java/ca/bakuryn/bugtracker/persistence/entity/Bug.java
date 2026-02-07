package ca.bakuryn.bugtracker.persistence.entity;

import ca.bakuryn.bugtracker.persistence.converter.BugSeverityConverter;
import ca.bakuryn.bugtracker.persistence.converter.BugStatusConverter;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bugs")
public class Bug {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "severity_id")
  @Convert(converter = BugSeverityConverter.class)
  private BugSeverity severity;

  @Column(name = "status_id")
  @Convert(converter = BugStatusConverter.class)
  private BugStatus status;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BugSeverity getSeverity() {
    return severity;
  }

  public void setSeverity(BugSeverity severity) {
    this.severity = severity;
  }

  public BugStatus getStatus() {
    return status;
  }

  public void setStatus(BugStatus status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
