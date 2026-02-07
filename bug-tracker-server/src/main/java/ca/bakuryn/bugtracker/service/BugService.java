package ca.bakuryn.bugtracker.service;

import ca.bakuryn.bugtracker.dto.BugDto;
import ca.bakuryn.bugtracker.dto.SeverityDto;
import java.util.List;

public interface BugService {

  List<BugDto> findAll(SeverityDto severityDto);
  BugDto findById(Long id);
  BugDto create(BugDto bugDto);
  BugDto update(Long id, BugDto bugDto);
}
