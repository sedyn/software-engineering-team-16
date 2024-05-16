package kr.ac.cau.issue.repository.model;

public enum IssuePriority {
    Blocker,
    Critical,
    Major,
    Minor,
    Trivial;

    public boolean isDefault() {
        return this == Major;
    }
}
