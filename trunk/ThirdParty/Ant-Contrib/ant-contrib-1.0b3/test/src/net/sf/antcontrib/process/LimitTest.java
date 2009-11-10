/*
 * Copyright (c) 2001-2004 Ant-Contrib project.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.antcontrib.process;

import net.sf.antcontrib.BuildFileTestBase;

public class LimitTest extends BuildFileTestBase { 
    
    public LimitTest(String name) {
        super(name);
    }    
    
    public void setUp() {
        configureProject("test/resources/logic/limittest.xml");
    }
    
    public void test1() {
       expectLogNotContaining("test1", "_failed_");    
    }
    
    public void test2() {
       expectLogContaining("test2", "_passed_");    
    }
}
