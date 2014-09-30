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
package org.cruxframework.phonebook.shared.dto;

import java.io.Serializable;
import java.util.Date;

import org.cruxframework.crux.core.client.dto.DataObject;

/**
 * @author Flavia.Yeshua
 *
 */
@DataObject("contact")
public class ContactDTO implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String firstName;
	private String lastName;
	private Date birthDay;
	private String phone;
	private String email;
	private Integer group;

	/**
	 * Constructor.
	 */
	public ContactDTO() 
	{
	}
	
	/**
	 * Constructor.
	 * @param id set id for the contact
	 */
	public ContactDTO(Integer id)
	{
		this.id = id;
	}
	
	/**
	 * Constructor.
	 * @param id sets id for the contact
	 * @param firstName sets firstName for the contact
	 * @param lastName sets lastName for the contact
	 * @param birthDay sets birthDay for the contact
	 * @param phone sets phone for the contact
	 * @param email sets email for the contact
	 * @param group sets group for the contact
	 */
	public ContactDTO(Integer id, String firstName, String lastName, Date birthDay, String phone, String email, Integer group)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = birthDay;
		this.phone = phone;	
		this.email = email;
		this.group = group;
	}

	/** @return the id */
	public Integer getId() 
	{
		return id;
	}

	/** @param id the id to set */
	public void setId(int id) 
	{
		this.id = id;
	}
	
	/** @return the firstName */
	public String getFirstName() 
	{
		return firstName;
	}

	/** @param firstName the firstName to set */
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}

	/** @return the lastName */
	public String getLastName() 
	{
		return lastName;
	}

	/** @param lastName the lastName to set */
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}

	/** @return the phone number */
	public String getPhone() 
	{
		return phone;
	}

	/** @param phone the phone number to set */
	public void setPhone(String phone) 
	{
		this.phone = phone;
	}
	
	/** @return the email */
	public String getEmail() 
	{
		return email;
	}

	/** @param email the email to set */
	public void setEmail(String email) 
	{
		this.email = email;
	}

	/** @return the birthDay */
	public Date getBirthDay() 
	{
		return birthDay;
	}

	/** @param birthDay the birthDay to set */
	public void setBirthDay(Date birthDay) 
	{
		this.birthDay = birthDay;
	}

	/** @return the group*/
	public Integer getGroup() 
	{
		return group;
	}

	/** @param group the group to set */
	public void setGroup(Integer group) 
	{
		this.group = group;
	}
}
