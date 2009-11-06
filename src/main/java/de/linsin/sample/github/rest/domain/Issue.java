package de.linsin.sample.github.rest.domain;

import java.util.Date;

/**
 * Represents an issue on GitHub
 *
 * @author David Linsin - linsin@synyx.de
 */
public class Issue {
    private long number;
    private int votes;
    private Date created_at;
    private String body;
    private String title;
    private Date updated_at;
    private String user;
    private State state;

    public enum State {
        OPEN, CLOSED;

        State() {
        }
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long argNumber) {
        number = argNumber;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int argVotes) {
        votes = argVotes;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date argCreated_at) {
        created_at = argCreated_at;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String argBody) {
        body = argBody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String argTitle) {
        title = argTitle;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date argUpdated_at) {
        updated_at = argUpdated_at;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String argUser) {
        user = argUser;
    }

    public String getState() {
        return state.name();
    }

    public void setState(String argState) {
        state = State.valueOf(argState);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        return number == issue.number;
    }

    @Override
    public int hashCode() {
        int result = (int) (number ^ (number >>> 32));
        result = 31 * result + votes;
        result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "number=" + number +
                ", votes=" + votes +
                ", created_at=" + created_at +
                ", body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", updated_at=" + updated_at +
                ", user='" + user + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
