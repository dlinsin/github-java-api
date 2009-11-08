package de.linsin.sample.github.rest.domain;

/**
 * Represents a response containing a {@link Issue}
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class IssueResponse {
    private Issue issue;

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue argIssue) {
        issue = argIssue;
    }

    @Override
    public String toString() {
        return "IssueResponse{" +
                "issue=" + issue +
                '}';
    }
}
