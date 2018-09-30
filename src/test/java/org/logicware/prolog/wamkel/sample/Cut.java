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
import org.junit.Ignore;
import org.junit.Test;
import org.logicware.pdb.prolog.PrologEngine;
import org.logicware.pdb.prolog.PrologQuery;
import org.logicware.prolog.wamkel.PrologBaseTest;

public class Cut extends PrologBaseTest {

	private PrologEngine engine;
	private PrologQuery query;

	@Before
	public void setUp() throws Exception {

		engine = provider.newEngine();

		engine.assertz("function(X,0):-X<3,!.");
		engine.assertz("function(X,2):-3=<X,X<6,!.");
		engine.assertz("function(X,4):-6=<X.");

		engine.assertz("member( X, [X|L] ) :- !.");
		engine.assertz("member( X, [Y|L] ) :- member( X, L).");

		engine.assertz("insert(X,[Y|Sorted],[Y|Sorted1]):-gt( X, Y), !,insert(X,Sorted,Sorted1).");
		engine.assertz("insert(X,Sorted,[X|Sorted]).");

		engine.assertz("gt(X,Y):- X > Y.");

		engine.assertz("bird(sparrow).");
		engine.assertz("bird(eagle).");
		engine.assertz("bird(duck).");
		engine.assertz("bird(crow).");
		engine.assertz("bird(ostrich).");
		engine.assertz("bird(puffin).");
		engine.assertz("bird(swan).");
		engine.assertz("bird(albatross).");
		engine.assertz("bird(starling).");
		engine.assertz("bird(owl).");
		engine.assertz("bird(kingfisher).");
		engine.assertz("bird(kingfisher).");

		engine.assertz("can_fly(ostrich):-!,fail.");
		engine.assertz("can_fly(X):-bird(X).");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test0() {

		query = engine.query("function(1,Y),2<Y.");
		assertFalse(query.hasSolution());

	}

	@Test
	public void test1() {

		query = engine.query("function(7,Y).");
		assertTrue(query.hasSolution());

	}

	// @Ignore
	@Test
	public void test2() {

		query = engine.query("can_fly(duck).");
		assertTrue(query.hasSolution());

	}

	@Ignore
	@Test
	public void test3() {

		query = engine.query("can_fly(ostrich).");
		assertFalse(query.hasSolution());

	}

	@Ignore
	@Test
	public void member() {

		query = engine.query("member(a,[1,2,3,4,5,a,6,7,8,9,0]).");
		assertTrue(query.hasSolution());
		PrologBaseTest.printSolution(query);

	}

	@Ignore
	@Test
	public void testInsert() {

		query = engine.query("insert(4,[1,2,3,5,6,7,8,9],Sorted).");
		assertTrue(query.hasSolution());
		PrologBaseTest.printSolution(query);

	}

}
