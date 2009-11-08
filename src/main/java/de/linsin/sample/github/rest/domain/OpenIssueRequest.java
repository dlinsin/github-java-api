package de.linsin.sample.github.rest.domain;

/**
 * Represents a request to open an issue
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class OpenIssueRequest extends IssueRequest {
    private String title;
    private String body;

    public OpenIssueRequest(String argLogin, String argToken, String argTitle, String argBody) {
        super(argLogin, argToken);
        title = argTitle;
        body = argBody;
    }

    public OpenIssueRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String argTitle) {
        title = argTitle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String argBody) {
        body = argBody;
    }
}
