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
package org.logicware.prolog.zprolog;

import java.util.Calendar;
import java.util.List;

import org.logicware.prolog.PrologClause;
import org.logicware.prolog.PrologEngine;
import org.logicware.prolog.PrologProgram;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologQuery;
import org.logicware.prolog.PrologTerm;
import org.worklogic.Licenses;

public class ZPrologEngine extends ZPrologRuntime implements PrologEngine {

	private static final int MAJOR = 1;
	private static final int MINOR = 0;
	private static final int MICRO = 0;
	private static final String NAME = "ZProlog";
	private static final String POWEREDBY = "WorkLogic Project";
	private static final String COPYRIGHT = "Copyright(C) 2012-" + Calendar.YEAR;

	// parser helper to build the goal from prolog string
	private static final ZPrologParserUtils parser = new ZPrologParserUtils(provider);

	public ZPrologEngine(PrologProvider provider) {
		super(provider);
	}

	public final void include(String file) {
		PrologProgram p = parser.parseProgram(file);
		if (program != null) {
			program.add(p);
		} else {
			program = p;
		}
	}

	public final void consult(String path) {
		load(path);
	}

	public final void persist(String path) {
		save(path);
	}

	public final void abolish(String functor, int arity) {
		program.removeAll(functor, arity);
	}

	public final void asserta(String stringClause) {
		program.push(parser.parseClause(stringClause));
	}

	public void asserta(PrologTerm head, PrologTerm... body) {
		program.push(new ZPrologClause(head, body));
	}

	public void assertz(String stringClause) {
		program.add(parser.parseClause(stringClause));
	}

	public void assertz(PrologTerm head, PrologTerm... body) {
		program.add(new ZPrologClause(head, body));
	}

	public boolean clause(String stringClause) {
		PrologClause c = parser.parseClause(stringClause);
		List<PrologClause> family = program.get(c.getIndicator());
		if (family != null) {
			for (PrologClause clause : family) {
				if (clause.unify(c)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean clause(PrologTerm head, PrologTerm... body) {
		String key = head.getIndicator();
		PrologClause c = new ZPrologClause(head, body);
		List<PrologClause> family = program.get(key);
		if (family != null) {
			for (PrologClause clause : family) {
				if (clause.unify(c)) {
					return true;
				}
			}
		}
		return false;
	}

	public void retract(String stringClause) {
		program.remove(parser.parseClause(stringClause));
	}

	public void retract(PrologTerm head, PrologTerm... body) {
		program.remove(new ZPrologClause(head, body));
	}

	public PrologQuery query(String stringQuery) {
		return new ZPrologQuery(this, stringQuery);
	}

	public PrologQuery query(PrologTerm[] goals) {
		return new ZPrologQuery(this, goals);
	}

	public PrologQuery query(PrologTerm term, PrologTerm... terms) {
		return new ZPrologQuery(this, term, terms);
	}

	public final int getProgramSize() {
		return program.size();
	}

	public final String getPoweredby() {
		return POWEREDBY;
	}

	public final String getCopyright() {
		return COPYRIGHT;
	}

	public final String getLicense() {
		return Licenses.APACHE_V2;
	}

	public final String getVersion() {
		return MAJOR + "." + MINOR + "." + MICRO;
	}

	public final String getName() {
		return NAME;
	}

	public void dispose() {
		if (program != null) {
			program.clear();
		}
	}

}
