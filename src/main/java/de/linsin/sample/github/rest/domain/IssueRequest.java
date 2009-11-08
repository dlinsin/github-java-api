package de.linsin.sample.github.rest.domain;

/**
 * Represents a request for an issue
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class IssueRequest {
    private String login;
    private String token;

    public IssueRequest(String argLogin, String argToken) {
        login = argLogin;
        token = argToken;
    }

    public IssueRequest() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String argLogin) {
        login = argLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String argToken) {
        token = argToken;
    }

}