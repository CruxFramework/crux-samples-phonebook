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
package org.cruxframework.phonebook.client.formatter;

import org.cruxframework.crux.core.client.formatter.InvalidFormatException;
import org.cruxframework.crux.core.client.formatter.annotation.FormatterName;
import org.cruxframework.crux.widgets.client.maskedtextbox.MaskedTextBoxBaseFormatter;

/**
 * Formatter for phone number. 
 * Formats as the data will be displayed and recorded in the database.
 * 
 * @author flavia.jesus
 */
@FormatterName("phoneFormatter")
public class PhoneFormatter extends MaskedTextBoxBaseFormatter
{

	@Override
	public String getMask() 
	{
		return "(999) 999-9999";
	}

	@Override
	public String format(Object input) throws InvalidFormatException 
	{
		return input == null ? "" : input.toString();
	}

	@Override
	public Object unformat(String input) throws InvalidFormatException 
	{
		return input;
	}

}
