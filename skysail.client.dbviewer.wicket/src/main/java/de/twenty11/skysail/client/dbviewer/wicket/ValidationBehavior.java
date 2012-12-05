package de.twenty11.skysail.client.dbviewer.wicket;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;

public class ValidationBehavior extends Behavior {
	
    public class ErrorHighlightBehavior extends AbstractValidator<String> {

    	@Override
    	public void onConfigure(Component component) {
    		super.onConfigure(component);
            String componentId = component.getId();
            String errorMessage =""; // model.getErrorMessage(componentId);
            if (errorMessage != null) {
                component.error(errorMessage);
                component.add(new AttributeModifier("title", errorMessage.replaceAll("\"", "'")));
                // StringEscapeUtils.escapeHtml4(errorMessage)));
            } else {
                component.add(AttributeModifier.remove("title"));
            }    	}
		@Override
		protected void onValidate(IValidatable<String> validatable) {
			// TODO Auto-generated method stub
			
		}

	}

	public class FormValidator extends Behavior {
		@Override
	    public void onComponentTag(final Component c, final ComponentTag tag) {
	        if (c instanceof FormComponent) {
	            @SuppressWarnings("rawtypes")
	            final FormComponent fc = (FormComponent) c;
	            if (!fc.isValid()) {
	                final String attribute = tag.getAttribute("class");
	                if (attribute != null) {
	                    tag.put("class", attribute + " error");
	                } else {
	                    tag.put("class", "error");
	                }
	            }
	        }
	    }
	}

	@Override
    public void bind(Component component) {
        component.add(new FormValidator());
        component.add(new ErrorHighlightBehavior());
    }
}