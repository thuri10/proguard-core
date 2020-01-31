/*
 * ProGuard Core -- library to process Java bytecode.
 *
 * Copyright (c) 2002-2020 Guardsquare NV
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
package proguard.resources.file.visitor;

import proguard.resources.file.ResourceFile;

/**
 * Abstract class providing default implementations for {@link ResourceFileVisitor}.
 *
 * @author Johan Leys
 */
public abstract class SimplifiedResourceFileVisitor
{
    // Simplifications for ResourceFileVisitor.

    public void visitAnyResourceFile(ResourceFile resourceFile)
    {
        throw new UnsupportedOperationException("Method must be overridden in ["+this.getClass().getName()+"] if ever called");
    }


    // ...


    public void visitResourceFile(ResourceFile resourceFile)
    {
        visitAnyResourceFile(resourceFile);
    }
}
