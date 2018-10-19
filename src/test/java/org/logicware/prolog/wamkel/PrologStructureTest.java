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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.logicware.prolog.PrologTermType.STRUCTURE_TYPE;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.logicware.prolog.PrologAtom;
import org.logicware.prolog.PrologDouble;
import org.logicware.prolog.PrologFloat;
import org.logicware.prolog.PrologInteger;
import org.logicware.prolog.PrologList;
import org.logicware.prolog.PrologLong;
import org.logicware.prolog.PrologStructure;
import org.logicware.prolog.PrologTerm;
import org.logicware.prolog.PrologVariable;

public final class PrologStructureTest extends PrologBaseTest {

	private PrologStructure structure;

	@Before
	public void setUp() throws Exception {
		structure = provider.newStructure("digits", zero, one, two, three, four, five, six, seven, eight, nine);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetIndicator() {
		assertEquals("digits/10", structure.getIndicator());
	}

	@Test
	public void testHasIndicator() {
		assertFalse(structure.hasIndicator("an_", 100));
		assertFalse(structure.hasIndicator("an_", 0));
		assertFalse(structure.hasIndicator("an_atom", 100));
		assertTrue(structure.hasIndicator("digits", 10));
	}

	@Test
	public void testToString() {
		assertEquals("digits( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 )", structure.toString());
	}

	// @Test
	// public void testIterator() {
	// Iterator<PrologTerm> iterator = structure.iterator();
	// assertTrue(iterator.hasNext());
	// assertEquals(zero, iterator.next());
	// assertTrue(iterator.hasNext());
	// assertEquals(one, iterator.next());
	// assertTrue(iterator.hasNext());
	// assertEquals(two, iterator.next());
	// assertTrue(iterator.hasNext());
	// assertEquals(three, iterator.next());
	// assertTrue(iterator.hasNext());
	// assertEquals(four, iterator.next());
	// assertTrue(iterator.hasNext());
	// assertEquals(five, iterator.next());
	// assertTrue(iterator.hasNext());
	// assertEquals(six, iterator.next());
	// assertTrue(iterator.hasNext());
	// assertEquals(seven, iterator.next());
	// assertTrue(iterator.hasNext());
	// assertEquals(eight, iterator.next());
	// assertTrue(iterator.hasNext());
	// assertEquals(nine, iterator.next());
	//
	// assertFalse(iterator.hasNext());
	// }

	@Test
	public void testGetArity() {
		assertEquals(10, structure.getArity());
	}

	@Test
	public void testGetFunctor() {
		assertEquals("digits", structure.getFunctor());
	}

	@Test
	public void testGetArguments() {
		PrologTerm[] expecteds = new PrologTerm[] { zero, one, two, three, four, five, six, seven, eight, nine };
		assertArrayEquals(expecteds, structure.getArguments());
	}

	@Test
	public void testEqualsObject() {
		PrologStructure prologStructure = provider.newStructure("digits", zero, one, two, three, four, five, six, seven,
				eight, nine);
		assertTrue(structure.equals(prologStructure));
	}

	@Test
	public void testGetType() {
		assertEquals(STRUCTURE_TYPE, structure.getType());
	}

	@Test
	public void testIsAtom() {
		assertFalse(structure.isAtom());
	}

	@Test
	public void testIsNumber() {
		assertFalse(structure.isNumber());
	}

	@Test
	public void testIsFloat() {
		assertFalse(structure.isFloat());
	}

	@Test
	public void testIsInteger() {
		assertFalse(structure.isInteger());
	}

	@Test
	public void testIsDouble() {
		assertFalse(structure.isDouble());
	}

	@Test
	public void testIsLong() {
		assertFalse(structure.isLong());
	}

	@Test
	public void testIsVariable() {
		assertFalse(structure.isVariable());
	}

	@Test
	public void testIsList() {
		assertFalse(structure.isList());
	}

	@Test
	public void testIsAtomic() {
		assertFalse(structure.isAtomic());
	}

	@Test
	public void testIsCompound() {
		assertTrue(structure.isCompound());
	}

	@Test
	public void testIsStructure() {
		assertTrue(structure.isStructure());
	}

	@Test
	public void testIsNil() {
		assertFalse(structure.isNil());
	}

	@Test
	public void testIsEmptyList() {
		assertFalse(structure.isEmptyList());
	}

	@Test
	public void testIsExpression() {
		assertFalse(structure.isEvaluable());
	}

	@Test
	public void testGetKey() {
		assertEquals("digits/10", structure.getIndicator());
	}

	@Test
	public void testUnify() {

		// with atom
		PrologAtom atom = provider.newAtom("John Doe");
		PrologStructure structure = provider.parsePrologStructure("some_predicate(a)");
		assertFalse(structure.unify(atom));

		// with integer
		PrologInteger iValue = provider.newInteger(28);
		assertFalse(structure.unify(iValue));

		// with long
		PrologLong lValue = provider.newLong(28);
		assertFalse(structure.unify(lValue));

		// with float
		PrologFloat fValue = provider.newFloat(36.47F);
		assertFalse(structure.unify(fValue));

		// with double
		PrologDouble dValue = provider.newDouble(36.47);
		assertFalse(structure.unify(dValue));

		// with variable
		PrologVariable variable = provider.newVariable("X");
		// true. case predicate and variable
		assertTrue(structure.unify(variable));

		// with predicate
		PrologStructure structure1 = provider.parsePrologStructure("some_predicate(X)");
		PrologStructure structure2 = provider.parsePrologStructure("some_predicate(28)");
		// true because are equals
		assertTrue(structure.unify(structure));
		// true because match and their arguments unify
		assertTrue(structure.unify(structure1));
		// false because match but their arguments not unify
		assertFalse(structure.unify(structure2));

		// with list
		PrologList flattenList = provider.parsePrologList("['Some Literal']");
		PrologList headTailList = provider.parsePrologList("['Some Literal'|[]]");
		PrologTerm empty = ZPrologTerm.EMPTY_TERM;
		assertFalse(structure.unify(flattenList));
		assertFalse(structure.unify(headTailList));
		assertFalse(structure.unify(empty));

		// with expression
		PrologTerm expression = provider.parsePrologTerm("58+93*10");
		assertFalse(structure.unify(expression));

	}

	@Test
	public void testCompareTo() {

		// with atom
		PrologAtom atom = provider.newAtom("John Doe");
		PrologStructure structure = provider.parsePrologStructure("some_predicate(a)");
		assertEquals(structure.compareTo(atom), 1);

		// with integer
		PrologInteger iValue = provider.newInteger(28);
		assertEquals(structure.compareTo(iValue), 1);

		// with long
		PrologLong lValue = provider.newLong(28);
		assertEquals(structure.compareTo(lValue), 1);

		// with float
		PrologFloat fValue = provider.newFloat(36.47F);
		assertEquals(structure.compareTo(fValue), 1);

		// with double
		PrologDouble dValue = provider.newDouble(36.47);
		assertEquals(structure.compareTo(dValue), 1);

		// with variable
		PrologVariable variable = provider.newVariable("X");
		// true. case predicate and variable
		assertEquals(structure.compareTo(variable), 1);

		// with predicate
		PrologStructure structure1 = provider.parsePrologStructure("some_predicate(X)");
		PrologStructure structure2 = provider.parsePrologStructure("some_predicate(28)");
		// true because are equals
		assertEquals(structure.compareTo(structure), 0);
		// true because match and their arguments compareTo
		assertEquals(structure.compareTo(structure1), 1);
		// false because match but their arguments not compareTo
		assertEquals(structure.compareTo(structure2), 1);

		// with list
		PrologList flattenList = provider.parsePrologList("['Some Literal']");
		PrologList headTailList = provider.parsePrologList("['Some Literal'|[]]");
		PrologTerm empty = ZPrologTerm.EMPTY_TERM;
		assertEquals(structure.compareTo(flattenList), -1);
		assertEquals(structure.compareTo(headTailList), -1);
		assertEquals(structure.compareTo(empty), 1);

		// with expression
		PrologTerm expression = provider.parsePrologTerm("58+93*10");
		assertEquals(structure.compareTo(expression), -1);

	}

}
