package de.linsin.sample.github.rest.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.linsin.sample.github.rest.domain.RepositoriesResponse;
import de.linsin.sample.github.rest.domain.Repository;
import de.linsin.sample.github.rest.domain.RepositoryResponse;
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
     * @throws a {@link NullPointerException} in case passed username is null
     * @throws a {@link HttpClientErrorException} in case the user doesn't exist
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
     * @throws a {@link NullPointerException} in case passed username or repository name is null
     * @throws a {@link HttpClientErrorException} in case passed user or repository doesn't exist 
     */
    public Repository browse(String argUsername, String argRepositoryname) {
        RestTemplate template = initTemplate();
        RepositoryResponse resp = template.getForObject(REPOSITORY_URL, RepositoryResponse.class, argUsername, argRepositoryname);
        if (resp != null) {
            return resp.getRepository();
        }
        return null;
    }

    protected RestTemplate initTemplate() {
        RestTemplate template = new RestTemplate();
        template.setMessageConverters(new HttpMessageConverter[] {new MappingJacksonHttpMessageConverter()});
        return template;
    }

}
