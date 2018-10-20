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

import static org.logicware.prolog.PrologTermType.ATOM_TYPE;
import static org.logicware.prolog.PrologTermType.CUT_TYPE;
import static org.logicware.prolog.PrologTermType.DOUBLE_TYPE;
import static org.logicware.prolog.PrologTermType.EMPTY_TYPE;
import static org.logicware.prolog.PrologTermType.FAIL_TYPE;
import static org.logicware.prolog.PrologTermType.FALSE_TYPE;
import static org.logicware.prolog.PrologTermType.FLOAT_TYPE;
import static org.logicware.prolog.PrologTermType.INTEGER_TYPE;
import static org.logicware.prolog.PrologTermType.LIST_TYPE;
import static org.logicware.prolog.PrologTermType.LONG_TYPE;
import static org.logicware.prolog.PrologTermType.NIL_TYPE;
import static org.logicware.prolog.PrologTermType.STRUCTURE_TYPE;
import static org.logicware.prolog.PrologTermType.TRUE_TYPE;
import static org.logicware.prolog.PrologTermType.VARIABLE_TYPE;
import static org.logicware.prolog.zprolog.ZPrologOperator.TOKEN_DIV;
import static org.logicware.prolog.zprolog.ZPrologOperator.TOKEN_MINUS;
import static org.logicware.prolog.zprolog.ZPrologOperator.TOKEN_PLUS;
import static org.logicware.prolog.zprolog.ZPrologOperator.TOKEN_REM;
import static org.logicware.prolog.zprolog.ZPrologOperator.TOKEN_TIMES;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.logicware.prolog.AbstractTerm;
import org.logicware.prolog.ArityError;
import org.logicware.prolog.CompoundExpectedError;
import org.logicware.prolog.FunctorError;
import org.logicware.prolog.IndicatorError;
import org.logicware.prolog.PrologAtom;
import org.logicware.prolog.PrologDouble;
import org.logicware.prolog.PrologFloat;
import org.logicware.prolog.PrologInteger;
import org.logicware.prolog.PrologList;
import org.logicware.prolog.PrologLong;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologStructure;
import org.logicware.prolog.PrologTerm;
import org.logicware.prolog.PrologVariable;

public class ZPrologTerm extends AbstractTerm implements PrologTerm, PrologAtom, PrologStructure, PrologList,
		PrologVariable, PrologInteger, PrologLong, PrologFloat, PrologDouble {

	static final int HEAD_POSITION = 0;
	static final int TAIL_POSITION = 1;
	static final int LEFT_POSITION = 0;
	static final int RIGHT_POSITION = 1;

	protected static final PrologProvider provider = new ZPrologProvider();

	/** Anonymous variable name */
	public static final String ANONYMOUS = "_";

	/**
	 * <pre>
	 * !/0, cut
	 * </pre>
	 * 
	 * <p>
	 * <tt>!</tt> is true. All choice points between the cut and the parent goal are
	 * removed. The effect is commit to use of both the current clause and the
	 * substitutions found at the point of the cut.
	 * </p>
	 * 
	 * @since 1.0
	 */
	static final ZPrologTerm CUT_TERM = new ZPrologTerm(ZPrologToken.TOKEN_CUT, CUT_TYPE, provider, ZPrologBuiltin.CUT);
	static final ZPrologTerm NIL_TERM = new ZPrologTerm(ZPrologToken.TOKEN_NIL, NIL_TYPE, provider, ZPrologBuiltin.NIL);

	/**
	 * <pre>
	 * true / 0
	 * </pre>
	 * 
	 * true is true.
	 * 
	 * @since 1.0
	 */
	static final ZPrologTerm TRUE_TERM = new ZPrologTerm(ZPrologToken.TOKEN_TRUE, TRUE_TYPE, provider,
			ZPrologBuiltin.TRUE_FUNCTOR);

	/**
	 * <pre>
	 * fail / 0
	 * </pre>
	 * 
	 * fail is false.
	 * 
	 * @since 1.0
	 */
	static final ZPrologTerm FAIL_TERM = new ZPrologTerm(ZPrologToken.TOKEN_FAIL, FAIL_TYPE, provider,
			ZPrologBuiltin.FAIL_FUNCTOR);
	static final ZPrologTerm EMPTY_TERM = new ZPrologTerm(ZPrologToken.TOKEN_EMPTY, EMPTY_TYPE, provider,
			ZPrologBuiltin.EMPTY_FUNCTOR);

	/**
	 * <pre>
	 * false / 0
	 * </pre>
	 * 
	 * false is false.
	 * 
	 * @since 1.0
	 */
	static final ZPrologTerm FALSE_TERM = new ZPrologTerm(ZPrologToken.TOKEN_FALSE, FALSE_TYPE, provider,
			ZPrologBuiltin.FALSE_FUNCTOR);

	static final String COMPLEX_ATOM_REGEX = "'.*.'";
	static final String VARIABLE_NAME_REGEX = "[A-Z_][A-Za-z0-9_]*";
	static final String SIMPLE_ATOM_REGEX = "\\.|\\?|#|[a-z][A-Za-z0-9_]*";

	/** term id */
	int id;

	/** atoms and structures name */
	String functor;

	/** argument number */
	int arity;

	/** arguments for compound terms */
	ZPrologTerm[] arguments;

	/** variable Name */
	String vName;

	/** variable instance value */
	ZPrologTerm vValue;

	/** variable index */
	int vIndex;

	/** numeric value */
	Number number;

	/** java object value */
	Object object;

	static final ZPrologParser parser = new ZPrologParser(provider);

	/**
	 * Create a shallow copy of the given term. Replace clone method.
	 * 
	 * @param term term to be cloned.
	 */
	ZPrologTerm(ZPrologTerm term) {
		super(term.type, term.getProvider());
		this.arguments = term.arguments;
		this.functor = term.functor;
		this.vIndex = term.vIndex;
		this.vValue = term.vValue;
		this.number = term.number;
		this.object = term.object;
		this.vName = term.vName;
		this.arity = term.arity;
		this.id = term.id;
	}

	/**
	 * Create a int number term.
	 * 
	 * @param value int value
	 * @since 1.0
	 */
	ZPrologTerm(PrologProvider provider, int value) {
		super(INTEGER_TYPE, provider);
		this.id = ZPrologToken.TOKEN_INTEGER;
		this.functor = "" + value + "";
		this.number = value;
	}

	/**
	 * Create a long number term.
	 * 
	 * @param value long value
	 * @since 1.0
	 */
	ZPrologTerm(PrologProvider provider, long value) {
		super(LONG_TYPE, provider);
		this.id = ZPrologToken.TOKEN_LONG;
		this.functor = "" + value + "";
		this.number = value;
	}

	/**
	 * Create a float number term.
	 * 
	 * @param value float value
	 * @since 1.0
	 */
	ZPrologTerm(PrologProvider provider, float value) {
		super(FLOAT_TYPE, provider);
		this.id = ZPrologToken.TOKEN_FLOAT;
		this.functor = "" + value + "";
		this.number = value;
	}

	/**
	 * Create a double number term.
	 * 
	 * @param value double value
	 * @since 1.0
	 */
	ZPrologTerm(PrologProvider provider, double value) {
		super(DOUBLE_TYPE, provider);
		this.id = ZPrologToken.TOKEN_DOUBLE;
		this.functor = "" + value + "";
		this.number = value;
	}

	/**
	 * Create an atom term
	 * 
	 * @param provider
	 * @param name
	 */
	ZPrologTerm(PrologProvider provider, String name) {
		super(ATOM_TYPE, provider);
		this.functor = name;
		this.id = ZPrologToken.TOKEN_ATOM;
		this.arguments = new ZPrologTerm[0];
	}

	/**
	 * Create a variable term with specified name and index to assigned to this
	 * variable.
	 * 
	 * @param provider
	 * @param name     variable name
	 * @param index    variable index
	 * @since 1.0
	 */
	ZPrologTerm(PrologProvider provider, String name, int index) {
		super(VARIABLE_TYPE, provider);
		this.id = ZPrologToken.TOKEN_VARIABLE;
		this.vIndex = index;
		this.vName = name;
	}

	/**
	 * Create a structure term with specified functor arguments. If arguments
	 * specifications are empty the created term will be an atom.
	 * 
	 * @param functor   prolog structure functor.
	 * @param arguments prolog structure arguments
	 * @since 1.0
	 */
	ZPrologTerm(PrologProvider provider, String functor, ZPrologTerm... arguments) {
		super(STRUCTURE_TYPE, provider);
		this.id = ZPrologToken.TOKEN_STRUCTURE;
		this.arity = arguments.length;
		this.arguments = arguments;
		this.functor = functor;
	}

	ZPrologTerm(PrologProvider provider, String functor, PrologTerm... arguments) {
		super(STRUCTURE_TYPE, provider);
		this.arguments = fromTermArray(arguments, ZPrologTerm[].class);
		this.id = ZPrologToken.TOKEN_STRUCTURE;
		this.arity = arguments.length;
		this.functor = functor;
	}

	/**
	 * Structure generic term constructor. Used in built-in creation
	 * 
	 * @param id
	 * @param provider
	 * @param functor
	 * @param zPrologTerm
	 */
	ZPrologTerm(int id, PrologProvider provider, String functor, ZPrologTerm... arguments) {
		super(STRUCTURE_TYPE, provider);
		this.arity = arguments.length;
		this.arguments = arguments;
		this.functor = functor;
		this.id = id;
	}

	/**
	 * Generic term constructor
	 * 
	 * @param id
	 * @param type
	 * @param functor
	 * @param arguments
	 * @since 1.0
	 */
	ZPrologTerm(int id, int type, PrologProvider provider, String functor, ZPrologTerm... arguments) {
		super(type, provider);
		this.arity = arguments.length;
		this.arguments = arguments;
		this.functor = functor;
		this.id = id;
	}

	/**
	 * Create a prolog expression
	 * 
	 * @deprecated use
	 *             {@code ZPrologTerm#ZPrologTerm(int, PrologProvider, ZPrologTerm, String, ZPrologTerm)}
	 * 
	 * @param left left operand
	 * @param tail right operand
	 * @since 1.0
	 */
	ZPrologTerm(PrologProvider provider, ZPrologTerm left, String op, ZPrologTerm right) {
		this(provider, op, new ZPrologTerm[] { left, right });
	}

	/**
	 * Create a prolog expression
	 * 
	 * @param left left operand
	 * @param tail right operand
	 * @since 1.0
	 */
	ZPrologTerm(PrologProvider provider, PrologTerm left, String op, PrologTerm right) {
		this(provider, op, new PrologTerm[] { left, right });
	}

	/**
	 * Create a prolog list term with specific head and tail
	 * 
	 * @param head list head
	 * @param tail list tail
	 * @since 1.0
	 */
	ZPrologTerm(PrologProvider provider, ZPrologTerm head, ZPrologTerm tail) {
		super(LIST_TYPE, provider);
		this.id = ZPrologToken.TOKEN_LIST;
		this.arity = ZPrologBuiltin.LIST_ARITY;
		this.functor = ZPrologBuiltin.LIST_FUNCTOR;
		this.arguments = new ZPrologTerm[this.arity];
		this.arguments[HEAD_POSITION] = head;
		this.arguments[TAIL_POSITION] = tail;
	}

	/**
	 * Create a prolog list term with specific head and tail
	 * 
	 * @param head list head
	 * @param tail list tail
	 * @since 1.0
	 */
	ZPrologTerm(PrologProvider provider, PrologTerm head, PrologTerm tail) {
		super(LIST_TYPE, provider);
		this.id = ZPrologToken.TOKEN_LIST;
		this.arity = ZPrologBuiltin.LIST_ARITY;
		this.functor = ZPrologBuiltin.LIST_FUNCTOR;
		this.arguments = new ZPrologTerm[this.arity];

		this.arguments[HEAD_POSITION] = fromTerm(head, ZPrologTerm.class);
		this.arguments[TAIL_POSITION] = fromTerm(tail, ZPrologTerm.class);
	}

	/**
	 * Create a prolog list term from arguments array
	 * 
	 * @param arguments arguments array
	 * @since 1.0
	 */
	ZPrologTerm(PrologProvider provider, ZPrologTerm... arguments) {
		this(provider, arguments,
				new ZPrologTerm(ZPrologToken.TOKEN_EMPTY, EMPTY_TYPE, provider, ZPrologBuiltin.EMPTY_FUNCTOR));
	}

	/**
	 * Create a prolog list term from arguments array
	 * 
	 * @param arguments arguments array
	 * @since 1.0
	 */
	ZPrologTerm(PrologProvider provider, PrologTerm... arguments) {
		this(provider, arguments,
				new ZPrologTerm(ZPrologToken.TOKEN_EMPTY, EMPTY_TYPE, provider, ZPrologBuiltin.EMPTY_FUNCTOR));
	}

	ZPrologTerm(PrologProvider provider, ZPrologTerm[] arguments, ZPrologTerm tail) {
		super(LIST_TYPE, provider);
		this.id = ZPrologToken.TOKEN_LIST;
		this.arity = ZPrologBuiltin.LIST_ARITY;
		this.functor = ZPrologBuiltin.LIST_FUNCTOR;
		this.arguments = new ZPrologTerm[ZPrologBuiltin.LIST_ARITY];

		ZPrologTerm argument = tail;
		for (int i = arguments.length - 1; i >= 0; --i) {
			argument = new ZPrologTerm(provider, arguments[i], argument);
		}

		this.arguments[HEAD_POSITION] = argument.arguments[HEAD_POSITION];
		this.arguments[TAIL_POSITION] = argument.arguments[TAIL_POSITION];

	}

	ZPrologTerm(PrologProvider provider, PrologTerm[] arguments, PrologTerm tail) {
		super(LIST_TYPE, provider);
		this.id = ZPrologToken.TOKEN_LIST;
		this.arity = ZPrologBuiltin.LIST_ARITY;
		this.functor = ZPrologBuiltin.LIST_FUNCTOR;
		this.arguments = new ZPrologTerm[ZPrologBuiltin.LIST_ARITY];

		PrologTerm argument = tail;
		for (int i = arguments.length - 1; i >= 0; --i) {
			argument = new ZPrologTerm(provider, arguments[i], argument);
		}

		this.arguments[HEAD_POSITION] = fromTerm(((PrologList) argument).getHead(), ZPrologTerm.class);
		this.arguments[TAIL_POSITION] = fromTerm(((PrologList) argument).getTail(), ZPrologTerm.class);

	}

	/**
	 * Create a prolog expression
	 * 
	 * @param id
	 * @param provider
	 * @param left     left operand
	 * @param tail     right operand
	 * @since 1.0
	 */
	ZPrologTerm(int id, PrologProvider provider, ZPrologTerm left, String operator, ZPrologTerm right) {
		this(id, provider, operator, new ZPrologTerm[] { left, right });
	}

	public static final PrologTerm parseTerm(String term) {
		return parser.parseTerm(term);
	}

	public final String getIndicator() {
		assertHasIndicator();
		if (isVariableBound()) {
			return vValue.dereference().getIndicator();
		}
		return functor + "/" + arity;
	}

	public final boolean hasIndicator(String functor, int arity) {
		assertHasIndicator();
		if (isVariableBound()) {
			return vValue.dereference().hasIndicator(functor, arity);
		}
		return this.functor.equals(functor) && this.arity == arity;
	}

	public final boolean isAtom() {
		return type >> 8 == 3 || type == EMPTY_TYPE;
	}

	public final boolean isNumber() {
		return type >> 8 == 2;
	}

	public final boolean isFloat() {
		return type == FLOAT_TYPE;
	}

	public final boolean isInteger() {
		return type == INTEGER_TYPE;
	}

	public final boolean isDouble() {
		return type == DOUBLE_TYPE;
	}

	public final boolean isLong() {
		return type == LONG_TYPE;
	}

	public final boolean isVariable() {
		return type == VARIABLE_TYPE;
	}

	public final boolean isList() {
		return type == LIST_TYPE || type == EMPTY_TYPE;
	}

	public final boolean isStructure() {
		return type == STRUCTURE_TYPE;
	}

	public final boolean isNil() {
		return type == NIL_TYPE;
	}

	public final boolean isEmptyList() {
		return type == EMPTY_TYPE;
	}

	public final boolean isAtomic() {
		return type >> 8 < 5;
	}

	public final boolean isCompound() {
		return type >> 8 > 4;
	}

	public final boolean isEvaluable() {
		return ZPrologParser.operators.containsKey(functor);
	}

	public final int getArity() {
		if (isNumber() || isVariable()) {
			throw new ArityError(this);
		}
		return arity;
	}

	public final String getFunctor() {
		if (isNumber() || isVariable()) {
			throw new FunctorError(this);
		}
		return functor;
	}

	public final PrologTerm[] getArguments() {
		int size = size();
		PrologTerm[] a = new PrologTerm[size];
		for (int i = 0; i < a.length; i++) {
			a[i] = getArgument(i);
		}
		return a;
	}

	public final PrologTerm getArgument(int index) {
		switch (type) {
		case STRUCTURE_TYPE:
			checkIndex(index, arity);
			return arguments[index];
		case LIST_TYPE:
			checkIndex(index);
			if (!isEmpty()) {
				int idx = 0;
				Iterator<PrologTerm> i = iterator();
				for (; i.hasNext() && idx <= index; idx++) {
					PrologTerm term = i.next();
					if (idx == index) {
						return term;
					}
				}
				checkIndex(index, idx);
			}
			return EMPTY_TERM;
		}
		throw new CompoundExpectedError(this);
	}

	public final boolean unify(PrologTerm term) {
		if (term instanceof ZPrologTerm) {
			ZPrologTerm prologTerm = (ZPrologTerm) term;
			ZPrologStack stack = new ZPrologStack();
			if (this.unify(prologTerm, stack)) {
				int size = stack.size();
				while (size > 0) {
					ZPrologTerm variable = (ZPrologTerm) stack.pop();
					variable.unbind();
					size--;
				}
				return true;
			}
		}
		return false;
	}

	public final int compareTo(PrologTerm term) {

		int termType = term.getType();

		if ((type >> 8) < (termType >> 8)) {
			return -1;
		} else if ((type >> 8) > (termType >> 8)) {
			return 1;
		}

		switch (type) {
		case ATOM_TYPE:

			// alphabetic functor comparison
			int result = functor.compareTo(term.getFunctor());
			if (result < 0) {
				return -1;
			} else if (result > 0) {
				return 1;
			}
			break;

		case FLOAT_TYPE: {

			float thisValue = getFloatValue();
			float otherValue = ((ZPrologTerm) term).getFloatValue();

			if (thisValue < otherValue) {
				return -1;
			} else if (thisValue > otherValue) {
				return 1;
			}

		}
			break;

		case LONG_TYPE: {

			long thisValue = getLongValue();
			long otherValue = ((ZPrologTerm) term).getLongValue();

			if (thisValue < otherValue) {
				return -1;
			} else if (thisValue > otherValue) {
				return 1;
			}

		}
			break;

		case DOUBLE_TYPE: {

			double thisValue = getDoubleValue();
			double otherValue = ((ZPrologTerm) term).getDoubleValue();

			if (thisValue < otherValue) {
				return -1;
			} else if (thisValue > otherValue) {
				return 1;
			}

		}
			break;

		case INTEGER_TYPE: {

			int thisValue = getIntValue();
			int otherValue = ((ZPrologTerm) term).getIntValue();

			if (thisValue < otherValue) {
				return -1;
			} else if (thisValue > otherValue) {
				return 1;
			}

		}
			break;

		case LIST_TYPE:
		case EMPTY_TYPE:
		case STRUCTURE_TYPE:

			ZPrologTerm thisCompound = this;
			ZPrologTerm otherCompound = (ZPrologTerm) term;

			// comparison by arity
			if (thisCompound.arity < otherCompound.arity) {
				return -1;
			} else if (thisCompound.arity > otherCompound.arity) {
				return 1;
			}

			// alphabetic functor comparison
			result = thisCompound.functor.compareTo(otherCompound.functor);
			if (result < 0) {
				return -1;
			} else if (result > 0) {
				return 1;
			}

			// arguments comparison
			PrologTerm[] thisArguments = thisCompound.arguments;
			PrologTerm[] otherArguments = otherCompound.arguments;

			for (int i = 0; i < thisArguments.length; i++) {
				PrologTerm thisArgument = thisArguments[i];
				PrologTerm otherArgument = otherArguments[i];
				if (thisArgument != null && otherArgument != null) {
					result = thisArgument.compareTo(otherArgument);
					if (result != 0) {
						return result;
					}
				}
			}
			break;

		case VARIABLE_TYPE:

			PrologTerm thisVariable = this;
			PrologTerm otherVariable = (PrologTerm) term;
			if (thisVariable.hashCode() < otherVariable.hashCode()) {
				return -1;
			} else if (thisVariable.hashCode() > otherVariable.hashCode()) {
				return 1;
			}
			break;

		}

		return 0;
	}

	public final Iterator<PrologTerm> iterator() {
		switch (type) {
		case STRUCTURE_TYPE:
			return new PrologStructureIterator();
		case LIST_TYPE:
			return new PrologListIterator(this);
		}
		throw new CompoundExpectedError(this);
	}

	public final PrologTerm getTerm() {
		if (isVariableBound()) {
			return dereference();
		}
		return this;
	}

	/**
	 * Return an evaluation of built-ins like +,-,*,/
	 * 
	 * @since 1.0
	 * @return built-in evaluation
	 */
	public final ZPrologTerm getValue() {
		// assertEvaluable();
		ZPrologTerm result = this;
		if (isStructure()) {

			switch (arity) {
			case 0:

			{

			}

				break;

			case 1:

			{

			}

				break;

			case 2:

			{
				ZPrologTerm left = ((ZPrologTerm) getLeft()).dereference();
				ZPrologTerm right = ((ZPrologTerm) getRight()).dereference();

				switch (id) {
				case TOKEN_PLUS:

				{

					// for integers, always return integers
					if (left.isInteger() && right.isInteger()) {

						long leftLong = left.getLongValue();
						long rightLong = right.getLongValue();
						long resultLong = leftLong + rightLong;
						result = new ZPrologTerm(provider, resultLong);

					} else {

						double leftDouble = left.getDoubleValue();
						double rightDouble = right.getDoubleValue();
						double resultDouble = leftDouble + rightDouble;
						result = new ZPrologTerm(provider, resultDouble);

					}

				}

					break;
				case TOKEN_MINUS:

				{

					// for integers, always return integers
					if (left.isInteger() && right.isInteger()) {

						long leftLong = left.getLongValue();
						long rightLong = right.getLongValue();
						long resultLong = leftLong - rightLong;
						result = new ZPrologTerm(provider, resultLong);

					} else {

						double leftDouble = left.getDoubleValue();
						double rightDouble = right.getDoubleValue();
						double resultDouble = leftDouble - rightDouble;
						result = new ZPrologTerm(provider, resultDouble);

					}

				}

					break;
				case TOKEN_TIMES:

				{

					// for integers, always return integers
					if (left.isInteger() && right.isInteger()) {

						long leftLong = left.getLongValue();
						long rightLong = right.getLongValue();
						long resultLong = leftLong * rightLong;
						result = new ZPrologTerm(provider, resultLong);

					} else {

						double leftDouble = left.getDoubleValue();
						double rightDouble = right.getDoubleValue();
						double resultDouble = leftDouble * rightDouble;
						result = new ZPrologTerm(provider, resultDouble);

					}

				}

					break;
				case TOKEN_DIV:

				{

					// always return doubles
					double leftDouble = left.getDoubleValue();
					double rightDouble = right.getDoubleValue();
					double resultDouble = leftDouble / rightDouble;
					result = new ZPrologTerm(provider, resultDouble);

				}

					break;
				case TOKEN_REM:

				{

					// always return integers
					if (left.isInteger() && right.isInteger()) {

						long leftLong = left.getLongValue();
						long rightLong = right.getLongValue();
						long resultLong = leftLong - (leftLong / rightLong) * rightLong;
						result = new ZPrologTerm(provider, resultLong);

					}

				}

					break;

				// another cases go here !!!

				}
			}

				break;
			}

		}
		return result;
	}

	public final int getIntValue() {
		return number.intValue();
	}

	public final long getLongValue() {
		return number.longValue();
	}

	public final float getFloatValue() {
		return number.floatValue();
	}

	public final double getDoubleValue() {
		return number.doubleValue();
	}

	public final PrologFloat getPrologFloat() {
		return new ZPrologTerm(provider, getFloatValue());
	}

	public final PrologInteger getPrologInteger() {
		return new ZPrologTerm(provider, getIntValue());
	}

	public final PrologDouble getPrologDouble() {
		return new ZPrologTerm(provider, getDoubleValue());
	}

	public final PrologLong getPrologLong() {
		return new ZPrologTerm(provider, getLongValue());
	}

	public final String getStringValue() {
		return functor;
	}

	public final void setStringValue(String value) {
		functor = value;
	}

	public final boolean isAnonymous() {
		return vName.equals(ANONYMOUS);
	}

	public final String getName() {
		return vName;
	}

	public final void setName(String name) {
		vName = name;
	}

	public final int size() {
		int counter = 0;
		switch (type) {
		case STRUCTURE_TYPE:
			counter = arity;
			break;
		case LIST_TYPE:
			if (!isEmpty()) {
				Iterator<PrologTerm> i;
				for (i = iterator(); i.hasNext();) {
					++counter;
					i.next();
				}
			}
			break;
		}
		return counter;
	}

	public final void clear() {
		arity = 0;
		type = EMPTY_TYPE;
		id = ZPrologToken.TOKEN_EMPTY;
		arguments = new ZPrologTerm[0];
		functor = ZPrologBuiltin.EMPTY_FUNCTOR;
	}

	public final boolean isEmpty() {
		return type == EMPTY_TYPE;
	}

	public final PrologTerm getHead() {
		return !isEmpty() ? arguments[0] : EMPTY_TERM;
	}

	public final PrologTerm getTail() {
		return !isEmpty() ? arguments[1] : EMPTY_TERM;
	}

	public final String getOperator() {
		return functor;
	}

	public int getPosition() {
		return vIndex;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(arguments);
		result = prime * result + arity;
		result = prime * result + ((functor == null) ? 0 : functor.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + type;
		result = prime * result + vIndex;
		result = prime * result + ((vName == null) ? 0 : vName.hashCode());
		result = prime * result + ((vValue == null) ? 0 : vValue.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZPrologTerm other = (ZPrologTerm) obj;
		if (!Arrays.equals(arguments, other.arguments))
			return false;
		if (arity != other.arity)
			return false;
		if (functor == null) {
			if (other.functor != null)
				return false;
		} else if (!functor.equals(other.functor))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (type != other.type)
			return false;
		// if (vIndex != other.vIndex)
		// return false;
		if (vName == null) {
			if (other.vName != null)
				return false;
		} else if (!vName.equals(other.vName))
			return false;
		if (vValue == null) {
			if (other.vValue != null)
				return false;
		} else if (!vValue.equals(other.vValue))
			return false;
		return true;
	}

	@Override
	public final String toString() {
		String string = "";
		switch (type) {
		case NIL_TYPE:
		case CUT_TYPE:
		case FALSE_TYPE:
		case TRUE_TYPE:
		case FAIL_TYPE:
		case EMPTY_TYPE:
		case ATOM_TYPE:
			string = functor;
			break;
		case LONG_TYPE:
			string = "" + number + "";
			break;
		case FLOAT_TYPE:
			string = "" + number + "";
			break;
		case DOUBLE_TYPE:
			string = "" + number + "";
			break;
		case INTEGER_TYPE:
			string = "" + number + "";
			break;
		case VARIABLE_TYPE:
			string = "" + (vValue != null ? vValue : !vName.equals(ANONYMOUS) ? vName : ANONYMOUS + vIndex) + "";
			break;

		case STRUCTURE_TYPE: {

			if (ZPrologParser.operators.containsKey(functor)) {

				string += arity == 1 ? functor + " " + arguments[0] : arguments[0] + " " + functor + " " + arguments[1];

			} else {

				string = functor;
				if (arity > 0) {
					string += "( ";
					for (int i = 0; i < arity; i++) {
						string += i < arity - 1 ? arguments[i] + ", " : arguments[i];
					}
					string += " )";
				}

			}
		}
			break;

		case LIST_TYPE: {

			string = "[";
			if (arguments[0] != null) {
				PrologList listPtr = this;
				string += listPtr.getHead();
				PrologTerm tail = listPtr.getTail();
				if (tail != null && tail.isList()) {
					while (tail != null && tail.isList() /*
															 * && !(tail instanceof IPrologEmpty)
															 */) {
						listPtr = (PrologList) tail;
						string += ", " + listPtr.getHead();
						tail = listPtr.getTail();
					}
				} else if (tail != null && !tail.isEmptyList()) {
					string += ", " + tail;
				}

			}
			string += "]";

		}

			break;

		}

		return string;
	}

	@Override
	public final PrologTerm clone() {
		PrologTerm term = null;
		switch (type) {
		case NIL_TYPE:
			term = CUT_TERM;
			break;
		case CUT_TYPE:
			term = CUT_TERM;
			break;
		case FAIL_TYPE:
			term = FAIL_TERM;
			break;
		case FALSE_TYPE:
			term = FALSE_TERM;
			break;
		case TRUE_TYPE:
			term = TRUE_TERM;
			break;
		case EMPTY_TYPE:
			term = EMPTY_TERM;
			break;
		case ATOM_TYPE:
			term = new ZPrologTerm(provider, functor);
			break;
		case LONG_TYPE:
			term = new ZPrologTerm(provider, getLongValue());
			break;
		case FLOAT_TYPE:
			term = new ZPrologTerm(provider, getFloatValue());
			break;
		case DOUBLE_TYPE:
			term = new ZPrologTerm(provider, getDoubleValue());
			break;
		case INTEGER_TYPE:
			term = new ZPrologTerm(provider, getIntValue());
			break;
		case VARIABLE_TYPE:
			term = new ZPrologTerm(provider, vName, vIndex);
			break;
		case STRUCTURE_TYPE:
			term = new ZPrologTerm(provider, functor, arguments);
			break;
		case LIST_TYPE:
			term = new ZPrologTerm(provider, arguments[0], arguments[1]);
			break;
		default:
			term = new ZPrologTerm(id, type, provider, functor, arguments);
			break;
		}

		return term;
	}

	boolean isBuiltin() {
		return ((id >> 8) == 0x3)

				|| ((id >> 8) == 0x4)

				|| ((id >> 8) == 0x7)

				|| ((id >> 8) == 0x8)

				|| ((id >> 8) == 0x9);
	}

	void setHead(ZPrologTerm head) {
		if (arguments[1] == null) {
			arguments[1] = EMPTY_TERM;
		}
		arguments[0] = head;
	}

	void setTail(ZPrologTerm tail) {
		if (arguments[0] == null) {
			arguments[0] = tail;
			arguments[1] = EMPTY_TERM;
			return;
		}
		arguments[1] = tail;
	}

	/**
	 * Unification is the basic primitive operation in logic programming. Check that
	 * two terms (x and y) unify. Prolog unification algorithm is based on three
	 * principals rules:
	 * <ul>
	 * <li>If x and y are atomics constants then x and y unify only if they are same
	 * object.</li>
	 * <li>If x is a variable and y is anything then they unify and x is
	 * instantiated to y. Conversely, if y is a variable then this is instantiated
	 * to x.</li>
	 * <li>If x and y are structured terms then unify only if they match (equals
	 * funtor and arity) and all their correspondents arguments unify.</li>
	 * </ul>
	 * 
	 * 
	 * @param term  the term to unify with the current term
	 * @param stack the stack is used to store the addresses of variables which are
	 *              bound by the unification. This is needed when backtracking.
	 * @return true if the specified term unify whit the current term, false if not
	 */
	boolean unify(ZPrologTerm term, ZPrologStack stack) {

		// PrologTerm thisTerm = this.dereference();
		// PrologTerm otherTerm = term.dereference();

		ZPrologTerm thisTerm = this;
		ZPrologTerm otherTerm = term;

		if (thisTerm.isVariableBound()) {
			return thisTerm.vValue.unify(otherTerm, stack);
		}

		else if (otherTerm.isVariableBound()) {
			return otherTerm.vValue.unify(thisTerm, stack);
		}

		// current term is a free variable
		else if (thisTerm.isVariableNotBound()) {
			// if (!thisTerm.occurs(otherTerm)) {
			thisTerm.bind(otherTerm);
			stack.push(thisTerm);
			return true;
			// }
		}

		// the other term is a free variable
		else if (otherTerm.isVariableNotBound()) {
			// if (!otherTerm.occurs(thisTerm)) {
			otherTerm.bind(thisTerm);
			stack.push(otherTerm);
			return true;
			// }
		}

		else {

			int thisArity = thisTerm.arity;
			int otherArity = otherTerm.arity;
			String thisFunctor = thisTerm.functor;
			String otherFunctor = otherTerm.functor;

			if (thisFunctor.equals(otherFunctor) && thisArity == otherArity) {
				for (int i = 0; i < thisArity; i++) {
					if (thisTerm.arguments[i] != null && otherTerm.arguments[i] != null) {
						if (!thisTerm.arguments[i].unify(otherTerm.arguments[i], stack)) {
							return false;
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * If the current term is variable check that your occurrence in other term
	 * passed as parameter. If the other term is compound and at less one argument
	 * match with the current variable term then return true indicating that the
	 * current variable term occurs in compound term. If the other term is compound
	 * and at less one argument is another compound term, then the current term
	 * check your occurrence in this compound term in recursive way.
	 * 
	 * @param otherTerm term to check if current term occurs inside him
	 * @return true if current term occurs in other compound term, false in another
	 *         case
	 */
	boolean occurs(PrologTerm otherTerm) {
		ZPrologTerm thisTerm = this;
		if (thisTerm.isVariable() && otherTerm.isCompound()) {
			PrologTerm[] otherArguments = otherTerm.getArguments();
                    for (PrologTerm argument : otherArguments) {
                        if (argument != null) {
                            if (argument.isVariable()) {
                                return thisTerm == argument;
                            } else if (argument.isCompound()) {
                                if (thisTerm.occurs(argument)) {
                                    return true;
                                }
                            }
                        }
                    }
		}
		return false;
	}

	ZPrologTerm refresh(ZPrologTerm[] variables) {
		if (isCompound()) {

			// create new compound term
			ZPrologTerm[] args = new ZPrologTerm[arity];
			ZPrologTerm compound = new ZPrologTerm(id, type, provider, functor, args);

			// refresh all arguments
			for (int i = 0; i < arguments.length; i++) {
				if (arguments[i] != null) {
					compound.arguments[i] = arguments[i].refresh(variables);
				}
			}
			return compound;
		}

		else if (isVariable()) {
			if (variables[vIndex] == null) {

				// declare and return new variable
				return variables[vIndex] = new ZPrologTerm(provider, ANONYMOUS, vIndex);
			}

			// return declared variable
			return variables[vIndex];
		}
		return this;
	}

	/**
	 * Check if Variable and bound. A variable bound is synonym of not free variable
	 * because this variable have instance value.
	 * 
	 * @return true if Variable and bound.
	 */
	boolean isVariableBound() {
		return isVariable() && vValue != null;
	}

	/**
	 * Check if current variable is not bound. A variable not bound is synonym of
	 * free variable because this variable don't have instance value.
	 * 
	 * @return true if Variable and not bound.
	 */
	boolean isVariableNotBound() {
		return isVariable() && vValue == null;
	}

	/** Binds a variable to a term */
	void bind(ZPrologTerm term) {
		if (this != term) {
			vValue = term;
		}
	}

	/** Unbinds a term reseting it to a variable */
	void unbind() {
		vValue = null;
	}

	ZPrologTerm dereference() {
		ZPrologTerm thisTerm = this;
		if (thisTerm.isVariableBound()) {
			return thisTerm.vValue.dereference();
		}
		return thisTerm;
	}

	/**
	 * Pull up to abstract term
	 */
	void assertHasIndicator() {
		if (!isCompound() && !isAtom() && !isEmptyList() && !isVariableBound()) {
			throw new IndicatorError(this);
		}
	}

	/**
	 * Iterator class implementation for Prolog list term
	 * 
	 * @author Jose Zalacain
	 * @since 1.0
	 */
	private class PrologListIterator implements Iterator<PrologTerm> {

		// next index to returned
		// protected int nextIndex;

		// check illegal state
		protected boolean canRemove;

		// iterator pointers
		protected ZPrologTerm prevPtr;
		protected ZPrologTerm listPtr;
		protected PrologTerm headPtr;
		protected ZPrologTerm tailPtr;

		PrologListIterator(ZPrologTerm list) {
			tailPtr = list;
		}

		public boolean hasNext() {
			return tailPtr != null && !tailPtr.isEmptyList();
		}

		public PrologTerm next() {

			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			// nextIndex++;
			canRemove = true;
			prevPtr = listPtr;
			if (tailPtr.isList()) {
				listPtr = (ZPrologTerm) tailPtr;
				headPtr = (PrologTerm) listPtr.getHead();
				tailPtr = (ZPrologTerm) listPtr.getTail();
			} else {
				headPtr = tailPtr;
				listPtr = null;
				tailPtr = null;
			}
			return headPtr;
		}

		public void remove() {

			if (!canRemove) {
				throw new IllegalStateException();
			}

			// nextIndex--;
			if (tailPtr != null) {

				// head = 1 tail = []
				if (tailPtr.isEmptyList()) {
					listPtr.clear();
					if (prevPtr != null) {
						// prevPtr = null;
						if (!prevPtr.isEmpty()) {
							prevPtr.setTail(EMPTY_TERM);
						}
					}
					// listPtr = null;
					headPtr = null;
					tailPtr = null;
				}

				// head = 1 tail = [2,...,N]
				else if (tailPtr.isList()) {
					ZPrologTerm tail = (ZPrologTerm) tailPtr;
					listPtr.setHead((ZPrologTerm) tail.getHead());
					listPtr.setTail((ZPrologTerm) tail.getTail());
					tailPtr = listPtr;
				}

				// head = 1 tail = 2
				else {
					listPtr.setHead(tailPtr);
					listPtr.setTail(EMPTY_TERM);
					tailPtr = listPtr;
				}

			} else {
				prevPtr.setTail(EMPTY_TERM);
			}

			canRemove = false;

		}

	}

	/**
	 * Iterator class for Prolog structure term
	 * 
	 * @author Jose Zalacain
	 * @since 1.0
	 */
	private class PrologStructureIterator implements Iterator<PrologTerm> {

		private int nextIndex;

		public boolean hasNext() {
			return nextIndex < arity;
		}

		public PrologTerm next() {
			return arguments[nextIndex++];
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
