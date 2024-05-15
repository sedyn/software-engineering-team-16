package kr.ac.cau.issue.repository;

import kr.ac.cau.issue.repository.model.Issue;
import kr.ac.cau.issue.repository.model.SimpleIssue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IssueRepository extends CrudRepository<Issue, Integer> {

    List<SimpleIssue> findAllByProjectId(String projectId);

}
