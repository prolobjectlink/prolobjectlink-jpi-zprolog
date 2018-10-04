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

import java.util.Collection;
import java.util.Iterator;

import org.logicware.prolog.AbstractClause;
import org.logicware.prolog.AbstractClauses;
import org.logicware.prolog.PrologClause;
import org.logicware.prolog.PrologClauses;

public final class ZPrologClauses extends AbstractClauses implements PrologClauses {

	private static final long serialVersionUID = 672192977298906786L;

	private final String indicator;

	ZPrologClauses(String indicator) {
		this.indicator = indicator;
	}

	ZPrologClauses(String indicator, PrologClause e) {
		this(indicator);
		add(e);
	}

	ZPrologClauses(String indicator, Collection<? extends PrologClause> clauses) {
		this(indicator);
		addAll(clauses);
	}

	@Override
	public boolean add(PrologClause e) {
		if (!contains(e)) {
			return super.add(e);
		}
		return false;
	}

	public void markDynamic() {
		Iterator<PrologClause> i = iterator();
		while (i.hasNext()) {
			PrologClause clause = i.next();
			if (clause instanceof AbstractClause) {
				((AbstractClause) clause).markDynamic();
			}
		}
	}

	public void unmarkDynamic() {
		Iterator<PrologClause> i = iterator();
		while (i.hasNext()) {
			PrologClause clause = i.next();
			if (clause instanceof AbstractClause) {
				((AbstractClause) clause).unmarkDynamic();
			}
		}
	}

	public boolean isDynamic() {
		Iterator<PrologClause> i = iterator();
		while (i.hasNext()) {
			if (!i.next().isDynamic()) {
				return false;
			}
		}
		return true;
	}

	public void markMultifile() {
		Iterator<PrologClause> i = iterator();
		while (i.hasNext()) {
			PrologClause clause = i.next();
			if (clause instanceof AbstractClause) {
				((AbstractClause) clause).markMultifile();
			}
		}
	}

	public void unmarkMultifile() {
		Iterator<PrologClause> i = iterator();
		while (i.hasNext()) {
			PrologClause clause = i.next();
			if (clause instanceof AbstractClause) {
				((AbstractClause) clause).unmarkMultifile();
			}
		}
	}

	public boolean isMultifile() {
		Iterator<PrologClause> i = iterator();
		while (i.hasNext()) {
			if (!i.next().isMultifile()) {
				return false;
			}
		}
		return true;
	}

	public void markDiscontiguous() {
		Iterator<PrologClause> i = iterator();
		while (i.hasNext()) {
			PrologClause clause = i.next();
			if (clause instanceof AbstractClause) {
				((AbstractClause) clause).markDiscontiguous();
			}
		}
	}

	public void unmarkDiscontiguous() {
		Iterator<PrologClause> i = iterator();
		while (i.hasNext()) {
			PrologClause clause = i.next();
			if (clause instanceof AbstractClause) {
				((AbstractClause) clause).unmarkDiscontiguous();
			}
		}
	}

	public boolean isDiscontiguous() {
		Iterator<PrologClause> i = iterator();
		while (i.hasNext()) {
			if (!i.next().isDiscontiguous()) {
				return false;
			}
		}
		return true;
	}

	public String getIndicator() {
		return indicator;
	}

	@Override
	public String toString() {
		String clause = "";
		Iterator<PrologClause> i = iterator();
		while (i.hasNext()) {
			clause += i.next() + "\n";
		}
		return clause;
	}

}
