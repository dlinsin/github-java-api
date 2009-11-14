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

package de.linsin.github.rest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import de.linsin.github.rest.service.IssueBrowserTest;
import de.linsin.github.rest.service.RepositoryBrowserTest;

/**
 * TestSuit running all unit tests
 *
 * @author David Linsin - dlinsin@gmail.com
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({IssueBrowserTest.class, RepositoryBrowserTest.class})
public class UnitTestSuit {
}