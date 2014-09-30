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
package org.cruxframework.phonebook.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.cruxframework.crux.core.server.rest.annotation.RestService;
import org.cruxframework.crux.core.shared.rest.annotation.DELETE;
import org.cruxframework.crux.core.shared.rest.annotation.GET;
import org.cruxframework.crux.core.shared.rest.annotation.POST;
import org.cruxframework.crux.core.shared.rest.annotation.PUT;
import org.cruxframework.crux.core.shared.rest.annotation.Path;
import org.cruxframework.crux.core.shared.rest.annotation.PathParam;
import org.cruxframework.crux.core.shared.rest.annotation.StateValidationModel;
import org.cruxframework.phonebook.shared.dto.ContactDTO;
import org.cruxframework.phonebook.shared.util.BusinessException;

/**
 * Class that implements necessary methods in Rest service
 * for execute search, insert, update and removal of contacts.
 * 
 * @author Flavia.Yeshua
 */
@RestService("contactService")
@Path("/contacts")
public class ContactServiceImpl 
{
	private static Map<Integer, ContactDTO> mockMap;
	private static List<ContactDTO> contactList;
	private static AtomicInteger mapIndex = new AtomicInteger(0);
	private static NameComparator comparator;
	
	static
	{
		mockMapFill();
	}
	
	private static void mockMapFill()
	{
		mockMap = new HashMap<Integer, ContactDTO>();
		
		// CHECKSTYLE:OFF
		createContact("Ashley", "Smith", getDate(1198, 3, 5), "(222) 730-3102", "ashley.smith@sample.com", 2);
		createContact("Blair", "Johnson",  getDate(1987, 7, 23), "(444) 830-0200", "blair.johnson@sample.com", 1);
		createContact("Charles", "Williams", getDate(1980, 5, 18), "(555) 944-3262", "charles.wiliams@sample.com", 3);
		createContact("Catherine", "Jones", getDate(1997, 4, 27), "(787) 303-1120", "catherine.jones@sample.com", 2);
		createContact("Lindsey", "Brown", getDate(1983, 8, 15), "(252) 894-3762", "lindsey.brown@sample.com", 2);
		createContact("Daniel", "Davis", getDate(1984, 10, 11), "(131) 987-4043", "daniel.davis@sample.com", 0);
		createContact("Jorge", "Miller", getDate(1980, 9, 9), "(465) 703-1020", "jorge.miller@sample.com", 3);
		createContact("Paul", "Wilson", getDate(1981, 7, 5), "(303) 404-7782", "paul.wilson@sample.com", 0);
		createContact("Mary", "Moore", getDate(1982, 5, 9), "(433) 254-3877", "mary.moore@sample.com", 1);
		createContact("Mike", "Moore", getDate(1967, 11, 8), "(301) 524-6682", "mike.moore@sample.com", 2);
		createContact("John", "White", getDate(1992, 3, 23), "(463) 521-3826", "john.white@sample.com", 0);
		createContact("Bill", "Miller", getDate(1965, 4, 22), "(323) 624-3899", "bill.miller@sample.com", 1);
		createContact("Paul", "Brown", getDate(1978, 1, 18), "(113) 654-6112", "paul.brown@sample.com", 3);
		createContact("Mike", "Smith", getDate(1971, 2, 13), "(543) 266-3882", "mike.smith@sample.com", 3);
		createContact("Mary", "Williams", getDate(1969, 5, 24), "(113) 325-3882", "mary.williams@sample.com", 1);
		// CHECKSTYLE:ON
	}
	
	private static void  createContact(String firstName, String lastName, Date birthDay, String phone, String email, Integer group)
	{
		Integer index = mapIndex.getAndIncrement();
		mockMap.put(index, new ContactDTO(index, firstName, lastName, birthDay, phone, email, group));
	}

	private static List<ContactDTO> sortList()
	{
		contactList = new ArrayList<ContactDTO>();
		contactList.addAll(mockMap.values());
		Collections.sort(contactList, comparatorGetInstance());
		return contactList;
	}
	
	private static NameComparator comparatorGetInstance()
	{
		if (comparator == null)
		{
			return comparator = new NameComparator();
		}
		return comparator;
	}
	
	private static Date getDate(int year, int month, int date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.set(year, month, date);
		return calendar.getTime();
	}
	
	/**
	 * Adds a new contact to the contacts list.
	 * @param dto the contact object to add.
	 * @return contact object added.
	 * @throws BusinessException if contact already exists.
	 */
	@POST
	@Path("add")
	public ContactDTO save(ContactDTO dto) throws BusinessException
	{
		boolean hasEquals = false;
		
		for (ContactDTO contact : mockMap.values()) 
		{
			if (contact.getFirstName().equals(dto.getFirstName()) && 
				contact.getLastName().equals(dto.getLastName()))
			{
				hasEquals = true;
				break;
			}
		}
		
		if (hasEquals)
		{
			throw new BusinessException();
		}
		
		Integer id = mapIndex.getAndIncrement();
		dto.setId(id);
		mockMap.put(id, dto);
		return dto;
	}
	
	/**
	 * Returns a contact collection according parameter firstName.
	 * @param firstName filter for generation of a contact collection.
	 * @return contact collection
	 */
	@GET
	@Path("{firstName}")
	public List<ContactDTO> get(@PathParam ("firstName") String firstName)
	{
		List<ContactDTO> contacts = new ArrayList<ContactDTO>();
		
		for (ContactDTO contact : mockMap.values()) 
		{
			if (contact.getFirstName().toLowerCase().contains(firstName.toLowerCase()))
			{
				contacts.add(contact);
			}
		}
		
		if (!contacts.isEmpty())
		{
			Collections.sort(contacts, comparatorGetInstance());
		}
		return contacts;
	}
	
	/**
	 * Returns a contact collection with all contacts existing.
	 * @return a contact collection 
	 */
	@GET
	@Path("all")
	public Collection<ContactDTO> getAll()
	{
		return sortList();
	}
	
	/**
	 * Updates data of specific contact.
	 * @param id identifier of the contact to update.
	 * @param dto contact object to be updated.
	 * @return contact object updated
	 * @throws BusinessException if contact already exists.
	 */
	@PUT(validatePreviousState = StateValidationModel.ENSURE_STATE_MATCHES)
	@Path("{id:\\d+}")
	public ContactDTO update(@PathParam("id") Integer id, ContactDTO dto) throws BusinessException
	{
		boolean hasEquals = false;
		
		if (mockMap.get(id) != null)
		{	
			for (ContactDTO contact : mockMap.values()) 
			{
				if (contact.getFirstName().equals(dto.getFirstName()) && 
					contact.getLastName().equals(dto.getLastName()) &&
					!contact.getId().equals(dto.getId()))
				{
					hasEquals = true;
					break;
				}
			}
			
			if (!hasEquals)
			{
				mockMap.put(id, dto);
			}
		}
		
		if (hasEquals)
		{
			throw new BusinessException();
		}
		return dto;
	}
	
	/**
	 * Deletes a specific contact.
	 * @param id identifier of the contact to delete.
	 */
	@DELETE
	@Path("{id:\\d+}")
	public void delete(@PathParam("id") Integer id)
	{
		if (mockMap.get(id) != null)
		{
			mockMap.remove(id);
		}
	}
}
