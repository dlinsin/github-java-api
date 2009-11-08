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
        Repository repo = setupTestRepo();
        List<Issue> issues = classUnderTest.browseOpen(repo);
        assertFalse(issues.isEmpty());
        assertEquals(Issue.State.open.name(), issues.get(0).getState());
    }

    private Repository setupTestRepo() {
        Repository repo = new Repository();
        repo.setOwner(username);
        repo.setName(repoName);
        return repo;
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
        repo.setName(noGoodRepoName);
        classUnderTest.browseOpen(repo);
    }

    @Test
    public void browse_closed() {
        Repository repo = setupTestRepo();
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
        repo.setName(noGoodRepoName);
        classUnderTest.browseClosed(repo);
    }

    @Test
    public void browse_issue() {
        Repository repo = setupTestRepo();
        Issue issue = classUnderTest.browse(repo, 1);
        assertNotNull(issue);
        assertTrue(issue.getNumber() == 1);
    }

    @Test(expected = HttpClientErrorException.class)
    public void browse_none_existing_issue() {
        Repository repo = setupTestRepo();
        classUnderTest.browse(repo, 1024);
    }

    @Test(expected = HttpClientErrorException.class)
    public void browse_invalid_user() {
        Repository repo = new Repository();
        repo.setOwner(invalidUsername);
        repo.setName(repoName);
        classUnderTest.browse(repo, 1);
    }

    @Test(expected = HttpClientErrorException.class)
    public void browse_invalid_repo() {
        Repository repo = new Repository();
        repo.setOwner(username);
        repo.setName(noGoodRepoName);
        classUnderTest.browse(repo, 1024);
    }

    @Test
    public void open_issue() {
        Repository repo = setupTestRepo();
        Issue newIssue = new Issue();
        String title = "Issue " + System.currentTimeMillis();
        newIssue.setTitle(title);
        String body = "my test issue";
        newIssue.setBody(body);
        Issue ret = classUnderTest.open(repo, newIssue);
        assertEquals(title, ret.getTitle());
        assertEquals(body, ret.getBody());
        assertTrue(ret.getNumber() > 0);
    }

    @Test(expected = HttpClientErrorException.class)
    public void open_issue_invalid_user() {
        Repository repo = setupTestRepo();
        repo.setOwner(invalidUsername);

        Issue newIssue = new Issue();
        String title = "Issue " + System.currentTimeMillis();
        newIssue.setTitle(title);
        String body = "my test issue";
        newIssue.setBody(body);
        classUnderTest.open(repo, newIssue);
    }

    @Test(expected = HttpClientErrorException.class)
    public void open_issue_invalid_repo() {
        Repository repo = setupTestRepo();
        repo.setName(noGoodRepoName);

        Issue newIssue = new Issue();
        String title = "Issue " + System.currentTimeMillis();
        newIssue.setTitle(title);
        String body = "my test issue";
        newIssue.setBody(body);
        classUnderTest.open(repo, newIssue);
    }

    @Test(expected = HttpClientErrorException.class)
    public void open_issue_not_authorized() {
        classUnderTest = new IssueBrowser(username, "123");
        Repository repo = setupTestRepo();
        Issue newIssue = new Issue();
        String title = "Issue " + System.currentTimeMillis();
        newIssue.setTitle(title);
        String body = "my test issue";
        newIssue.setBody(body);
        classUnderTest.open(repo, newIssue);
    }
}