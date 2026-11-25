/*
 * =============================================================================
 *
 *   Copyright (c) 2011-2026 Thymeleaf (http://www.thymeleaf.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package org.thymeleaf.templateengine.conversion.conversion8;

import java.lang.reflect.Array;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.standard.expression.AbstractStandardConversionService;


/*
 * Mimics general-purpose conversion services (like the Spring ConversionService used by #conversions in
 * Spring-enabled applications) which are able to instantiate any target type offering a constructor that accepts
 * a String, and which also convert each element to the component type when the target type is an array.
 */
public class TestStandardConversionService8 extends AbstractStandardConversionService {


    public TestStandardConversionService8() {
        super();
    }


    @Override
    @SuppressWarnings("unchecked")
    protected <T> T convertOther(
            final IExpressionContext context, final Object object, final Class<T> targetClass) {

        if (targetClass.isArray()) {
            final Class<?> componentType = targetClass.getComponentType();
            final Object array = Array.newInstance(componentType, 1);
            Array.set(array, 0, convert(context, object, componentType));
            return (T) array;
        }

        try {
            return targetClass.getConstructor(String.class).newInstance(String.valueOf(object));
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException(
                    "No available conversion for target class \"" + targetClass.getName() + "\"", e);
        }

    }

}
