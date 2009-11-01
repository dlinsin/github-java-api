package de.linsin.sample.github;

import de.linsin.sample.github.rest.domain.RepositoriesResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * TODO document
 *
 * @author David Linsin - linsin@synyx.de
 */
public class StartUp {
    public static void main(String[] args) {
        RestTemplate template = new RestTemplate();
//        template.setMessageConverters(new HttpMessageConverter[] {new MappingJacksonHttpMessageConverter<RepositoryResponse>()});
//        RepositoryResponse object = template.getForObject("http://github.com/api/v2/json/repos/show/{username}/{repo}", RepositoryResponse.class, "dlinsin", "area51");
        template.setMessageConverters(new HttpMessageConverter[] {new MappingJacksonHttpMessageConverter<RepositoriesResponse>()});
        String test = null;
        RepositoriesResponse object = template.getForObject("http://github.com/api/v2/json/repos/show/{username}", RepositoriesResponse.class, test);
//        template.setMessageConverters(new HttpMessageConverter[]{new MappingJacksonHttpMessageConverter()});
//        Map object = template.getForObject("http://github.com/api/v2/json/repos/show/{username}", HashMap.class, "dlinsin");
        System.out.println("object = " + object);
    }
}
