package de.linsin.sample.github.rest.service;

import de.linsin.sample.github.rest.domain.Issue;
import de.linsin.sample.github.rest.domain.IssueResponse;
import de.linsin.sample.github.rest.domain.IssuesResponse;
import de.linsin.sample.github.rest.domain.Repository;
import static org.easymock.classextension.EasyMock.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 * Testing {@link IssueBrowser}
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class IssueBrowserTest {
    private IssueBrowser classUnderTest;
    private RestTemplate mockRestTemplate;

    @Before
    public void setUp() {
        mockRestTemplate = createMock(RestTemplate.class);
        classUnderTest = new IssueBrowser() {
            @Override
            protected RestTemplate initTemplate() {
                return mockRestTemplate;
            }
        };
    }

    @After
    public void tearDown() {
        reset(mockRestTemplate);
    }

    @Test
    public void browse_open() {
        Repository repo = setupTestRepo();
        IssuesResponse response = new IssuesResponse();
        response.setIssues(new Issue[]{new Issue()});
        expect(mockRestTemplate.getForObject(IssueBrowser.ISSUES_OPEN_URL, IssuesResponse.class, repo.getOwner(), repo.getName())).andReturn(response);
        replay(mockRestTemplate);
        assertFalse(classUnderTest.browseOpen(repo).isEmpty());
        verify(mockRestTemplate);
    }

    @Test
    public void browse_open_empty() {
        Repository repo = setupTestRepo();
        IssuesResponse response = new IssuesResponse();
        response.setIssues(new Issue[0]);
        expect(mockRestTemplate.getForObject(IssueBrowser.ISSUES_OPEN_URL, IssuesResponse.class, repo.getOwner(), repo.getName())).andReturn(response);
        replay(mockRestTemplate);
        assertTrue(classUnderTest.browseOpen(repo).isEmpty());
        verify(mockRestTemplate);
    }

    @Test
    public void browse_open_nothing() {
        Repository repo = setupTestRepo();
        IssuesResponse response = new IssuesResponse();
        expect(mockRestTemplate.getForObject(IssueBrowser.ISSUES_OPEN_URL, IssuesResponse.class, repo.getOwner(), repo.getName())).andReturn(response);
        replay(mockRestTemplate);
        assertTrue(classUnderTest.browseOpen(repo).isEmpty());
        verify(mockRestTemplate);
    }

    @Test(expected = NullPointerException.class)
    public void browse_open_invalid_repo() {
        classUnderTest.browseOpen(null);
    }

    @Test
    public void browse_closed() {
        Repository repo = setupTestRepo();
        IssuesResponse response = new IssuesResponse();
        response.setIssues(new Issue[]{new Issue()});
        expect(mockRestTemplate.getForObject(IssueBrowser.ISSUES_CLOSED_URL, IssuesResponse.class, repo.getOwner(), repo.getName())).andReturn(response);
        replay(mockRestTemplate);
        assertFalse(classUnderTest.browseClosed(repo).isEmpty());
        verify(mockRestTemplate);
    }

    @Test
    public void browse_closed_empty() {
        Repository repo = setupTestRepo();
        IssuesResponse response = new IssuesResponse();
        response.setIssues(new Issue[0]);
        expect(mockRestTemplate.getForObject(IssueBrowser.ISSUES_OPEN_URL, IssuesResponse.class, repo.getOwner(), repo.getName())).andReturn(response);
        replay(mockRestTemplate);
        assertTrue(classUnderTest.browseOpen(repo).isEmpty());
        verify(mockRestTemplate);
    }

    @Test
    public void browse_closed_nothing() {
        Repository repo = setupTestRepo();
        IssuesResponse response = new IssuesResponse();
        expect(mockRestTemplate.getForObject(IssueBrowser.ISSUES_OPEN_URL, IssuesResponse.class, repo.getOwner(), repo.getName())).andReturn(response);
        replay(mockRestTemplate);
        assertTrue(classUnderTest.browseOpen(repo).isEmpty());
        verify(mockRestTemplate);
    }

    @Test(expected = NullPointerException.class)
    public void browse_closed_invalid_repo() {
        classUnderTest.browseOpen(null);
    }

    @Test
    public void browse_issue() {
        Repository repo = setupTestRepo();
        IssueResponse response = new IssueResponse();
        response.setIssue(new Issue());
        String issueNo = "1";
        expect(mockRestTemplate.getForObject(IssueBrowser.ISSUE_URL, IssueResponse.class, repo.getOwner(), repo.getName(), issueNo)).andReturn(response);
        replay(mockRestTemplate);
        assertNotNull(classUnderTest.browse(repo, Integer.valueOf(issueNo)));
        verify(mockRestTemplate);
    }

    @Test(expected = NullPointerException.class)
    public void browse_issue_response_null() {
        Repository repo = setupTestRepo();
        String issueNo = "1";
        expect(mockRestTemplate.getForObject(IssueBrowser.ISSUE_URL, IssueResponse.class, repo.getOwner(), repo.getName(), issueNo)).andReturn(null);
        replay(mockRestTemplate);
        assertNotNull(classUnderTest.browse(repo, Integer.valueOf(issueNo)));
    }

    @Test(expected = NullPointerException.class)
    public void browse_passed_null_repo() {
        classUnderTest.browse(null, 1);
    }

    @Test
    public void open_issue() {
        Repository repo = setupTestRepo();
        Issue issue = new Issue();
        issue.setTitle("Test");
        issue.setBody("test");
        IssueResponse response = new IssueResponse();
        response.setIssue(issue);
        expect(mockRestTemplate.postForObject(eq(IssueBrowser.OPEN_ISSUE_URL), anyObject(), eq(IssueResponse.class), eq(repo.getOwner()), eq(repo.getName()))).andReturn(response);
        replay(mockRestTemplate);
        assertNotNull(classUnderTest.open(repo, issue));
        verify(mockRestTemplate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void open_issue_no_title_passed() {
        classUnderTest.open(setupTestRepo(), new Issue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void open_issue_no_body_passed() {
        Issue issue = new Issue();
        issue.setTitle("test");
        classUnderTest.open(setupTestRepo(), issue);
    }

    @Test(expected = NullPointerException.class)
    public void open_issue_null_issue_passed() {
        classUnderTest.open(setupTestRepo(), null);
    }

    @Test(expected = NullPointerException.class)
    public void open_issue_null_repo_passed() {
        Issue issue = new Issue();
        issue.setTitle("Test");
        issue.setBody("test");
        classUnderTest.open(null, issue);
    }

    private Repository setupTestRepo() {
        Repository repo = new Repository();
        repo.setName("area51");
        repo.setOwner("dlinsin");
        return repo;
    }

}