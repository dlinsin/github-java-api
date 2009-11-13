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

package de.linsin.github.rest.domain;

import java.util.Arrays;

/**
 * Represents a set of {@link Issue} instances
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class IssuesResponse {
    private Issue[] issues;

    public Issue[] getIssues() {
        return issues;
    }

    public void setIssues(Issue[] argIssues) {
        issues = argIssues;
    }

    @Override
    public String toString() {
        return "IssuesResponse{" +
                "issues=" + (issues == null ? null : Arrays.asList(issues)) +
                '}';
    }
}
