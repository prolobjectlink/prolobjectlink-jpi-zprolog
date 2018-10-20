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

import org.logicware.prolog.PrologOperator;
import org.logicware.prolog.PrologProvider;

public final class ZPrologOperator extends ZPrologToken implements PrologOperator {

	/** low priority limits */
	static final int LOW = 0x1;

	/** high priority limits */
	static final int HIGH = 0x4B0;

	/**
	 * fy prefix associative no
	 */
	static final int FY = 3;

	/**
	 * fx prefix associative yes
	 */
	static final int FX = 2;

	/**
	 * yfx infix associative left
	 */
	static final int YFX = 1;

	/**
	 * xfx infix associative no
	 */
	static final int XFX = 0;

	/**
	 * xfy infix associative right
	 */
	static final int XFY = -1;

	/**
	 * xf postfix associative no
	 */
	static final int XF = -2;

	/**
	 * yf postfix associative yes
	 */
	static final int YF = -3;

	// arithmetic operators
	static final int TOKEN_MINUS = 0x700;
	static final int TOKEN_PLUS = 0x701;
	static final int TOKEN_TIMES = 0x702;
	static final int TOKEN_DIV = 0x703;
	static final int TOKEN_MOD = 0x704;
	static final int TOKEN_REM = 0x705;
	static final int TOKEN_FLOAT_POW = 0x706;
	static final int TOKEN_INT_FLOAT_POW = 0x707;
	static final int TOKEN_BITWISE_AND = 0x708;
	static final int TOKEN_BITWISE_OR = 0x709;
	static final int TOKEN_BITWISE_SHIFT_LEFT = 0x70A;
	static final int TOKEN_BITWISE_SHIFT_RIGHT = 0x70B;
	static final int TOKEN_BITWISE_COMPLEMENT = 0x70C;
	static final int TOKEN_BITWISE_XOR = 0x70D;
	static final int TOKEN_INTEGER_DIV = 0x70E;

	// boolean operators
	static final int TOKEN_LESS = 0x800;
	static final int TOKEN_GREATER = 0x801;
	static final int TOKEN_LESS_EQUAL = 0x802;
	static final int TOKEN_GREATER_EQUAL = 0x803;
	static final int TOKEN_EQUAL = 0x804;
	static final int TOKEN_NOT_EQUAL = 0x805;
	static final int TOKEN_UNIFY = 0x806;
	static final int TOKEN_NOT_UNIFY = 0x807;
	static final int TOKEN_IS = 0x808;
	static final int TOKEN_BEFORE = 0x809;
	static final int TOKEN_AFTER = 0x810;
	static final int TOKEN_BEFORE_EQUALS = 0x811;
	static final int TOKEN_AFTER_EQUALS = 0x812;
	static final int TOKEN_EQUIVALENT = 0x813;
	static final int TOKEN_NOT_EQUIVALENT = 0x814;

	static final int TOKEN_UNIV = 0x900;
	static final int TOKEN_QUERY = 0x901;
	static final int TOKEN_NOT = 0x902;
	// static final int TOKEN_IF_THEN = 0x903;

	static final int TOKEN_USER_DEFINED = 0xA00;

	//
	static final ZPrologOperator QUERY = new ZPrologOperator(TOKEN_QUERY, "?-", 1200, FX);
	static final ZPrologOperator NECK = new ZPrologOperator(TOKEN_QUERY, ":-", 1200, XFX);
	static final ZPrologOperator SEMICOLON = new ZPrologOperator(TOKEN_SEMICOLON, ";", 1100, XFY);
	static final ZPrologOperator IF_THEN = new ZPrologOperator(TOKEN_IF_THEN, "->", 1050, XFY);
	static final ZPrologOperator COMMA = new ZPrologOperator(TOKEN_COMMA, ",", 1000, XFY);
	static final ZPrologOperator NOT = new ZPrologOperator(TOKEN_NOT, "\\+", 900, FY);

	// unifications
	static final ZPrologOperator IS = new ZPrologOperator(TOKEN_IS, "is", 700, XFX);
	static final ZPrologOperator UNIFY = new ZPrologOperator(TOKEN_UNIFY, "=", 700, XFX);
	static final ZPrologOperator NOT_UNIFY = new ZPrologOperator(TOKEN_NOT_UNIFY, "\\=", 700, XFX);

	// term comparison
	static final ZPrologOperator UNIV = new ZPrologOperator(TOKEN_UNIV, "=..", 700, XFX);
	static final ZPrologOperator EQUIVALENT = new ZPrologOperator(TOKEN_EQUIVALENT, "==", 700, XFX);
	static final ZPrologOperator NOT_EQUIVALENT = new ZPrologOperator(TOKEN_NOT_EQUIVALENT, "\\==", 700, XFX);
	static final ZPrologOperator BEFORE = new ZPrologOperator(TOKEN_BEFORE, "@<", 700, XFX);
	static final ZPrologOperator AFTER = new ZPrologOperator(TOKEN_AFTER, "@>", 700, XFX);
	static final ZPrologOperator BEFORE_EQUALS = new ZPrologOperator(TOKEN_BEFORE_EQUALS, "@=<", 700, XFX);
	static final ZPrologOperator AFTER_EQUALS = new ZPrologOperator(TOKEN_AFTER_EQUALS, "@>=", 700, XFX);

	// arithmetic comparison
	static final ZPrologOperator LESS = new ZPrologOperator(TOKEN_LESS, "<", 700, XFX);
	static final ZPrologOperator GREATER = new ZPrologOperator(TOKEN_GREATER, ">", 700, XFX);
	static final ZPrologOperator LESS_EQUAL = new ZPrologOperator(TOKEN_LESS_EQUAL, "=<", 700, XFX);
	static final ZPrologOperator GREATER_EQUAL = new ZPrologOperator(TOKEN_GREATER_EQUAL, ">=", 700, XFX);
	static final ZPrologOperator EQUAL = new ZPrologOperator(TOKEN_EQUAL, "=:=", 700, XFX);
	static final ZPrologOperator NOT_EQUAL = new ZPrologOperator(TOKEN_NOT_EQUAL, "=\\=", 700, XFX);

	// arithmetic evaluators
	static final ZPrologOperator MINUS = new ZPrologOperator(TOKEN_MINUS, "-", 500, YFX);
	static final ZPrologOperator PLUS = new ZPrologOperator(TOKEN_PLUS, "+", 500, YFX);
	static final ZPrologOperator BITWISE_AND = new ZPrologOperator(TOKEN_BITWISE_AND, "/\\", 500, YFX);
	static final ZPrologOperator BITWISE_XOR = new ZPrologOperator(TOKEN_BITWISE_XOR, "><", 500, YFX);
	static final ZPrologOperator BITWISE_OR = new ZPrologOperator(TOKEN_BITWISE_OR, "\\/", 500, YFX);
	static final ZPrologOperator TIMES = new ZPrologOperator(TOKEN_TIMES, "*", 400, YFX);
	static final ZPrologOperator DIV = new ZPrologOperator(TOKEN_DIV, "/", 400, YFX);
	static final ZPrologOperator INTEGER_DIV = new ZPrologOperator(TOKEN_INTEGER_DIV, "//", 400, YFX);
	static final ZPrologOperator MOD = new ZPrologOperator(TOKEN_MOD, "mod", 400, YFX);
	static final ZPrologOperator REM = new ZPrologOperator(TOKEN_REM, "rem", 400, YFX);
	static final ZPrologOperator BITWISE_SHIFT_LEFT = new ZPrologOperator(TOKEN_BITWISE_SHIFT_LEFT, "<<", 400, YFX);
	static final ZPrologOperator BITWISE_SHIFT_RIGHT = new ZPrologOperator(TOKEN_BITWISE_SHIFT_RIGHT, ">>", 400, YFX);
	static final ZPrologOperator BITWISE_COMPLEMENT = new ZPrologOperator(TOKEN_BITWISE_COMPLEMENT, "\\", 200, FY);
	static final ZPrologOperator FLOAT_POW = new ZPrologOperator(TOKEN_FLOAT_POW, "**", 200, XFX);
	static final ZPrologOperator INT_FLOAT_POW = new ZPrologOperator(TOKEN_INT_FLOAT_POW, "^", 200, XFY);

	protected static final PrologProvider provider = new ZPrologProvider();

	int priority;
	int position;

	ZPrologOperator() {
	}

	ZPrologOperator(int id, String value, int priority, int position) {
		super(id, value);
		this.priority = priority;
		this.position = position;
	}

	ZPrologOperator(int id, int line, int column, String value, int priority, int position) {
		super(id, line, column, value);
		this.priority = priority;
		this.position = position;
	}

	ZPrologOperator(int id, int line, int column, int length, String value, int priority, int position) {
		super(id, line, column, length, value);
		this.priority = priority;
		this.position = position;
	}

	ZPrologOperator(int id, String value, int priority, String specifier) {
		super(id, value);
		this.priority = priority;
		position = -1;
		if (specifier.equals("fx")) {
			position = FX;
		} else if (specifier.equals("fy")) {
			position = FY;
		} else if (specifier.equals("xfx")) {
			position = XFX;
		} else if (specifier.equals("xfy")) {
			position = XFY;
		} else if (specifier.equals("yfx")) {
			position = YFX;
		} else if (specifier.equals("xf")) {
			position = XF;
		} else if (specifier.equals("yf")) {
			position = YF;
		} else {
			// throw new PrologError(getClass(),
			// PrologError.ILLEGAL_OPERATOR_SPECIFIER, specifier);
		}
	}

	boolean isMinus() {
		return id == TOKEN_MINUS;
	}

	boolean isPlus() {
		return id == TOKEN_PLUS;
	}

	boolean isTimes() {
		return id == TOKEN_TIMES;
	}

	boolean isDivision() {
		return id == TOKEN_DIV;
	}

	boolean isQuery() {
		return id == TOKEN_QUERY;
	}

	boolean isUniv() {
		return id == TOKEN_UNIV;
	}

	final boolean isNot() {
		return id == TOKEN_NOT;
	}

	final boolean isIfThen() {
		return id == TOKEN_IF_THEN;
	}

	boolean isUserDefined() {
		return id == TOKEN_USER_DEFINED;
	}

	boolean isPrefix() {
		return isPrefixNonAssociative() || isPrefixRightAssociative();
	}

	boolean isPrefixRightAssociative() {
		return position == FY;
	}

	boolean isPrefixNonAssociative() {
		return position == FX;
	}

	boolean isPostfix() {
		return isPostfixNonAssociative() || isPostfixLeftAssociative();
	}

	boolean isPostfixLeftAssociative() {
		return position == YF;
	}

	boolean isPostfixNonAssociative() {
		return position == XF;
	}

	boolean isInfix() {
		return isInfixLeftAssociative() || isInfixNonAssociative() || isInfixRightAssociative();
	}

	boolean isInfixLeftAssociative() {
		return position == YFX;
	}

	boolean isInfixNonAssociative() {
		return position == XFX;
	}

	boolean isInfixRightAssociative() {
		return position == XFY;
	}

	boolean less(ZPrologOperator operator) {
		return this.compareTo(operator) < 0;
	}

	boolean lessEqual(ZPrologOperator operator) {
		return less(operator) || equal(operator);
	}

	boolean equal(ZPrologOperator operator) {
		return this.compareTo(operator) == 0;
	}

	boolean greaterEqual(ZPrologOperator operator) {
		return greater(operator) || equal(operator);
	}

	boolean greater(ZPrologOperator operator) {
		return this.compareTo(operator) > 0;
	}

	public int compareTo(PrologOperator operator) {
		if (operator != null) {
			if (priority > operator.getPriority()) {
				return 1;
			} else if (priority < operator.getPriority()) {
				return -1;
			}
		}
		return 0;
	}

	public int getPriority() {
		return priority;
	}

	public String getOperator() {
		return token;
	}

	public String getSpecifier() {
		String specifier = "";
		switch (position) {
		case FX:
			specifier = "fx";
			break;
		case FY:
			specifier = "fy";
			break;
		case XFX:
			specifier = "xfx";
			break;
		case XFY:
			specifier = "xfy";
			break;
		case YFX:
			specifier = "yfx";
			break;
		case XF:
			specifier = "xf";
			break;
		case YF:
			specifier = "yf";
			break;
		}
		return specifier;
	}

	@Override
	public String toString() {
		return "[token=" + token + ", priority=" + priority + ", position=" + position + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + position;
		result = prime * result + priority;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZPrologOperator other = (ZPrologOperator) obj;
		if (position != other.position)
			return false;
		return priority == other.priority;
	}

	boolean unify(ZPrologOperator op, ZPrologStack stack) {

		ZPrologTerm thisPriority = new ZPrologTerm(provider, getPriority());
		ZPrologTerm thisPosition = new ZPrologTerm(provider, getSpecifier());
		ZPrologTerm thisOperator = new ZPrologTerm(provider, getOperator());

		ZPrologTerm otherPriority = new ZPrologTerm(provider, op.getPriority());
		ZPrologTerm otherPosition = new ZPrologTerm(provider, op.getSpecifier());
		ZPrologTerm otherOperator = new ZPrologTerm(provider, op.getOperator());

		return

		(thisPriority.unify(otherPriority, stack))

				&&

				(thisPosition.unify(otherPosition, stack))

				&&

				(thisOperator.unify(otherOperator, stack));

	}

}
