/*
 * Copyright 2016 the original author or authors.
 *
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

package org.gradle.testing.jacoco.plugins.rules

import org.gradle.integtests.fixtures.MultiVersionIntegrationSpec
import org.gradle.testing.jacoco.plugins.fixtures.JavaProjectUnderTest
import org.gradle.util.Requires
import org.gradle.util.TestPrecondition

import static JacocoViolationRulesLimit.Sufficient

@Requires(TestPrecondition.JDK7_OR_EARLIER)
abstract class AbstractJacocoPluginCoverageVerificationVersionIntegrationTest extends MultiVersionIntegrationSpec {

    private final JavaProjectUnderTest javaProjectUnderTest = new JavaProjectUnderTest(testDirectory)
    protected final static String[] TEST_AND_JACOCO_COVERAGE_VERIFICATION_TASK_PATHS = [':test', ':jacocoTestCoverageVerification'] as String[]

    def setup() {
        javaProjectUnderTest.writeBuildScript().writeSourceFiles()

        buildFile << """
            jacoco {
                toolVersion = '$version'
            }
            
            jacocoTestCoverageVerification {
                violationRules {
                    rule {
                        $Sufficient.LINE_METRIC_COVERED_RATIO
                    }
                }
            }
        """
    }
}
