/*
 * #%L
 * prolobjectlink-jpi-zprolog
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PrologClauseTest extends PrologBaseTest {

	ZPrologClause fact = new ZPrologClause(provider.parseTerm("p(a,b,c)"));
	ZPrologClause rule = new ZPrologClause(provider.parseTerm("p(a,X,b):-q,r"));

	@Test
	public final void testHashCode() {
		assertEquals(fact.hashCode(), new ZPrologClause(provider.parseTerm("p(a,b,c)")).hashCode());
		assertEquals(rule.hashCode(), new ZPrologClause(provider.parseTerm("p(a,X,b):-q,r")).hashCode());
	}

	@Test
	public final void testGetTerm() {
		assertEquals(fact, new ZPrologClause(provider.parseTerm("p(a,b,c)")));
		assertEquals(rule, new ZPrologClause(provider.parseTerm("p(a,X,b):-q,r")));
	}

	@Test
	public final void testGetFunctor() {
		assertEquals("p", fact.getFunctor());
		assertEquals("p", rule.getFunctor());
	}

	@Test
	public final void testGetArity() {
		assertEquals(3, fact.getArity());
		assertEquals(3, rule.getArity());
	}

	@Test
	public final void testGetIndicator() {
		assertEquals("p/3", fact.getIndicator());
		assertEquals("p/3", rule.getIndicator());
	}

	@Test
	public final void testIsFact() {
		assertTrue(fact.isFact());
		assertFalse(rule.isFact());
	}

	@Test
	public final void testIsRule() {
		assertFalse(fact.isRule());
		assertTrue(rule.isRule());
	}

	@Test
	public final void testIsDynamic() {
		assertFalse(fact.isDynamic());
		assertFalse(rule.isDynamic());
	}

	@Test
	public final void testMarkDynamic() {
		assertFalse(fact.isDynamic());
		fact.markDynamic();
		assertTrue(fact.isDynamic());

		assertFalse(rule.isDynamic());
		rule.markDynamic();
		assertTrue(rule.isDynamic());
	}

	@Test
	public final void testUnmarkDynamic() {
		assertFalse(fact.isDynamic());
		fact.markDynamic();
		assertTrue(fact.isDynamic());
		fact.unmarkDynamic();
		assertFalse(fact.isDynamic());

		assertFalse(rule.isDynamic());
		rule.markDynamic();
		assertTrue(rule.isDynamic());
		rule.unmarkDynamic();
		assertFalse(rule.isDynamic());
	}

	@Test
	public final void testIsMultifile() {
		assertFalse(fact.isMultifile());
		assertFalse(rule.isMultifile());
	}

	@Test
	public final void testMarkMultifile() {
		assertFalse(fact.isMultifile());
		fact.markMultifile();
		assertTrue(fact.isMultifile());

		assertFalse(rule.isMultifile());
		rule.markMultifile();
		assertTrue(rule.isMultifile());
	}

	@Test
	public final void testUnmarkMultifile() {
		assertFalse(fact.isMultifile());
		fact.markMultifile();
		assertTrue(fact.isMultifile());
		fact.unmarkMultifile();
		assertFalse(fact.isMultifile());

		assertFalse(rule.isMultifile());
		rule.markMultifile();
		assertTrue(rule.isMultifile());
		rule.unmarkMultifile();
		assertFalse(rule.isMultifile());

	}

	@Test
	public final void testIsDiscontiguous() {
		assertFalse(fact.isDiscontiguous());
	}

	@Test
	public final void testMarkDiscontiguous() {
		assertFalse(fact.isDiscontiguous());
		fact.markDiscontiguous();
		assertTrue(fact.isDiscontiguous());
		assertFalse(rule.isDiscontiguous());
		rule.markDiscontiguous();
		assertTrue(rule.isDiscontiguous());
	}

	@Test
	public final void testUnmarkDiscontiguous() {

		assertFalse(fact.isDiscontiguous());
		fact.markDiscontiguous();
		assertTrue(fact.isDiscontiguous());
		fact.unmarkDiscontiguous();
		assertFalse(fact.isDiscontiguous());

		assertFalse(rule.isDiscontiguous());
		rule.markDiscontiguous();
		assertTrue(rule.isDiscontiguous());
		rule.unmarkDiscontiguous();
		assertFalse(rule.isDiscontiguous());

	}

	@Test
	public final void testUnify() {
		assertTrue(fact.unify(new ZPrologClause(provider.parseTerm("p(a,b,c)"))));
		assertTrue(rule.unify(new ZPrologClause(provider.parseTerm("p(a,X,b):-q,r"))));
	}

	@Test
	public final void testEqualsObject() {
		assertEquals(fact, new ZPrologClause(provider.parseTerm("p(a,b,c)")));
		assertEquals(rule, new ZPrologClause(provider.parseTerm("p(a,X,b):-q,r")));
	}

}
