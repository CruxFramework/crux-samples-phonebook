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

import org.cruxframework.crux.core.client.Crux;
import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.smartfaces.client.dialog.WaitBox;
import org.cruxframework.crux.smartfaces.client.dialog.animation.DialogAnimation;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;
import org.cruxframework.phonebook.shared.messages.PhoneBookMessages;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.shared.GWT;
/**
 * Abstract class to show pop-up with result service.
 * @param <T>
 * 
 * @author flavia.jesus
 */
public abstract class WaitboxCallback<T> implements Callback<T>
{
	private static final int SUCCESS_MSG_TIMEOUT = 1500;
	private static PhoneBookMessages messages = GWT.create(PhoneBookMessages.class);
	private final WaitBox wait = new WaitBox();
	private final WaitBox successWait = new WaitBox();
	private State finalState;

	/**
	 * Show waitBox.
	 *
	 */
	public WaitboxCallback()
	{
		this(null);
	}

	/**
	 * Show waitBox.
	 * @param state true if this is an operation that has an success final message.
	 */
	public WaitboxCallback(State state)
	{
		this.finalState = state;
		showWaitBox();
	}

	private void showSuccessWaitBox(String message)
	{
		successWait.setAnimation(DialogAnimation.fade);
		successWait.setMessage(message);
		successWait.center();
		successWait.show();
		Scheduler.get().scheduleFixedDelay(new RepeatingCommand()
		{
			@Override
			public boolean execute()
			{
				successWait.hide();
				return false;
			}
		}, SUCCESS_MSG_TIMEOUT);
	}

	/**
	 * @param result of the call service
	 */
	public abstract void onOk(T result);

	private void showWaitBox() 
	{
		wait.setAnimation(DialogAnimation.fade);
		wait.setMessage(messages.loading());
		wait.center();
	}

	@Override
	public void onSuccess(T result)
	{
		try
		{
			onOk(result);

			if (finalState != null)
			{
				switch (finalState)
				{
					case ADDED:
						showSuccessWaitBox(messages.successAdd());
						break;
					case UPDATED:
						showSuccessWaitBox(messages.successUpdate());
						break;
					case DELETED:
						showSuccessWaitBox(messages.successDel());
						break;
					default:
						break;
				}
			}
		} 
		finally
		{
			wait.hide();
		}
	}

	/**
	 * Close the popup.
	 */
	public void closeWaitPopup() 
	{
		wait.hide();
	}

	@Override
	public void onError(Exception e)
	{
		wait.hide();
		FlatMessageBox.show(messages.restError(), MessageType.ERROR);
		Crux.getErrorHandler().handleError(e.getLocalizedMessage(), e);
	}
}
