package de.twenty11.skysail.client.cli.converter;

import java.util.List;

import org.springframework.shell.core.Completion;
import org.springframework.shell.core.Converter;
import org.springframework.shell.core.MethodTarget;
import org.springframework.stereotype.Component;

import de.twenty11.skysail.client.cli.commands.AssertionCommands.Condition;
import de.twenty11.skysail.client.cli.commands.AssertionCommands.Identifier;
import de.twenty11.skysail.client.cli.domain.LinkAssertion;

@Component
public class LinkAssertionConverter implements Converter<LinkAssertion> {

    @Override
    public boolean supports(Class<?> type, String optionContext) {
        return type.isAssignableFrom(LinkAssertion.class);
    }

    @Override
    public LinkAssertion convertFromText(String value, Class<?> targetType, String optionContext) {
        String[] split = value.split("\\s+");
        if (split.length != 3) {
            throw new IllegalStateException("could not convert '" + value + "' to LinkAssertion");
        }
        return new LinkAssertion(Identifier.valueOf(split[0].toUpperCase()),split[1], Condition.valueOf(split[2].toUpperCase()));
    }

    @Override
    public boolean getAllPossibleValues(List<Completion> completions, Class<?> targetType, String existingData,
            String optionContext, MethodTarget target) {
        return false;
    }
}
