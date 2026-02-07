package ca.bakuryn.bugtracker.service;

import ca.bakuryn.bugtracker.dto.BugDto;
import ca.bakuryn.bugtracker.dto.SeverityDto;
import ca.bakuryn.bugtracker.mapper.BugMapper;
import ca.bakuryn.bugtracker.mapper.SeverityMapper;
import ca.bakuryn.bugtracker.persistence.entity.Bug;
import ca.bakuryn.bugtracker.persistence.repository.BugRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BugServiceImpl implements BugService {

  private final BugRepository bugRepository;
  private final BugMapper bugMapper;
  private final SeverityMapper severityMapper;

  public BugServiceImpl(BugRepository bugRepository, BugMapper bugMapper, SeverityMapper severityMapper) {
    this.bugRepository = bugRepository;
    this.bugMapper = bugMapper;
    this.severityMapper = severityMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<BugDto> findAll(SeverityDto severityDto) {
    List<Bug> bugList = severityDto != null
        ? bugRepository.findByFilter(severityMapper.fromDto(severityDto))
        : bugRepository.findAll();
    return bugList.stream()
        .map(bugMapper::toDto)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public BugDto findById(Long id) {
    return bugRepository.findById(id)
        .map(bugMapper::toDto)
        .orElseThrow(() -> new EntityNotFoundException("Bug not found"));
  }

  @Override
  public BugDto create(BugDto bugDto) {
    bugDto.setId(null);
    return save(bugDto);
  }

  @Override
  public BugDto update(Long id, BugDto bugDto) {
    if (id == null || bugDto.getId() == null || !id.equals(bugDto.getId())) {
      throw new IllegalArgumentException("Id mismatch error");
    }
    if (!bugRepository.existsById(bugDto.getId())) {
      throw new EntityNotFoundException("Bug not found");
    }
    return save(bugDto);
  }

  private BugDto save(BugDto bugDto) {
    Bug bug = bugMapper.fromDto(bugDto);
    Bug saved = bugRepository.save(bug);
    return bugMapper.toDto(saved);
  }
}
