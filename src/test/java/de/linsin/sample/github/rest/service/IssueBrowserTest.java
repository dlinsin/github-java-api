package de.linsin.sample.github.rest.service;

import de.linsin.sample.github.rest.domain.Issue;
import de.linsin.sample.github.rest.domain.IssuesResponse;
import de.linsin.sample.github.rest.domain.Repository;
import static org.easymock.classextension.EasyMock.*;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

    private Repository setupTestRepo() {
        Repository repo = new Repository();
        repo.setName("area51");
        repo.setOwner("dlinsin");
        return repo;
    }

}