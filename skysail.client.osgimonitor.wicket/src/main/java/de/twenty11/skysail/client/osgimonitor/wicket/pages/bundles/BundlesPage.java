package de.twenty11.skysail.client.osgimonitor.wicket.pages.bundles;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.twenty11.skysail.client.osgimonitor.wicket.Template;

public class BundlesPage extends Template {

	private static final long serialVersionUID = -443311476786152390L;

	public BundlesPage(PageParameters parameters) {
		super(parameters);
		//Iterator<Bundle> bundles = new BundlesDataProvider().iterator(0, 10);

		List<Bundle> bundles = new BundlesDataProvider().getBundleData();

		RepeatingView repeating = new RepeatingView("repeating");
		add(repeating);

		int index = 0;
		
		for (Bundle bundle : bundles) {
			AbstractItem item = new AbstractItem(repeating.newChildId());
			repeating.add(item);
			
			// item.add(new ActionPanel("actions", new
			// DetachableContactModel(contact)));
			item.add(new Label("contactid", String.valueOf(bundle.getId())));
			item.add(new Label("firstname", bundle.getSymbolicName()));
			item.add(new Label("lastname", bundle.getSymbolicName()));
			item.add(new Label("homephone", bundle.getHomePhone()));
			item.add(new Label("cellphone", bundle.getCellPhone()));

			final int idx = index;
			item.add(AttributeModifier.replace("class",
					new AbstractReadOnlyModel<String>() {
						private static final long serialVersionUID = 1L;

						@Override
						public String getObject() {
							return (idx % 2 == 1) ? "even" : "odd";
						}
					}));

			index++;
			
		}
	}
}
