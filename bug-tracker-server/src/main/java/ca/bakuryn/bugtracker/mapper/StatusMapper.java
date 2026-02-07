package ca.bakuryn.bugtracker.mapper;

import ca.bakuryn.bugtracker.dto.StatusDto;
import ca.bakuryn.bugtracker.persistence.entity.BugStatus;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {

  public StatusDto toDto(BugStatus status) {
    if (status == null) {
      return null;
    }
    for (StatusDto dto : StatusDto.values()) {
      if (dto.getBugStatus().equals(status)) {
        return dto;
      }
    }
    throw new IllegalArgumentException("StatusDto not found by: " + status.name());
  }

  public BugStatus fromDto(StatusDto dto) {
    if (dto == null) {
      return null;
    }
    return dto.getBugStatus();
  }

}
