package kr.ac.cau.issue.repository;

import kr.ac.cau.issue.repository.model.Issue;
import kr.ac.cau.issue.repository.model.IssueStatisticRecord;
import kr.ac.cau.issue.repository.model.SimpleIssue;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends CrudRepository<Issue, Integer> {

    List<SimpleIssue> findAllByProjectId(String projectId);

    List<Issue> findAll(Specification<Issue> specification);

    Optional<Issue> findIssueByIssueIdAndProjectId(int issueId, String projectId);

    @Query(
            value = "select date_trunc('day', reported_at) as datetime, status, count(*) as count from issues where project_id = ?1 group by datetime, status",
            nativeQuery = true
    )
    List<IssueStatisticRecord> getIssueStatistic(String projectId);

    int countIssueByProjectId(String projectId);

}
