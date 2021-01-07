/*
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.java.tree

import org.junit.jupiter.api.Test
import org.openrewrite.java.JavaParser
import org.openrewrite.java.JavaTreeTest
import org.openrewrite.java.JavaTreeTest.NestingLevel.Block
import org.openrewrite.java.JavaTreeTest.NestingLevel.Class

interface VariableDeclsTest : JavaTreeTest {

    @Test
    fun fieldDefinition(jp: JavaParser) = assertParsePrintAndProcess(
        jp, Class, """
            public String a = "";
        """
    )

    @Test
    fun localVariableDefinition(jp: JavaParser) = assertParsePrintAndProcess(
        jp, Block, """
            String a = "";
        """
    )

    @Test
    fun fieldWithNoInitializer(jp: JavaParser) = assertParsePrintAndProcess(
        jp, Class, """
            public String a;
        """
    )

    @Test
    fun arrayVariables(jp: JavaParser) = assertParsePrintAndProcess(
        jp, Block, """
           int n [ ];
           String s [ ] [ ];
           int [ ] n2;
           String [ ] [ ] s2;
        """
    )

    @Test
    fun multipleDeclarationOneAssignment(jp: JavaParser) = assertParsePrintAndProcess(
        jp, Block, """
            int i , j = 0;
        """
    )

    @Test
    fun multipleDeclaration(jp: JavaParser) = assertParsePrintAndProcess(
        jp, Block, """
            Integer[] m = { 0 }, n[] = { { 0 } };
        """
    )

    /**
     * OpenJDK does NOT preserve the order of modifiers in its AST representation
     */
    @Test
    fun modifierOrdering(jp: JavaParser) = assertParsePrintAndProcess(
        jp, Class, """
            public /* static */ final static Integer n = 0;
        """
    )
}
