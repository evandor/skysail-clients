/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.twenty11.skysail.client.osgimonitor.wicket.pages.bundles.dg;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import de.twenty11.skysail.client.osgimonitor.wicket.pages.bundles.Bundle;
import de.twenty11.skysail.client.osgimonitor.wicket.pages.bundles.ExamplePage;

/**
 * Base page for component demo pages.
 * 
 * @author igor
 */
public class BasePage extends ExamplePage
{
	private Bundle selected;

	/**
	 * Constructor
	 */
	public BasePage()
	{
		//add(new Label("selectedLabel", new PropertyModel<String>(this, "selectedContactLabel")));
		//add(new FeedbackPanel("feedback"));
	}

	/**
	 * @return string representation of selected contact property
	 */
	public String getSelectedContactLabel()
	{
		if (selected == null)
		{
			return "No Bundle Selected";
		}
		else
		{
			return selected.getSymbolicName() + " " + selected.getSymbolicName();
		}
	}

	/**
	 * 
	 */
	class ActionPanel extends Panel
	{
		/**
		 * @param id
		 *            component id
		 * @param model
		 *            model for contact
		 */
		public ActionPanel(String id, IModel<Bundle> model)
		{
			super(id, model);
			add(new Link("select")
			{
				@Override
				public void onClick()
				{
					selected = (Bundle)getParent().getDefaultModelObject();
				}
			});
		}
	}

	/**
	 * @return selected contact
	 */
	public Bundle getSelected()
	{
		return selected;
	}

	/**
	 * sets selected contact
	 * 
	 * @param selected
	 */
	public void setSelected(Bundle selected)
	{
		addStateChange();
		this.selected = selected;
	}
}
