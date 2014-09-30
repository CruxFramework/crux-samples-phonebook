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
package org.cruxframework.phonebook.client.util;

import org.cruxframework.phonebook.shared.messages.PhoneBookMessages;
import com.google.gwt.core.shared.GWT;
/**
 * Defines the group value of contactPanel panel.
 * 
 * @author Flavia.Yeshua
 */
public enum Group
{	
	/** Family value. */
	FAMILY(0),
	/** Friends value. */
	FRIENDS(1),
	/** Work value. */
	WORK(2),
	/** Others value. */
	OTHERS(3);
	
	private static PhoneBookMessages i18n = GWT.create(PhoneBookMessages.class);
	private int index;
	
	Group(int index)
	{
		this.index = index;
	}
	
	/** @return the message */
	public String getMessage()
	{
		switch (this)
		{
			case FAMILY:
				return i18n.family();
			case FRIENDS:
				return i18n.friends();
			case WORK:
				return i18n.work();
			case OTHERS:
				return i18n.others();	
			default:
				break;
		}
		return "";
	}
	
	/** @return the index */
	public int getIndex()
	{
		return index;
	}
}
