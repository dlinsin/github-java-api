/*
 * Copyright 2009 David Linsin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.linsin.github.rest.service;

import java.util.List;

import de.linsin.github.rest.domain.Issue;
import de.linsin.github.rest.domain.Repository;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
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

    @Test
    public void close_issue() {
        Repository repo = setupTestRepo();
        Issue issue = setUpTestIssue(repo);
        classUnderTest.close(repo, issue);
        assertTrue(classUnderTest.browseClosed(repo).contains(issue));
    }

    @Test
    public void close_issue_invalid_user() {
        Repository repo = setupTestRepo();
        Issue issue = setUpTestIssue(repo);
        repo.setOwner(invalidUsername);
        try {
            classUnderTest.close(repo, issue);
            fail("expected exception");
        } catch (HttpClientErrorException e) {
            assertTrue(classUnderTest.browseOpen(setupTestRepo()).contains(issue));
        }
    }

    @Test
    public void close_issue_invalid_repo() {
        Repository repo = setupTestRepo();
        Issue issue = setUpTestIssue(repo);
        repo.setName(noGoodRepoName);
        try {
            classUnderTest.close(repo, issue);
            fail("expected exception");
        } catch (HttpClientErrorException e) {
            assertTrue(classUnderTest.browseOpen(setupTestRepo()).contains(issue));
        }
    }

    @Test
    public void close_issue_invalid_number() {
        Repository repo = setupTestRepo();
        Issue issue = setUpTestIssue(repo);
        Issue newIssue = new Issue();
        newIssue.setNumber(99999);
        try {
            classUnderTest.close(repo, newIssue);
            fail("expected exception");
        } catch (HttpClientErrorException e) {
            assertTrue(classUnderTest.browseOpen(repo).contains(issue));
        }
    }

    @Test
    public void reopen_issue() {
        Repository repo = setupTestRepo();
        Issue issue = setUpClosedTestIssue(repo);
        issue = classUnderTest.reopen(repo, issue);
        assertTrue(classUnderTest.browseOpen(repo).contains(issue));
    }

    @Test
    public void reopen_issue_invalid_user() {
        Repository repo = setupTestRepo();
        Issue issue = setUpClosedTestIssue(repo);
        repo.setOwner(invalidUsername);
        try {
            classUnderTest.reopen(repo, issue);
            fail("expected exception");
        } catch (HttpClientErrorException e) {
            assertTrue(classUnderTest.browseClosed(setupTestRepo()).contains(issue));
        }
    }

    @Test
    public void reopen_issue_invalid_repo() {
        Repository repo = setupTestRepo();
        Issue issue = setUpClosedTestIssue(repo);
        repo.setName(noGoodRepoName);
        try {
            classUnderTest.reopen(repo, issue);
            fail("expected exception");
        } catch (HttpClientErrorException e) {
            assertTrue(classUnderTest.browseClosed(setupTestRepo()).contains(issue));
        }
    }

    @Test(expected = HttpClientErrorException.class)
    public void reopen_issue_invalid_number() {
        Repository repo = setupTestRepo();
        Issue issue = new Issue();
        issue.setNumber(99999);
        classUnderTest.close(repo, issue);
    }

    @Test
    public void edit_issue() {
        Repository repo = setupTestRepo();
        Issue issue = setUpTestIssue(repo);
        String body = "this was edited";
        issue.setBody(body);
        Issue editedIssue = classUnderTest.edit(repo, issue);
        assertEquals(issue, editedIssue);
        assertEquals(body, editedIssue.getBody());
        assertEquals(issue.getBody(), editedIssue.getBody());
    }

    @Test
    public void edit_issue_invalid_repo() {
        Repository repo = setupTestRepo();
        Issue issue = setUpTestIssue(repo);
        issue.setBody("this is edit");
        repo.setName(noGoodRepoName);
        try {
            classUnderTest.edit(repo, issue);
            fail("expected exception");
        } catch (HttpClientErrorException e) {
            assertFalse(classUnderTest.browse(setupTestRepo(), issue.getNumber()).getBody().equals(issue.getBody()));
        }
    }

    // TODO check this
    @Test
    @Ignore
    public void edit_issue_invalid_user() {
        Repository repo = setupTestRepo();
        Issue issue = setUpTestIssue(repo);
        issue.setBody("this is edit");
        repo.setOwner(invalidUsername);
        try {
            classUnderTest.edit(repo, issue);
            fail("expected exception");
        } catch (HttpClientErrorException e) {
            assertFalse(classUnderTest.browse(setupTestRepo(), issue.getNumber()).getBody().equals(issue.getBody()));
        }
    }

    @Test(expected = HttpClientErrorException.class)
    public void edit_issue_invalid_number() {
        Repository repo = setupTestRepo();
        Issue issue = new Issue();
        issue.setNumber(99999);
        issue.setTitle("test");
        issue.setBody("test body");
        classUnderTest.edit(repo, issue);
    }

    private Issue setUpTestIssue(Repository argRepo) {
        Issue newIssue = new Issue();
        String title = "Issue " + System.currentTimeMillis();
        newIssue.setTitle(title);
        String body = "my test issue";
        newIssue.setBody(body);
        return classUnderTest.open(argRepo, newIssue);
    }

    private Issue setUpClosedTestIssue(Repository argRepo) {
        Issue newIssue = new Issue();
        String title = "Issue " + System.currentTimeMillis();
        newIssue.setTitle(title);
        String body = "my test issue";
        newIssue.setBody(body);
        newIssue = classUnderTest.open(argRepo, newIssue);
        classUnderTest.close(argRepo, newIssue);
        return newIssue;
    }
}