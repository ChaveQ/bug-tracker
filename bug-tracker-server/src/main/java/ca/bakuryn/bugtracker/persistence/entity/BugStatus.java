package ca.bakuryn.bugtracker.persistence.entity;

public enum BugStatus {
  OPEN(1), IN_PROGRESS(2), RESOLVED(3), REOPENED(4), CLOSED(5);

  private final Integer id;

  BugStatus(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public static BugStatus fromId(Integer id) {
    for (BugStatus status : values()) {
      if (status.id.equals(id)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Unknown Status ID: " + id);
  }

}
