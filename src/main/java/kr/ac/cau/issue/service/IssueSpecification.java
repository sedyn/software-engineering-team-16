package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.model.Issue;
import kr.ac.cau.issue.repository.model.IssuePriority;
import kr.ac.cau.issue.repository.model.IssueStatus;
import kr.ac.cau.issue.repository.model.User;
import org.springframework.data.jpa.domain.Specification;

public class IssueSpecification {

    public static Specification<Issue> equalAssignee(User user) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("assignee"), user);
    }

    public static Specification<Issue> equalReporter(User user) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("reporter"), user);
    }

    public static Specification<Issue> equalStatus(IssueStatus issueStatus) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), issueStatus);
    }

    public static Specification<Issue> equalPriority(IssuePriority issuePriority) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority"), issuePriority);
    }

}
