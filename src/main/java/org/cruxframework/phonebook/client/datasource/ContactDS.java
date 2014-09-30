/*
 * Copyright 2014 cruxframework.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cruxframework.phonebook.client.datasource;

import java.util.List;

import org.cruxframework.crux.core.client.datasource.LocalPagedDataSource;
import org.cruxframework.crux.core.client.datasource.annotation.DataSource;
import org.cruxframework.crux.core.client.datasource.annotation.DataSourceRecordIdentifier;
import org.cruxframework.phonebook.shared.dto.ContactDTO;

/**
 * @author Flavia.Yeshua
 * 
 */
@DataSource("contactDS")
@DataSourceRecordIdentifier("id")
public class ContactDS extends LocalPagedDataSource<ContactDTO>
{
	private List<ContactDTO> contacts;

	@Override
	public void load()
	{
		updateData(getContacts());
	}

	/**
	 * @return the contacts
	 */
	public List<ContactDTO> getContacts()
	{
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(List<ContactDTO> contacts)
	{
		this.contacts = contacts;
	}
}
