/*-
 * #%L
 * prolobjectlink-jpi-jtrolog
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package org.prolobjectlink.prolog.zprolog;

import javax.script.ScriptEngineFactory;

import org.prolobjectlink.prolog.PrologEngine;
import org.prolobjectlink.prolog.PrologScriptEngineFactory;

public final class ZPrologScriptFactory extends PrologScriptEngineFactory implements ScriptEngineFactory {

	public ZPrologScriptFactory() {
		super(new ZProlog().newEngine());
	}

	public ZPrologScriptFactory(PrologEngine engine) {
		super(engine);
	}

	public String getMethodCallSyntax(String obj, String m, String... args) {
		throw new UnsupportedOperationException("getMethodCallSyntax(String obj, String m, String... args)");
	}

}
