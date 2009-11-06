package de.linsin.sample.github.rest.service;

import java.util.List;

import de.linsin.sample.github.rest.domain.Issue;
import de.linsin.sample.github.rest.domain.Repository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Testing {@link IssueBrowserIntegration}
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class IssueBrowserIntegrationTest {
    private IssueBrowser classUnderTest;
    private String username;
    private String invalidUsername;
    private String repoName;
    private String noGoodRepoName;
    private String noIssuesRepoName;

    @Before
    public void setUp() {
        username = "dlinsin";
        invalidUsername = "dlinsin3805";
        repoName = "area51";
        noGoodRepoName = "blaha232";
        noIssuesRepoName = "synoccer";
        classUnderTest = new IssueBrowser(username, System.getProperty("apiToken"));
    }

    @Test
    public void browse_open() {
        Repository repo = new Repository();
        repo.setOwner(username);
        repo.setName(repoName);
        List<Issue> issues = classUnderTest.browseOpen(repo);
        assertFalse(issues.isEmpty());
        assertEquals(Issue.State.open.name(), issues.get(0).getState());
    }

    @Test
    public void browse_open_no_issues() {
        Repository repo = new Repository();
        repo.setOwner(username);
        repo.setName(noIssuesRepoName);
        List<Issue> issues = classUnderTest.browseOpen(repo);
        assertTrue(issues.isEmpty());
    }

    @Test(expected = HttpClientErrorException.class)
    public void browse_open_invalid_user() {
        Repository repo = new Repository();
        repo.setOwner(invalidUsername);
        repo.setName(noIssuesRepoName);
        classUnderTest.browseOpen(repo);
    }

    @Test(expected = HttpClientErrorException.class)
    public void browse_open_invalid_repo() {
        Repository repo = new Repository();
        repo.setOwner(username);
        repo.setName(invalidUsername);
        classUnderTest.browseOpen(repo);
    }

    @Test
    public void browse_closed() {
        Repository repo = new Repository();
        repo.setOwner(username);
        repo.setName(repoName);
        List<Issue> issues = classUnderTest.browseClosed(repo);
        assertFalse(issues.isEmpty());
        assertEquals(Issue.State.closed.name(), issues.get(0).getState());
    }

    @Test
    public void browse_closed_no_issues() {
        Repository repo = new Repository();
        repo.setOwner(username);
        repo.setName(noIssuesRepoName);
        List<Issue> issues = classUnderTest.browseClosed(repo);
        assertTrue(issues.isEmpty());
    }

    @Test(expected = HttpClientErrorException.class)
    public void browse_closed_invalid_user() {
        Repository repo = new Repository();
        repo.setOwner(invalidUsername);
        repo.setName(noIssuesRepoName);
        classUnderTest.browseClosed(repo);
    }

    @Test(expected = HttpClientErrorException.class)
    public void browse_closed_invalid_repo() {
        Repository repo = new Repository();
        repo.setOwner(username);
        repo.setName(invalidUsername);
        classUnderTest.browseClosed(repo);
    }
}