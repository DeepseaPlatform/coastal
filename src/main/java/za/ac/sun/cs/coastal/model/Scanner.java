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
 * Model for java.util.Scanner that simply notifies the user that this functionality is unimplemented.
 */
public class Scanner {

	public Scanner(COASTAL coastal, Configuration config) {
	}

	public boolean _init___Ljava_1lang_1String_2__V(SymbolicState state) throws COASTALException {
		throw new UnsupportedOperationException("Scanner.<init>() is unimplemented");
	}
	
	public boolean nextInt____I(SymbolicState state) throws COASTALException {
		throw new UnsupportedOperationException("Scanner.nextInt() is unimplemented");
	}
	
}
