/*
 * #%L
 * prolobjectlink-jpi-zprolog
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
package org.logicware.prolog.zprolog;

import static org.logicware.prolog.PrologTermType.FALSE_TYPE;
import static org.logicware.prolog.PrologTermType.TRUE_TYPE;

import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;

final class ZPrologFlag {

	static final boolean NO = false;
	static final boolean YES = true;

	static final PrologProvider provider = new ZPrologProvider();

	static final PrologTerm TRUE = new ZPrologTerm(ZPrologToken.TOKEN_TRUE, TRUE_TYPE, provider,
			ZPrologBuiltin.TRUE_FUNCTOR);
	static final PrologTerm FALSE = new ZPrologTerm(ZPrologToken.TOKEN_FALSE, FALSE_TYPE, provider,
			ZPrologBuiltin.FALSE_FUNCTOR);

	static final PrologTerm ON = new ZPrologTerm(provider, "on");
	static final PrologTerm OFF = new ZPrologTerm(provider, "off");

	static final PrologTerm ATOM = new ZPrologTerm(provider, "atom");
	static final PrologTerm CHARS = new ZPrologTerm(provider, "chars");
	static final PrologTerm CODES = new ZPrologTerm(provider, "codes");

	static final PrologTerm FAIL = new ZPrologTerm(provider, "fail");
	static final PrologTerm ERROR = new ZPrologTerm(provider, "error");
	static final PrologTerm WARNING = new ZPrologTerm(provider, "warning");

	static final PrologTerm DOWN = new ZPrologTerm(provider, "down");
	static final PrologTerm TOWARD_ZERO = new ZPrologTerm(provider, "toward_zero");

	static final PrologTerm CHAR_CONVERSION = new ZPrologTerm(provider, "char_conversion");

	static final PrologTerm MAX_INTEGER_VALUE = new ZPrologTerm(provider, Long.MAX_VALUE);
	static final PrologTerm MIN_INTEGER_VALUE = new ZPrologTerm(provider, Long.MIN_VALUE);

	static final PrologTerm MAX_ARITY_VALUE = new ZPrologTerm(provider, Integer.MAX_VALUE);

	// not supported yet
	static final ZPrologFlag DEBUG_FLAG = new ZPrologFlag("debug", OFF, OFF, YES);
	static final ZPrologFlag BOUNDED_FLAG = new ZPrologFlag("bounded", FALSE, FALSE, NO);
	static final ZPrologFlag UNKNOWN_FLAG = new ZPrologFlag("unknown", ERROR, ERROR, YES);
	static final ZPrologFlag CHAR_CONVERSION_FLAG = new ZPrologFlag("char_conversion", ON, ON, YES);
	static final ZPrologFlag DOUBLE_QUOTES_FLAG = new ZPrologFlag("double_quotes", CHARS, CHARS, YES);
	static final ZPrologFlag MAX_ARITY_FLAG = new ZPrologFlag(" max_arity ", MAX_ARITY_VALUE, MAX_ARITY_VALUE, NO);
	static final ZPrologFlag MAX_INTEGER_FLAG = new ZPrologFlag("max_integer", MAX_INTEGER_VALUE, MAX_INTEGER_VALUE,
			NO);
	static final ZPrologFlag MIN_INTEGER_FLAG = new ZPrologFlag("min_integer", MIN_INTEGER_VALUE, MIN_INTEGER_VALUE,
			NO);
	static final ZPrologFlag INTEGER_ROUNDING_FUNCTION_FLAG = new ZPrologFlag("integer_rounding_function", DOWN, DOWN,
			NO);

	private String name;
	private PrologTerm value;
	private PrologTerm values;
	private PrologTerm defaultValue;
	private boolean changeable;

	ZPrologFlag() {
	}

	ZPrologFlag(String name, PrologTerm value, PrologTerm defaultValue, boolean changeable) {
		super();
		this.name = name;
		this.value = value;
		this.defaultValue = defaultValue;
		this.changeable = changeable;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	PrologTerm getValue() {
		return value;
	}

	void setValue(PrologTerm value) {
		this.value = value;
	}

	/**
	 * List term of values
	 * 
	 * @return list term of values
	 * @since 1.0
	 */
	PrologTerm getValues() {
		return values;
	}

	void setValues(PrologTerm values) {
		this.values = values;
	}

	PrologTerm getDefaultValue() {
		return defaultValue;
	}

	void setDefaultValue(PrologTerm defaultValue) {
		this.defaultValue = defaultValue;
	}

	boolean isChangeable() {
		return changeable;
	}

	void setChangeable(boolean changeable) {
		this.changeable = changeable;
	}

	@Override
	public String toString() {
		return name + " = " + value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (changeable ? 1231 : 1237);
		result = prime * result + ((defaultValue == null) ? 0 : defaultValue.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZPrologFlag other = (ZPrologFlag) obj;
		if (changeable != other.changeable)
			return false;
		if (defaultValue == null) {
			if (other.defaultValue != null)
				return false;
		} else if (!defaultValue.equals(other.defaultValue)) {
			return false;
		}
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value)) {
			return false;
		}
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values)) {
			return false;
		}
		return true;
	}

}
