package de.linsin.sample.github.rest.service;

import java.util.List;

import de.linsin.sample.github.rest.domain.Issue;
import de.linsin.sample.github.rest.domain.Repository;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing {@link IssueBrowserIntegration}
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class IssueBrowserIntegrationTest {
    private IssueBrowser classUnderTest;
    private Repository repo;
    private String username;
    private String repoName;

    @Before
    public void setUp() {
        username = "dlinsin";
        repoName = "area51";
        repo = new Repository();
        repo.setOwner(username);
        repo.setName(repoName);
        classUnderTest = new IssueBrowser(username, System.getProperty("apiToken"));
    }

    @Test
    public void browse_open() {
        List<Issue> issues = classUnderTest.browseOpen(repo);
        assertFalse(issues.isEmpty());
    }

}