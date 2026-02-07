package ca.bakuryn.bugtracker.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.bakuryn.bugtracker.dto.BugDto;
import ca.bakuryn.bugtracker.mapper.BugMapper;
import ca.bakuryn.bugtracker.persistence.entity.Bug;
import ca.bakuryn.bugtracker.persistence.repository.BugRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BugServiceImplTest {

  @Mock
  private BugRepository bugRepository;
  @Mock
  private BugMapper bugMapper;
  @InjectMocks
  private BugServiceImpl bugService;

  @Test
  public void shouldFindAll() {
    Bug bug = new Bug();
    BugDto bugDto = new BugDto();

    when(bugRepository.findAll()).thenReturn(List.of(bug));
    when(bugMapper.toDto(bug)).thenReturn(bugDto);

    List<BugDto> result = bugService.findAll(null);

    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(bugDto);
    verify(bugRepository).findAll();
    verify(bugRepository, never()).findByFilter(any());
  }

  @Test
  public void shouldFindById() {
    Long id = 1L;
    Bug bug = new Bug();
    BugDto dto = new BugDto();

    when(bugRepository.findById(id)).thenReturn(Optional.of(bug));
    when(bugMapper.toDto(bug)).thenReturn(dto);

    BugDto result = bugService.findById(id);

    assertThat(result).isEqualTo(dto);
    verify(bugRepository).findById(id);
  }

  @Test
  public void shouldThrowExceptionBugNotFound() {
    Long id = 1L;
    when(bugRepository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> bugService.findById(id))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage("Bug not found");
  }

  @Test
  public void shouldCreate() {
    BugDto inputDto = new BugDto();
    inputDto.setId(999L);

    Bug mappedEntity = new Bug();
    Bug savedEntity = new Bug();
    BugDto resultDto = new BugDto();

    when(bugMapper.fromDto(inputDto)).thenReturn(mappedEntity);
    when(bugRepository.save(mappedEntity)).thenReturn(savedEntity);
    when(bugMapper.toDto(savedEntity)).thenReturn(resultDto);

    BugDto result = bugService.create(inputDto);

    assertThat(inputDto.getId()).isNull();
    assertThat(resultDto).isEqualTo(result);
    verify(bugRepository).save(mappedEntity);
  }

  @Test
  public void shouldThrowExceptionIdIsNull() {
    BugDto dto = new BugDto();
    dto.setId(null);

    assertThatThrownBy(() -> bugService.update(1L, dto))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Id mismatch error");

    verify(bugRepository, never()).save(any());
  }

  @Test
  public void shouldThrowExceptionIdNotEqual() {
    BugDto dto = new BugDto();
    dto.setId(10L);

    assertThatThrownBy(() -> bugService.update(12L, dto))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Id mismatch error");

    verify(bugRepository, never()).save(any());
  }

}
