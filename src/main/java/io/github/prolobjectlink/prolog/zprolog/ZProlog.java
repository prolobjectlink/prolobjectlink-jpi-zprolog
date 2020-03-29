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

import static io.github.prolobjectlink.prolog.PrologTermType.LIST_TYPE;
import static io.github.prolobjectlink.prolog.zprolog.ZPrologEngine.MAJOR;
import static io.github.prolobjectlink.prolog.zprolog.ZPrologEngine.MICRO;
import static io.github.prolobjectlink.prolog.zprolog.ZPrologEngine.MINOR;

import io.github.prolobjectlink.prolog.AbstractProvider;
import io.github.prolobjectlink.prolog.PrologAtom;
import io.github.prolobjectlink.prolog.PrologConverter;
import io.github.prolobjectlink.prolog.PrologDouble;
import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologFloat;
import io.github.prolobjectlink.prolog.PrologInteger;
import io.github.prolobjectlink.prolog.PrologJavaConverter;
import io.github.prolobjectlink.prolog.PrologList;
import io.github.prolobjectlink.prolog.PrologLogger;
import io.github.prolobjectlink.prolog.PrologLong;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologStructure;
import io.github.prolobjectlink.prolog.PrologTerm;
import io.github.prolobjectlink.prolog.PrologVariable;

public class ZProlog extends AbstractProvider implements PrologProvider {

	static int vIndexer = 0;
	public static final String VERSION = MAJOR + "." + MINOR + "." + MICRO;
	public static final String NAME = "ZProlog";
	static final PrologLogger logger = new ZPrologLogger();

	public ZProlog() {
		super(new ZPrologConverter());
	}

	public ZProlog(PrologConverter<ZPrologTerm> converter) {
		super(converter);
	}

	public boolean isCompliant() {
		return false;
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

	public PrologTerm prologInclude(String file) {
		return newStructure("ensure_loaded", newAtom(file));
	}

	public PrologTerm parseTerm(String term) {
		return new ZPrologParser(this).parseTerm(term);
	}

	public PrologTerm[] parseTerms(String stringTerms) {
		return new ZPrologParser(this).parseTerms(stringTerms);
	}

	public PrologEngine newEngine() {
		return new ZPrologEngine(this);
	}

	public PrologEngine newEngine(String path) {
		PrologEngine engine = newEngine();
		engine.consult(path);
		return engine;
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

	public PrologVariable newVariable(int position) {
		return newVariable(ZPrologTerm.ANONYMOUS, position);
	}

	public PrologVariable newVariable(String name, int position) {
		return new ZPrologTerm(this, name, position);
	}

	public PrologList newList() {
		return new ZPrologTerm(ZPrologToken.TOKEN_EMPTY, LIST_TYPE, this, ZPrologBuiltin.EMPTY_FUNCTOR);
	}

	public PrologList newList(PrologTerm[] arguments) {
		if (arguments.length > 0) {
			return new ZPrologTerm(this, arguments);
		}
		return new ZPrologTerm(ZPrologToken.TOKEN_EMPTY, LIST_TYPE, this, ZPrologBuiltin.EMPTY_FUNCTOR);
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

	public PrologTerm newReference(Object reference) {
		throw new UnsupportedOperationException("newReference(Object reference)");
	}

	public PrologJavaConverter getJavaConverter() {
		return new ZPrologJavaConverter(this);
	}

	public PrologLogger getLogger() {
		return logger;
	}

	@Override
	public String toString() {
		return "ZPrologProvider [converter=" + converter + "]";
	}

}
