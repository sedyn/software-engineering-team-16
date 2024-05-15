package kr.ac.cau.issue.controller.model;

import java.util.List;

public record GetSimpleIssuesResponse(List<SimpleIssueDto> issues) {
}
