package kr.ac.cau.issue.repository;

import kr.ac.cau.issue.repository.model.Issue;
import kr.ac.cau.issue.repository.model.IssueStatisticRecord;
import kr.ac.cau.issue.repository.model.SimpleIssue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IssueRepository extends CrudRepository<Issue, Integer> {

    List<SimpleIssue> findAllByProjectId(String projectId);

    @Query(value = "select date_trunc('day', reported_at) as datetime, status, count(*) as issue_count from issues group by datetime, status", nativeQuery = true)
    List<IssueStatisticRecord> getIssueStatistic();

}
