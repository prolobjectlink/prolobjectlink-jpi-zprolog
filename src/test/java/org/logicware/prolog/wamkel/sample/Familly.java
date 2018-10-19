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
package org.logicware.prolog.wamkel.sample;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.logicware.prolog.PrologEngine;
import org.logicware.prolog.PrologQuery;
import org.logicware.prolog.wamkel.PrologBaseTest;

public class Familly extends PrologBaseTest {

	private PrologEngine engine;
	private PrologQuery query;

	@Before
	public void setUp() throws Exception {

		engine = provider.newEngine();

		engine.assertz("parent( pam, bob).");
		engine.assertz("parent( tom, bob).");
		engine.assertz("parent( tom, liz).");
		engine.assertz("parent( bob, ann).");
		engine.assertz("parent( bob, pat).");
		engine.assertz("parent( pat, jim).");
		engine.assertz("female( pam).");
		engine.assertz("male( tom).");
		engine.assertz("male( bob).");
		engine.assertz("female( liz).");
		engine.assertz("female( ann).");
		engine.assertz("female( pat).");
		engine.assertz("male( jim).");
		engine.assertz("offspring( Y, X):-parent( X, Y).");
		engine.assertz("mother( X, Y):-parent( X, Y),female( X).");
		engine.assertz("grandparent( X, Z):-parent( X, Y),parent( Y, Z).");
		engine.assertz("sister( X, Y):-parent( Z, X),parent( Z, Y),female( X),different( X, Y).");
		engine.assertz("predecessor( X, Z):-parent( X, Z).");
		engine.assertz("predecessor( X, Z):-parent( X, Y),predecessor( Y, Z).");
		engine.assertz("different( X, X) :- !, fail.");
		engine.assertz("different( X, Y).");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMother() {

		query = engine.query("mother(X,Y).");
		assertTrue(query.hasSolution());
		PrologBaseTest.printSolution(query);
		query.dispose();

	}

	@Test
	public void testGrandparent() {

		query = engine.query("grandparent(X,Y).");
		assertTrue(query.hasSolution());
		PrologBaseTest.printSolution(query);
		query.dispose();

	}

	@Test
	public void testMotherPat() {

		query = engine.query("mother(X,pat).");
		assertFalse(query.hasSolution());
		PrologBaseTest.printSolution(query);
		query.dispose();

	}

	@Test
	public void testMotherGrandparent() {

		query = engine.query("mother(X,Y),grandparent(X,Z).");
		assertTrue(query.hasSolution());
		PrologBaseTest.printSolution(query);
		query.dispose();

	}

	@Test
	public void testSister() {
		query = engine.query("sister(X,Y)");
		assertTrue(query.hasSolution());
		PrologBaseTest.printSolution(query);
		query.dispose();

	}

}
