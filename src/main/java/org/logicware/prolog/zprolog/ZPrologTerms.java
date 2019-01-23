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

import org.logicware.prolog.PrologTerm;

class ZPrologTerms {

	/** current prolog term */
	PrologTerm term;

	/** next prolog terms */
	ZPrologTerms next;

	ZPrologTerms() {
	}

	ZPrologTerms(PrologTerm head) {
		this.term = head;
	}

	ZPrologTerms(PrologTerm term, ZPrologTerms next) {
		this.term = term;
		this.next = next;
	}

	public String toString() {
		StringBuilder string = new StringBuilder();
		if (term != null) {
			string.append(term);
			ZPrologTerms termsPtr = next;
			while (termsPtr != null) {
				string.append(", ");
				string.append(termsPtr.term);
				termsPtr = termsPtr.next;
			}
		}
		return "" + string + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		ZPrologTerms other = (ZPrologTerms) obj;
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

	public final PrologTerm getHead() {
		return term;
	}

	public final String getFunctor() {
		return term.getFunctor();
	}

	public final int getArity() {
		return term.getArity();
	}

	public final String getIndicator() {
		return term.getIndicator();
	}

}
