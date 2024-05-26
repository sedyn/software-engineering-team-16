package kr.ac.cau.issue.service;

import kr.ac.cau.issue.controller.model.AddIssueRequest;
import kr.ac.cau.issue.repository.IssueRepository;
import kr.ac.cau.issue.repository.ProjectRepository;
import kr.ac.cau.issue.repository.UserRepository;
import kr.ac.cau.issue.repository.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static kr.ac.cau.issue.repository.model.User.NOT_ASSIGNED_USER;

@RequiredArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public List<Issue> getSimpleIssuesByProjectId(
            String projectId,
            String assignee,
            String reporter,
            String status,
            String priority
    ) {
        Specification<Issue> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("project"),
                        projectRepository.findById(projectId).orElseThrow()
                );

        if (!StringUtils.isEmpty(assignee) && !assignee.equals("Assignee")) {
            User user = userRepository.findByUsername(assignee).orElseThrow();
            spec = spec.and(IssueSpecification.equalAssignee(user));
        }

        if (!StringUtils.isEmpty(reporter) && !reporter.equals("Reporter")) {
            User user = userRepository.findByUsername(reporter).orElseThrow();
            spec = spec.and(IssueSpecification.equalReporter(user));
        }

        if (!StringUtils.isEmpty(status)) {
            try {
                IssueStatus issueStatus = IssueStatus.valueOf(status);
                spec = spec.and(IssueSpecification.equalStatus(issueStatus));
            } catch (Exception ignored) {

            }
        }

        if (!StringUtils.isEmpty(priority)) {
            try {
                IssuePriority issuePriority = IssuePriority.valueOf(priority);
                spec = spec.and(IssueSpecification.equalPriority(issuePriority));
            } catch (Exception ignored) {

            }
        }

        return issueRepository.findAll(spec);
    }

    public Optional<Issue> getIssue(int issueId, String projectId) {
        return issueRepository.findIssueByIssueIdAndProjectId(issueId, projectId);
    }

    public void addIssue(User user, String projectId, AddIssueRequest request) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        Issue issue = new Issue();
        issue.setIssueId(issueRepository.countIssueByProjectId(projectId) + 1);
        issue.setTitle(request.title());
        issue.setProject(project);
        issue.setReporter(user);
        issue.setStatus(request.status());
        issue.setPriority(request.priority());
        issue.setReportedAt(LocalDateTime.now());

        issueRepository.save(issue);
    }

    public void updateIssueDetail(
            String projectId,
            int issueId,
            String description,
            String fixer,
            String assignee,
            IssueStatus status,
            IssuePriority priority
    ) {
        Issue issue = issueRepository.findIssueByIssueIdAndProjectId(issueId, projectId).orElseThrow();
        issue.setDescription(description);
        issue.setStatus(status);
        issue.setPriority(priority);
        if (!fixer.equals(NOT_ASSIGNED_USER)) {
            issue.setFixer(userRepository.findByUsername(fixer).orElseThrow());
        }

        if (!assignee.equals(NOT_ASSIGNED_USER)) {
            issue.setAssignee(userRepository.findByUsername(assignee).orElseThrow());
        }

        issueRepository.save(issue);
    }

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Map<String, Map<IssueStatus, Integer>> getIssueStatistic(String projectId) {
        List<IssueStatisticRecord> issueStatisticRecords = issueRepository.getIssueStatistic(projectId);

        return issueStatisticRecords
                .stream()
                .reduce(
                        new HashMap<>(),
                        (acc, item) -> {
                            acc.compute(
                                    item.getDatetime().format(dateTimeFormatter),
                                    (k, v) -> {
                                        if (v == null) {
                                            v = new HashMap<>();
                                        }
                                        v.put(IssueStatus.valueOf(item.getStatus()), item.getCount());
                                        return v;
                                    }
                            );

                            return acc;
                        },
                        (a, b) -> {
                            a.putAll(b);
                            return a;
                        });
    }
}
