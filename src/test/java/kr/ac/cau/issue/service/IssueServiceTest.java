package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.IssueRepository;
import kr.ac.cau.issue.repository.model.Issue;
import kr.ac.cau.issue.repository.model.IssueStatisticRecord;
import kr.ac.cau.issue.repository.model.IssueStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueService issueService;

    @Test
    public void testGetIssue() {
        Issue expected = new Issue();
        expected.setId(123);
        expected.setTitle("Test");

        when(issueRepository.findIssueByIssueIdAndProjectId(123, "TEST")).thenReturn(Optional.of(expected));
        Optional<Issue> actual = issueService.getIssue(123, "TEST");

        assertEquals(Optional.of(expected), actual);
    }

    record IssueStatisticRecordImpl(
            LocalDateTime datetime,
            String status,
            int count
    ) implements IssueStatisticRecord {

        @Override
        public LocalDateTime getDatetime() {
            return datetime;
        }

        @Override
        public String getStatus() {
            return status;
        }

        @Override
        public int getCount() {
            return count;
        }
    }

    @Test
    public void testStatistic() {
        List<IssueStatisticRecord> records = List.of(
                new IssueStatisticRecordImpl(
                        LocalDateTime.of(2024, Month.APRIL, 10, 0, 0),
                        IssueStatus.Assigned.name(),
                        100
                ),
                new IssueStatisticRecordImpl(
                        LocalDateTime.of(2024, Month.APRIL, 10, 0, 0),
                        IssueStatus.New.name(),
                        123
                )
        );

        when(issueRepository.getIssueStatistic("TEST")).thenReturn(records);

        Map<String, Map<IssueStatus, Integer>> actual = issueService.getIssueStatistic("TEST");

        Map<String, Map<IssueStatus, Integer>> expected = new HashMap<>();
        Map<IssueStatus, Integer> inner = new HashMap<>();
        inner.put(IssueStatus.Assigned, 100);
        inner.put(IssueStatus.New, 123);

        expected.put("2024-04-10", inner);

        assertEquals(expected, actual);
    }

}
