package ca.bakuryn.bugtracker.dto;

public class BugDto {
  private Long id;
  private String title;
  private String description;
  private SeverityDto severity;
  private StatusDto status;

  public BugDto() {
  }

  public BugDto(Long id, String title, String description, SeverityDto severity, StatusDto status) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.severity = severity;
    this.status = status;
  }

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

  public SeverityDto getSeverity() {
    return severity;
  }

  public void setSeverity(SeverityDto severity) {
    this.severity = severity;
  }

  public StatusDto getStatus() {
    return status;
  }

  public void setStatus(StatusDto status) {
    this.status = status;
  }
}
