package ca.bakuryn.bugtracker.dto;

import ca.bakuryn.bugtracker.persistence.entity.BugSeverity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SeverityDto {
  CRITICAL(BugSeverity.CRITICAL, "Critical"),
  HIGH(BugSeverity.HIGH, "High"),
  MEDIUM(BugSeverity.MEDIUM, "Medium"),
  LOW(BugSeverity.LOW, "Low");

  private final BugSeverity bugSeverity;
  private final String name;

  SeverityDto(BugSeverity bugSeverity, String name) {
    this.bugSeverity = bugSeverity;
    this.name = name;
  }

  public BugSeverity getBugSeverity() {
    return bugSeverity;
  }

  @JsonValue
  public String getName() {
    return name;
  }

  @JsonCreator
  public static SeverityDto fromName(String name) {
    for (SeverityDto dto : SeverityDto.values()) {
      if (dto.name.equals(name)) {
        return dto;
      }
    }
    throw new IllegalArgumentException("Unknown Severity name: " + name);
  }

}
