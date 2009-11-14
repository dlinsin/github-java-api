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

package de.linsin.github.rest.resource;

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
