package ca.bakuryn.bugtracker.mapper;

import ca.bakuryn.bugtracker.dto.SeverityDto;
import ca.bakuryn.bugtracker.persistence.entity.BugSeverity;
import org.springframework.stereotype.Component;

@Component
public class SeverityMapper {

  public SeverityDto toDto(BugSeverity severity) {
    if (severity == null) {
      return null;
    }
    for (SeverityDto dto : SeverityDto.values()) {
      if (dto.getBugSeverity().equals(severity)) {
        return dto;
      }
    }
    throw new IllegalArgumentException("SeverityDto not found by: " + severity.name());
  }

  public BugSeverity fromDto(SeverityDto dto) {
    if (dto == null) {
      return null;
    }
    return dto.getBugSeverity();
  }

}
