package de.linsin.sample.github.rest.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Represents an issue on GitHub
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class Issue {
    private long number;
    private int votes;
    private String created_at;
    private String body;
    private String title;
    private String updated_at;
    private String user;
    private State state;
    private String closed_at;
    private String[] labels;

    public Issue() {
        state = State.open;
    }

    public String getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(String argClosed_at) {
        closed_at = argClosed_at;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] argLabels) {
        labels = argLabels;
    }

    public enum State {
        open, closed;

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String argCreated_at) {
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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String argUpdated_at) {
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

    public Date created() {
        return convertDate(created_at);
    }

    public Date closed() {
        return convertDate(updated_at);
    }

    /**
     * Converts from  2009/11/01 07:27:56 -0800
     *
     * @param argDate {@link String} with date information
     * @return a {@link Date} instance, null if parsing fails
     */
    protected Date convertDate(String argDate) {
        String withoutTimeZone = argDate.substring(0, argDate.length() - 5).trim();
        try {
            return new SimpleDateFormat().parse(withoutTimeZone);
        } catch (ParseException e) {
            // TODO what can we do
            e.printStackTrace();
        }
        return null;
    }

    public Date updated() {
        return convertDate(updated_at);
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
        result = 31 * result + (closed_at != null ? closed_at.hashCode() : 0);
        result = 31 * result + (labels != null ? Arrays.hashCode(labels) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "number=" + number +
                ", votes=" + votes +
                ", created_at='" + created_at + '\'' +
                ", body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", user='" + user + '\'' +
                ", state=" + state +
                ", closed_at='" + closed_at + '\'' +
                ", labels=" + (labels == null ? null : Arrays.asList(labels)) +
                '}';
    }
}
