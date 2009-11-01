package de.linsin.sample.github.rest.domain;

/**
 * Represents a response containing a {@link Repository}
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class RepositoryResponse {
    private Repository repository;

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository argRepository) {
        repository = argRepository;
    }

    @Override
    public String toString() {
        return "RepositoryResponse{" +
                "repository=" + repository +
                '}';
    }
}
