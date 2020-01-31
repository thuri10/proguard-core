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
package proguard.classfile.kotlin.asserter;

import proguard.classfile.*;
import proguard.classfile.kotlin.KotlinMetadata;
import proguard.classfile.util.SimplifiedVisitor;
import proguard.classfile.visitor.*;

import java.util.function.Consumer;

public class AssertUtil
{
    private final String         parentElement;
    private final Clazz          clazz;
    private final KotlinMetadata kotlinMetadata;
    private final Reporter       reporter;


    public AssertUtil(String         parentElement,
                      Clazz          clazz,
                      KotlinMetadata kotlinMetadata,
                      Reporter       reporter)
    {
        this.parentElement  = parentElement;
        this.clazz          = clazz;
        this.kotlinMetadata = kotlinMetadata;
        this.reporter       = reporter;
    }


    public Consumer<Object> reportIfNullReference(String checkedElement)
    {
        return metadataElement ->
               reportIfNullReference(metadataElement, checkedElement);
    }


    public Consumer<Field> reportIfFieldDangling(Clazz  checkedClass,
                                                 String checkedElement)
    {
        return field ->
               reportIfFieldDangling(checkedClass, field, checkedElement);
    }


    public void reportIfNullReference(Object checkedElement,
                                      String checkedElementName)
    {
        if (checkedElement == null)
        {
            reporter.report(new MissingReferenceError(parentElement,
                                                      checkedElementName,
                                                      clazz,
                                                      kotlinMetadata));
        }
    }


    public void reportIfFieldDangling(Clazz  checkedClass,
                                      Field  field,
                                      String checkedElementName)
    {
        ExactMemberMatcher match = new ExactMemberMatcher(field);

        checkedClass.accept(new AllFieldVisitor(match));

        if (!match.memberMatched)
        {
            reporter.report(new InvalidReferenceError(parentElement,
                                                      checkedElementName,
                                                      clazz,
                                                      kotlinMetadata));
        }
    }


    public void reportIfMethodDangling(Clazz  checkedClass,
                                       Method method,
                                       String checkedElementName)
    {
        ExactMemberMatcher match = new ExactMemberMatcher(method);

        checkedClass.accept(new AllMethodVisitor(match));

        if (!match.memberMatched)
        {
            reporter.report(new InvalidReferenceError(parentElement,
                                                      checkedElementName,
                                                      clazz,
                                                      kotlinMetadata));
        }
    }


    // Small helper classes.

    private static class ExactMemberMatcher
    extends    SimplifiedVisitor
    implements MemberVisitor
    {
        private final Member  memberToMatch;

        boolean memberMatched;

        ExactMemberMatcher(Member memberToMatch)
        {
            this.memberToMatch = memberToMatch;
        }


        // Implementations for MemberVisitor.
        @Override
        public void visitAnyMember(Clazz clazz, Member member)
        {
            if (member == memberToMatch)
            {
                memberMatched = true;
            }
        }
    }
}
