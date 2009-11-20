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

/**
 * Domain class representing a comment on an Issue
 * Note: this is preliminary, since the github API only support adding a comment
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class Comment {
    private String comment;
    private Status status;

    public Comment() {
        status = Status.saved;
    }

    // TODO find out other statuses
    public enum Status {
        saved;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String argComment) {
        comment = argComment;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(String argStatus) {
        status = Status.valueOf(argStatus);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }
}
