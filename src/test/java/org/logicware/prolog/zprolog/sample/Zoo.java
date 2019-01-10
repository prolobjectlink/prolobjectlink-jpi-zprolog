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
package org.logicware.prolog.zprolog.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.logicware.prolog.PrologEngine;
import org.logicware.prolog.PrologQuery;
import org.logicware.prolog.PrologTerm;
import org.logicware.prolog.zprolog.PrologBaseTest;

public class Zoo extends PrologBaseTest {

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

		query = engine.query("dark(X),big(X).");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDarkBig() {

		solutionMap = new HashMap<String, PrologTerm>();
		solutionMap.put("X", provider.newAtom("bear"));

		assertTrue(query.hasSolution());
		assertEquals(solutionMap, query.oneVariablesSolution());
		assertEquals(solutionMap, engine.queryOne("dark(X),big(X)."));

	}
}
