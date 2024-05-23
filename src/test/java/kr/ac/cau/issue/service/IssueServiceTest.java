package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.IssueRepository;
import kr.ac.cau.issue.repository.model.Issue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        when(issueRepository.findById(123)).thenReturn(Optional.of(expected));
        Optional<Issue> actual = issueService.getIssue(123);

        assertEquals(Optional.of(expected), actual);
    }

}
