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
package org.logicware.prolog.wamkel.builtin;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.logicware.prolog.PrologEngine;
import org.logicware.prolog.PrologQuery;
import org.logicware.prolog.PrologTerm;
import org.logicware.prolog.wamkel.PrologBaseTest;

public class ControlConstruct extends PrologBaseTest {

	private PrologEngine engine;
	private PrologQuery query;

	@Before
	public void setUp() throws Exception {

		engine = provider.newEngine();

		engine.assertz("big(bear).");
		engine.assertz("big(elephant).");
		engine.assertz("small(cat).");
		engine.assertz("brown(bear).");
		engine.assertz("black(cat).");
		engine.assertz("gray(elephant).");
		engine.assertz("dark(Z) :- black(Z).");
		engine.assertz("dark(Z) :- brown(Z).");
	}

	@After
	public void tearDown() throws Exception {
		engine.dispose();
	}

	@Test
	public void testTrue() {
		query = engine.query("true");
		assertTrue(query.hasSolution());
	}

	@Test
	public void testFalse() {
		query = engine.query("false");
		assertFalse(query.hasSolution());
	}

	@Test
	public void testFail() {
		query = engine.query("fail");
		assertFalse(query.hasSolution());
	}

	@Test
	@Ignore
	public void testCut() {
		query = engine.query("!");
		assertTrue(query.hasSolution());
		query.dispose();
	}

	@Test
	public void testCall() {

		query = engine.query("call(big(elephant))");
		assertTrue(query.hasSolution());
		query.dispose();

		query = engine.query("call(dark(cat))");
		assertTrue(query.hasSolution());
		query.dispose();

		query = engine.query("call(dark(elephant))");
		assertFalse(query.hasSolution());
		query.dispose();

		query = engine.query("call(dark(X))");
		assertTrue(query.hasSolution());
		assertArrayEquals(new PrologTerm[] { provider.newAtom("cat") }, query.oneSolution());
		query.dispose();

		query = engine.query("call(true)");
		assertTrue(query.hasSolution());
		query.dispose();

		query = engine.query("call(false)");
		assertFalse(query.hasSolution());
		query.dispose();

	}

	@Test
	// @Ignore
	public void testIfThen() {

		query = engine.query("small(X) -> X = cat");
		assertTrue(query.hasSolution());
		query.dispose();

		query = engine.query("big(X) -> X = bear");
		assertTrue(query.hasSolution());
		query.dispose();

		query = engine.query("small(X) -> X = bear");
		assertFalse(query.hasSolution());
		query.dispose();

		query = engine.query("big(X) -> X = cat");
		assertFalse(query.hasSolution());
		query.dispose();

	}

	@Test
	@Ignore
	public void testIfThenElse() {

		query = engine.query("big(cat)->write(cat);big(X),write(X)");
		assertTrue(query.hasSolution());
		query.dispose();

	}

}
