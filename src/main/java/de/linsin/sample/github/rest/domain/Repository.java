package de.linsin.sample.github.rest.domain;

/**
 * Represents a GitHub repository
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class Repository {
    private String description;
    private int forks;
    private int open_issues;
    private int watchers;
    private String url;
    private String homepage;
    private String fork;
    private String is_private;
    private String name;
    private String owner;

    public String getDescription() {
        return description;
    }

    public void setDescription(String argDescription) {
        description = argDescription;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int argForks) {
        forks = argForks;
    }

    public int getOpen_issues() {
        return open_issues;
    }

    public void setOpen_issues(int argOpen_issues) {
        open_issues = argOpen_issues;
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int argWatchers) {
        watchers = argWatchers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String argUrl) {
        url = argUrl;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String argHomepage) {
        homepage = argHomepage;
    }

    public String getFork() {
        return fork;
    }

    public void setFork(String argFork) {
        fork = argFork;
    }

    public String getPrivate() {
        return is_private;
    }

    public void setPrivate(String argIs_private) {
        is_private = argIs_private;
    }

    public String getName() {
        return name;
    }

    public void setName(String argName) {
        name = argName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String argOwner) {
        owner = argOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Repository that = (Repository) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + forks;
        result = 31 * result + open_issues;
        result = 31 * result + watchers;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (homepage != null ? homepage.hashCode() : 0);
        result = 31 * result + (fork != null ? fork.hashCode() : 0);
        result = 31 * result + (is_private != null ? is_private.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "description='" + description + '\'' +
                ", forks=" + forks +
                ", open_issues=" + open_issues +
                ", watchers=" + watchers +
                ", url='" + url + '\'' +
                ", homepage='" + homepage + '\'' +
                ", fork='" + fork + '\'' +
                ", is_private='" + is_private + '\'' +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
