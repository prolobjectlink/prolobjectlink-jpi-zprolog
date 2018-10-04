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
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.logicware.prolog.PrologProgram;
import org.logicware.prolog.PrologTerm;

public class PrologParserTest extends PrologBaseTest {

	ZPrologParser parser = new ZPrologParser(provider);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testTerm() {

		assertEquals(provider.prologCut(), parser.parseTerm("!"));
		assertEquals(provider.prologFail(), parser.parseTerm("fail"));
		assertEquals(provider.prologFalse(), parser.parseTerm("false"));
		assertEquals(provider.prologTrue(), parser.parseTerm("true"));
		assertEquals(provider.prologEmpty(), parser.parseTerm("[]"));

		assertEquals(provider.newVariable(), parser.parseTerm("_"));
		assertEquals(provider.newVariable("X"), parser.parseTerm("X"));

		assertEquals(provider.newInteger(), parser.parseTerm("0"));
		assertEquals(provider.newInteger(28), parser.parseTerm("28"));

		assertEquals(provider.newDouble(0.0), parser.parseTerm("0.0"));
		assertEquals(provider.newDouble(28.0), parser.parseTerm("28.0"));

		assertEquals(provider.newAtom("prolobjectlink"), parser.parseTerm("prolobjectlink"));
		assertEquals(provider.newAtom("'ProlobjectLink'"), parser.parseTerm("'ProlobjectLink'"));
		assertEquals(provider.newAtom("'Prolobject Link'"), parser.parseTerm("'Prolobject Link'"));

		assertEquals(provider.newStructure("digits", zero, one, two, three, four, five, six, seven, eight, nine),
				parser.parseTerm("digits( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 )"));
		assertEquals(provider.newStructure("'Digits'", zero, one, two, three, four, five, six, seven, eight, nine),
				parser.parseTerm("'Digits'( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 )"));
		assertEquals(provider.newStructure("'digits from 0 to 9'", zero, one, two, three, four, five, six, seven, eight,
				nine), parser.parseTerm("'digits from 0 to 9'( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 )"));

		assertEquals(provider.newStructure("a", provider.newInteger(11)), parser.parseTerm("a(+11)"));
		// assertEquals(provider.newPrologStructure("a",
		// provider.newPrologStructure("+", provider.newPrologInteger(11))),
		// parser.parseTerm("a(+11)"));
		// assertEquals(provider.newPrologStructure("a",
		// provider.newPrologStructure("-", provider.newPrologInteger(11))),
		// parser.parseTerm("a(-11)"));
		assertEquals(
				provider.newStructure("a", provider.newStructure("-", provider.newInteger(3), provider.newInteger(11))),
				parser.parseTerm("a(3-11)"));
		// assertEquals(provider.newPrologStructure("abs",
		// provider.newPrologStructure("-", provider.newPrologInteger(3),
		// provider.newPrologInteger(11))), parser.parseTerm("abs(3-11)"));

		assertEquals(
				provider.newStructure("+", provider.newInteger(10),
						provider.newStructure("*", provider.newInteger(90), provider.newInteger(3))),
				parser.parseTerm("10+90*3"));
		assertEquals(
				provider.newStructure("-", provider.newInteger(10),
						provider.newStructure("*", provider.newInteger(90), provider.newInteger(3))),
				parser.parseTerm("10-90*3"));
		assertEquals(provider.newStructure("-",
				provider.newStructure("*", provider.newInteger(10), provider.newInteger(90)), provider.newInteger(3)),
				parser.parseTerm("10*90-3"));
		assertEquals(
				provider.newStructure("*", provider.newInteger(10),
						provider.newStructure("^", provider.newInteger(90), provider.newInteger(3))),
				parser.parseTerm("10*90^3"));
		assertEquals(
				provider.newStructure("+", provider.newInteger(10),
						provider.newStructure("^", provider.newInteger(90), provider.newInteger(3))),
				parser.parseTerm("10+90^3"));
		assertEquals(provider.newStructure("=\\=",
				provider.newStructure("-", provider.newInteger(10), provider.newInteger(90)), provider.newInteger(3)),
				parser.parseTerm("10-90=\\=3"));
		assertEquals(
				provider.newStructure("is", provider.newInteger(10),
						provider.newStructure("*", provider.newInteger(90), provider.newInteger(3))),
				parser.parseTerm("10 is 90*3"));
		assertEquals(provider.newStructure("+",
				provider.newStructure("/", provider.newInteger(10), provider.newInteger(90)), provider.newInteger(3)),
				parser.parseTerm("10/90+3"));

		assertEquals(provider.newStructure("+",
				provider.newStructure("-", provider.newDouble(3.14),
						provider.newStructure("/", provider.newInteger(10), provider.newInteger(90))),
				provider.newInteger(3)), parser.parseTerm("3.14-10/90+3"));

		assertEquals(provider.newStructure("+", provider.newStructure("-",
				provider.newStructure("-", provider.newDouble(3.14), provider.newInteger(10)), provider.newInteger(90)),
				provider.newInteger(3)), parser.parseTerm("3.14-10-90+3"));

		assertEquals(provider.newStructure("+", provider.newStructure("/",
				provider.newStructure("*", provider.newDouble(3.14), provider.newInteger(10)), provider.newInteger(90)),
				provider.newInteger(3)), parser.parseTerm("3.14*10/90+3"));

		assertEquals(
				provider.newStructure("-",
						provider.newStructure("*", provider.newDouble(3.14), provider.newInteger(10)),
						provider.newStructure("*", provider.newInteger(90), provider.newInteger(3))),
				parser.parseTerm("3.14*10-90*3"));

		assertEquals(
				provider.newStructure(",", provider.newAtom("a"),
						provider.newStructure(",", provider.newAtom("b"), provider.newAtom("c"))),
				parser.parseTerm("a,b,c"));
		assertEquals(
				provider.newStructure(";", provider.newAtom("a"),
						provider.newStructure(";", provider.newAtom("b"), provider.newAtom("c"))),
				parser.parseTerm("a;b;c"));
		assertEquals(provider.newStructure(";",
				provider.newStructure("->", provider.newAtom("if"), provider.newAtom("then")),
				provider.newAtom("else")), parser.parseTerm("if->then;else"));

		assertEquals(provider.newStructure(":-", provider.newAtom("a"), provider.newAtom("b")),
				parser.parseTerm("a:-b"));
		assertEquals(
				provider.newStructure(":-", provider.newAtom("a"),
						provider.newStructure(";", provider.newAtom("b"), provider.newAtom("c"))),
				parser.parseTerm("a:-b;c"));

		System.out.println(parser.parseTerm("a,b;c,d"));
		// IPrologTerm ab = provider.newPrologStructure(",",
		// provider.newPrologAtom("a"), provider.newPrologStructure("b"));
		// IPrologTerm cd = provider.newPrologStructure(",",
		// provider.newPrologAtom("c"), provider.newPrologStructure("d"));
		// assertEquals(provider.newPrologStructure(";", ab, cd),
		// parser.parseTerm("a,b;c,d"));

		assertEquals(provider.newList(new PrologTerm[] { zero, one, two, three, four, five, six, seven, eight, nine }),
				parser.parseTerm("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]"));
		assertEquals(provider.newList(provider.newAtom("a"), provider.newAtom("b")), parser.parseTerm("[a|b]"));
		assertEquals(provider.newList(new PrologTerm[] { provider.newAtom("a") }), parser.parseTerm("[a]"));

		// TODO RESOLVE INDEX FOR VARIABLES
		// assertEquals(provider.newPrologHTList(provider.newPrologVariable("H"),
		// provider.newPrologVariable("T")), parser.parseTerm("[H|T]"));

	}

	@Test
	public final void testTermParenthesis() {

		assertEquals(provider.prologCut(), parser.parseTerm("(!)"));
		assertEquals(provider.prologFail(), parser.parseTerm("(fail)"));
		assertEquals(provider.prologFalse(), parser.parseTerm("(false)"));
		assertEquals(provider.prologTrue(), parser.parseTerm("(true)"));
		assertEquals(provider.prologEmpty(), parser.parseTerm("([])"));

		assertEquals(provider.newVariable(), parser.parseTerm("(_)"));
		assertEquals(provider.newVariable("X"), parser.parseTerm("(X)"));

		assertEquals(provider.newInteger(), parser.parseTerm("(0)"));
		assertEquals(provider.newInteger(28), parser.parseTerm("(28)"));

		assertEquals(provider.newDouble(0.0), parser.parseTerm("(0.0)"));
		assertEquals(provider.newDouble(28.0), parser.parseTerm("(28.0)"));

		assertEquals(provider.newAtom("prolobjectlink"), parser.parseTerm("(prolobjectlink)"));
		assertEquals(provider.newAtom("'ProlobjectLink'"), parser.parseTerm("('ProlobjectLink')"));
		assertEquals(provider.newAtom("'Prolobject Link'"), parser.parseTerm("('Prolobject Link')"));

		assertEquals(provider.newStructure("digits", zero, one, two, three, four, five, six, seven, eight, nine),
				parser.parseTerm("(digits( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ))"));
		assertEquals(provider.newStructure("'Digits'", zero, one, two, three, four, five, six, seven, eight, nine),
				parser.parseTerm("('Digits'( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ))"));
		assertEquals(provider.newStructure("'digits from 0 to 9'", zero, one, two, three, four, five, six, seven, eight,
				nine), parser.parseTerm("('digits from 0 to 9'( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ))"));

		assertEquals(
				provider.newStructure(",", provider.newAtom("a"),
						provider.newStructure(",", provider.newAtom("b"), provider.newAtom("c"))),
				parser.parseTerm("(a,b,c)"));
		assertEquals(
				provider.newStructure(";", provider.newAtom("a"),
						provider.newStructure(";", provider.newAtom("b"), provider.newAtom("c"))),
				parser.parseTerm("(a;b;c)"));

		assertEquals(provider.newList(new PrologTerm[] { zero, one, two, three, four, five, six, seven, eight, nine }),
				parser.parseTerm("([0, 1, 2, 3, 4, 5, 6, 7, 8, 9])"));
		assertEquals(provider.newList(provider.newAtom("a"), provider.newAtom("b")), parser.parseTerm("([a|b])"));
		assertEquals(provider.newList(new PrologTerm[] { provider.newAtom("a") }), parser.parseTerm("([a])"));

		// TODO RESOLVE INDEX FOR VARIABLES
		// assertEquals(provider.newPrologList(provider.newPrologVariable("H"),
		// provider.newPrologVariable("T")), parser.parseTerm("([H|T])"));

	}

	@Test
	public final void testTermCurly() {

		assertEquals(provider.prologCut(), parser.parseTerm("{!}"));
		assertEquals(provider.prologFail(), parser.parseTerm("{fail}"));
		assertEquals(provider.prologFalse(), parser.parseTerm("{false}"));
		assertEquals(provider.prologTrue(), parser.parseTerm("{true}"));
		assertEquals(provider.prologEmpty(), parser.parseTerm("{[]}"));

		assertEquals(provider.newVariable(), parser.parseTerm("{_}"));
		assertEquals(provider.newVariable("X"), parser.parseTerm("{X}"));

		assertEquals(provider.newInteger(), parser.parseTerm("{0}"));
		assertEquals(provider.newInteger(28), parser.parseTerm("{28}"));

		assertEquals(provider.newDouble(0.0), parser.parseTerm("{0.0}"));
		assertEquals(provider.newDouble(28.0), parser.parseTerm("{28.0}"));

		assertEquals(provider.newAtom("prolobjectlink"), parser.parseTerm("{prolobjectlink}"));
		assertEquals(provider.newAtom("'ProlobjectLink'"), parser.parseTerm("{'ProlobjectLink'}"));
		assertEquals(provider.newAtom("'Prolobject Link'"), parser.parseTerm("{'Prolobject Link'}"));

		assertEquals(provider.newStructure("digits", zero, one, two, three, four, five, six, seven, eight, nine),
				parser.parseTerm("{digits( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 )}"));
		assertEquals(provider.newStructure("'Digits'", zero, one, two, three, four, five, six, seven, eight, nine),
				parser.parseTerm("{'Digits'( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 )}"));
		assertEquals(provider.newStructure("'digits from 0 to 9'", zero, one, two, three, four, five, six, seven, eight,
				nine), parser.parseTerm("{'digits from 0 to 9'( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 )}"));

		assertEquals(
				provider.newStructure(",", provider.newAtom("a"),
						provider.newStructure(",", provider.newAtom("b"), provider.newAtom("c"))),
				parser.parseTerm("{a,b,c}"));
		assertEquals(
				provider.newStructure(";", provider.newAtom("a"),
						provider.newStructure(";", provider.newAtom("b"), provider.newAtom("c"))),
				parser.parseTerm("{a;b;c}"));

		assertEquals(provider.newList(new PrologTerm[] { zero, one, two, three, four, five, six, seven, eight, nine }),
				parser.parseTerm("{[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]}"));
		assertEquals(provider.newList(provider.newAtom("a"), provider.newAtom("b")), parser.parseTerm("{[a|b]}"));
		assertEquals(provider.newList(new PrologTerm[] { provider.newAtom("a") }), parser.parseTerm("{[a]}"));

		// assertEquals(provider.newPrologList(provider.newPrologAtom("a")),
		// parser.parseTerm("{[a|b]}"));

		// TODO RESOLVE INDEX FOR VARIABLES
		// assertEquals(provider.newPrologList(zero, one, two, three, four,
		// five, six, seven, eight, nine), parser.parseTerm("{[H|T]}"));

	}

	@Test
	public final void testParseTerms() {

		PrologTerm employeeStructure = provider.newStructure(employee, name, dpto, scale);
		PrologTerm departmentStructure = provider.newStructure(department, dpto, dptoName);
		PrologTerm salaryStructure = provider.newStructure(salary, scale, money);

		PrologTerm expression = provider.newStructure(money, "<", provider.newInteger(2000));

		PrologTerm[] structures = new PrologTerm[] { employeeStructure, departmentStructure, salaryStructure,
				expression };
		assertArrayEquals(structures, provider.parsePrologTerms(
				"employee(Name,Dpto,Scale),department(Dpto,DepartmentName),salary(Scale,Money),Money < 2000"));

	}

	@Test
	@Ignore
	public final void testParseGoal() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testParseClause() {

		assertEquals(
				new ZPrologClause(provider.newStructure(grandparent, x, z), provider.newStructure(parent, x, y),
						provider.newStructure(parent, y, z)),
				parser.parseClause("grandparent( X, Z ) :- parent( X, Y ), parent( Y, Z )"));

		assertEquals(new ZPrologClause(provider.newStructure(parent, pam, bob)),
				parser.parseClause("parent( pam, bob )."));
		assertEquals(new ZPrologClause(provider.newStructure(parent, tom, bob)),
				parser.parseClause("parent( tom, bob )."));
		assertEquals(new ZPrologClause(provider.newStructure(parent, tom, liz)),
				parser.parseClause("parent( tom, liz )."));
		assertEquals(new ZPrologClause(provider.newStructure(parent, bob, ann)),
				parser.parseClause("parent( bob, ann )."));
		assertEquals(new ZPrologClause(provider.newStructure(parent, bob, pat)),
				parser.parseClause("parent( bob, pat )."));
		assertEquals(new ZPrologClause(provider.newStructure(parent, pat, jim)),
				parser.parseClause("parent( pat, jim )."));

	}

	@Test
	public final void testParseProgram() {

		PrologProgram f = parser.parseProgram("family.pl");
		assertFalse(f.isEmpty());
		assertEquals(21, f.size());

		PrologProgram c = parser.parseProgram("company.pl");
		assertFalse(c.isEmpty());
		assertEquals(21, c.size());

		PrologProgram z = parser.parseProgram("zoo.pl");
		assertFalse(z.isEmpty());
		assertEquals(8, z.size());

	}

}
