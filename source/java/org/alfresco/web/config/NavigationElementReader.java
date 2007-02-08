/*
 * Copyright (C) 2005 Alfresco, Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.alfresco.web.config;

import java.util.Iterator;

import org.alfresco.config.ConfigElement;
import org.alfresco.config.ConfigException;
import org.alfresco.config.xml.elementreader.ConfigElementReader;
import org.dom4j.Element;

/**
 * Custom element reader to parse config for navigation overrides
 * 
 * @author gavinc
 */
public class NavigationElementReader implements ConfigElementReader
{
   public static final String ELEMENT_NAVIGATION = "navigation";
   public static final String ELEMENT_OVERRIDE = "override";
   public static final String ATTR_FROM_VIEWID = "from-view-id";
   public static final String ATTR_FROM_OUTCOME = "from-outcome";
   public static final String ATTR_TO_VIEWID = "to-view-id";
   public static final String ATTR_TO_OUTCOME = "to-outcome";
   
   /**
    * @see org.alfresco.config.xml.elementreader.ConfigElementReader#parse(org.dom4j.Element)
    */
   public ConfigElement parse(Element element)
   {
      NavigationConfigElement configElement = null;
      
      if (element != null)
      {
         String name = element.getName();
         if (ELEMENT_NAVIGATION.equals(name) == false)
         {
            throw new ConfigException("NavigationElementReader can only parse " +
                  ELEMENT_NAVIGATION + "elements, " + "the element passed was '" + 
                  name + "'");
         }
         
         configElement = new NavigationConfigElement();
         
         // go through the items to show
         Iterator<Element> items = element.elementIterator();
         while (items.hasNext())
         {
            Element item = items.next();
            
            // only process the override elements
            if (ELEMENT_OVERRIDE.equals(item.getName()))
            {
               String fromViewId = item.attributeValue(ATTR_FROM_VIEWID);
               String fromOutcome = item.attributeValue(ATTR_FROM_OUTCOME);
               String toViewId = item.attributeValue(ATTR_TO_VIEWID);
               String toOutcome = item.attributeValue(ATTR_TO_OUTCOME);
               
               configElement.addOverride(fromViewId, fromOutcome, toViewId, toOutcome);
            }
         }
      }
      
      return configElement;
   }
}
