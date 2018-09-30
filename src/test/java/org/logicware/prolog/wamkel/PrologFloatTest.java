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
import static org.logicware.pdb.prolog.PrologTermType.FLOAT_TYPE;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.logicware.pdb.prolog.ArityError;
import org.logicware.pdb.prolog.CompoundExpectedError;
import org.logicware.pdb.prolog.FunctorError;
import org.logicware.pdb.prolog.IndicatorError;
import org.logicware.pdb.prolog.PrologAtom;
import org.logicware.pdb.prolog.PrologDouble;
import org.logicware.pdb.prolog.PrologFloat;
import org.logicware.pdb.prolog.PrologInteger;
import org.logicware.pdb.prolog.PrologList;
import org.logicware.pdb.prolog.PrologLong;
import org.logicware.pdb.prolog.PrologStructure;
import org.logicware.pdb.prolog.PrologTerm;
import org.logicware.pdb.prolog.PrologVariable;

public class PrologFloatTest extends PrologBaseTest {

	private PrologFloat float1 = provider.newFloat(1.6180339887F);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetLongValue() {
		assertEquals(1, float1.getLongValue());
	}

	@Test
	public void testGetDoubleValue() {
		assertEquals(1.6180340051651, float1.getDoubleValue(), 0);
	}

	@Test
	public void testGetPrologInteger() {
		assertEquals(provider.newInteger(1), float1.getPrologInteger());
	}

	@Test
	public void testGetPrologFloat() {
		assertEquals(provider.newFloat(1.618034F), float1.getPrologFloat());
	}

	@Test
	public void testPrologFloat() {
		assertEquals(0, provider.newFloat(0).getDoubleValue(), 0);
	}

	@Test
	public void testPrologFloatDouble() {
		assertEquals(1.6180340051651, provider.newFloat(1.6180339887F).getDoubleValue(), 0);
	}

	@Test(expected = FunctorError.class)
	public void testGetFunctor() {
		assertEquals("1.6180339887", float1.getFunctor());
	}

	@Test(expected = IndicatorError.class)
	public void testHasIndicator() {
		assertFalse(float1.hasIndicator("1.6180339887", 0));
	}

	@Test
	public void testEqualsObject() {
		assertTrue(float1.equals(provider.newFloat(1.6180339887F)));
	}

	@Test
	public void testToString() {
		assertEquals("1.618034", float1.toString());
	}

	@Test(expected = ArityError.class)
	public void testGetArity() {
		assertEquals(0, float1.getArity());
	}

	@Test(expected = CompoundExpectedError.class)
	public void testGetArguments() {
		assertArrayEquals(new PrologTerm[0], float1.getArguments());
	}

	@Test
	public void testGetType() {
		assertEquals(FLOAT_TYPE, float1.getType());
	}

	@Test
	public void testIsAtom() {
		assertFalse(float1.isAtom());
	}

	@Test
	public void testIsNumber() {
		assertTrue(float1.isNumber());
	}

	@Test
	public void testIsFloat() {
		assertTrue(float1.isFloat());
	}

	@Test
	public void testIsInteger() {
		assertFalse(float1.isInteger());
	}

	@Test
	public void testIsDouble() {
		assertFalse(float1.isDouble());
	}

	@Test
	public void testIsLong() {
		assertFalse(float1.isLong());
	}

	@Test
	public void testIsVariable() {
		assertFalse(float1.isVariable());
	}

	@Test
	public void testIsList() {
		assertFalse(float1.isList());
	}

	@Test
	public void testIsAtomic() {
		assertTrue(float1.isAtomic());
	}

	@Test
	public void testIsCompound() {
		assertFalse(float1.isCompound());
	}

	@Test
	public void testIsStructure() {
		assertFalse(float1.isStructure());
	}

	@Test
	public void testIsNil() {
		assertFalse(float1.isNil());
	}

	@Test
	public void testIsEmptyList() {
		assertFalse(float1.isEmptyList());
	}

	@Test
	public void testIsExpression() {
		assertFalse(float1.isEvaluable());
	}

	@Test(expected = IndicatorError.class)
	public void testGetKey() {
		float1.getIndicator();
	}

	@Test
	public void testUntify() {

		// with atom
		PrologFloat fValue = provider.newFloat(36.47F);
		PrologAtom atom = provider.newAtom("doe");
		assertFalse(fValue.unify(atom));

		// with integer
		PrologInteger iValue = provider.newInteger(28);
		assertFalse(fValue.unify(iValue));

		// with long
		PrologLong lValue = provider.newLong(28);
		assertFalse(fValue.unify(lValue));

		// with float
		PrologFloat fValue1 = provider.newFloat(100.98F);
		// true because are equals
		assertTrue(fValue.unify(fValue));
		// false because are different
		assertFalse(fValue.unify(fValue1));

		// with double
		PrologDouble dValue = provider.newDouble(36.47);
		PrologDouble dValue1 = provider.newDouble(100.98);
		// true because are equals
		assertTrue(fValue.unify(dValue));
		// false because are different
		assertFalse(fValue.unify(dValue1));

		// with variable
		PrologVariable variable = provider.newVariable("X");
		// true. case float and variable
		assertTrue(fValue.unify(variable));

		// with predicate
		PrologStructure structure = provider.parsePrologStructure("some_predicate(a,b,c)");
		assertFalse(fValue.unify(structure));

		// with list
		PrologList flattenedList = provider.parsePrologList("[a,b,c]");
		assertFalse(fValue.unify(flattenedList));

		// with expression
		PrologTerm expression = provider.parsePrologTerm("58+93*10");
		assertFalse(fValue.unify(expression));

	}

	@Test
	public void testCompareTo() {

		// with atom
		PrologFloat fValue = provider.newFloat(36.47F);
		PrologAtom atom = provider.newAtom("doe");
		assertEquals(fValue.compareTo(atom), -1);

		// with integer
		PrologInteger iValue = provider.newInteger(28);
		assertEquals(fValue.compareTo(iValue), 1);

		// with long
		PrologLong lValue = provider.newLong(28);
		assertEquals(fValue.compareTo(lValue), 1);

		// with float
		PrologFloat fValue1 = provider.newFloat(100.98F);
		// true because are equals
		assertEquals(fValue.compareTo(fValue), 0);
		// false because are different
		assertEquals(fValue.compareTo(fValue1), -1);

		// with double
		PrologDouble dValue = provider.newDouble(36.47);
		PrologDouble dValue1 = provider.newDouble(100.98);
		// true because are equals
		assertEquals(fValue.compareTo(dValue), 0);
		// false because are different
		assertEquals(fValue.compareTo(dValue1), -1);

		// with variable
		PrologVariable variable = provider.newVariable("X");
		// true. case float and variable
		assertEquals(fValue.compareTo(variable), 1);

		// with predicate
		PrologStructure structure = provider.parsePrologStructure("some_predicate(a,b,c)");
		assertEquals(fValue.compareTo(structure), -1);

		// with list
		PrologList flattenedList = provider.parsePrologList("[a,b,c]");
		assertEquals(fValue.compareTo(flattenedList), -1);

		// with expression
		PrologTerm expression = provider.parsePrologTerm("58+93*10");
		assertEquals(fValue.compareTo(expression), -1);

	}

}
