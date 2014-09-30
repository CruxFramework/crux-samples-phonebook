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
package org.cruxframework.phonebook.client.controller;

import java.util.List;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.event.OkEvent;
import org.cruxframework.crux.core.client.event.OkHandler;
import org.cruxframework.crux.core.client.event.SelectEvent;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.BindableView;
import org.cruxframework.crux.core.client.screen.views.ViewAccessor;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.core.client.utils.StringUtils;
import org.cruxframework.crux.smartfaces.client.button.Button;
import org.cruxframework.crux.smartfaces.client.dialog.Confirm;
import org.cruxframework.crux.smartfaces.client.dialog.MessageBox;
import org.cruxframework.crux.smartfaces.client.dialog.MessageBox.MessageType;
import org.cruxframework.crux.smartfaces.client.dialog.animation.DialogAnimation;
import org.cruxframework.crux.smartfaces.client.label.Label;
import org.cruxframework.crux.widgets.client.datebox.DateBox;
import org.cruxframework.crux.widgets.client.deviceadaptivegrid.DeviceAdaptiveGrid;
import org.cruxframework.crux.widgets.client.grid.DataRow;
import org.cruxframework.crux.widgets.client.maskedtextbox.MaskedTextBox;
import org.cruxframework.phonebook.client.datasource.ContactDS;
import org.cruxframework.phonebook.client.remote.ContactService;
import org.cruxframework.phonebook.client.util.State;
import org.cruxframework.phonebook.client.util.WaitboxCallback;
import org.cruxframework.phonebook.shared.dto.ContactDTO;
import org.cruxframework.phonebook.shared.messages.PhoneBookMessages;
import org.cruxframework.phonebook.shared.util.BusinessException;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Flavia.Yeshua
 *
 */
@Controller("contactController")
public class ContactController 
{
	private static final int INDEX_LISTGROUP_OTHERS = 3;
	private int stateCode;
	
	@Inject
	private ContactView contactView;

	@Inject
	private ContactWidget contactWidget;

	@Inject
	private ContactService service;

	@Inject
	private PhoneBookMessages messages;

	/** Calls loadData method at contactView Activate moment. */
	@Expose
	public void onActivate()
	{
		loadData();
	}
	
	/**
	 * Loads all contacts in the grid at method "getAll" of the Rest service. 
	 */
	@Expose
	public void loadData() 
	{
		service.getAll(new WaitboxCallback<List<ContactDTO>>() 
		{
			@Override
			public void onOk(List<ContactDTO> result) 
			{
				loadGrid(result);
				clearSearch();
			}
		});
	}
	
	private void loadGrid(List<ContactDTO> contacts)
	{
		ContactDS ds = (ContactDS) contactWidget.grid().getDataSource();	
		ds.setContacts(contacts);

		contactWidget.grid().clear();
		contactWidget.grid().loadData();
		contactWidget.grid().refresh();
	}
	
	/** 
	 * Loads specific contacts in the grid at method "get" of the Rest service. 
	 */
	@Expose
	public void searchContact() 
	{
		String firstName = contactWidget.txtSearch().getValue();

		if (!isFieldNull(firstName))
		{
			service.get(firstName, new WaitboxCallback<List<ContactDTO>>() 
			{
				@Override
				public void onOk(List<ContactDTO> result) 
				{
					if (!result.isEmpty()) 
					{
						loadGrid(result);
					} 
					else 
					{
						showMessageBox(messages.notFound());
					}
				}
			});
		} 
		else 
		{
			showMessageBox(messages.nameSearch());
		}
	}
	
	/**
	 * Exposes the details of the selected contact.
	 * @param event the selection event in the grid line(click in button).
	 */
	@Expose
	public void detail(SelectEvent event)
	{
		if (!loseData())
		{
			DataRow row = contactWidget.grid().getRow((Widget) event.getSource());
			ContactDTO dto = (ContactDTO) row.getBoundObject();
			contactView.contact().setData(dto);
			setListGroup(dto.getGroup());

			enableFields(false);
			defineState(State.VIEWING);
		}
	}
	
	/**
	 * Exposes data of the selected contact in edit mode.
	 * @param event the selection event in the grid line(click in button).
	 */
	@Expose
	public void edit(SelectEvent event)
	{
		if (!loseData())
		{
			DataRow row = contactWidget.grid().getRow((Widget) event.getSource());
			ContactDTO dto = (ContactDTO) row.getBoundObject();
			contactView.contact().setData(dto);
			setListGroup(dto.getGroup());
	
			enableFields(true);
			defineState(State.UPDATING);
		}
	}
	
	/**
	 * Save button action.
	 * Verify if this is a new contact or not, after invokes correct method (Add or Update).
	 */
	@Expose
	public void saveAddOrUpdate() 
	{
		final ContactDTO DTO = contactView.contact().getData();
		DTO.setGroup(contactWidget.listBoxGroup().getSelectedIndex());

		if (checkRequiredFields()) 
		{
			if (DTO.getId() != null) 
			{
				Confirm.show(messages.confirmTitle(), messages.updateConfirm(), new OkHandler() 
				{
					@Override
					public void onOk(OkEvent event) 
					{
						saveUpdate(DTO);
					}
				}, null);
			} 
			else 
			{
				saveAdd(DTO);
			}
		}
	}

	/**
	 * Calls save method of the Rest service for save the new contact.
	 * @param dto the contact object to save.
	 */
	private void saveAdd(ContactDTO dto)
	{
		service.save(dto, new WaitboxCallback<ContactDTO>(State.ADDED) 
		{
			@Override
			public void onOk(ContactDTO result)
			{
				loadData();
				detailLastAction(result, State.ADDED);
			}
			
			@Override
			public void onError(Exception e) 
			{
				if (e instanceof BusinessException)
				{
					closeWaitPopup();
					showMessageBox(messages.existingContact());
				} else
				{
					super.onError(e);
				}
			}
		});
	}

	/** 
	 * Calls update method of the Rest service for update the selected contact.
	 * @param dto the contact object to update.
	 */
	private void saveUpdate(ContactDTO dto) 
	{
		service.update(dto.getId(), dto, new WaitboxCallback<ContactDTO>(State.UPDATED) 
		{
			@Override
			public void onOk(ContactDTO result) 
			{
				loadData();
				detailLastAction(result, State.UPDATED);
			}
			
			@Override
			public void onError(Exception e) 
			{
				if (e instanceof BusinessException)
				{
					closeWaitPopup();
					showMessageBox(messages.existingContact());
				} else
				{
					super.onError(e);
				}
			}
		});
	}

	/**
	 * Deletes the selected record after user confirmation.
	 * @param event the selection event in the grid line(click in button).
	 */
	@Expose
	public void delete(SelectEvent event)
	{
		if (!loseData())
		{
			DataRow row = contactWidget.grid().getRow((Widget) event.getSource());
			final ContactDTO DTO = (ContactDTO) row.getBoundObject();

			Confirm.show(messages.confirmTitle(), messages.deleteConfirm() + DTO.getFirstName() + " " + DTO.getLastName() + "?",
			    new OkHandler()
			    {
				    @Override
				    public void onOk(OkEvent event)
				    {
					    service.delete(DTO.getId(), new WaitboxCallback<Void>(State.DELETED)
					    {
						    @Override
						    public void onOk(Void result)
						    {
							    loadData();
							    clearAction();
						    }
					    });
				    }
			    }, null);
		}
	}
	
	/** 
	 * Prepares the screen to add a new contact. 
	 */
	@Expose
	public void addContact()
	{
		if (!loseData())
		{
			contactView.contact().setData(new ContactDTO(null));
			enableFields(true);
			clearFields();
			defineState(State.ADDING);
		}
	}
	
	private void detailLastAction(ContactDTO dto, State action)
	{
		contactView.contact().setData(dto);
		setListGroup(dto.getGroup());

		enableFields(false);
		defineState(action);
	}

	/**
	 * Cancels current action. 
	 */
	@Expose
	public void cancel()
	{
		clearAction();
	}
	
	private void clearAction()
	{
		enableFields(false);
		clearFields();
		defineState(State.DEFAULT);
	}
	
	private void clearSearch()
	{
		contactWidget.txtSearch().setText("");
	}
	
	/** Verifies risk of losing data. */
	private boolean loseData()
	{		
		if (stateCode == State.ADDING.getStateCode() || stateCode == State.UPDATING.getStateCode())
		{	
			showMessageBox(messages.unsavedData());
			return true;
		}
		return false;
	}
	
	private boolean checkRequiredFields()
	{
		if (isFieldNull(contactWidget.txtFirstName().getText()))
		{
			MessageBox msgBox = MessageBox.show(messages.requiredField(), MessageType.ERROR, DialogAnimation.bounce);
			msgBox.addOkHandler(new OkHandler() 
			{
				@Override
				public void onOk(OkEvent event) 
				{
					contactWidget.txtFirstName().setFocus(true);
				}
			});
			return false;
		}
		return true;
	}

	private void showMessageBox(String message)
	{
		MessageBox.show(message, MessageType.ERROR, true, true, true, true, MessageBox.DEFAULT_STYLE_NAMES, DialogAnimation.bounce);	
	}
	
	private static boolean isFieldNull(String fieldText) 
	{
		return StringUtils.isEmpty(fieldText);
	}
	
	//Sets text of the contactPanel panel and value of the stateCode variable for treatments necessary.
	private void defineState(State state)
	{
		contactWidget.lblAction().setText(state.getMessage());
		stateCode = state.getStateCode();
	}
	
	//Sets option in the listGroup widget of the contactPanel panel.
	private void setListGroup(Integer option)
	{
		contactWidget.listBoxGroup().setSelectedIndex(option);
	}

	//Sets state of the widgets of the contactPanel panel.
	private void enableFields(boolean status) 
	{
		contactWidget.txtFirstName().setEnabled(status);
		contactWidget.txtLastName().setEnabled(status);
		contactWidget.maskTextPhone().setEnabled(status);
		contactWidget.dateBoxBirthDay().setEnabled(status);
		contactWidget.txtEmail().setEnabled(status);
		contactWidget.listBoxGroup().setEnabled(status);
		contactWidget.btnSave().setEnabled(status);
		contactWidget.btnCancel().setEnabled(status);
	}

	//Sets value of the widgets of the contactPanel panel.
	private void clearFields()
	{
		contactWidget.txtFirstName().setValue(null);
		contactWidget.txtLastName().setValue(null);
		contactWidget.maskTextPhone().setValue(null);
		contactWidget.dateBoxBirthDay().setValue(null);
		contactWidget.txtEmail().setValue(null);
		contactWidget.listBoxGroup().setSelectedIndex(INDEX_LISTGROUP_OTHERS);
	}

	/**
	 * Interface that allows to access the data object of the "contact" view.
	 * @see BindableView
	 */
	@BindView("contact")
	public static interface ContactView extends ViewAccessor
	{
		/** @return data object of the view. */
		BindableView<ContactDTO> contact();
	}
	
	// CHECKSTYLE:OFF
	/**
	 * Interface that allows to access the widgets of the "contact" view.
	 */
	@BindView("contact")
	public static interface ContactWidget extends WidgetAccessor 
	{
		/** @return the widgets of the view. */
		TextBox txtSearch();
		DeviceAdaptiveGrid grid();
		TextBox txtFirstName();
		TextBox txtLastName();
		MaskedTextBox maskTextPhone();	
		DateBox dateBoxBirthDay();
		TextBox txtEmail();
		ListBox listBoxGroup();
		Button btnSave();
		Button btnCancel();
		Label lblAction();
	}
	// CHECKSTYLE:ON
	
	/** @param contactView the contactView to set */
	public void setContactView(ContactView contactView) 
	{
		this.contactView = contactView;
	}
	
	/** @param contactWidget the contactWidget to set */
	public void setContactWidget(ContactWidget contactWidget) 
	{
		this.contactWidget = contactWidget;
	}

	/** @param service the service to set */
	public void setService(ContactService service) 
	{
		this.service = service;
	}

	/** @param messages the messages to set */
	public void setMessages(PhoneBookMessages messages)
	{
		this.messages = messages;
	}
}
