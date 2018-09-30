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

import static org.logicware.pdb.prolog.PrologTermType.EMPTY_TYPE;

import org.logicware.pdb.prolog.AbstractProvider;
import org.logicware.pdb.prolog.PrologAtom;
import org.logicware.pdb.prolog.PrologConverter;
import org.logicware.pdb.prolog.PrologDouble;
import org.logicware.pdb.prolog.PrologEngine;
import org.logicware.pdb.prolog.PrologFloat;
import org.logicware.pdb.prolog.PrologInteger;
import org.logicware.pdb.prolog.PrologList;
import org.logicware.pdb.prolog.PrologLong;
import org.logicware.pdb.prolog.PrologProvider;
import org.logicware.pdb.prolog.PrologStructure;
import org.logicware.pdb.prolog.PrologTerm;
import org.logicware.pdb.prolog.PrologVariable;

public final class ZPrologProvider extends AbstractProvider implements PrologProvider {

	static int vIndexer = 0;

	public ZPrologProvider() {
		super(new ZPrologConverter());
	}

	public ZPrologProvider(PrologConverter<ZPrologTerm> converter) {
		super(converter);
	}

	public boolean isCompliant() {
		return false;
	}

	public boolean preserveQuotes() {
		return true;
	}

	public PrologTerm prologNil() {
		return ZPrologTerm.NIL_TERM;
	}

	public PrologTerm prologCut() {
		return ZPrologTerm.CUT_TERM;
	}

	public PrologTerm prologFail() {
		return ZPrologTerm.FAIL_TERM;
	}

	public PrologTerm prologTrue() {
		return ZPrologTerm.TRUE_TERM;
	}

	public PrologTerm prologFalse() {
		return ZPrologTerm.FALSE_TERM;
	}

	public PrologTerm prologEmpty() {
		return ZPrologTerm.EMPTY_TERM;
	}

	public PrologEngine newEngine() {
		return new ZPrologEngine(this);
	}

	public PrologAtom newAtom(String functor) {
		return new ZPrologTerm(this, functor);
	}

	public PrologFloat newFloat(Number value) {
		return new ZPrologTerm(this, value.floatValue());
	}

	public PrologDouble newDouble(Number value) {
		return new ZPrologTerm(this, value.doubleValue());
	}

	public PrologInteger newInteger(Number value) {
		return new ZPrologTerm(this, value.intValue());
	}

	public PrologLong newLong(Number value) {
		return new ZPrologTerm(this, value.longValue());
	}

	@Deprecated
	public PrologVariable newVariable() {
		return newVariable(ZPrologTerm.ANONYMOUS);
	}

	@Deprecated
	public PrologVariable newVariable(String name) {
		return new ZPrologTerm(this, name, vIndexer++);
	}

	public PrologVariable newVariable(int position) {
		return newVariable(ZPrologTerm.ANONYMOUS, position);
	}

	public PrologVariable newVariable(String name, int position) {
		return new ZPrologTerm(this, name, position);
	}

	public PrologList newList() {
		return new ZPrologTerm(ZPrologToken.TOKEN_EMPTY, EMPTY_TYPE, this, ZPrologBuiltin.EMPTY_FUNCTOR);
	}

	public PrologList newList(PrologTerm[] arguments) {
		if (arguments.length > 0) {
			return new ZPrologTerm(this, arguments);
		}
		return new ZPrologTerm(ZPrologToken.TOKEN_EMPTY, EMPTY_TYPE, this, ZPrologBuiltin.EMPTY_FUNCTOR);
	}

	public PrologList newList(PrologTerm head, PrologTerm tail) {
		return new ZPrologTerm(this, head, tail);
	}

	public PrologList newList(PrologTerm[] arguments, PrologTerm tail) {
		// TODO what happen if arguments length is zero ???
		return new ZPrologTerm(this, arguments, tail);
	}

	public PrologStructure newStructure(String functor, PrologTerm... arguments) {
		return new ZPrologTerm(this, functor, arguments);
	}

	public PrologTerm newStructure(PrologTerm left, String operator, PrologTerm right) {
		return new ZPrologTerm(this, left, operator, right);
	}

	public PrologTerm parsePrologTerm(String term) {
		return new ZPrologParser(this).parseTerm(term);
	}

	public PrologTerm[] parsePrologTerms(String stringTerms) {
		return new ZPrologParser(this).parseTerms(stringTerms);
	}

	@Override
	public String toString() {
		return "ZPrologProvider [converter=" + converter + "]";
	}

}
