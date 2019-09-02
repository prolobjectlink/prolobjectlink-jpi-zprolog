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
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.prolobjectlink.prolog.AbstractIterator;
import org.prolobjectlink.prolog.PrologClause;
import org.prolobjectlink.prolog.PrologIndicator;
import org.prolobjectlink.prolog.PrologTerm;

public class ZPrologClause /* extends AbstractClause */ implements PrologClause {

	private boolean dynamic;
	private boolean multifile;
	private boolean discontiguous;

	protected PrologTerm term;
	protected PrologTerm next;

	ZPrologClause(PrologTerm term) {
		this.term = term;
		if (term.hasIndicator(":-", 2)) {
			this.term = term.getArguments()[0];
			this.next = term.getArguments()[1];
		}
	}

	/**
	 * Used only for goals
	 * 
	 * @param terms goal array
	 */
	ZPrologClause(PrologTerm[] terms) {
		term = terms[terms.length - 1];
		for (int i = terms.length; i > 1; i--) {
			term = new ZPrologTerm(term.getProvider(), ",", terms[i - 2], term);
		}
	}

	ZPrologClause(PrologTerm term, PrologTerm... terms) {
		this.term = term;
		if (terms != null && terms.length > 0) {
			this.next = terms[terms.length - 1];
			for (int i = terms.length; i > 1; i--) {
				next = new ZPrologTerm(next.getProvider(), ",", terms[i - 2], next);
			}
		}
	}

	public int getArity() {
		return term.getArity();
	}

	public String getFunctor() {
		return term.getFunctor();
	}
	
	

	@Override
	public PrologTerm[] getArguments() {
		return term.getArguments();
	}

	@Override
	public PrologTerm getArgument(int index) {
		return term.getArgument(index);
	}

	public PrologTerm getTerm() {
		return term;
	}

	public PrologTerm getHead() {
		return term;
	}

	public PrologTerm getBody() {
		return next;
	}

	public String getIndicator() {
		return term.getIndicator();
	}

	public boolean isDirective() {
		return term == null && next != null;
	}

	public boolean isFact() {
		return term != null && next == null;
	}

	public boolean isRule() {
		return term != null && next != null;
	}

	public boolean isDynamic() {
		return dynamic;
	}

	public void markDynamic() {
		dynamic = true;
	}

	public void unmarkDynamic() {
		dynamic = false;
	}

	public boolean isMultifile() {
		return multifile;
	}

	public void markMultifile() {
		multifile = true;
	}

	public void unmarkMultifile() {
		multifile = false;
	}

	public boolean isDiscontiguous() {
		return discontiguous;
	}

	public void markDiscontiguous() {
		discontiguous = true;
	}

	public void unmarkDiscontiguous() {
		discontiguous = false;
	}

	public boolean unify(PrologClause clause) {
		return term.unify(clause.getTerm());
	}

	public PrologTerm[] getBodyArray() {
		PrologTerm ptr = getBody();
		List<PrologTerm> terms = new ArrayList<PrologTerm>();
		while (ptr.isCompound() && ptr.hasIndicator(",", 2)) {
			terms.add(ptr.getArgument(0));
			ptr = ptr.getArgument(1);
		}
		terms.add(ptr);
		return terms.toArray(new PrologTerm[0]);
	}

	public PrologIndicator getPrologIndicator() {
		int arity = getArity();
		String functor = getFunctor();
		return new ZPrologIndicator(functor, arity);
	}

	public final Iterator<PrologTerm> getBodyIterator() {
		return new BodyIterator(getBodyArray());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (discontiguous ? 1231 : 1237);
		result = prime * result + (dynamic ? 1231 : 1237);
		result = prime * result + (multifile ? 1231 : 1237);
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
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
		ZPrologClause other = (ZPrologClause) obj;
		if (discontiguous != other.discontiguous)
			return false;
		if (dynamic != other.dynamic)
			return false;
		if (multifile != other.multifile)
			return false;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next)) {
			return false;
		}
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		if (isDirective()) {
			PrologTerm b = next;
			StringBuilder buffer = new StringBuilder();
			buffer.append(":-");
			while (b.hasIndicator(",", 2)) {
				buffer.append(b.getArguments()[0]);
				buffer.append(",\n\t");
				b = b.getArguments()[1];
			}
			buffer.append(b);
			buffer.append(".\n");
			return "" + buffer + "";
		} else if (isRule()) {
			PrologTerm h = term;
			PrologTerm b = next;
			StringBuilder buffer = new StringBuilder();
			buffer.append(h);
			buffer.append(" :- \n\t");
			while (b.hasIndicator(",", 2)) {
				buffer.append(b.getArguments()[0]);
				buffer.append(",\n\t");
				b = b.getArguments()[1];
			}
			buffer.append(b);
			buffer.append(".\n");
			return "" + buffer + "";
		}
		return "" + term + ".";
	}

	private class BodyIterator extends AbstractIterator<PrologTerm> implements Iterator<PrologTerm> {

		private int nextIndex;

		private final PrologTerm[] elements;

		protected BodyIterator(PrologTerm[] elements) {
			this.elements = elements;
		}

		public boolean hasNext() {
			return nextIndex < elements.length;
		}

		public PrologTerm next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return elements[nextIndex++];
		}

	}

}
