package de.linsin.sample.github.rest.service;

import de.linsin.sample.github.rest.domain.RepositoriesResponse;
import de.linsin.sample.github.rest.domain.Repository;
import de.linsin.sample.github.rest.domain.RepositoryResponse;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.*;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 * Testing {@link RepositoryBrowser}
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class RepositoryBrowserTest {
    private RepositoryBrowser classUnderTest;
    private RestTemplate mockRestTemplate;

    @Before
    public void setUp() {
        mockRestTemplate = createMock(RestTemplate.class);
        classUnderTest = new RepositoryBrowser() {
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
    public void browse_repos() {
        String testUser = "dlinsin";
        RepositoriesResponse response = new RepositoriesResponse();
        response.setRepositories(new Repository[]{new Repository()});
        expect(mockRestTemplate.getForObject(RepositoryBrowser.REPOSITORIES_URL, RepositoriesResponse.class, testUser)).andReturn(response);
        replay(mockRestTemplate);
        assertNotNull(classUnderTest.browse(testUser));
        verify(mockRestTemplate);
    }

    @Test
    public void browse_repos_return_null() {
        String testUser = "dlinsin";
        expect(mockRestTemplate.getForObject(RepositoryBrowser.REPOSITORIES_URL, RepositoriesResponse.class, testUser)).andReturn(null);
        replay(mockRestTemplate);
        assertNotNull(classUnderTest.browse(testUser));
        verify(mockRestTemplate);
    }

    @Test(expected = NullPointerException.class)
    public void browse_repos_pass_null_user() {
        String testUser = null;
        expect(mockRestTemplate.getForObject(RepositoryBrowser.REPOSITORIES_URL, RepositoriesResponse.class, testUser)).andThrow(new NullPointerException());
        replay(mockRestTemplate);
        classUnderTest.browse(testUser);
    }

    @Test
    public void browse_repo() {
        String testUser = "dlinsin";
        String testRepo = "area51";
        RepositoryResponse response = new RepositoryResponse();
        response.setRepository(new Repository());
        expect(mockRestTemplate.getForObject(RepositoryBrowser.REPOSITORY_URL, RepositoryResponse.class, testUser, testRepo)).andReturn(response);
        replay(mockRestTemplate);
        assertNotNull(classUnderTest.browse(testUser, testRepo));
        verify(mockRestTemplate);
    }

    @Test
    public void browse_repo_return_null() {
        String testUser = "dlinsin";
        String testRepo = "area51";
        expect(mockRestTemplate.getForObject(RepositoryBrowser.REPOSITORY_URL, RepositoryResponse.class, testUser, testRepo)).andReturn(null);
        replay(mockRestTemplate);
        assertNull(classUnderTest.browse(testUser, testRepo));
        verify(mockRestTemplate);
    }

    @Test(expected = NullPointerException.class)
    public void browse_repos_pass_null_param() {
        String testUser = null;
        String testRepo = "area51";
        expect(mockRestTemplate.getForObject(RepositoryBrowser.REPOSITORY_URL, RepositoryResponse.class, testUser, testRepo)).andThrow(new NullPointerException());
        replay(mockRestTemplate);
        classUnderTest.browse(testUser, testRepo);
    }
}