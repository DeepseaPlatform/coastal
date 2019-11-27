/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.model;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.diver.SymbolicState;
import za.ac.sun.cs.coastal.symbolic.exceptions.COASTALException;
import za.ac.sun.cs.coastal.symbolic.exceptions.UnsupportedOperationException;

/**
 * Model for java.util.regex.Matcher that simply notifies the user that this functionality is unimplemented.
 */
public class Matcher {

	public Matcher(COASTAL coastal, Configuration config) {
	}

	public boolean find____Z(SymbolicState state) throws COASTALException {
		throw new UnsupportedOperationException("Matcher.find() is unimplemented");
	}
	
}
