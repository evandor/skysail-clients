/*
 * Copyright 2011-2012 the original author or authors.
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
package de.twenty11.skysail.client.cli.converter;

import java.util.List;

import org.springframework.shell.core.Completion;
import org.springframework.shell.core.Converter;
import org.springframework.shell.core.MethodTarget;
import org.springframework.stereotype.Component;

/**
 * {@link Converter} for {@link String}.
 * 
 * @author Ben Alex
 * @since 1.0
 */
@Component
public class StringConverter implements Converter<String> {

    @Override
    public String convertFromText(final String value, final Class<?> requiredType, final String optionContext) {
        return value;
    }

    @Override
    public boolean getAllPossibleValues(final List<Completion> completions, final Class<?> requiredType,
            final String existingData, final String optionContext, final MethodTarget target) {
        return false;
    }

    @Override
    public boolean supports(final Class<?> requiredType, final String optionContext) {
        return String.class.isAssignableFrom(requiredType)
                && (optionContext == null || !optionContext.contains("disable-string-converter"));
    }
}
