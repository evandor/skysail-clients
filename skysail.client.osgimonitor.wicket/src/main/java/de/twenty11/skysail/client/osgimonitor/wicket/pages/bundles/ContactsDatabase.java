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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;

/**
 * simple database for contacts
 * 
 * @author igor
 * 
 */
public class ContactsDatabase {
	private final Map<Long, Bundle> map = Collections
			.synchronizedMap(new HashMap<Long, Bundle>());
	private final List<Bundle> fnameIdx = Collections
			.synchronizedList(new ArrayList<Bundle>());
	private final List<Bundle> lnameIdx = Collections
			.synchronizedList(new ArrayList<Bundle>());
	private final List<Bundle> fnameDescIdx = Collections
			.synchronizedList(new ArrayList<Bundle>());
	private final List<Bundle> lnameDescIdx = Collections
			.synchronizedList(new ArrayList<Bundle>());

	/**
	 * Constructor
	 * 
	 * @param count
	 *            number of contacts to generate at startup
	 */
	public ContactsDatabase(int count) {
		for (int i = 0; i < count; i++) {
			add(ContactGenerator.getInstance().generate());
		}
		//updateIndecies();
	}

	/**
	 * find contact by id
	 * 
	 * @param id
	 * @return contact
	 */
	public Bundle get(long id) {
		Bundle c = map.get(id);
		if (c == null) {
			throw new RuntimeException("contact with id [" + id
					+ "] not found in the database");
		}
		return c;
	}

	protected void add(final Bundle bundle) {
		map.put(bundle.getId(), bundle);
		fnameIdx.add(bundle);
		lnameIdx.add(bundle);
		fnameDescIdx.add(bundle);
		lnameDescIdx.add(bundle);
	}

	/**
	 * select contacts and apply sort
	 * 
	 * @param first
	 * @param count
	 * @param sort
	 * @return list of contacts
	 */
	public List<Bundle> find(int first, int count, SortParam sort) {
		return getIndex(sort).subList(first, first + count);
	}

	protected List<Bundle> getIndex(SortParam sort) {
		if (sort == null) {
			return fnameIdx;
		}

		if (sort.getProperty().equals("symboliName")) {
			return sort.isAscending() ? fnameIdx : fnameDescIdx;
		} else if (sort.getProperty().equals("lastName")) {
			return sort.isAscending() ? lnameIdx : lnameDescIdx;
		}
		throw new RuntimeException("unknown sort option [" + sort
				+ "]. valid fields: [firstName], [lastName]");
	}

	/**
	 * @return number of contacts in the database
	 */
	public int getCount() {
		return fnameIdx.size();
	}

	/**
	 * add contact to the database
	 * 
	 * @param bundle
	 */
	public void save(final Bundle bundle) {
		if (bundle.getId() == 0) {
			bundle.setId(ContactGenerator.getInstance().generateId());
			add(bundle);
			//updateIndecies();
		} else {
			throw new IllegalArgumentException("contact ["
					+ bundle.getSymbolicName() + "] is already persistent");
		}
	}

	/**
	 * delete contact from the database
	 * 
	 * @param bundle
	 */
	public void delete(final Bundle bundle) {
		map.remove(bundle.getId());

		fnameIdx.remove(bundle);
		lnameIdx.remove(bundle);
		fnameDescIdx.remove(bundle);
		lnameDescIdx.remove(bundle);

		bundle.setId(0);
	}

//	private void updateIndecies() {
//		Collections.sort(fnameIdx, new Comparator<Bundle>() {
//			public int compare(Bundle arg0, Bundle arg1) {
//				return (arg0).getSymbolicName().compareTo((arg1).getSymbolicName());
//			}
//		});
//
//		Collections.sort(lnameIdx, new Comparator<Bundle>() {
//			public int compare(Bundle arg0, Bundle arg1) {
//				return (arg0).getLastName().compareTo((arg1).getLastName());
//			}
//		});
//
//		Collections.sort(fnameDescIdx, new Comparator<Bundle>() {
//			public int compare(Bundle arg0, Bundle arg1) {
//				return (arg1).getFirstName().compareTo((arg0).getFirstName());
//			}
//		});
//
//		Collections.sort(lnameDescIdx, new Comparator<Bundle>() {
//			public int compare(Bundle arg0, Bundle arg1) {
//				return (arg1).getLastName().compareTo((arg0).getLastName());
//			}
//		});
//
//	}

}
