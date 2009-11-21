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

import de.linsin.github.rest.domain.Comment;

/**
 * Represents a response containing a {@link Comment}
 *
 * @author David Linsin - dlinsin@gmail.com
 */
public class CommentIssueResponse {
    private Comment comment;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment argComment) {
        comment = argComment;
    }

    @Override
    public String toString() {
        return "CommentIssueResponse{" +
                "comment=" + comment +
                '}';
    }
}
