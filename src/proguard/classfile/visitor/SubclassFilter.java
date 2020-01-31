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
package proguard.classfile.visitor;

import proguard.classfile.*;

/**
 * This {@link ClassVisitor} delegates its visits to another given
 * {@link ClassVisitor}, except for classes that have a given class as
 * direct subclass.
 *
 * @author Eric Lafortune
 */
public class SubclassFilter implements ClassVisitor
{
    private final Clazz        subclass;
    private final ClassVisitor classVisitor;


    /**
     * Creates a new SubclassFilter.
     * @param subclass     the class whose superclasses will not be visited.
     * @param classVisitor the <code>ClassVisitor</code> to which visits will
     *                     be delegated.
     */
    public SubclassFilter(Clazz        subclass,
                          ClassVisitor classVisitor)
    {
        this.subclass     = subclass;
        this.classVisitor = classVisitor;
    }


    // Implementations for ClassVisitor.

    public void visitProgramClass(ProgramClass programClass)
    {
        if (!present(programClass.subClasses))
        {
            classVisitor.visitProgramClass(programClass);
        }
    }


    public void visitLibraryClass(LibraryClass libraryClass)
    {
        if (!present(libraryClass.subClasses))
        {
            classVisitor.visitLibraryClass(libraryClass);
        }
    }


    // Small utility methods.

    private boolean present(Clazz[] subclasses)
    {
        if (subclasses == null)
        {
            return false;
        }

        for (int index = 0; index < subclasses.length; index++)
        {
            if (subclasses[index].equals(subclass))
            {
                return true;
            }
        }

        return false;
    }
}