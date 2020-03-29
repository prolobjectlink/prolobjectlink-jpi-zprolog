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

class ZPrologToken {

	static final int TOKEN_SPACE = 0x001;
	static final int TOKEN_TAB = 0x002;
	static final int TOKEN_WORD_BOUNDARY = 0x003;
	static final int TOKEN_FORM_FEED = 0x004;
	static final int TOKEN_CARRIAGE_RETURN = 0x005;
	static final int TOKEN_NEW_LINE = 0x006;
	static final int TOKEN_EOL = 0x007;

	static final int TOKEN_EOF = 0x100;
	static final int TOKEN_ERROR = 0x101;

	// delimiters
	static final int TOKEN_LPAR = 0x200;
	static final int TOKEN_RPAR = 0x201;
	static final int TOKEN_LBRACKET = 0x202;
	static final int TOKEN_RBRACKET = 0x203;
	static final int TOKEN_LCURLY = 0x204;
	static final int TOKEN_RCURLY = 0x205;

	// separators
	static final int TOKEN_DOT = 0x300;
	static final int TOKEN_NECK = 0x301;
	static final int TOKEN_COMMA = 0x302;
	static final int TOKEN_SEMICOLON = 0x303;
	static final int TOKEN_BAR = 0x304;
	static final int TOKEN_IF_THEN = 0x305;

	// built-ins predicates
	static final int TOKEN_FAIL = 0x400;
	static final int TOKEN_FALSE = 0x401;
	static final int TOKEN_TRUE = 0x402;
	static final int TOKEN_NIL = 0x403;
	static final int TOKEN_CUT = 0x404;

	// 8.14 Term input/output
	static final int TOKEN_READ = 0x405;
	static final int TOKEN_WRITE = 0x406;
	static final int TOKEN_NL = 0x407;

	// 8.9 clause creation and destruction
	static final int TOKEN_ASSERTA = 0x408;
	static final int TOKEN_ASSERTZ = 0x409;
	static final int TOKEN_RETRACT = 0x40A;
	static final int TOKEN_ABOLISH = 0x40B;

	//
	static final int TOKEN_VAR_BUILTIN = 0x40C;
	static final int TOKEN_ATOM_BUILTIN = 0x40D;
	static final int TOKEN_ATOMIC_BUILTIN = 0x40E;
	static final int TOKEN_INTEGER_BUILTIN = 0x40F;
	static final int TOKEN_FLOAT_BUILTIN = 0x410;
	static final int TOKEN_COMPOUND_BUILTIN = 0x411;
	static final int TOKEN_NUMBER_BUILTIN = 0x412;
	static final int TOKEN_NONVAR_BUILTIN = 0x413;

	//
	static final int TOKEN_CLAUSE_BUILTIN = 0x414;
	static final int TOKEN_ARG_BUILTIN = 0x415;
	static final int TOKEN_FUNCTOR_BUILTIN = 0x416;
	static final int TOKEN_COPY_TERM_BUILTIN = 0x417;
	static final int TOKEN_NOT_BUILTIN = 0x418;
	static final int TOKEN_THROW_BUILTIN = 0x419;
	static final int TOKEN_HALT_BUILTIN = 0x41A;
	static final int TOKEN_OP_BUILTIN = 0x41B;
	static final int TOKEN_CURRENT_OP_BUILTIN = 0x41C;
	static final int TOKEN_ABS_BUILTIN = 0x41D;
	static final int TOKEN_SIGN_BUILTIN = 0x41E;
	static final int TOKEN_FLOAT_INTEGER_PART_BUILTIN = 0x41F;
	static final int TOKEN_FLOAT_FRACTIONAL_PART_BUILTIN = 0x420;
	static final int TOKEN_FLOOR_BUILTIN = 0x421;
	static final int TOKEN_TRUNCATE_BUILTIN = 0x422;
	static final int TOKEN_ROUND_BUILTIN = 0x423;
	static final int TOKEN_CEILING_BUILTIN = 0x424;
	static final int TOKEN_SQRT_BUILTIN = 0x425;
	static final int TOKEN_SIN_BUILTIN = 0x426;
	static final int TOKEN_COS_BUILTIN = 0x427;
	static final int TOKEN_EXP_BUILTIN = 0x428;
	static final int TOKEN_LOG_BUILTIN = 0x429;
	static final int TOKEN_ATAN_BUILTIN = 0x42A;
	static final int TOKEN_SET_PROLOG_FLAG_BUILTIN = 0x42B;
	static final int TOKEN_CURRENT_PROLOG_FLAG_BUILTIN = 0x42C;
	static final int TOKEN_CHAR_CONVERSION_BUILTIN = 0x42D;
	static final int TOKEN_INITIALIZATION_BUILTIN = 0x42E;
	static final int TOKEN_ENSURE_LOADED_BUILTIN = 0x42F;
	static final int TOKEN_DISCONTIGUOUS_BUILTIN = 0x430;
	static final int TOKEN_MULTIFILE_BUILTIN = 0x431;
	static final int TOKEN_DYNAMIC_BUILTIN = 0x432;
	static final int TOKEN_INCLUDE_BUILTIN = 0x433;
	static final int TOKEN_ATOM_LENGTH_BUILTIN = 0x434;
	static final int TOKEN_ATOM_CONCAT_BUILTIN = 0x435;
	static final int TOKEN_ATOM_CHARS_BUILTIN = 0x436;
	static final int TOKEN_ATOM_CODES_BUILTIN = 0x437;
	static final int TOKEN_SUB_ATOM_BUILTIN = 0x438;
	static final int TOKEN_CURRENT_PREDICATE_BUILTIN = 0x439;

	// 8.2 term unification
	static final int TOKEN_UNIFY_WITH_OCCURS_CHECK_BUILTIN = 0x43A;

	// 8.10 All solutions
	static final int TOKEN_FINDALL_BUILTIN = 0x43B;
	static final int TOKEN_BAGOF_BUILTIN = 0x43C;
	static final int TOKEN_SETOF_BUILTIN = 0x43D;

	static final int TOKEN_CHAR_CODE_BUILTIN = 0x43E;
	static final int TOKEN_NUMBER_CODES_BUILTIN = 0x43F;
	static final int TOKEN_NUMBER_CHARS_BUILTIN = 0x440;
	static final int TOKEN_EMPTY = 0x441;

	// 8.15 logic and control
	static final int TOKEN_CALL = 0x442;
	static final int TOKEN_ONCE = 0x443;
	static final int TOKEN_REPEAT = 0x444;

	// 8.4 term comparison
	static final int TOKEN_COMPARE = 0x445;
	static final int TOKEN_SORT = 0x446;

	static final int TOKEN_TERM_VARIABLES = 0x447;
	static final int TOKEN_CURRENT_CHAR_CONVERSION = 0x448;

	// TYPE CHEKC
	static final int TOKEN_OBJECT = 0x449;
	static final int TOKEN_GROUND = 0x44A;
	static final int TOKEN_CALLABLE = 0x44B;
	static final int TOKEN_CYCLIC_TERM = 0x44C;
	static final int TOKEN_ACYCLIC_TERM = 0x44D;

	// EVALUABLE
	static final int TOKEN_E = 0x44E;
	static final int TOKEN_PI = 0x44F;
	static final int TOKEN_MAX = 0x450;
	static final int TOKEN_MIN = 0x451;
	static final int TOKEN_GCD = 0x452;
	static final int TOKEN_LCM = 0x453;
	static final int TOKEN_TAN = 0x454;
	static final int TOKEN_ASIN = 0x455;
	static final int TOKEN_ACOS = 0x456;
	static final int TOKEN_CBRT = 0x457;
	static final int TOKEN_EPSILON = 0x458;

	// runtime statistics
	static final int TOKEN_STATISTICS = 0x459;
	static final int TOKEN_CURRENT_TIME = 0x45A;

	// java foreign language
	static final int TOKEN_GET = 0x45B;
	static final int TOKEN_SET = 0x45C;
	static final int TOKEN_CAST = 0x45D;
	static final int TOKEN_INVOKE = 0x45E;
	static final int TOKEN_INSTANCE_OF = 0x45F;
	static final int TOKEN_NEW_INSTANCE = 0x460;
	static final int TOKEN_LOAD_LIBRARY = 0x461;
	static final int TOKEN_OBJECT_CONVERSION = 0x462;

	// java runtime reflection
	static final int TOKEN_CLASS_OF = 0x463;
	static final int TOKEN_FIELDS_OF = 0x464;
	static final int TOKEN_METHODS_OF = 0x465;
	static final int TOKEN_SUPER_CLASS_OF = 0x466;
	static final int TOKEN_CONSTRUCTORS_OF = 0x467;

	// 8.11 Stream Selection and Control
	static final int TOKEN_OPEN = 0x468;
	static final int TOKEN_CLOSE = 0x469;
	static final int TOKEN_SET_INPUT = 0x46A;
	static final int TOKEN_SET_OUTPUT = 0x46B;
	static final int TOKEN_CURRENT_INPUT = 0x46C;
	static final int TOKEN_CURRENT_OUTPUT = 0x46D;

	// data objects
	static final int TOKEN_INTEGER = 0x500;
	static final int TOKEN_FLOAT = 0x501;
	static final int TOKEN_ATOM = 0x502;
	static final int TOKEN_STRING = 0x503;
	static final int TOKEN_VARIABLE = 0x504;
	static final int TOKEN_ANONIM = 0x505;
	static final int TOKEN_LONG = 0x506;
	static final int TOKEN_DOUBLE = 0x507;
	static final int TOKEN_STRUCTURE = 0x508;
	static final int TOKEN_LIST = 0x509;

	protected int id;
	protected int line;
	protected int column;
	protected int length;
	protected String token;

	ZPrologToken() {
	}

	ZPrologToken(int id, String value) {
		this.id = id;
		this.token = value;
	}

	ZPrologToken(int id, int line, int column) {
		this.id = id;
		this.line = line;
		this.column = column;
	}

	ZPrologToken(int id, int line, int column, int length, String value) {
		this.id = id;
		this.line = line;
		this.column = column;
		this.length = length;
		this.token = value;
	}

	ZPrologToken(int id, int line, int column, String value) {
		this(id, line, column);
		this.token = value;
	}

	ZPrologToken(int id, int line, int column, double value) {
		this(id, line, column);
		this.token = String.valueOf(value);
	}

	ZPrologToken(int id, int line, int column, int value) {
		this(id, line, column);
		this.token = String.valueOf(value);
	}

	final boolean isEndOfFile() {
		return id == TOKEN_EOF;
	}

	final boolean isError() {
		return id == TOKEN_ERROR;
	}

	final boolean isDot() {
		return id == TOKEN_DOT;
	}

	final boolean isNeck() {
		return id == TOKEN_NECK;
	}

	final boolean isSemicolon() {
		return id == TOKEN_SEMICOLON;
	}

	final boolean isComma() {
		return id == TOKEN_COMMA;
	}

	final boolean isCut() {
		return id == TOKEN_CUT;
	}

	final boolean isAnonymous() {
		return id == TOKEN_ANONIM;
	}

	final boolean isAtom() {
		return id == TOKEN_ATOM;
	}

	final boolean isInteger() {
		return id == TOKEN_INTEGER;
	}

	final boolean isFloat() {
		return id == TOKEN_FLOAT;
	}

	final boolean isString() {
		return id == TOKEN_STRING;
	}

	final boolean isVariable() {
		return id == TOKEN_VARIABLE;
	}

	final boolean isLeftParenthesis() {
		return id == TOKEN_LPAR;
	}

	final boolean isRightParenthesis() {
		return id == TOKEN_RPAR;
	}

	final boolean isLeftBracket() {
		return id == TOKEN_LBRACKET;
	}

	final boolean isRightBracket() {
		return id == TOKEN_RBRACKET;
	}

	final boolean isLeftCurly() {
		return id == TOKEN_LCURLY;
	}

	final boolean isRightCurly() {
		return id == TOKEN_RCURLY;
	}

	final boolean isBar() {
		return id == TOKEN_BAR;
	}

	public boolean isIf() {
		return id == TOKEN_IF_THEN;
	}

	final boolean isWhiteSpace() {
		return (id >> 8) == 0x0;
	}

	final boolean isBuiltin() {
		return (id >> 8) == 0x4;
	}

	final boolean isOperator() {
		return this instanceof ZPrologOperator;
	}

	final boolean isOperator(boolean commaIsEndMarker) {
		if (isComma() && commaIsEndMarker) {
			return false;
		}
		return isOperator();
	}

	final String getToken() {
		return token;
	}

	final int getLine() {
		return line;
	}

	final int getColumn() {
		return column;
	}

	final int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "PrologToken [id=" + id + ", line=" + line + ", column=" + column + ", value=" + token + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZPrologToken other = (ZPrologToken) obj;
		if (id != other.id)
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token)) {
			return false;
		}
		return true;
	}

}
