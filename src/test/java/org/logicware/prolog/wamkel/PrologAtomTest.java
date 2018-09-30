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
import static org.logicware.pdb.prolog.PrologTermType.ATOM_TYPE;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.logicware.pdb.prolog.CompoundExpectedError;
import org.logicware.pdb.prolog.PrologAtom;
import org.logicware.pdb.prolog.PrologDouble;
import org.logicware.pdb.prolog.PrologFloat;
import org.logicware.pdb.prolog.PrologInteger;
import org.logicware.pdb.prolog.PrologList;
import org.logicware.pdb.prolog.PrologLong;
import org.logicware.pdb.prolog.PrologStructure;
import org.logicware.pdb.prolog.PrologTerm;
import org.logicware.pdb.prolog.PrologVariable;

public class PrologAtomTest extends PrologBaseTest {

	private PrologAtom atom = provider.newAtom("prolobjectlink");

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	public void testPrologAtom() {
		assertEquals("prolog", provider.newAtom("prolog").getStringValue());
		assertEquals("'somebody@mail.subdomain.domain'",
				provider.newAtom("somebody@mail.subdomain.domain").getStringValue());
		assertEquals("'1.6180339887'", provider.newAtom("1.6180339887").getStringValue());
		assertEquals("'100'", provider.newAtom("100").getStringValue());
	}

	@Test
	public void testGetValue() {
		assertEquals("prolobjectlink", atom.getStringValue());
	}

	@Test
	public void testSetValue() {
		atom.setStringValue("prolog");
		assertEquals("prolog", atom.getStringValue());
	}

	@Test
	public void testGetFunctor() {
		assertEquals("prolobjectlink", atom.getFunctor());
	}

	@Test
	public void testHasIndicatorStringInt() {
		assertTrue(atom.hasIndicator("prolobjectlink", 0));
		assertFalse(atom.hasIndicator("prolobjectlink", 3));
	}

	@Test
	public void testEqualsObject() {
		assertTrue(atom.equals(provider.newAtom("prolobjectlink")));
		assertFalse(atom.equals(provider.newAtom("prolog")));
	}

	@Test
	public void testToString() {
		assertEquals("prolobjectlink", atom.toString());
	}

	@Test
	public void testGetArity() {
		assertEquals(0, atom.getArity());
	}

	public void testGetArguments() {
		assertArrayEquals(new PrologTerm[0], atom.getArguments());
	}

	@Test
	public void testGetType() {
		assertEquals(ATOM_TYPE, atom.getType());
	}

	@Test
	public void testIsAtom() {
		assertTrue(atom.isAtom());
	}

	@Test
	public void testIsNumber() {
		assertFalse(atom.isNumber());
	}

	@Test
	public void testIsFloat() {
		assertFalse(atom.isFloat());
	}

	@Test
	public void testIsInteger() {
		assertFalse(atom.isInteger());
	}

	@Test
	public final void testIsDouble() {
		assertFalse(atom.isDouble());
	}

	@Test
	public final void testIsLong() {
		assertFalse(atom.isLong());
	}

	@Test
	public void testIsVariable() {
		assertFalse(atom.isVariable());
	}

	@Test
	public void testIsList() {
		assertFalse(atom.isList());
	}

	@Test
	public void testIsAtomic() {
		assertTrue(atom.isAtomic());
	}

	@Test
	public void testIsCompound() {
		assertFalse(atom.isCompound());
	}

	@Test
	public void testIsStructure() {
		assertFalse(atom.isStructure());
	}

	@Test
	public void testIsNil() {
		assertFalse(atom.isNil());
	}

	@Test
	public void testIsEmptyList() {
		assertFalse(atom.isEmptyList());
	}

	@Test
	public void testIsExpression() {
		assertFalse(atom.isEvaluable());
	}

	@Test
	public void testGetIndicator() {
		assertEquals("prolobjectlink/0", atom.getIndicator());
	}

	@Test
	public void testUnify() {

		// with atom
		PrologAtom atom = provider.newAtom("smith");
		PrologAtom atom1 = provider.newAtom("doe");
		// true because the atoms are equals
		assertTrue(atom.unify(atom));
		// false because the atoms are different
		assertFalse(atom.unify(atom1));

		// with integer
		PrologInteger iValue = provider.newInteger(28);
		assertFalse(atom.unify(iValue));

		// with long
		PrologLong lValue = provider.newLong(28);
		assertFalse(atom.unify(lValue));

		// with float
		PrologFloat fValue = provider.newFloat(36.47);
		assertFalse(atom.unify(fValue));

		// with double
		PrologDouble dValue = provider.newDouble(36.47);
		assertFalse(atom.unify(dValue));

		// with variable
		PrologVariable variable = provider.newVariable("X");
		// true. case atom and variable
		assertTrue(atom.unify(variable));

		// with predicate
		PrologStructure structure = provider.parsePrologStructure("some_predicate(a,b,c)");
		assertFalse(atom.unify(structure));

		// with list
		PrologList flattenedList = provider.parsePrologList("[a,b,c]");
		assertFalse(atom.unify(flattenedList));

		// with expression
		PrologTerm expression = provider.parsePrologTerm("58+93*10");
		assertFalse(atom.unify(expression));
	}

	@Test
	public void testCompareTo() {

		// with atom
		PrologAtom atom = provider.newAtom("smith");
		PrologAtom atom1 = provider.newAtom("doe");
		// true because the atoms are equals
		assertEquals(atom.compareTo(atom), 0);
		// false because the atoms are different
		assertEquals(atom.compareTo(atom1), 1);

		// with integer
		PrologInteger iValue = provider.newInteger(28);
		assertEquals(atom.compareTo(iValue), 1);

		// with long
		PrologLong lValue = provider.newLong(28);
		assertEquals(atom.compareTo(lValue), 1);

		// with float
		PrologFloat fValue = provider.newFloat(36.47);
		assertEquals(atom.compareTo(fValue), 1);

		// with double
		PrologDouble dValue = provider.newDouble(36.47);
		assertEquals(atom.compareTo(dValue), 1);

		// with variable
		PrologVariable variable = provider.newVariable("X");
		// true. case atom and variable
		assertEquals(atom.compareTo(variable), 1);

		// with predicate
		PrologStructure structure = provider.parsePrologStructure("some_predicate(a,b,c)");
		assertEquals(atom.compareTo(structure), -1);

		// with list
		PrologList flattenedList = provider.parsePrologList("[a,b,c]");
		assertEquals(atom.compareTo(flattenedList), -1);

		// with expression
		PrologTerm expression = provider.parsePrologTerm("58+93*10");
		assertEquals(atom.compareTo(expression), -1);

	}

}
