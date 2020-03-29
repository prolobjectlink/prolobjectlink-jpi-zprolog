/*
 * #%L
 * prolobjectlink-jpi-zprolog
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package io.github.prolobjectlink.prolog.zprolog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.github.prolobjectlink.prolog.zprolog.ZPrologOperator;
import io.github.prolobjectlink.prolog.zprolog.ZPrologScanner;
import io.github.prolobjectlink.prolog.zprolog.ZPrologToken;

public class PrologScannerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetNextToken() throws IOException {

		// separators
		assertEquals(ZPrologToken.TOKEN_CUT, new ZPrologScanner(new StringReader("!")).getNextToken().id);
		assertEquals(ZPrologToken.TOKEN_DOT, new ZPrologScanner(new StringReader(".")).getNextToken().id);
		assertEquals(ZPrologToken.TOKEN_BAR, new ZPrologScanner(new StringReader("|")).getNextToken().id);
		assertEquals(ZPrologToken.TOKEN_LPAR, new ZPrologScanner(new StringReader("(")).getNextToken().id);
		assertEquals(ZPrologToken.TOKEN_RPAR, new ZPrologScanner(new StringReader(")")).getNextToken().id);
		assertEquals(ZPrologToken.TOKEN_LBRACKET, new ZPrologScanner(new StringReader("[")).getNextToken().id);
		assertEquals(ZPrologToken.TOKEN_RBRACKET, new ZPrologScanner(new StringReader("]")).getNextToken().id);
		assertEquals(ZPrologToken.TOKEN_LCURLY, new ZPrologScanner(new StringReader("{")).getNextToken().id);
		assertEquals(ZPrologToken.TOKEN_RCURLY, new ZPrologScanner(new StringReader("}")).getNextToken().id);

		// operators
		assertEquals(ZPrologOperator.TOKEN_QUERY, new ZPrologScanner(new StringReader("?-")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_NECK, new ZPrologScanner(new StringReader(":-")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_SEMICOLON, new ZPrologScanner(new StringReader(";")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_IF_THEN, new ZPrologScanner(new StringReader("->")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_COMMA, new ZPrologScanner(new StringReader(",")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_NOT, new ZPrologScanner(new StringReader("\\+")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_UNIFY, new ZPrologScanner(new StringReader("=")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_LESS, new ZPrologScanner(new StringReader("<")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_GREATER, new ZPrologScanner(new StringReader(">")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_IS, new ZPrologScanner(new StringReader("is")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_LESS_EQUAL, new ZPrologScanner(new StringReader("=<")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_GREATER_EQUAL, new ZPrologScanner(new StringReader(">=")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_EQUIVALENT, new ZPrologScanner(new StringReader("==")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_NOT_UNIFY, new ZPrologScanner(new StringReader("\\=")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_NOT_EQUIVALENT,
				new ZPrologScanner(new StringReader("\\==")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_EQUAL, new ZPrologScanner(new StringReader("=:=")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_NOT_EQUAL, new ZPrologScanner(new StringReader("=\\=")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_UNIV, new ZPrologScanner(new StringReader("=..")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_BEFORE, new ZPrologScanner(new StringReader("@<")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_AFTER, new ZPrologScanner(new StringReader("@>")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_BEFORE_EQUALS,
				new ZPrologScanner(new StringReader("@=<")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_AFTER_EQUALS, new ZPrologScanner(new StringReader("@>=")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_PLUS, new ZPrologScanner(new StringReader("+")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_MINUS, new ZPrologScanner(new StringReader("-")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_BITWISE_AND, new ZPrologScanner(new StringReader("/\\")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_BITWISE_OR, new ZPrologScanner(new StringReader("\\/")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_BITWISE_XOR, new ZPrologScanner(new StringReader("><")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_BITWISE_XOR, new ZPrologScanner(new StringReader("xor")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_TIMES, new ZPrologScanner(new StringReader("*")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_DIV, new ZPrologScanner(new StringReader("/")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_INTEGER_DIV, new ZPrologScanner(new StringReader("//")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_MOD, new ZPrologScanner(new StringReader("mod")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_REM, new ZPrologScanner(new StringReader("rem")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_BITWISE_SHIFT_RIGHT,
				new ZPrologScanner(new StringReader(">>")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_BITWISE_SHIFT_LEFT,
				new ZPrologScanner(new StringReader("<<")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_INT_FLOAT_POW, new ZPrologScanner(new StringReader("^")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_FLOAT_POW, new ZPrologScanner(new StringReader("**")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_BITWISE_COMPLEMENT,
				new ZPrologScanner(new StringReader("\\")).getNextToken().id);

		// operators priority
		assertEquals(1200, ((ZPrologOperator) new ZPrologScanner(new StringReader("?-")).getNextToken()).priority);
		assertEquals(1200, ((ZPrologOperator) new ZPrologScanner(new StringReader(":-")).getNextToken()).priority);
		assertEquals(1100, ((ZPrologOperator) new ZPrologScanner(new StringReader(";")).getNextToken()).priority);
		assertEquals(1050, ((ZPrologOperator) new ZPrologScanner(new StringReader("->")).getNextToken()).priority);
		assertEquals(1000, ((ZPrologOperator) new ZPrologScanner(new StringReader(",")).getNextToken()).priority);
		assertEquals(900, ((ZPrologOperator) new ZPrologScanner(new StringReader("\\+")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("=")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("<")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader(">")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("is")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("=<")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader(">=")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("==")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("\\=")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("\\==")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("=:=")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("=\\=")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("=..")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("@<")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("@>")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("@=<")).getNextToken()).priority);
		assertEquals(700, ((ZPrologOperator) new ZPrologScanner(new StringReader("@>=")).getNextToken()).priority);
		assertEquals(500, ((ZPrologOperator) new ZPrologScanner(new StringReader("+")).getNextToken()).priority);
		assertEquals(500, ((ZPrologOperator) new ZPrologScanner(new StringReader("-")).getNextToken()).priority);
		assertEquals(500, ((ZPrologOperator) new ZPrologScanner(new StringReader("/\\")).getNextToken()).priority);
		assertEquals(500, ((ZPrologOperator) new ZPrologScanner(new StringReader("\\/")).getNextToken()).priority);
		assertEquals(500, ((ZPrologOperator) new ZPrologScanner(new StringReader("><")).getNextToken()).priority);
		assertEquals(500, ((ZPrologOperator) new ZPrologScanner(new StringReader("xor")).getNextToken()).priority);
		assertEquals(400, ((ZPrologOperator) new ZPrologScanner(new StringReader("*")).getNextToken()).priority);
		assertEquals(400, ((ZPrologOperator) new ZPrologScanner(new StringReader("/")).getNextToken()).priority);
		assertEquals(400, ((ZPrologOperator) new ZPrologScanner(new StringReader("//")).getNextToken()).priority);
		assertEquals(400, ((ZPrologOperator) new ZPrologScanner(new StringReader("mod")).getNextToken()).priority);
		assertEquals(400, ((ZPrologOperator) new ZPrologScanner(new StringReader("rem")).getNextToken()).priority);
		assertEquals(400, ((ZPrologOperator) new ZPrologScanner(new StringReader(">>")).getNextToken()).priority);
		assertEquals(400, ((ZPrologOperator) new ZPrologScanner(new StringReader("<<")).getNextToken()).priority);
		assertEquals(200, ((ZPrologOperator) new ZPrologScanner(new StringReader("^")).getNextToken()).priority);
		assertEquals(200, ((ZPrologOperator) new ZPrologScanner(new StringReader("**")).getNextToken()).priority);
		assertEquals(200, ((ZPrologOperator) new ZPrologScanner(new StringReader("\\")).getNextToken()).priority);

		// operators position
		assertEquals(ZPrologOperator.FX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("?-")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader(":-")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFY,
				((ZPrologOperator) new ZPrologScanner(new StringReader(";")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFY,
				((ZPrologOperator) new ZPrologScanner(new StringReader("->")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFY,
				((ZPrologOperator) new ZPrologScanner(new StringReader(",")).getNextToken()).position);
		assertEquals(ZPrologOperator.FY,
				((ZPrologOperator) new ZPrologScanner(new StringReader("\\+")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("=")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("<")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader(">")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("is")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("=<")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader(">=")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("==")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("\\=")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("\\==")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("=:=")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("=\\=")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("=..")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("@<")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("@>")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("@=<")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("@>=")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("+")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("-")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("/\\")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("\\/")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("><")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("xor")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("*")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("/")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("//")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("mod")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("rem")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader(">>")).getNextToken()).position);
		assertEquals(ZPrologOperator.YFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("<<")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFY,
				((ZPrologOperator) new ZPrologScanner(new StringReader("^")).getNextToken()).position);
		assertEquals(ZPrologOperator.XFX,
				((ZPrologOperator) new ZPrologScanner(new StringReader("**")).getNextToken()).position);
		assertEquals(ZPrologOperator.FY,
				((ZPrologOperator) new ZPrologScanner(new StringReader("\\")).getNextToken()).position);

		// atoms
		assertEquals(ZPrologOperator.TOKEN_ATOM, new ZPrologScanner(new StringReader("@")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM, new ZPrologScanner(new StringReader("#")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM, new ZPrologScanner(new StringReader("?")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM, new ZPrologScanner(new StringReader("abc")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM, new ZPrologScanner(new StringReader("abc_")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM, new ZPrologScanner(new StringReader("a_b_c")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM, new ZPrologScanner(new StringReader("aBC")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM, new ZPrologScanner(new StringReader("'abc'")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM, new ZPrologScanner(new StringReader("'abc_'")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM, new ZPrologScanner(new StringReader("'a_b_c'")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM, new ZPrologScanner(new StringReader("'aBC'")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM,
				new ZPrologScanner(new StringReader("'COMPLEX ATOM'")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM,
				new ZPrologScanner(new StringReader("'COMPLEX . ATOM'")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ATOM,
				new ZPrologScanner(new StringReader("'COMPLEX 45464 ATOM'")).getNextToken().id);

		// integers
		assertEquals(ZPrologOperator.TOKEN_INTEGER, new ZPrologScanner(new StringReader("0")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_INTEGER, new ZPrologScanner(new StringReader("1")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_INTEGER,
				new ZPrologScanner(new StringReader("2147483647")).getNextToken().id);
		// assertEquals(ZPrologOperator.TOKEN_INTEGER, new
		// ZPrologScanner(new
		// StringReader("-2147483648")).getNextToken().id);

		// float
		assertEquals(ZPrologOperator.TOKEN_FLOAT, new ZPrologScanner(new StringReader("0.0")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_FLOAT, new ZPrologScanner(new StringReader("0.1")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_FLOAT, new ZPrologScanner(new StringReader("1.0")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_FLOAT, new ZPrologScanner(new StringReader("3.14")).getNextToken().id);
		// assertEquals(ZPrologOperator.TOKEN_FLOAT, new ZPrologScanner(new
		// StringReader("-0.1")).getNextToken().id);
		// assertEquals(ZPrologOperator.TOKEN_FLOAT, new ZPrologScanner(new
		// StringReader("-1.0")).getNextToken().id);
		// assertEquals(ZPrologOperator.TOKEN_FLOAT, new ZPrologScanner(new
		// StringReader("-3.14")).getNextToken().id);

		// variables
		assertEquals(ZPrologOperator.TOKEN_ANONIM, new ZPrologScanner(new StringReader("_")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_VARIABLE, new ZPrologScanner(new StringReader("_abc")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_VARIABLE, new ZPrologScanner(new StringReader("ABC")).getNextToken().id);

		// escape characters
		assertEquals(ZPrologOperator.TOKEN_SPACE, new ZPrologScanner(new StringReader(" ")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_WORD_BOUNDARY, new ZPrologScanner(new StringReader("\b")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_TAB, new ZPrologScanner(new StringReader("\t")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_FORM_FEED, new ZPrologScanner(new StringReader("\f")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_CARRIAGE_RETURN,
				new ZPrologScanner(new StringReader("\r")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_NEW_LINE, new ZPrologScanner(new StringReader("\n")).getNextToken().id);
		// assertEquals(ZPrologOperator.TOKEN_EOL, new ZPrologScanner(new
		// StringReader("\r|\n|\r\n")).getNextToken().id);

		assertTrue(new ZPrologScanner(new StringReader(" ")).getNextToken().isWhiteSpace());
		assertTrue(new ZPrologScanner(new StringReader("\b")).getNextToken().isWhiteSpace());
		assertTrue(new ZPrologScanner(new StringReader("\t")).getNextToken().isWhiteSpace());
		assertTrue(new ZPrologScanner(new StringReader("\f")).getNextToken().isWhiteSpace());
		assertTrue(new ZPrologScanner(new StringReader("\r")).getNextToken().isWhiteSpace());
		assertTrue(new ZPrologScanner(new StringReader("\n")).getNextToken().isWhiteSpace());

		// built-ins
		assertEquals(ZPrologToken.TOKEN_NIL, new ZPrologScanner(new StringReader("nil")).getNextToken().id);
		assertEquals(ZPrologToken.TOKEN_FAIL, new ZPrologScanner(new StringReader("fail")).getNextToken().id);
		assertEquals(ZPrologToken.TOKEN_TRUE, new ZPrologScanner(new StringReader("true")).getNextToken().id);
		assertEquals(ZPrologToken.TOKEN_FALSE, new ZPrologScanner(new StringReader("false")).getNextToken().id);

		// errors
		assertEquals(ZPrologOperator.TOKEN_ERROR,
				new ZPrologScanner(new StringReader("'COMPLEX 45464 \n ATOM'")).getNextToken().id);
		assertEquals(ZPrologOperator.TOKEN_ERROR,
				new ZPrologScanner(new StringReader("'COMPLEX 45464 \\N ATOM'")).getNextToken().id);

	}

}
