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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.linsin.github.rest.domain.RepositoriesResponse;
import de.linsin.github.rest.domain.Repository;
import de.linsin.github.rest.domain.RepositoryResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Provides methods to browser through a GitHub repository
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class RepositoryBrowser {
    public static final String BASE_URL = "http://github.com/api/v2/json/";
    public static final String REPOSITORIES_URL = BASE_URL.concat("repos/show/{username}");
    public static final String REPOSITORY_URL = REPOSITORIES_URL.concat("/{repo}");

    /**
     * Browse repositories for a user
     *
     * @param argUsername {@link String} username
     * @return {@link List} containing {@link Repository} instances, empty List if user exists, but has no repositories
     * @throws NullPointerException in case passed username is null
     * @throws HttpClientErrorException in case the user doesn't exist
     */
    public List<Repository> browse(String argUsername) {
        RestTemplate template = initTemplate();
        RepositoriesResponse resp = template.getForObject(REPOSITORIES_URL, RepositoriesResponse.class, argUsername);
        if (resp == null || resp.getRepositories() == null || resp.getRepositories().length == 0) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(resp.getRepositories());
        }
    }

    /**
     * Browse a single respository for a user
     *
     * @param argUsername {@link String} username
     * @param argRepositoryname {@link String} name of repository
     * @return {@link Repository} instance denoted by passed user and repository name
     * @throws NullPointerException in case passed username or repository name is null
     * @throws HttpClientErrorException in case passed user or repository doesn't exist
     */
    public Repository browse(String argUsername, String argRepositoryname) {
        RestTemplate template = initTemplate();
        RepositoryResponse resp = template.getForObject(REPOSITORY_URL, RepositoryResponse.class, argUsername, argRepositoryname);
        return resp.getRepository();
    }

    // TODO move to base class
    protected RestTemplate initTemplate() {
        RestTemplate template = new RestTemplate();
        template.setMessageConverters(new HttpMessageConverter[] {new MappingJacksonHttpMessageConverter()});
        return template;
    }

}
