/******************************************************************
*
*	CyberLink for Java
*
*	Copyright (C) Satoshi Konno 2002-2004
*
*	File: AllowedValue.java
*
*	Revision:
*
*	03/27/04
*		- first revision.
*	
******************************************************************/

package org.cybergarage.upnp;

import org.cybergarage.xml.*;

public class AllowedValue
{
	////////////////////////////////////////////////
	//	Constants
	////////////////////////////////////////////////
	
	public final static String ELEM_NAME = "allowedValue";

	////////////////////////////////////////////////
	//	isIconNode
	////////////////////////////////////////////////

	public static boolean isAllowedValueNode(Node node)
	{
		return ELEM_NAME.equals(node.getName());
	}

}
