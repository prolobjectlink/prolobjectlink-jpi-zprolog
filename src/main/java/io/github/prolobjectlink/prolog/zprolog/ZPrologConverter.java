/*
 * #%L
 * prolobjectlink-jpi-zprolog
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package io.github.prolobjectlink.prolog.zprolog;

import static io.github.prolobjectlink.prolog.PrologTermType.ATOM_TYPE;
import static io.github.prolobjectlink.prolog.PrologTermType.CUT_TYPE;
import static io.github.prolobjectlink.prolog.PrologTermType.DOUBLE_TYPE;
import static io.github.prolobjectlink.prolog.PrologTermType.FAIL_TYPE;
import static io.github.prolobjectlink.prolog.PrologTermType.FALSE_TYPE;
import static io.github.prolobjectlink.prolog.PrologTermType.FLOAT_TYPE;
import static io.github.prolobjectlink.prolog.PrologTermType.INTEGER_TYPE;
import static io.github.prolobjectlink.prolog.PrologTermType.LIST_TYPE;
import static io.github.prolobjectlink.prolog.PrologTermType.LONG_TYPE;
import static io.github.prolobjectlink.prolog.PrologTermType.NIL_TYPE;
import static io.github.prolobjectlink.prolog.PrologTermType.STRUCTURE_TYPE;
import static io.github.prolobjectlink.prolog.PrologTermType.TRUE_TYPE;
import static io.github.prolobjectlink.prolog.PrologTermType.VARIABLE_TYPE;
import static io.github.prolobjectlink.prolog.zprolog.ZPrologTerm.CUT_TERM;
import static io.github.prolobjectlink.prolog.zprolog.ZPrologTerm.EMPTY_TERM;
import static io.github.prolobjectlink.prolog.zprolog.ZPrologTerm.FAIL_TERM;
import static io.github.prolobjectlink.prolog.zprolog.ZPrologTerm.FALSE_TERM;
import static io.github.prolobjectlink.prolog.zprolog.ZPrologTerm.NIL_TERM;
import static io.github.prolobjectlink.prolog.zprolog.ZPrologTerm.TRUE_TERM;

import io.github.prolobjectlink.prolog.AbstractConverter;
import io.github.prolobjectlink.prolog.PrologAtom;
import io.github.prolobjectlink.prolog.PrologConverter;
import io.github.prolobjectlink.prolog.PrologDouble;
import io.github.prolobjectlink.prolog.PrologFloat;
import io.github.prolobjectlink.prolog.PrologInteger;
import io.github.prolobjectlink.prolog.PrologLong;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologTerm;
import io.github.prolobjectlink.prolog.PrologVariable;
import io.github.prolobjectlink.prolog.UnknownTermError;

public class ZPrologConverter extends AbstractConverter<ZPrologTerm> implements PrologConverter<ZPrologTerm> {

	public PrologTerm toTerm(ZPrologTerm term) {
		return term;
	}

	public ZPrologTerm fromTerm(PrologTerm term) {
		switch (term.getType()) {
		case NIL_TYPE:
			return NIL_TERM;
		case CUT_TYPE:
			return CUT_TERM;
		case FAIL_TYPE:
			return FAIL_TERM;
		case TRUE_TYPE:
			return TRUE_TERM;
		case FALSE_TYPE:
			return FALSE_TERM;
		case ATOM_TYPE:
			return new ZPrologTerm(provider, ((PrologAtom) term).getStringValue());
		case FLOAT_TYPE:
			return new ZPrologTerm(provider, ((PrologFloat) term).getFloatValue());
		case INTEGER_TYPE:
			return new ZPrologTerm(provider, ((PrologInteger) term).getIntegerValue());
		case DOUBLE_TYPE:
			return new ZPrologTerm(provider, ((PrologDouble) term).getDoubleValue());
		case LONG_TYPE:
			return new ZPrologTerm(provider, ((PrologLong) term).getLongValue());
		case VARIABLE_TYPE:
			PrologVariable v = (PrologVariable) term;
			String name = v.getName();
			ZPrologTerm variable = sharedPrologVariables.get(name);
			if (variable == null) {
				variable = new ZPrologTerm(provider, name, v.getPosition());
				sharedPrologVariables.put(name, variable);
			}
			return variable;
		case LIST_TYPE:
			return new ZPrologTerm(provider, term.getArguments());
		case STRUCTURE_TYPE:
			String functor = term.getFunctor();
			return new ZPrologTerm(provider, functor, term.getArguments());
		default:
			throw new UnknownTermError(term);
		}
	}

	public ZPrologTerm[] fromTermArray(PrologTerm[] terms) {
		ZPrologTerm[] prologTerms = new ZPrologTerm[terms.length];
		for (int i = 0; i < terms.length; i++) {
			prologTerms[i] = fromTerm(terms[i]);
		}
		return prologTerms;
	}

	public ZPrologTerm fromTerm(PrologTerm head, PrologTerm[] body) {
		ZPrologTerm clauseHead = fromTerm(head);
		if (body != null && body.length > 0) {
			ZPrologTerm clauseBody = fromTerm(body[body.length - 1]);
			for (int i = body.length - 2; i >= 0; --i) {
				clauseBody = new ZPrologTerm(provider, ",", fromTerm(body[i]), clauseBody);
			}
			return new ZPrologTerm(provider, ":-", clauseHead, clauseBody);
		}
		return clauseHead;
	}

	public PrologProvider createProvider() {
		return new ZProlog(this);
	}

}
