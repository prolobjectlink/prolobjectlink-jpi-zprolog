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

public class PrologExpressionTest extends PrologBaseTest {

	private String operator = "+";

	private PrologVariable variable = provider.newVariable("X");
	private PrologInteger integer = provider.newInteger(100);
	private PrologDouble double1 = provider.newDouble(1.6180339887);

	private PrologTerm expression0 = provider.newStructure(variable, operator, integer);
	private PrologTerm expression1 = provider.newStructure(integer, operator, variable);

	private PrologTerm expression2 = provider.newStructure(variable, operator, double1);
	private PrologTerm expression3 = provider.newStructure(double1, operator, variable);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPrecedence() {

		expression0 = provider.parsePrologTerm("Y1 - Y =\\= Xdist");
		assertTrue(expression0.getLeft().isEvaluable());
		assertTrue(expression0.getRight().isVariable());

		expression0 = provider.parsePrologTerm("Y1 is Y * Xdist");
		assertTrue(expression0.getLeft().isVariable());
		assertTrue(expression0.getRight().isEvaluable());

		expression0 = provider.parsePrologTerm("Y1 - Y * Xdist");
		assertTrue(expression0.getLeft().isVariable());
		assertTrue(expression0.getRight().isEvaluable());

		expression0 = provider.parsePrologTerm("Y1 / Y + Xdist");
		assertTrue(expression0.getLeft().isEvaluable());
		assertTrue(expression0.getRight().isVariable());

		expression0 = provider.parsePrologTerm("Z - Y1 / Y + Xdist");
		assertTrue(expression0.getLeft().isEvaluable());
		assertTrue(expression0.getRight().isVariable());

		expression0 = provider.parsePrologTerm("Z - Y1 - Y * Xdist");
		assertTrue(expression0.getLeft().isEvaluable());
		assertTrue(expression0.getRight().isEvaluable());

		expression0 = provider.parsePrologTerm("Z * Y1 / Y + Xdist");
		assertTrue(expression0.getLeft().isEvaluable());
		assertTrue(expression0.getRight().isVariable());

		expression0 = provider.parsePrologTerm("Z * Y1 - Y * Xdist");
		assertTrue(expression0.getLeft().isEvaluable());
		assertTrue(expression0.getRight().isEvaluable());

	}

	@Test
	public void testHasIndicator() {
		assertTrue(expression0.hasIndicator("+", 2));
	}

	@Test
	public void testClone() {
		PrologTerm expected = provider.newStructure(variable, operator, integer);
		assertEquals(expected, expression0);
	}

	@Test
	public void testToString() {
		assertEquals("X + 100", expression0.toString());
	}

	@Test
	public void testPrologExpressionPrologTermStringPrologTerm() {
		PrologTerm expression = provider.newStructure(variable, "+", integer);
		assertEquals(expression0, expression);
	}

	@Test
	public void testPrologExpressionPrologTermPrologOperatorPrologTerm() {
		PrologTerm expression = provider.newStructure(variable, operator, integer);
		assertEquals(expression0, expression);
	}

	@Test
	public void testGetOperator() {
		assertEquals("+", expression0.getFunctor());
	}

	@Test
	public void testGetLeft() {
		assertEquals(provider.newVariable("X"), expression0.getArguments()[0]);
	}

	@Test
	public void testGetRight() {
		assertEquals(provider.newInteger(100), expression0.getArguments()[1]);
	}

	@Test
	public void testGetArity() {
		assertEquals(2, expression0.getArity());
	}

	@Test
	public void testGetFunctor() {
		assertEquals("+", expression0.getFunctor());
	}

	@Test
	public void testGetArguments() {
		PrologVariable variable = provider.newVariable("X");
		PrologInteger integer = provider.newInteger(100);
		PrologTerm[] args = new PrologTerm[] { variable, integer };
		assertArrayEquals(args, expression0.getArguments());
	}

	@Test
	public void testEqualsObject() {
		PrologVariable variable = provider.newVariable("X");
		PrologInteger integer = provider.newInteger(100);
		PrologTerm otherExpression = provider.newStructure(variable, "+", integer);
		assertTrue(expression0.equals(otherExpression));
	}

	@Test
	public void testGetType() {
		assertEquals(STRUCTURE_TYPE, expression0.getType());
	}

	@Test
	public void testIsAtom() {
		assertFalse(expression0.isAtom());
	}

	@Test
	public void testIsNumber() {
		assertFalse(expression0.isNumber());
	}

	@Test
	public void testIsFloat() {
		assertFalse(expression0.isFloat());
	}

	@Test
	public void testIsInteger() {
		assertFalse(expression0.isInteger());
	}

	@Test
	public void testIsVariable() {
		assertFalse(expression0.isVariable());
	}

	@Test
	public void testIsList() {
		assertFalse(expression0.isList());
	}

	@Test
	public void testIsAtomic() {
		assertFalse(expression0.isAtomic());
	}

	@Test
	public void testIsCompound() {
		assertTrue(expression0.isCompound());
	}

	@Test
	public void testIsStructure() {
		assertTrue(expression0.isStructure());
	}

	@Test
	public void testIsNil() {
		assertFalse(expression0.isNil());
	}

	@Test
	public void testIsEmptyList() {
		assertFalse(expression0.isEmptyList());
	}

	@Test
	public void testIsExpression() {
		assertTrue(expression0.isEvaluable());
	}

	@Test
	public void testGetIndicator() {
		assertEquals("+/2", expression0.getIndicator());
	}

	@Test
	public void testUnify() {

		PrologTerm expression = provider.parsePrologTerm("58+93*10");

		// with atom
		PrologAtom atom = provider.newAtom("John Doe");
		assertFalse(expression.unify(atom));

		// with integer
		PrologInteger iValue = provider.newInteger(28);
		assertFalse(expression.unify(iValue));

		// with long
		PrologLong lValue = provider.newLong(28);
		assertFalse(expression.unify(lValue));

		// with float
		PrologFloat fValue = provider.newFloat(36.47);
		assertFalse(expression.unify(fValue));

		// with double
		PrologDouble dValue = provider.newDouble(36.47);
		assertFalse(expression.unify(dValue));

		// with variable
		PrologVariable variable = provider.newVariable("X");
		// true. case expression and variable
		assertTrue(expression.unify(variable));

		// with predicate
		PrologStructure structure = provider.parsePrologStructure("some_predicate(a)");
		assertFalse(expression.unify(structure));

		// with list
		PrologList flattenList = provider.parsePrologList("['Some Literal']");
		PrologList headTailList = provider.parsePrologList("['Some Literal'|[]]");
		PrologTerm empty = provider.prologEmpty();
		assertFalse(expression.unify(flattenList));
		assertFalse(expression.unify(headTailList));
		assertFalse(expression.unify(empty));

		// with expression
		PrologTerm expression1 = provider.parsePrologTerm("X+Y*Z");
		PrologTerm expression2 = provider.parsePrologTerm("3.14+1.58*2.71");
		// true because are equals
		assertTrue(expression.unify(expression));
		// true because match and their arguments unify
		assertTrue(expression.unify(expression1));
		// false because match but their arguments not unify
		assertFalse(expression.unify(expression2));

	}

	@Test
	public void testCompareTo() {

		PrologTerm expression = provider.parsePrologTerm("58+93*10");

		// with atom
		PrologAtom atom = provider.newAtom("John Doe");
		assertEquals(expression.compareTo(atom), 1);

		// with integer
		PrologInteger iValue = provider.newInteger(28);
		assertEquals(expression.compareTo(iValue), 1);

		// with long
		PrologLong lValue = provider.newLong(28);
		assertEquals(expression.compareTo(lValue), 1);

		// with float
		PrologFloat fValue = provider.newFloat(36.47);
		assertEquals(expression.compareTo(fValue), 1);

		// with double
		PrologDouble dValue = provider.newDouble(36.47);
		assertEquals(expression.compareTo(dValue), 1);

		// with variable
		PrologVariable variable = provider.newVariable("X");
		// true. case expression and variable
		assertEquals(expression.compareTo(variable), 1);

		// with predicate
		PrologStructure structure = provider.parsePrologStructure("some_predicate(a)");
		assertEquals(expression.compareTo(structure), 1);

		// with list
		PrologList flattenList = provider.parsePrologList("['Some Literal']");
		PrologList headTailList = provider.parsePrologList("['Some Literal'|[]]");
		PrologTerm empty = provider.prologEmpty();

		assertEquals(expression.compareTo(flattenList), -1);
		assertEquals(expression.compareTo(headTailList), -1);
		assertEquals(expression.compareTo(empty), 1);

		// with expression
		PrologTerm expression1 = provider.parsePrologTerm("X+Y*Z");
		PrologTerm expression2 = provider.parsePrologTerm("3.14+1.58*2.71");

		// true because are equals
		assertEquals(expression.compareTo(expression), 0);
		// true because match and their arguments are equals
		assertEquals(expression.compareTo(expression1), 1);
		// false because match but their arguments not equals
		assertEquals(expression.compareTo(expression2), 1);

	}

}
