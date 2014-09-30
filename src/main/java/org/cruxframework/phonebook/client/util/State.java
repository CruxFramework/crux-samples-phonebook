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
 * Defines the current state of contactPanel panel (message and code).
 * 
 * @author Flavia.Yeshua
 */
public enum State
{	
	/** Default state of the contactPanel panel. */
	DEFAULT(0),
	/** State viewing of the contactPanel panel. */
	VIEWING(0),
	/** State adding of the contactPanel panel. */
	ADDING(1),
	/** State updating of the contactPanel panel. */
	UPDATING(2), 
	/** State added of the contactPanel panel and message of the info box. */
	ADDED(0), 
	/** State updated of the contactPanel panel and message of the info box. */
	UPDATED(0),
	/** State deleted of the message info box.*/
	DELETED(0);
	
	private static PhoneBookMessages i18n = GWT.create(PhoneBookMessages.class);
	private int stateCode;

	State(int stateCode)
	{
		this.stateCode = stateCode;
	}

	/** @return the message */
	public String getMessage()
	{
		switch (this)
		{
			case DEFAULT:
				return i18n.stateDefault();
			case VIEWING:
				return i18n.stateViewing();
			case ADDING:
				return i18n.stateAdding();
			case UPDATING:
				return i18n.stateUpdating();	
			case ADDED:
				return i18n.stateAdded();	
			case UPDATED:
				return i18n.stateUpdated();	
			case DELETED:
				return i18n.successDel();
			default:
				break;
		}
		return "";
	}

	/** @return the status */
	public int getStateCode()
	{
		return stateCode;
	}
}
