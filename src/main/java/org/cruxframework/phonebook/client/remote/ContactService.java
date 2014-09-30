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
package org.cruxframework.phonebook.client.remote;

import java.util.List;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetRestService;
import org.cruxframework.phonebook.shared.dto.ContactDTO;
/**
 * @author Flavia.Yeshua
 *
 */
@TargetRestService("contactService")
public interface ContactService extends RestProxy
{
	/**
	 * @param dto new contact object to save. 
	 * @param callback contact object.
	 */
	void save(ContactDTO dto, Callback<ContactDTO> callback);
	
	/**
	 * Returns a contact collection according parameter firstName.
	 * @param firstName filter for generation of a contact collection.
	 * @param callback contact collection.
	 */
	void get(String firstName, Callback<List<ContactDTO>> callback);
	
	/**
	 * Returns a contact collection with all contacts existing.
	 * @param callback contact collection.
	 */
	void getAll(Callback<List<ContactDTO>> callback);
	
	/**
	 * Updates data of specific contact.
	 * @param id identifier of the contact to update.
	 * @param dto contact object to be updated.
	 * @param callback contact object.
	 */
	void update(Integer id, ContactDTO dto, Callback<ContactDTO> callback);
	
	/**
	 * Deletes a specific contact.
	 * @param id identifier of the contact to delete.
	 * @param callback result.
	 */
	void delete(Integer id, Callback<Void> callback);
}
