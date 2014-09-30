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
package org.cruxframework.phonebook.shared.messages;

import org.cruxframework.crux.core.client.i18n.MessageName;

import com.google.gwt.i18n.client.Messages;

/**
 * Interface for generation of the internacionalization methods.
 * 
 * @author flavia.jesus
 *
 */
@MessageName("messages")
public interface PhoneBookMessages extends Messages
{
	/**
	 * @return the message according the method
	 */
	//CHECKSTYLE:OFF
	
	/*Controller messages*/
	@DefaultMessage("Contact not found.")
	String notFound();
	
	@DefaultMessage("Insert a name to search.")
	String nameSearch();
	
	@DefaultMessage("Error in Rest Service.")
	String restError();
	
	@DefaultMessage("Confirm Action")
	String confirmTitle();
	
	@DefaultMessage("Are you sure you want to update this contact?")
	String updateConfirm();
	
	@DefaultMessage("Are you sure you want to delete ")
	String deleteConfirm();
	
	@DefaultMessage("Contact already exists.")
	String existingContact();
	
	@DefaultMessage("Successfully updated contact.")
	String successUpdate();
	
	@DefaultMessage("successfully saved contact.")
	String successAdd();
	
	@DefaultMessage("Successfully deleted contact.")
	String successDel();
	
	@DefaultMessage("You must Fill the First Name field.")
	String requiredField();
	
	@DefaultMessage("You have unsaved data.")
	String unsavedData();
	
	@DefaultMessage("Loading...")
	String loading();
	
	
	/*View messages*/
	@DefaultMessage("PhoneBook")
	String title();
	
	@DefaultMessage("Search Contact")
	String search();
	
	@DefaultMessage("Insert First Name")
	String placeHolder();
	
	@DefaultMessage("Search")
	String btnSearch();
	
	@DefaultMessage("List All")
	String btnList();
	
	@DefaultMessage("First Name")
	String lblFirstName();
	
	@DefaultMessage("Last Name")
	String lblLastName();
	
	@DefaultMessage("Phone")
	String lblPhone();
	
	@DefaultMessage("Group")
	String lblGroup();
	
	@DefaultMessage("Actions")
	String lblActions();
	
	@DefaultMessage("Det")
	String btnDetail();
	
	@DefaultMessage("Edt")
	String btnEdit();
	
	@DefaultMessage("Del")
	String btnDelete();
	
	@DefaultMessage("Detail")
	String btnDetailToolTip();
	
	@DefaultMessage("Edit")
	String btnEditToolTip();
	
	@DefaultMessage("Delete")
	String btnDeleteToolTip();
	
	@DefaultMessage("Add Contact")
	String btnAdd();
	
	@DefaultMessage("Save")
	String btnSave();
	
	@DefaultMessage("Cancel")
	String btnCancel();	
	
	@DefaultMessage("First Name:")
	String formFirstName();
	
	@DefaultMessage("Last Name:")
	String formLastName();
	
	@DefaultMessage("Phone:")
	String formPhone();
	
	@DefaultMessage("Birth Day:")
	String formBirthDay();
	
	@DefaultMessage("Age:")
	String formAge();
	
	@DefaultMessage("E-mail:")
	String formEmail();
	
	@DefaultMessage("Group:")
	String formGroup();
	
	@DefaultMessage("Family")
	String family();
	
	@DefaultMessage("Friends")
	String friends();
	
	@DefaultMessage("Work")
	String work();
	
	@DefaultMessage("Others")
	String others();
	
	@DefaultMessage("View, Edit or Add contacts")
	String stateDefault();
	
	@DefaultMessage("Viewing Contact")
	String stateViewing();
	
	@DefaultMessage("Adding Contact")
	String stateAdding();
	
	@DefaultMessage("Updating Contact")
	String stateUpdating();
	
	@DefaultMessage("Added Contact")
	String stateAdded();
	
	@DefaultMessage("Updated Contact")
	String stateUpdated();
	
	//CHECKSTYLE:ON
}
