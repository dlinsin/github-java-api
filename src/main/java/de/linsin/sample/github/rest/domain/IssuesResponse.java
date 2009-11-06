package de.linsin.sample.github.rest.domain;

import java.util.Arrays;

/**
 * Represents a set of {@link Issue} instances
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class IssuesResponse {
    private Issue[] issues;

    public Issue[] getIssues() {
        return issues;
    }

    public void setIssues(Issue[] argIssues) {
        issues = argIssues;
    }

    @Override
    public String toString() {
        return "IssuesResponse{" +
                "issues=" + (issues == null ? null : Arrays.asList(issues)) +
                '}';
    }
}
