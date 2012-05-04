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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.representation.Representation;

import de.skysail.client.ClientUtils;
import de.twenty11.skysail.common.grids.GridData;
import de.twenty11.skysail.common.grids.RowData;
import de.twenty11.skysail.common.responses.SkysailResponse;

/**
 * Implementation of IDataProvider that retrieves contacts from the contact
 * database.
 * 
 * @author igor
 * 
 */
public class BundlesDataProvider implements IDataProvider<Bundle> {

	/** deals with json objects */
	private ObjectMapper mapper = new ObjectMapper();

	protected List<Bundle> getBundleData() {
		List<Bundle> bundles = new ArrayList<Bundle>();
		try {
			Representation representation = ClientUtils
					.restletCall("osgi/bundles/");
			SkysailResponse<GridData> response = mapper.readValue(
					representation.getText(),
					new TypeReference<SkysailResponse<GridData>>() {
					});
			GridData payload = response.getData();
			List<RowData> gridData = payload.getGridData();
			//List<String> result = new ArrayList<String>();
			for (RowData rowData : gridData) {
				Bundle bundle = new Bundle();
				List<Object> columnData = rowData.getColumnData();
				//result.add((String) columnData.get(1));
				bundle.setSymbolicName((String)columnData.get(1));
				bundles.add(bundle);
			}
			return bundles;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	protected ContactsDatabase getContactsDB() {
		return DatabaseLocator.getDatabase();
	}

	/**
	 * retrieves contacts from database starting with index <code>first</code>
	 * and ending with <code>first+count</code>
	 * 
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(int,
	 *      int)
	 */
	public Iterator<Bundle> iterator(int first, int count) {
		return getContactsDB().find(first, count,
				new SortParam("symboliName", true)).iterator();
	}

	/**
	 * returns total number of contacts in the database
	 * 
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
	 */
	public int size() {
		return getContactsDB().getCount();
	}

	/**
	 * wraps retrieved contact pojo with a wicket model
	 * 
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#model(java.lang.Object)
	 */
	public IModel<Bundle> model(Bundle object) {
		return new DetachableContactModel(object);
	}

	/**
	 * @see org.apache.wicket.model.IDetachable#detach()
	 */
	public void detach() {
	}

}
