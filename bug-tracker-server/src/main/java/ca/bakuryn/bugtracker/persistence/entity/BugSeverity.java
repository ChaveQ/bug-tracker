package ca.bakuryn.bugtracker.persistence.entity;

public enum BugSeverity {
  CRITICAL(1), HIGH(2), MEDIUM(3), LOW(4);

  private final Integer id;

  BugSeverity(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public static BugSeverity fromId(Integer id) {
    for (BugSeverity status : values()) {
      if (status.id.equals(id)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Unknown Severity ID: " + id);
  }

}
