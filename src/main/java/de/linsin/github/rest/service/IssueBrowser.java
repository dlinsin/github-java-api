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

import de.linsin.github.rest.domain.Issue;
import de.linsin.github.rest.domain.Repository;
import de.linsin.github.rest.resource.IssueRequest;
import de.linsin.github.rest.resource.IssueResponse;
import de.linsin.github.rest.resource.IssuesResponse;
import de.linsin.github.rest.resource.ManipulateIssueRequest;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * Provides methods to browser through GitHub issues for a particular repository.
 * You need your GitHub credentials, in order to use certain methods
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class IssueBrowser extends Browser {
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

        ManipulateIssueRequest req = new ManipulateIssueRequest(username, apiToken, argIssue.getTitle(), argIssue.getBody());
        IssueResponse resp = template.postForObject(OPEN_ISSUE_URL, req, IssueResponse.class, argRepository.getOwner(), argRepository.getName());

        return resp.getIssue();
    }

    /**
     * Reopens the passed {@link Issue} in the passed {@link Repository}
     *
     * @param argRepository {@link Repository} instance used to reopen issue
     * @param argIssue      {@link Issue} instance containing id
     * @return the {@link Issue} instance which was reopened
     * @throws IllegalArgumentException in case passed Issue doesn't have an id
     * @throws NullPointerException     in case passed repository or issue is null
     * @throws HttpClientErrorException in case passed user, repository or issue doesn't exist
     */
    public Issue reopen(Repository argRepository, Issue argIssue) {
        RestTemplate template = initTemplate();

        Assert.isTrue(argIssue.getNumber() > 0);

        IssueRequest req = new IssueRequest(username, apiToken);
        IssueResponse resp = template.postForObject(REOPEN_ISSUE_URL, req, IssueResponse.class, argRepository.getOwner(), argRepository.getName(), String.valueOf(argIssue.getNumber()));

        return resp.getIssue();
    }

    /**
     * Closes the passed {@link Issue} in the passed {@link Repository}
     *
     * @param argRepository {@link Repository} instance used to close issue
     * @param argIssue      {@link Issue} instance containing id
     * @throws IllegalArgumentException in case passed Issue doesn't contain an id
     * @throws NullPointerException     in case passed repository or issue is null
     * @throws HttpClientErrorException in case passed user, repository or issue doesn't exist
     */
    public void close(Repository argRepository, Issue argIssue) {
        RestTemplate template = initTemplate();

        Assert.isTrue(argIssue.getNumber() > 0);

        IssueRequest req = new IssueRequest(username, apiToken);
        template.postForObject(CLOSE_ISSUE_URL, req, IssueResponse.class, argRepository.getOwner(), argRepository.getName(), String.valueOf(argIssue.getNumber()));
    }


    /**
     * Edits the existing {@link Issue} passed, which resides in the provided {@link Repository}
     * Note: so far only id, title and body are used from passed instance
     *
     * @param argRepository {@link Repository} instance used to open issue
     * @param argIssue      {@link Issue} instance containing id, title and body of the issue
     * @return the {@link Issue} instance which was edited
     * @throws IllegalArgumentException in case passed Issue doesn't contain an id, body or title
     * @throws NullPointerException     in case passed repository or issue is null
     * @throws HttpClientErrorException in case passed user, repository or issue doesn't exist
     */
    public Issue edit(Repository argRepository, Issue argIssue) {
        RestTemplate template = initTemplate();

        Assert.isTrue(argIssue.getNumber() > 0);
        Assert.hasText(argIssue.getTitle());
        Assert.hasText(argIssue.getBody());

        ManipulateIssueRequest req = new ManipulateIssueRequest(username, apiToken, argIssue.getTitle(), argIssue.getBody());
        IssueResponse resp = template.postForObject(EDIT_ISSUE_URL, req, IssueResponse.class, argRepository.getOwner(), argRepository.getName(), String.valueOf(argIssue.getNumber()));

        return resp.getIssue();
    }

    // TODO implement comment

    protected List<Issue> doBrowse(Repository argRepository, String argUrl) {
        RestTemplate template = initTemplate();
        IssuesResponse resp = template.getForObject(argUrl, IssuesResponse.class, argRepository.getOwner(), argRepository.getName());
        if (resp == null || resp.getIssues() == null || resp.getIssues().length == 0) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(resp.getIssues());
        }
    }
}
