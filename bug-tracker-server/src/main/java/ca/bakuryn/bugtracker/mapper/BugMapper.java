package ca.bakuryn.bugtracker.mapper;

import ca.bakuryn.bugtracker.dto.BugDto;
import ca.bakuryn.bugtracker.persistence.entity.Bug;
import org.springframework.stereotype.Component;

@Component
public class BugMapper {

  private final SeverityMapper severityMapper;
  private final StatusMapper statusMapper;

  public BugMapper(SeverityMapper severityMapper, StatusMapper statusMapper) {
    this.severityMapper = severityMapper;
    this.statusMapper = statusMapper;
  }

  public BugDto toDto(Bug entity) {
    if (entity == null) {
      return null;
    }
    BugDto dto = new BugDto();
    dto.setId(entity.getId());
    dto.setTitle(entity.getTitle());
    dto.setDescription(entity.getDescription());
    dto.setSeverity(severityMapper.toDto(entity.getSeverity()));
    dto.setStatus(statusMapper.toDto(entity.getStatus()));
    return dto;
  }

  public Bug fromDto(BugDto dto) {
    if (dto == null) {
      return null;
    }
    Bug entity = new Bug();
    entity.setId(dto.getId());
    entity.setTitle(dto.getTitle());
    entity.setDescription(dto.getDescription());
    entity.setSeverity(severityMapper.fromDto(dto.getSeverity()));
    entity.setStatus(statusMapper.fromDto(dto.getStatus()));
    return entity;
  }

}
