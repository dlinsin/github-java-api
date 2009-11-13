package de.linsin.sample.github.rest.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.linsin.sample.github.rest.domain.Issue;
import de.linsin.sample.github.rest.domain.IssueResponse;
import de.linsin.sample.github.rest.domain.IssuesResponse;
import de.linsin.sample.github.rest.domain.OpenIssueRequest;
import de.linsin.sample.github.rest.domain.Repository;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * Provides methods to browser through GitHub issues for a particular repository.
 * You need your GitHub credentials, in order to use certain methods
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class IssueBrowser {
    public static final String BASE_URL = "http://github.com/api/v2/json/";
    public static final String ISSUES_BASE_URL = BASE_URL.concat("issues/list/{username}/{repo}");
    public static final String ISSUE_BASE_URL = BASE_URL.concat("issues/show/{username}/{repo}");
    public static final String ISSUES_OPEN_URL = ISSUES_BASE_URL.concat("/open");
    public static final String ISSUES_CLOSED_URL = ISSUES_BASE_URL.concat("/closed");
    public static final String ISSUE_URL = ISSUE_BASE_URL.concat("/{no}");

    public static final String OPEN_ISSUE_URL = BASE_URL.concat("issues/open/{username}/{repo}");
    public static final String CLOSE_ISSUE_URL = BASE_URL.concat("issues/close/{username}/{repo}/{no}");
    public static final String REOPEN_ISSUE_URL = BASE_URL.concat("issues/reopen/{username}/{repo}/{no}");
    public static final String EDIT_ISSUE_URL = BASE_URL.concat("issues/edit/{username}/{repo}/{no}");

    public static final String COMMENT_ISSUE_URL = BASE_URL.concat("issues/comment/{username}/{repo}/{no}");

    private String apiToken;
    private String username;

    /**
     * Initializes invariables of IssueBrowser
     *
     * @param argUsername {@link String} username of the callee
     * @param argApiToken {@link String} api token that you need in order to call GitHub
     */
    public IssueBrowser(String argUsername, String argApiToken) {
        apiToken = argApiToken;
        username = argUsername;
    }

    /**
     * Use this constructor in case you don't want to authorize.
     * Note: various methods need authorization.
     */
    public IssueBrowser() {
    }

    /**
     * Browse open issues of a repository
     *
     * @param argRepository {@link Repository} instance which contains name and owner
     * @return {@link List} containing {@link Issue} instances, empty List if no open issues are found
     * @throws NullPointerException     in case passed repository is null
     * @throws HttpClientErrorException in case passed user or repository doesn't exist
     */
    public List<Issue> browseOpen(Repository argRepository) {
        return doBrowse(argRepository, ISSUES_OPEN_URL);
    }

    /**
     * Browse closed issues of a repository
     *
     * @param argRepository {@link Repository} instance which contains name and owner
     * @return {@link List} containing {@link Issue} instances, empty List if no closed issues are found
     * @throws NullPointerException     in case passed repository is null
     * @throws HttpClientErrorException in case passed user or repository doesn't exist
     */
    public List<Issue> browseClosed(Repository argRepository) {
        return doBrowse(argRepository, ISSUES_CLOSED_URL);
    }

    /**
     * Browse a specific issue of a repository
     *
     * @param argRepository {@link Repository} instance which contains name and owner
     * @param argIssueNo    int which is the number of the issue, you want to browse
     * @return {@link Issue} instance with passed id
     * @throws NullPointerException     in case passed repository is null
     * @throws HttpClientErrorException in case passed user, repository or issue doesn't exist
     */
    public Issue browse(Repository argRepository, int argIssueNo) {
        RestTemplate template = initTemplate();
        IssueResponse resp = template.getForObject(ISSUE_URL, IssueResponse.class, argRepository.getOwner(), argRepository.getName(), String.valueOf(argIssueNo));
        return resp.getIssue();
    }

    /**
     * Opens the passed {@link Issue} in the passed {@link Repository}
     * Note: so far only title and body are used from passed instance
     *
     * @param argRepository {@link Repository} instance used to open issue
     * @param argIssue      {@link Issue} instance containing title and body of the issue
     * @return the {@link Issue} instance which was opened and contains assigned id
     * @throws IllegalArgumentException in case passed Issue doesn't contain a body or title
     * @throws NullPointerException     in case passed repository or issue is null
     * @throws HttpClientErrorException in case passed user or repository doesn't exist
     */
    public Issue open(Repository argRepository, Issue argIssue) {
        RestTemplate template = initTemplate();

        Assert.hasText(argIssue.getTitle());
        Assert.hasText(argIssue.getBody());

        OpenIssueRequest req = new OpenIssueRequest(username, apiToken, argIssue.getTitle(), argIssue.getBody());
        IssueResponse resp = template.postForObject(OPEN_ISSUE_URL, req, IssueResponse.class, argRepository.getOwner(), argRepository.getName());

        return resp.getIssue();
    }

    /**
     * Reopens the passed {@link Issue} in the passed {@link Repository}
     * Note: so far only title and body are used from passed instance
     *
     * @param argRepository {@link Repository} instance used to open issue
     * @param argIssue      {@link Issue} instance containing title and body of the issue
     * @return the {@link Issue} instance which was opened and contains assigned id
     * @throws IllegalArgumentException in case passed Issue doesn't contain a body or title
     * @throws NullPointerException     in case passed repository or issue is null
     * @throws HttpClientErrorException in case passed user, repository or issue doesn't exist
     */
    public Issue reopen(Repository argRepository, Issue argIssue) {
        RestTemplate template = initTemplate();

        Assert.hasText(argIssue.getTitle());
        Assert.hasText(argIssue.getBody());

        OpenIssueRequest req = new OpenIssueRequest(username, apiToken, argIssue.getTitle(), argIssue.getBody());
        IssueResponse resp = template.postForObject(OPEN_ISSUE_URL, req, IssueResponse.class, argRepository.getOwner(), argRepository.getName());

        return resp.getIssue();
    }

    protected List<Issue> doBrowse(Repository argRepository, String argUrl) {
        RestTemplate template = initTemplate();
        IssuesResponse resp = template.getForObject(argUrl, IssuesResponse.class, argRepository.getOwner(), argRepository.getName());
        if (resp == null || resp.getIssues() == null || resp.getIssues().length == 0) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(resp.getIssues());
        }
    }

    protected RestTemplate initTemplate() {
        RestTemplate template = new RestTemplate();
        template.setMessageConverters(new HttpMessageConverter[]{new MappingJacksonHttpMessageConverter()});
        return template;
    }
}
