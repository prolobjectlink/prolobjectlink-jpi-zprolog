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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.logicware.prolog.PrologTermType.VARIABLE_TYPE;

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

public class PrologVariableTest extends PrologBaseTest {

	private PrologVariable anonymous = provider.newVariable();
	private PrologVariable variable = provider.newVariable("X");

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = FunctorError.class)
	public void testGetFunctor() {
		anonymous.getFunctor();
		variable.getFunctor();
	}

	@Test(expected = IndicatorError.class)
	public void testHasIndicator() {
		assertFalse(anonymous.hasIndicator("any_functor", 0));
		assertFalse(variable.hasIndicator("any_functor", 3));
	}

	@Test
	public void testEqualsObject() {
		assertFalse(anonymous.equals(variable));
		assertTrue(anonymous.equals(provider.newVariable()));
		assertTrue(variable.equals(provider.newVariable("X")));
	}

	@Test
	public void testToString() {
		assertEquals(ZPrologTerm.ANONYMOUS, anonymous.getName());
		assertEquals("X", variable.getName());
	}

	@Test
	public void testPrologVariable() {
		assertEquals(ZPrologTerm.ANONYMOUS, provider.newVariable().getName());
	}

	@Test
	public void testGetName() {
		assertEquals(ZPrologTerm.ANONYMOUS, anonymous.getName());
		assertEquals("X", variable.getName());
	}

	@Test
	public void testSetName() {
		anonymous.setName("Z");
		assertEquals("Z", anonymous.getName());
	}

	@Test
	public void testIsAnonim() {
		assertTrue(anonymous.isAnonymous());
		assertFalse(variable.isAnonymous());
	}

	@Test(expected = ArityError.class)
	public void testGetArity() {
		anonymous.getArity();
		variable.getArity();
	}

	public void testGetArguments() {
		anonymous.getArguments();
		variable.getArguments();
	}

	@Test
	public void testGetType() {
		assertEquals(VARIABLE_TYPE, anonymous.getType());
		assertEquals(VARIABLE_TYPE, variable.getType());
	}

	@Test
	public void testIsAtom() {
		assertFalse(anonymous.isAtom());
		assertFalse(variable.isAtom());
	}

	@Test
	public void testIsNumber() {
		assertFalse(anonymous.isNumber());
		assertFalse(variable.isNumber());
	}

	@Test
	public void testIsFloat() {
		assertFalse(anonymous.isFloat());
		assertFalse(variable.isFloat());
	}

	@Test
	public void testIsInteger() {
		assertFalse(anonymous.isInteger());
		assertFalse(variable.isInteger());
	}

	@Test
	public void testIsDouble() {
		assertFalse(anonymous.isDouble());
		assertFalse(variable.isDouble());
	}

	@Test
	public void testIsLong() {
		assertFalse(anonymous.isLong());
		assertFalse(variable.isLong());
	}

	@Test
	public void testIsVariable() {
		assertTrue(anonymous.isVariable());
		assertTrue(variable.isVariable());
	}

	@Test
	public void testIsList() {
		assertFalse(anonymous.isList());
		assertFalse(variable.isList());
	}

	@Test
	public void testIsAtomic() {
		assertTrue(anonymous.isAtomic());
		assertTrue(variable.isAtomic());
	}

	@Test
	public void testIsCompound() {
		assertFalse(anonymous.isCompound());
		assertFalse(variable.isCompound());
	}

	@Test
	public void testIsStructure() {
		assertFalse(anonymous.isStructure());
		assertFalse(variable.isStructure());
	}

	@Test
	public void testIsNil() {
		assertFalse(anonymous.isNil());
		assertFalse(variable.isNil());
	}

	@Test
	public void testIsEmptyList() {
		assertFalse(anonymous.isEmptyList());
		assertFalse(variable.isEmptyList());
	}

	@Test
	public void testIsExpression() {
		assertFalse(anonymous.isEvaluable());
		assertFalse(variable.isEvaluable());
	}

	@Test(expected = IndicatorError.class)
	public void testGetKey() {
		variable.getIndicator();
	}

	@Test
	public void testUnify() {

		// with atom
		PrologVariable variable = provider.newVariable("X");
		PrologAtom atom = provider.newAtom("John Smith");
		assertTrue(variable.unify(atom));

		// with integer
		variable = provider.newVariable("X");
		PrologInteger iValue = provider.newInteger(28);
		assertTrue(variable.unify(iValue));

		// with long
		variable = provider.newVariable("X");
		PrologLong lValue = provider.newLong(28);
		assertTrue(variable.unify(lValue));

		// with float
		variable = provider.newVariable("X");
		PrologFloat fValue = provider.newFloat(36.47F);
		assertTrue(variable.unify(fValue));

		// with double
		variable = provider.newVariable("X");
		PrologDouble dValue = provider.newDouble(36.47);
		assertTrue(variable.unify(dValue));

		// with variable
		variable = provider.newVariable("X");
		PrologVariable y = provider.newVariable("Y");
		assertTrue(variable.unify(variable)); // are
		// equals
		assertTrue(variable.unify(y)); // alphabetic
		// substitution

		// with predicate with occurs check
		variable = provider.newVariable("X");
		PrologStructure structure = provider.parsePrologStructure("structure(X)");
		assertTrue(variable.unify(structure));
		structure = provider.parsePrologStructure("structure([X])");
		assertTrue(variable.unify(structure));

		variable = provider.newVariable("X");
		structure = provider.parsePrologStructure("structure(A,b,C)");
		assertTrue(variable.unify(structure));

		// with list
		variable = provider.newVariable("X");
		PrologVariable z = provider.newVariable("Z");
		PrologList flattenList = provider.parsePrologList("[X]");
		PrologList headTailList = provider.parsePrologList("[Y|[]]");
		PrologTerm empty = ZPrologTerm.EMPTY_TERM;
		assertTrue(variable.unify(flattenList));
		assertTrue(y.unify(headTailList));
		assertTrue(z.unify(empty));
	}

	@Test
	public void testCompareTo() {

		// with atom
		PrologVariable variable = provider.newVariable("X");
		PrologAtom atom = provider.newAtom("John Smith");
		assertEquals(variable.compareTo(atom), -1);

		// with integer
		variable = provider.newVariable("X");
		PrologInteger iValue = provider.newInteger(28);
		assertEquals(variable.compareTo(iValue), -1);

		// with long
		variable = provider.newVariable("X");
		PrologLong lValue = provider.newLong(28);
		assertEquals(variable.compareTo(lValue), -1);

		// with float
		variable = provider.newVariable("X");
		PrologFloat fValue = provider.newFloat(36.47F);
		assertEquals(variable.compareTo(fValue), -1);

		// with double
		variable = provider.newVariable("X");
		PrologDouble dValue = provider.newDouble(36.47);
		assertEquals(variable.compareTo(dValue), -1);

		// with variable
		variable = provider.newVariable("X");
		PrologVariable y = provider.newVariable("Y");
		assertEquals(variable.compareTo(variable), 0); // are
		// equals
		assertEquals(variable.compareTo(y), -1); // alphabetic
		// substitution

		variable = provider.newVariable("X");
		PrologStructure structure = provider.parsePrologStructure("some_predicate(a,b,c)");
		assertEquals(variable.compareTo(structure), -1);
		structure = provider.parsePrologStructure("structure([X])");
		assertEquals(variable.compareTo(structure), -1);

		variable = provider.newVariable("X");
		structure = provider.parsePrologStructure("structure(A,b,C)");
		assertEquals(variable.compareTo(structure), -1);

		// with list
		variable = provider.newVariable("X");
		PrologVariable z = provider.newVariable("Z");
		PrologList flattenList = provider.parsePrologList("[X]");
		PrologList headTailList = provider.parsePrologList("[Y|[]]");
		PrologTerm empty = ZPrologTerm.EMPTY_TERM;
		assertEquals(variable.compareTo(flattenList), -1);
		assertEquals(y.compareTo(headTailList), -1);
		assertEquals(z.compareTo(empty), -1);
	}

}
