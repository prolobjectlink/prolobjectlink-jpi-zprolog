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
package org.logicware.prolog.wamkel;

import java.util.Iterator;
import java.util.Map;

import org.logicware.prolog.PrologClause;
import org.logicware.prolog.PrologClauses;
import org.logicware.prolog.PrologGoal;
import org.logicware.prolog.PrologProgram;
import org.logicware.prolog.PrologTerm;

final class ZPrologGoal extends ZPrologClause implements PrologGoal {

	PrologGoal nextGoal;
	PrologClauses clauses;
	Iterator<PrologClause> i;

	ZPrologGoal(PrologTerm goal) {
		super(goal);
	}

	ZPrologGoal(PrologTerm[] terms) {
		super(terms);
	}

	/** @deprecated used only for test */
	@Deprecated
	ZPrologGoal(PrologTerm term, PrologClauses clauses) {
		super(term);
		this.clauses = clauses;
		this.i = clauses.listIterator();
	}

	/** @deprecated used only for test */
	@Deprecated
	ZPrologGoal(PrologTerm[] terms, PrologClauses clauses) {
		super(terms);
		this.clauses = clauses;
		this.i = clauses.listIterator();
	}

	public ZPrologGoal resolve(PrologProgram program, Map<String, PrologClauses> builtins) {
		return resolve(program, builtins, null);
	}

	/**
	 * Link the current goal with a clause recovery in the program database or
	 * runtime built-in.
	 * 
	 * @param program  program for lookup clause that match with the current goal
	 * @param builtins prolog built-ins for lookup clause that match with the
	 *                 current goal
	 * @return the current goal linked with the matched clause
	 * @since 1.0
	 */
	public ZPrologGoal resolve(PrologProgram program, Map<String, PrologClauses> builtins, PrologGoal next) {
		nextGoal = next;
		String key = getIndicator();
		if ((clauses = program.get(key)) == null) {
			clauses = builtins.get(key);
		}
		if (clauses != null) {
			i = clauses.iterator();
		}
		return this;
	}

	public ZPrologGoal replace(PrologClause clause, ZPrologTerm[] variables, int stackMark) {
		return new ZPrologGoal(((ZPrologTerm) clause.getBody()).refresh(variables));
	}

	public Iterator<PrologClause> iterator() {
		return i;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((clauses == null) ? 0 : clauses.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZPrologGoal other = (ZPrologGoal) obj;
		if (clauses == null) {
			if (other.clauses != null)
				return false;
		} else if (!clauses.equals(other.clauses)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "" + term + ".";
	}

	public boolean hasNextGoal() {
		return nextGoal != null;
	}

	public PrologClause nextGoal() {
		return nextGoal;
	}

	public boolean hasNextClause() {
		return i.hasNext();
	}

	public PrologClause nextClause() {
		return i.next();
	}

	public void removeClause() {
		i.remove();
	}

}
