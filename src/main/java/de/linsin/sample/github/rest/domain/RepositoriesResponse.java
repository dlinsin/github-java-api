package de.linsin.sample.github.rest.domain;

import java.util.Arrays;

/**
 * Represents a set of {@link Repository} instances
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class RepositoriesResponse {
    private Repository[] repositories;

    public Repository[] getRepositories() {
        return repositories;
    }

    public void setRepositories(Repository[] argRepositories) {
        repositories = argRepositories;
    }

    @Override
    public String toString() {
        return "RepositoriesResponse{" +
                "repositories=" + (repositories == null ? null : Arrays.asList(repositories)) +
                '}';
    }
}
