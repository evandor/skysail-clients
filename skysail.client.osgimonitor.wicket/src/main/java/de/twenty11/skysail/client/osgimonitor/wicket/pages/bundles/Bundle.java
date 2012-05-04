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

import org.apache.wicket.IClusterable;

/**
 * domain object for demonstrations.
 * 
 * @author igor
 * 
 */
public class Bundle implements IClusterable {

	private long id;

	private String symbolicName;

	private String homePhone;

	private String cellPhone;

	/**
	 * Constructor
	 */
	public Bundle() {

	}

//	/**
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	public boolean equals(Object obj) {
//		if (obj == this) {
//			return true;
//		}
//		if (obj == null) {
//			return false;
//		}
//		if (obj instanceof Bundle) {
//			Bundle other = (Bundle) obj;
//			return other.getFirstName().equals(getFirstName())
//					&& other.getLastName().equals(getLastName())
//					&& other.getHomePhone().equals(getHomePhone())
//					&& other.getCellPhone().equals(getCellPhone());
//
//		} else {
//			return false;
//		}
//	}

	/**
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return cellPhone
	 */
	public String getCellPhone() {
		return cellPhone;
	}

	/**
	 * @param cellPhone
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	/**
	 * @return symboliName
	 */
	public String getSymbolicName() {
		return symbolicName;
	}

	/**
	 * @param symboliName
	 */
	public void setSymbolicName(String name) {
		this.symbolicName = name;
	}

	/**
	 * @return homePhone
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * @param homePhone
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

}
