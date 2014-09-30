package de.twenty11.skysail.client.cli.converter;

import java.util.List;

import org.springframework.shell.core.Completion;
import org.springframework.shell.core.Converter;
import org.springframework.shell.core.MethodTarget;
import org.springframework.stereotype.Component;

import de.twenty11.skysail.client.cli.domain.KeyValueAssertion;

@Component
public class JsonAssertionConverter implements Converter<KeyValueAssertion> {

    @Override
    public boolean supports(Class<?> type, String optionContext) {
        return type.isAssignableFrom(KeyValueAssertion.class);
    }

    @Override
    public KeyValueAssertion convertFromText(String value, Class<?> targetType, String optionContext) {
        String[] split = value.split("=",2);
        if (split.length != 2) {
            throw new IllegalStateException("could not convert '" + value + "' to JsonAssertion");
        }
        return new KeyValueAssertion(split[0],split[1]);
    }

    @Override
    public boolean getAllPossibleValues(List<Completion> completions, Class<?> targetType, String existingData,
            String optionContext, MethodTarget target) {
        return false;
    }
}
