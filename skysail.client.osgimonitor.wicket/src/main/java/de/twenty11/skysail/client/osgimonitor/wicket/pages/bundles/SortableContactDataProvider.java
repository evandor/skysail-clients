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
package de.twenty11.skysail.client.osgimonitor.wicket.pages.bundles;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;


/**
 * implementation of IDataProvider for contacts that keeps track of sort information
 * 
 * @author igor
 * 
 */
public class SortableContactDataProvider extends SortableDataProvider<Bundle>
{
	/**
	 * constructor
	 */
	public SortableContactDataProvider()
	{
		// set default sort
		setSort("symboliName", SortOrder.ASCENDING);
	}

	protected ContactsDatabase getContactsDB()
	{
		return DatabaseLocator.getDatabase();
	}

	/**
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(int, int)
	 */
	public Iterator<Bundle> iterator(int first, int count)
	{
		return getContactsDB().find(first, count, getSort()).iterator();
	}

	/**
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
	 */
	public int size()
	{
		return getContactsDB().getCount();
	}

	/**
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#model(java.lang.Object)
	 */
	public IModel<Bundle> model(Bundle object)
	{
		return new DetachableContactModel(object);
	}

}
