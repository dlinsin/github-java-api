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

import de.linsin.github.rest.domain.Repository;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Testing {@link RepositoryBrowserIntegration}
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class RepositoryBrowserIntegrationTest {
    private RepositoryBrowser classUnderTest;
    private String username;
    private String repoName;

    @Before
    public void setUp() {
        username = "dlinsin";
        repoName = "area51";
        classUnderTest = new RepositoryBrowser();
    }

    @Test
    public void browse_repos() {
        List<Repository> repositoryList = classUnderTest.browse(username);
        assertFalse(repositoryList.isEmpty());
        assertEquals(repoName, repositoryList.get(0).getName());
        assertEquals(username, repositoryList.get(0).getOwner());
    }

    @Test
    public void browse_repo() {
        Repository repository = classUnderTest.browse(username, repoName);
        assertNotNull(repository);
        assertEquals(repoName, repository.getName());
        assertEquals(username, repository.getOwner());
    }

    @Test(expected = HttpClientErrorException.class)
    public void browse_user_not_found() {
        classUnderTest.browse("dlinsin34323");
    }

    @Test(expected = HttpClientErrorException.class)
    public void browse_repo_user_nothing_found() {
        classUnderTest.browse("dlinsin2342", "no_way");
    }

    @Test(expected = NullPointerException.class)
    public void browse_repos_passed_null() {
        classUnderTest.browse(null);
    }

    @Test(expected = NullPointerException.class)
    public void browse_repo_passed_null() {
        classUnderTest.browse(null, repoName);
    }
}