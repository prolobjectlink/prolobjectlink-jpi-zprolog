/*
 * #%L
 * prolobjectlink-db-zprolog
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.logicware.prolog.wamkel;

import java.util.HashMap;
import java.util.Map;

import org.logicware.prolog.AbstractEngine;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;

abstract class ZPrologMachine extends AbstractEngine {

	// prolog flags container
	private final Map<String, ZPrologFlag> flags;

	protected ZPrologMachine(PrologProvider provider) {
		super(provider);

		flags = new HashMap<String, ZPrologFlag>();

		// defaults flag
		flags.put("debug", ZPrologFlag.DEBUG_FLAG);
		flags.put("unknown", ZPrologFlag.UNKNOWN_FLAG);
		flags.put("bounded", ZPrologFlag.BOUNDED_FLAG);
		flags.put("max_arity", ZPrologFlag.MAX_ARITY_FLAG);
		flags.put("max_integer", ZPrologFlag.MAX_INTEGER_FLAG);
		flags.put("min_integer", ZPrologFlag.MIN_INTEGER_FLAG);
		flags.put("double_quotes", ZPrologFlag.DOUBLE_QUOTES_FLAG);
		flags.put("char_conversion", ZPrologFlag.CHAR_CONVERSION_FLAG);
		flags.put("integer_rounding_function", ZPrologFlag.INTEGER_ROUNDING_FUNCTION_FLAG);

	}

	protected final Map<String, ZPrologFlag> getFlags() {
		return flags;
	}

	protected final boolean setFlag(String name, PrologTerm value) {
		ZPrologFlag flag = flags.get(name);
		if (flag.isChangeable()) {
			flag.setValue(value);
			flags.put(name, flag);
			return true;
		}
		return false;
	}

	protected final PrologTerm currentFlag(String name, PrologTerm value) {
		return flags.get(name).getValue();
	}

	protected final void defineFlag(String name, PrologTerm value, PrologTerm defaultValue, boolean changeable) {
		flags.put(name, new ZPrologFlag(name, value, defaultValue, changeable));
	}

	protected final boolean isChangeable(String name) {
		return flags.get(name).isChangeable();
	}

}
