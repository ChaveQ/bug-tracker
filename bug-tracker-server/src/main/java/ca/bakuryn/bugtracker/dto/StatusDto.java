package ca.bakuryn.bugtracker.dto;

import ca.bakuryn.bugtracker.persistence.entity.BugStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusDto {
  OPEN(BugStatus.OPEN, "Open"),
  IN_PROGRESS(BugStatus.IN_PROGRESS, "In Progress"),
  RESOLVED(BugStatus.RESOLVED, "Resolved"),
  REOPENED(BugStatus.REOPENED, "Reopened"),
  CLOSED(BugStatus.CLOSED, "Closed");

  private final BugStatus bugStatus;
  private final String name;

  StatusDto(BugStatus bugStatus, String name) {
    this.bugStatus = bugStatus;
    this.name = name;
  }

  public BugStatus getBugStatus() {
    return bugStatus;
  }

  @JsonValue
  public String getName() {
    return name;
  }

  @JsonCreator
  public static StatusDto fromName(String name) {
    for (StatusDto dto : StatusDto.values()) {
      if (dto.name.equals(name)) {
        return dto;
      }
    }
    throw new IllegalArgumentException("Unknown Status name: " + name);
  }
}
