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

package de.linsin.github;

import de.linsin.github.rest.resource.RepositoriesResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * TODO document
 *
 * @author David Linsin - dlinsin@gmail.com
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
