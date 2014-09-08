package de.twenty11.skysail.client.cli.converter;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.springframework.shell.core.Completion;
import org.springframework.shell.core.Converter;
import org.springframework.shell.core.MethodTarget;
import org.springframework.stereotype.Component;

@Component
public class HeaderConverter implements Converter<Header> {

    @Override
    public boolean supports(Class<?> type, String optionContext) {
        return type.isAssignableFrom(org.apache.http.Header.class);
    }

    @Override
    public Header convertFromText(String value, Class<?> targetType, String optionContext) {
        String[] split = value.split("=",2);
        if (split.length < 2) {
            throw new IllegalStateException("could not convert '" + value + "' to HeaderDefinition");
        }
        return new Header() {

            @Override
            public HeaderElement[] getElements() throws ParseException {
                return null;
            }

            @Override
            public String getName() {
                return split[0];
            }

            @Override
            public String getValue() {
                return split[1];
            }
            
            @Override
            public String toString() {
                return getName() + ": " + getValue();
            }
        };
    }

    @Override
    public boolean getAllPossibleValues(List<Completion> completions, Class<?> targetType, String existingData,
            String optionContext, MethodTarget target) {
        return false;
    }
}
