package ca.bakuryn.bugtracker.controller;

import ca.bakuryn.bugtracker.dto.BugDto;
import ca.bakuryn.bugtracker.dto.SeverityDto;
import ca.bakuryn.bugtracker.service.BugService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bugs")
public class BugController {

  private final BugService bugService;

  public BugController(BugService bugService) {
    this.bugService = bugService;
  }

  @GetMapping
  public List<BugDto> getAll(@RequestParam(required = false, name = "severity") SeverityDto severityDto) {
    return bugService.findAll(severityDto);
  }

  @GetMapping("/{id}")
  public BugDto getBugBiId(@PathVariable Long id) {
    return bugService.findById(id);
  }

  @PostMapping
  public BugDto create(@RequestBody BugDto bugDto) {
    return bugService.create(bugDto);
  }

  @PutMapping("/{id}")
  public BugDto update(@PathVariable Long id, @RequestBody BugDto dto) {
    return bugService.update(id, dto);
  }
}
