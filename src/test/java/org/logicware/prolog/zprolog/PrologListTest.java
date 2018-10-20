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
package org.logicware.prolog.zprolog;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.logicware.prolog.PrologTermType.LIST_TYPE;

import java.util.Iterator;

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

public class PrologListTest extends PrologBaseTest {

	private PrologList list;

	@Before
	public void setUp() throws Exception {
		list = provider.newList(new PrologTerm[] { zero, one, two, three, four, five, six, seven, eight, nine });
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetArguments() {
		PrologTerm[] terms = { zero, one, two, three, four, five, six, seven, eight, nine };
		assertArrayEquals(terms, list.getArguments());
	}

	@Test
	public void testSize() {
		assertEquals(10, list.size());
	}

	@Test
	public void testClear() {
		list.clear();
		assertTrue(list.isEmpty());
		assertEquals(provider.newList(), list);
	}

	@Test
	public void testIsEmpty() {
		assertFalse(list.isEmpty());
		list.clear();
		assertTrue(list.isEmpty());
	}

	@Test
	public void testIterator() {
		int number = 0;
		for (Iterator<PrologTerm> iterator = list.iterator(); iterator.hasNext();) {
			PrologTerm prologTerm = (PrologTerm) iterator.next();
			assertEquals(provider.newInteger(number++), prologTerm);
		}
	}

	@Test
	public void testGetHead() {
		assertEquals(provider.newInteger(0), list.getHead());
	}

	@Test
	public void testGetTail() {
		assertEquals(provider.newList(new PrologTerm[] { one, two, three, four, five, six, seven, eight, nine }),
				list.getTail());
	}

	@Test
	public void testGetType() {
		assertEquals(LIST_TYPE, list.getType());
	}

	@Test
	public void testGetIndicator() {
		assertEquals("./2", list.getIndicator());
	}

	@Test
	public void testHasIndicatorStringInt() {
		assertTrue(list.hasIndicator(".", 2));
	}

	@Test
	public void testIsAtom() {
		assertFalse(list.isAtom());
	}

	@Test
	public void testIsNumber() {
		assertFalse(list.isNumber());
	}

	@Test
	public void testIsFloat() {
		assertFalse(list.isFloat());
	}

	@Test
	public void testIsInteger() {
		assertFalse(list.isInteger());
	}

	@Test
	public void testIsDouble() {
		assertFalse(list.isDouble());
	}

	@Test
	public void testIsLong() {
		assertFalse(list.isLong());
	}

	@Test
	public void testIsVariable() {
		assertFalse(list.isVariable());
	}

	@Test
	public void testIsList() {
		assertTrue(list.isList());
	}

	@Test
	public void testIsStructure() {
		assertFalse(list.isStructure());
	}

	@Test
	public void testIsNil() {
		assertFalse(list.isNil());
	}

	@Test
	public void testIsEmptyList() {
		assertFalse(list.isEmptyList());
	}

	@Test
	public void testIsExpression() {
		assertFalse(list.isEvaluable());
	}

	@Test
	public void testGetArity() {
		assertEquals(2, list.getArity());
	}

	@Test
	public void testGetFunctor() {
		assertEquals(".", list.getFunctor());
	}

	@Test
	public void testUnify() {

		PrologTerm empty = provider.newList();
		PrologList flattened = provider.parsePrologList("[a,b,c]");
		PrologList headTail = provider.parsePrologList("[a|[b|[c|[]]]]");

		// with atom
		PrologAtom atom = provider.newAtom("John Doe");
		assertFalse(flattened.unify(atom));
		assertFalse(headTail.unify(atom));
		assertFalse(empty.unify(atom));

		// with integer
		PrologInteger iValue = provider.newInteger(28);
		assertFalse(flattened.unify(iValue));
		assertFalse(headTail.unify(iValue));
		assertFalse(empty.unify(iValue));

		// with long
		PrologLong lValue = provider.newLong(28);
		assertFalse(flattened.unify(lValue));
		assertFalse(headTail.unify(lValue));
		assertFalse(empty.unify(lValue));

		// with float
		PrologFloat fValue = provider.newFloat(36.47F);
		assertFalse(flattened.unify(fValue));
		assertFalse(headTail.unify(fValue));
		assertFalse(empty.unify(fValue));

		// with double
		PrologDouble dValue = provider.newDouble(36.47);
		assertFalse(flattened.unify(dValue));
		assertFalse(headTail.unify(dValue));
		assertFalse(empty.unify(dValue));

		// with variable
		PrologVariable x = provider.newVariable("X");
		PrologVariable y = provider.newVariable("Y");
		PrologVariable z = provider.newVariable("Z");
		assertTrue(flattened.unify(x));
		assertTrue(headTail.unify(y));
		assertTrue(empty.unify(z));

		// with predicate
		PrologStructure structure = provider.parsePrologStructure("somepredicate(a,b,c)");
		assertFalse(flattened.unify(structure));
		assertFalse(headTail.unify(structure));
		assertFalse(empty.unify(structure));

		// with list
		PrologList flattenList1 = provider.parsePrologList("[X,Y,Z]");
		// IPrologList headTailList1 = new
		// ProlobjectFactory().parseList("[X|[Y|Z]]");
		PrologList headTailList1 = provider.parsePrologList("[X|[Y|[Z]]]");

		// true because are equals
		assertTrue(flattened.unify(flattened));
		assertTrue(headTail.unify(headTail));
		assertTrue(empty.unify(empty));

		// true because their terms unify
		assertTrue(flattened.unify(flattenList1));
		assertTrue(headTail.unify(headTailList1));

		// testing different list type that unify
		assertTrue(flattened.unify(headTail));
		assertTrue(flattenList1.unify(headTailList1));
		assertTrue(flattened.unify(headTailList1));
		assertTrue(flattenList1.unify(headTail));

	}

	@Test
	public void testCompareTo() {

		PrologTerm empty = provider.newList();
		PrologList flattened = provider.parsePrologList("[a,b,c]");
		PrologList headTail = provider.parsePrologList("[a|[b|[c|[]]]]");

		// with atom
		PrologAtom atom = provider.newAtom("John Doe");
		assertEquals(flattened.compareTo(atom), 1);
		assertEquals(headTail.compareTo(atom), 1);
		assertEquals(empty.compareTo(atom), 1);

		// with integer
		PrologInteger iValue = provider.newInteger(28);
		assertEquals(flattened.compareTo(iValue), 1);
		assertEquals(headTail.compareTo(iValue), 1);
		assertEquals(empty.compareTo(iValue), 1);

		// with long
		PrologLong lValue = provider.newLong(28);
		assertEquals(flattened.compareTo(lValue), 1);
		assertEquals(headTail.compareTo(lValue), 1);
		assertEquals(empty.compareTo(lValue), 1);

		// with float
		PrologFloat fValue = provider.newFloat(36.47F);
		assertEquals(flattened.compareTo(fValue), 1);
		assertEquals(headTail.compareTo(fValue), 1);
		assertEquals(empty.compareTo(fValue), 1);

		// with double
		PrologDouble dValue = provider.newDouble(36.47);
		assertEquals(flattened.compareTo(dValue), 1);
		assertEquals(headTail.compareTo(dValue), 1);
		assertEquals(empty.compareTo(dValue), 1);

		// with variable
		PrologVariable x = provider.newVariable("X");
		PrologVariable y = provider.newVariable("Y");
		PrologVariable z = provider.newVariable("Z");
		assertEquals(flattened.compareTo(x), 1);
		assertEquals(headTail.compareTo(y), 1);
		assertEquals(empty.compareTo(z), 1);

		// with predicate
		PrologStructure structure = provider.parsePrologStructure("somepredicate(a,b,c)");
		assertEquals(flattened.compareTo(structure), -1);
		assertEquals(headTail.compareTo(structure), -1);
		assertEquals(empty.compareTo(structure), -1);

		// with list
		PrologList flattenList1 = provider.parsePrologList("[X,Y,Z]");
		PrologList headTailList1 = provider.parsePrologList("[X|[Y|[Z]]]");

		// true because are equals
		assertEquals(flattened.compareTo(flattened), 0);
		assertEquals(headTail.compareTo(headTail), 0);
		assertEquals(empty.compareTo(empty), 0);

		// true because their terms are equals
		assertEquals(flattened.compareTo(flattenList1), 1);
		assertEquals(headTail.compareTo(headTailList1), 1);

		// testing different list type
		assertEquals(flattened.compareTo(headTail), 0);
		assertEquals(flattenList1.compareTo(headTailList1), 0);
		assertEquals(flattened.compareTo(headTailList1), 1);
		assertEquals(flattenList1.compareTo(headTail), -1);

	}

}
