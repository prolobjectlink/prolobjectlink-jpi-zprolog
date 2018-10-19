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
import static org.logicware.prolog.PrologTermType.INTEGER_TYPE;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.logicware.prolog.ArityError;
import org.logicware.prolog.FunctorError;
import org.logicware.prolog.IndicatorError;
import org.logicware.prolog.PrologAtom;
import org.logicware.prolog.PrologDouble;
import org.logicware.prolog.PrologFloat;
import org.logicware.prolog.PrologInteger;
import org.logicware.prolog.PrologList;
import org.logicware.prolog.PrologLong;
import org.logicware.prolog.PrologStructure;
import org.logicware.prolog.PrologTerm;
import org.logicware.prolog.PrologVariable;

public class PrologIntegerTest extends PrologBaseTest {

	private PrologInteger integer = provider.newInteger(100);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClone() {
		assertEquals(provider.newInteger(100), integer);
	}

	@Test
	public void testGetLongValue() {
		assertEquals(100, integer.getLongValue());
	}

	@Test
	public void testGetDoubleValue() {
		assertEquals(100.0, integer.getDoubleValue(), 0);
	}

	@Test
	public void testGetPrologInteger() {
		assertEquals(provider.newInteger(100), integer.getPrologInteger());
	}

	@Test
	public void testGetPrologFloat() {
		assertEquals(provider.newFloat(100.0F), integer.getPrologFloat());
	}

	@Test
	public void testPrologInteger() {
		assertEquals(0, provider.newInteger(0).getLongValue());
	}

	@Test
	public void testPrologIntegerLong() {
		assertEquals(100, provider.newInteger(100).getLongValue());
	}

	@Test(expected = FunctorError.class)
	public void testGetFunctor() {
		assertEquals("100", integer.getFunctor());
	}

	@Test(expected = IndicatorError.class)
	public void testHasIndicator() {
		assertFalse(integer.hasIndicator("100", 0));
	}

	@Test
	public void testEqualsObject() {
		assertTrue(integer.equals(provider.newInteger(100)));
	}

	@Test
	public void testToString() {
		assertEquals("100", integer.toString());
	}

	@Test(expected = ArityError.class)
	public void testGetArity() {
		assertEquals(0, integer.getArity());
	}

	public void testGetArguments() {
		assertArrayEquals(new PrologTerm[0], integer.getArguments());
	}

	@Test
	public void testPrologAtomic() {
		assertTrue(integer.isAtomic());
	}

	@Test
	public void testGetType() {
		assertEquals(INTEGER_TYPE, integer.getType());
	}

	@Test
	public void testIsAtom() {
		assertFalse(integer.isAtom());
	}

	@Test
	public void testIsNumber() {
		assertTrue(integer.isNumber());
	}

	@Test
	public void testIsFloat() {
		assertFalse(integer.isFloat());
	}

	@Test
	public void testIsInteger() {
		assertTrue(integer.isInteger());
	}

	@Test
	public void testIsDouble() {
		assertFalse(integer.isDouble());
	}

	@Test
	public void testIsLong() {
		assertFalse(integer.isLong());
	}

	@Test
	public void testIsVariable() {
		assertFalse(integer.isVariable());
	}

	@Test
	public void testIsList() {
		assertFalse(integer.isList());
	}

	@Test
	public void testIsAtomic() {
		assertTrue(integer.isAtomic());
	}

	@Test
	public void testIsCompound() {
		assertFalse(integer.isCompound());
	}

	@Test
	public void testIsStructure() {
		assertFalse(integer.isStructure());
	}

	@Test
	public void testIsNil() {
		assertFalse(integer.isNil());
	}

	@Test
	public void testIsEmptyList() {
		assertFalse(integer.isEmptyList());
	}

	@Test
	public void testIsExpression() {
		assertFalse(integer.isEvaluable());
	}

	@Test(expected = IndicatorError.class)
	public void testGetKey() {
		integer.getIndicator();
	}

	@Test
	public void testUnify() {

		// with atom
		PrologInteger iValue = provider.newInteger(28);
		PrologAtom atom = provider.newAtom("John Doe");
		assertFalse(iValue.unify(atom));

		// with integer
		PrologInteger iValue1 = provider.newInteger(36);
		// true because are equals
		assertTrue(iValue.unify(iValue));
		// false because they are different
		assertFalse(iValue.unify(iValue1));

		// with long
		PrologLong lValue = provider.newLong(28);
		PrologLong lValue1 = provider.newLong(100);
		// true because are equals
		assertTrue(iValue.unify(lValue));
		// false because they are different
		assertFalse(iValue.unify(lValue1));

		// with float
		PrologFloat fValue = provider.newFloat(36.47F);
		assertFalse(iValue.unify(fValue));

		// with double
		PrologDouble dValue = provider.newDouble(36.47);
		assertFalse(iValue.unify(dValue));

		// with variable
		PrologVariable variable = provider.newVariable("X");
		// true. case atom and variable
		assertTrue(iValue.unify(variable));

		// with predicate
		PrologStructure structure = provider.parsePrologStructure("some_predicate(a,b,c)");
		assertFalse(iValue.unify(structure));

		// with list
		PrologList list = provider.parsePrologList("[a,b,c]");
		assertFalse(iValue.unify(list));

		// with expression
		PrologTerm expression = provider.parsePrologTerm("58+93*10");
		assertFalse(iValue.unify(expression));
	}

	@Test
	public void testCompareTo() {

		// with atom
		PrologInteger iValue = provider.newInteger(28);
		PrologAtom atom = provider.newAtom("John Doe");
		assertEquals(iValue.compareTo(atom), -1);

		// with integer
		PrologInteger iValue1 = provider.newInteger(36);
		// true because are equals
		assertEquals(iValue.compareTo(iValue), 0);
		// false because they are different
		assertEquals(iValue.compareTo(iValue1), -1);

		// with long
		PrologLong lValue = provider.newLong(28);
		PrologLong lValue1 = provider.newLong(100);
		// true because are equals
		assertEquals(iValue.compareTo(lValue), 0);
		// false because they are different
		assertEquals(iValue.compareTo(lValue1), -1);

		// with float
		PrologFloat fValue = provider.newFloat(36.47F);
		assertEquals(iValue.compareTo(fValue), -1);

		// with double
		PrologDouble dValue = provider.newDouble(36.47);
		assertEquals(iValue.compareTo(dValue), -1);

		// with variable
		PrologVariable variable = provider.newVariable("X");
		// true. case atom and variable
		assertEquals(iValue.compareTo(variable), 1);

		// with predicate
		PrologStructure structure = provider.parsePrologStructure("some_predicate(a,b,c)");
		assertEquals(iValue.compareTo(structure), -1);

		// with list
		PrologList list = provider.parsePrologList("[a,b,c]");
		assertEquals(iValue.compareTo(list), -1);

		// with expression
		PrologTerm expression = provider.parsePrologTerm("58+93*10");
		assertEquals(iValue.compareTo(expression), -1);
	}

}
