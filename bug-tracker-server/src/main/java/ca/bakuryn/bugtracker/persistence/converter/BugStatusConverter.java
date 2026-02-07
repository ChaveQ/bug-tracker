package ca.bakuryn.bugtracker.persistence.converter;

import ca.bakuryn.bugtracker.persistence.entity.BugStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BugStatusConverter implements AttributeConverter<BugStatus, Integer> {

  @Override
  public Integer convertToDatabaseColumn(BugStatus status) {
    return status == null ? null : status.getId();
  }

  @Override
  public BugStatus convertToEntityAttribute(Integer statusId) {
    return statusId == null ? null : BugStatus.fromId(statusId);
  }
}
