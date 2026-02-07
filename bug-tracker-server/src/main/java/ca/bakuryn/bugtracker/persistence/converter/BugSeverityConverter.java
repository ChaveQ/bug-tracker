package ca.bakuryn.bugtracker.persistence.converter;

import ca.bakuryn.bugtracker.persistence.entity.BugSeverity;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BugSeverityConverter implements AttributeConverter<BugSeverity, Integer> {

  @Override
  public Integer convertToDatabaseColumn(BugSeverity severity) {
    return severity == null ? null : severity.getId();
  }

  @Override
  public BugSeverity convertToEntityAttribute(Integer severityId) {
    return severityId == null ? null : BugSeverity.fromId(severityId);
  }
}
