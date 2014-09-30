package org.cruxframework.phonebook.server;

import java.util.Comparator;

import org.cruxframework.phonebook.shared.dto.ContactDTO;
/**
 * Necessary class to perform the ordination
 * 
 * @author Flavia.Yeshua
 */
public class NameComparator implements Comparator<ContactDTO>
{
	@Override
    public int compare(ContactDTO cont1, ContactDTO cont2)
    {
	   return cont1.getFirstName().toLowerCase().compareTo(cont2.getFirstName().toLowerCase());
    }
}
