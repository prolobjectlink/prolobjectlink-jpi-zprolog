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
package org.prolobjectlink.prolog.zprolog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.prolobjectlink.prolog.AbstractEngine;
import org.prolobjectlink.prolog.AbstractQuery;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologQuery;
import org.prolobjectlink.prolog.PrologTerm;
import org.prolobjectlink.prolog.RuntimeError;

public final class ZPrologQuery extends AbstractQuery implements PrologQuery {

	// goal to be queried
	private final ZPrologGoal goal;

	// query literals
	private final String query;

	// flag that indicate if the query has solution
	private boolean hasSolution;

	// flag that indicate if more solutions are possible
	private boolean hasMoreSolution;

	ZPrologQuery(AbstractEngine engine, String query) {
		super(engine);
		this.query = query;
		PrologProvider provider = engine.getProvider();
		goal = new ZPrologParser(provider).parseGoal(query);
		hasSolution = engine.unwrap(ZPrologEngine.class).run(goal);
	}

	ZPrologQuery(AbstractEngine engine, PrologTerm[] goals) {
		super(engine);
		goal = new ZPrologGoal(goals);
		this.query = "" + goal + "";
		hasSolution = engine.unwrap(ZPrologEngine.class).run(goal);
	}

	ZPrologQuery(AbstractEngine engine, PrologTerm term, PrologTerm[] terms) {
		super(engine);
		goal = new ZPrologGoal(term, terms);
		this.query = "" + goal + "";
		hasSolution = engine.unwrap(ZPrologEngine.class).run(goal);
	}

	/**
	 * <p>
	 * Check that the current query has solution.
	 * </p>
	 * 
	 * 
	 * 
	 * @return true if the current query has solution, false if not
	 * @since 1.0
	 */
	public final boolean hasSolution() {
		return hasSolution;
	}

	/**
	 * <p>
	 * Check if the current query has more solution.
	 * </p>
	 * 
	 * 
	 * @return true if the current query has more solution, false if not
	 * @since 1.0
	 */
	public final boolean hasMoreSolutions() {
		return hasSolution || hasMoreSolution;
	}

	public final PrologTerm[] oneSolution() {
		int index = 0;
		ZPrologStack stack = engine.unwrap(ZPrologEngine.class).stack;
		PrologTerm[] result = new PrologTerm[stack.getMaxVarNumber()];
		for (int i = 0; i < stack.size(); i++) {
			if (stack.get(i) instanceof ZPrologTerm) {
				ZPrologTerm v = (ZPrologTerm) stack.get(i);
				if (!v.isAnonymous() && index < result.length) {
					PrologTerm vValue = v.dereference();
					result[index++] = vValue;
				}
			}
		}
		return result;
	}

	public final Map<String, PrologTerm> oneVariablesSolution() {
		ZPrologStack stack = engine.unwrap(ZPrologEngine.class).stack;
		Map<String, PrologTerm> solution = new HashMap<String, PrologTerm>(stack.getMaxVarNumber());
		for (int i = 0; i < stack.size(); i++) {
			if (stack.get(i) instanceof ZPrologTerm) {
				ZPrologTerm v = (ZPrologTerm) stack.get(i);
				if (!v.isAnonymous()) {
					String vName = v.getName();
					PrologTerm vValue = v.dereference();
					solution.put(vName, vValue);
				}
			}
		}
		return solution;
	}

	public final PrologTerm[] nextSolution() {
		hasSolution = false;
		PrologTerm[] solution = oneSolution();
		ZPrologEngine e = engine.unwrap(ZPrologEngine.class);
		hasMoreSolution = (e.backtrack()) ? e.solve() : false;
		return solution;
	}

	public final Map<String, PrologTerm> nextVariablesSolution() {
		hasSolution = false;
		Map<String, PrologTerm> solution = oneVariablesSolution();
		ZPrologEngine e = engine.unwrap(ZPrologEngine.class);
		hasMoreSolution = e.backtrack() ? e.solve() : false;
		return solution;
	}

	public final PrologTerm[][] nSolutions(int n) {
		if (n > 0) {
			// m:solutionSize
			int m = 0;
			int index = 0;
			List<PrologTerm[]> all = new ArrayList<PrologTerm[]>();
			while (hasMoreSolutions() && index < n) {
				PrologTerm[] solution = nextSolution();
				m = solution.length > m ? solution.length : m;
				index++;
				all.add(solution);
			}

			PrologTerm[][] allSolutions = new PrologTerm[n][m];
			for (int i = 0; i < n; i++) {
				PrologTerm[] solution = all.get(i);
				System.arraycopy(solution, 0, allSolutions[i], 0, m);
			}
			return allSolutions;
		}
		throw new RuntimeError("Impossible find " + n + " solutions");
	}

	public final Map<String, PrologTerm>[] nVariablesSolutions(int n) {
		if (n > 0) {
			int index = 0;
			@SuppressWarnings("unchecked")
			Map<String, PrologTerm>[] solutionMaps = new Map[n];
			while (hasMoreSolutions() && index < n) {
				Map<String, PrologTerm> solutionMap = nextVariablesSolution();
				solutionMaps[index++] = solutionMap;
			}
			return solutionMaps;
		}
		throw new RuntimeError("Impossible find " + n + " solutions");
	}

	public final PrologTerm[][] allSolutions() {
		// n:solutionCount, m:solutionSize
		int n = 0;
		int m = 0;
		List<PrologTerm[]> all = new ArrayList<PrologTerm[]>();
		while (hasMoreSolutions()) {
			PrologTerm[] solution = nextSolution();
			m = solution.length > m ? solution.length : m;
			n++;
			all.add(solution);
		}

		PrologTerm[][] allSolutions = new PrologTerm[n][m];
		for (int i = 0; i < n; i++) {
			PrologTerm[] solution = all.get(i);
			System.arraycopy(solution, 0, allSolutions[i], 0, m);
		}
		return allSolutions;
	}

	public final Map<String, PrologTerm>[] allVariablesSolutions() {
		List<Map<String, PrologTerm>> allVariables = new ArrayList<Map<String, PrologTerm>>();
		while (hasMoreSolutions()) {
			Map<String, PrologTerm> variables = nextVariablesSolution();
			allVariables.add(variables);
		}
		return allVariables.toArray(new Map[0]);
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((goal == null) ? 0 : goal.hashCode());
		result = prime * result + (hasMoreSolution ? 1231 : 1237);
		result = prime * result + (hasSolution ? 1231 : 1237);
		result = prime * result + ((query == null) ? 0 : query.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZPrologQuery other = (ZPrologQuery) obj;
		if (goal == null) {
			if (other.goal != null)
				return false;
		} else if (!goal.equals(other.goal))
			return false;
		if (hasMoreSolution != other.hasMoreSolution)
			return false;
		if (hasSolution != other.hasSolution)
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		return true;
	}

	@Override
	public final String toString() {
		return "?- " + query;
	}

	/**
	 * Release all allocations for the query
	 * 
	 * @since 1.0
	 */
	public final void dispose() {
		this.hasSolution = false;
		this.hasMoreSolution = false;
	}

	public final List<Map<String, PrologTerm>> all() {
		List<Map<String, PrologTerm>> allVariables = new ArrayList<Map<String, PrologTerm>>();
		while (hasMoreSolutions()) {
			Map<String, PrologTerm> variables = nextVariablesSolution();
			allVariables.add(variables);
		}
		return allVariables;
	}

}
