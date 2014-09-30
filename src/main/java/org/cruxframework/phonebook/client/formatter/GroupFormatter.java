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

import org.cruxframework.crux.core.client.formatter.Formatter;
import org.cruxframework.crux.core.client.formatter.InvalidFormatException;
import org.cruxframework.crux.core.client.formatter.annotation.FormatterName;
import org.cruxframework.phonebook.client.util.Group;

/**
 * Formatter for listGroup. 
 * Formats as the data will be displayed and recorded in the database.
 * 
 * @author flavia.jesus
 */
@FormatterName("groupFormatter")
public class GroupFormatter implements Formatter 
{
	@Override
	public String format(Object input) throws InvalidFormatException 
	{
		if (input != null) 
		{
			if (input instanceof Integer) 
			{
				for (Group group : Group.values())
				{
					if (group.getIndex() == (Integer) input)
					{
						return group.getMessage(); 
					}
				}
			}
		}
		return null;
	}

	@Override
	public Object unformat(String input) throws InvalidFormatException 
	{
		if (input != null && !input.equals("")) 
		{
			for (Group group : Group.values())
			{
				if (group.getMessage().equals(input))
				{
					return group.getIndex(); 
				}
			}
		}
		return null;
	}
}
