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

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static org.logicware.prolog.PrologTermType.EMPTY_TYPE;
import static org.logicware.prolog.wamkel.ZPrologOperator.HIGH;
import static org.logicware.prolog.wamkel.ZPrologOperator.LOW;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.logicware.prolog.PrologClause;
import org.logicware.prolog.PrologGoal;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;
import org.logicware.prolog.SyntaxError;

final class ZPrologParser extends ZPrologEngine {

	private ZPrologToken current;
	private ZPrologScanner scanner;

	//
	private int varIndex;

	//
	private HashMap<String, ZPrologTerm> variables;

	ZPrologParser(PrologProvider provider) {
		super(provider);
		varIndex = 0;
		variables = new HashMap<String, ZPrologTerm>();
	}

	/**
	 * 
	 * 
	 * @param term
	 * @return
	 * @since 1.0
	 */
	ZPrologTerm parseTerm(String term) {
		return parseTerm(new StringReader(term));
	}

	/**
	 * 
	 * @param termsString
	 * @return
	 * @since 1.0
	 */
	PrologTerm[] parseTerms(String termsString) {
		return parseTerms(new StringReader(termsString));
	}

	ZPrologGoal parseGoal(String goal) {
		return parseGoal(new StringReader(goal));
	}

	ZPrologClause parseClause(String clause) {
		return parseClause(new StringReader(clause));
	}

	ZPrologProgram parseProgram(String file) {
		return parseProgram(new File(file));
	}

	ZPrologProgram parseProgram(File in) {
		try {
			return parseProgram(new FileReader(in));
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		}
		return new ZPrologProgram();
	}

	/**
	 * term ::= expr(1200)
	 * 
	 * @param term
	 * @return
	 * @since 1.0
	 */
	private ZPrologTerm parseTerm(Reader in) {
		reset();
		scanner = new ZPrologScanner(in);
		advance();
		return expr(HIGH, false);
	}

	/**
	 * terms(n) ::= exprs(1200)
	 * 
	 * @return
	 */
	private PrologTerm[] parseTerms(Reader in) {
		reset();
		scanner = new ZPrologScanner(in);
		advance();
		return exprs(HIGH);
	}

	private ZPrologGoal parseGoal(Reader in) {
		reset();
		scanner = new ZPrologScanner(in);
		advance();
		PrologTerm term = expr(HIGH, false);
		return new ZPrologGoal(term);
	}

	private ZPrologClause parseClause(Reader in) {
		reset();
		scanner = new ZPrologScanner(in);
		advance();
		PrologTerm term = expr(HIGH, false);
		if (term.hasIndicator(":-", 2)) {
			PrologTerm h = term.getArguments()[0];
			PrologTerm b = term.getArguments()[1];
			return new ZPrologClause(h, b);
		}
		return new ZPrologClause(term);
	}

	/**
	 * 
	 * @param in
	 * @return
	 * @since 1.0
	 */
	private ZPrologProgram parseProgram(Reader in) {
		reset();
		scanner = new ZPrologScanner(in);
		advance();
		ZPrologProgram p = new ZPrologProgram();
		while (!current.isEndOfFile()) {
			PrologTerm term = expr(HIGH, false);
			if (term != null) {
				PrologClause clause = new ZPrologClause(term);
				if (term.hasIndicator(":-", 2)) {
					PrologTerm h = term.getArguments()[0];
					PrologTerm b = term.getArguments()[1];
					clause = new ZPrologClause(h, b);
				}
				if (clause.isDirective()) {
					PrologGoal d = new ZPrologGoal(clause.getBody());
					p.addDirective(d);
				} else {
					p.add(clause);
				}
				while (current.isDot()) {
					advance();
					term = expr(HIGH, false);
					if (term != null) {
						clause = new ZPrologClause(term);
						if (term.hasIndicator(":-", 2)) {
							PrologTerm h = term.getArguments()[0];
							PrologTerm b = term.getArguments()[1];
							clause = new ZPrologClause(h, b);
						}
						if (clause.isDirective()) {
							PrologGoal d = new ZPrologGoal(clause.getBody());
							p.addDirective(d);
						} else {
							p.add(clause);
						}
					}
				}
			}
		}
		return p;
	}

	private ZPrologTerm expr(int maxPriority, boolean commaIsEndMarker) {

		int minPriority = 0;
		ZPrologTerm term = exprC(maxPriority, commaIsEndMarker);

		while (current.isOperator(commaIsEndMarker)) {

			String operator = current.token;

			int XFX = op(operator, ZPrologOperator.XFX);
			int XFY = op(operator, ZPrologOperator.XFY);
			int XF = op(operator, ZPrologOperator.XF);
			int YFX = op(operator, ZPrologOperator.YFX);
			int YF = op(operator, ZPrologOperator.YF);

			// check that no operator has a priority higher than permitted
			// or a lower priority than the left side expression
			if (XFX > maxPriority || XFX < LOW)
				XFX = -1;
			if (XFY > maxPriority || XFY < LOW)
				XFY = -1;
			if (XF > maxPriority || XF < LOW)
				XF = -1;
			if (YF < minPriority || YF > maxPriority)
				YF = -1;
			if (YFX < minPriority || YFX > maxPriority)
				YFX = -1;

			int id = ((ZPrologOperator) operators.get(operator)).id;

			// XFX has priority
			if (XFX >= XFY && XFX >= XF && XFX >= minPriority) {
				advance();
				ZPrologTerm found = expr(XFX - 1, commaIsEndMarker);
				if (found != null) {
					minPriority = XFX;
					term = new ZPrologTerm(id, provider, term, operator, found);
					continue;
				}
			}

			// XFY has priority or XFX has failed
			else if (XFY >= XF && XFY >= minPriority) {
				advance();
				ZPrologTerm found = expr(XFY, commaIsEndMarker);
				if (found != null) {
					minPriority = XFY;
					term = new ZPrologTerm(id, provider, term, operator, found);
					continue;
				}
			}

			// XF has priority or XFX and/or XFY has failed
			else if (XF >= minPriority) {
				advance();
				term = new ZPrologTerm(id, provider, operator, term);
			}

			// XFX did not have top priority, but XFY failed
			else if (XFX >= minPriority) {
				advance();
				ZPrologTerm found = expr(XFX - 1, commaIsEndMarker);
				if (found != null) {
					minPriority = XFX;
					term = new ZPrologTerm(id, provider, term, operator, found);
					continue;
				}
			}

			// YFX
			else if (YFX >= YF && YFX >= LOW) {
				advance();
				ZPrologTerm found = expr(YFX - 1, commaIsEndMarker);
				if (found != null) {
					minPriority = YFX;
					term = new ZPrologTerm(id, provider, term, operator, found);
					continue;
				}
			}

			// either YF has priority over YFX or YFX failed
			else if (YF >= LOW) {
				advance();
				minPriority = YF;
				term = new ZPrologTerm(id, provider, operator, term);
				continue;
			}

			break;

		}

		return term;
	}

	/**
	 * exprs(n) ::= expr(n) { ',' expr(n) }*
	 * 
	 * @return
	 */
	private ZPrologTerm[] exprs(int maxPriority) {
		List<ZPrologTerm> l = new ArrayList<ZPrologTerm>();
		l.add(expr(maxPriority, true));
		while (current.isComma()) {
			advance();
			l.add(expr(maxPriority, true));
		}
		return l.toArray(new ZPrologTerm[0]);
	}

	private ZPrologTerm exprA(int maxPriority) {
		ZPrologTerm term = null;

		// exprA(0) ::= anonymous
		if (current.isAnonymous()) {
			String name = ZPrologTerm.ANONYMOUS;
			term = new ZPrologTerm(provider, name, varIndex++);
			MAX_VAR_NUM = Math.max(varIndex, MAX_VAR_NUM);
			advance();
		}

		// exprA(0) ::= variable
		else if (current.isVariable()) {
			term = variables.get(current.getToken());
			if (term == null) {
				term = new ZPrologTerm(provider, current.getToken(), varIndex++);
				MAX_VAR_NUM = Math.max(varIndex, MAX_VAR_NUM);
				variables.put(current.getToken(), term);
			}
			advance();
		}

		// exprA(0) ::= integer | long
		else if (current.isInteger()) {
			BigInteger number = new BigInteger(current.getToken());
			if (number.longValue() > MIN_VALUE && number.longValue() < MAX_VALUE) {
				term = new ZPrologTerm(provider, number.intValue());
			} else {
				term = new ZPrologTerm(provider, number.longValue());
			}
			advance();
		}

		// exprA(0) ::= float | double
		else if (current.isFloat()) {
			double number = Double.parseDouble(current.getToken());
			term = new ZPrologTerm(provider, number);
			advance();
		}

		// exprA(0) ::= atom | atom( exprs(1200) )
		else if (current.isAtom()) {
			String functor = current.getToken();
			advance();
			if (current.isLeftParenthesis()) {
				advance();
				ZPrologTerm[] arguments = exprs(HIGH);
				if (current.isRightParenthesis()) {
					advance();
					term = new ZPrologTerm(provider, functor, arguments);
				}
			} else {
				term = new ZPrologTerm(provider, functor);
			}
		}

		// exprA(0) ::= [] | [ exprs(1200) ] | [ exprs(1200) | expr(1200) ]
		else if (current.isLeftBracket()) {
			advance();
			if (current.isRightBracket()) {
				term = new ZPrologTerm(ZPrologToken.TOKEN_EMPTY, EMPTY_TYPE, provider, ZPrologBuiltin.EMPTY_FUNCTOR);
				advance();
			} else {
				ZPrologTerm[] arguments = exprs(HIGH);
				if (current.isBar()) {
					advance();
					ZPrologTerm tail = expr(HIGH, true);
					if (current.isRightBracket()) {
						term = new ZPrologTerm(provider, arguments, tail);
					} else {
						throw new SyntaxError("Expected prolog token ']'");
					}
				} else if (current.isRightBracket()) {
					term = new ZPrologTerm(provider, arguments);
				} else {
					throw new SyntaxError("Expected prolog token ']'");
				}
				advance();
			}
		}

		// exprA(0) ::= ( expr(1200) )
		else if (current.isLeftParenthesis()) {
			advance();
			term = expr(HIGH, false);
			if (current.isRightParenthesis()) {
				// term.encloseParenthesis();
				advance();
			} else {
				throw new SyntaxError("Expected prolog token ')'");
			}
		}

		// exprA(0) ::= { expr(1200) }
		else if (current.isLeftCurly()) {
			advance();
			if (current.isRightCurly()) {
				advance();
				term = new ZPrologTerm(provider, "{}");
			}
			term = expr(HIGH, false);
			if (current.isRightCurly()) {
				// term.encloseCurly();
				advance();
			} else {
				throw new SyntaxError("Expected prolog token '}'");
			}
		}

		else if (current.isBuiltin()) {

			String functor = current.getToken();
			advance();
			if (current.isLeftParenthesis()) {
				advance();
				ZPrologTerm[] arguments = exprs(HIGH);
				if (current.isRightParenthesis()) {
					advance();
					String key = functor + "/" + arguments.length;
					PrologClause c = builtins.get(key).get(0);
					if (c != null && c instanceof ZPrologTerm) {
						term = new ZPrologTerm((ZPrologTerm) c.getTerm());
						term.arguments = arguments;
					} else {
						// structure with name of an built-in
						term = new ZPrologTerm(provider, functor, arguments);
					}
				}
			} else {
				String key = functor + "/" + 0;
				PrologClause c = builtins.get(key).get(0);
				if (c != null && c instanceof ZPrologTerm) {
					term = new ZPrologTerm((ZPrologTerm) c.getTerm());
				} else {
					// atom with name of an built-in
					term = new ZPrologTerm(provider, functor);
				}
			}

		}

		// prolog term formed
		return term;

	}

	private ZPrologTerm exprC(int maxPriority, boolean commaIsEndMarker) {

		if (current.isOperator() && !current.isAtom()) {

			ZPrologOperator operator = (ZPrologOperator) current;

			int FX = op(current.token, ZPrologOperator.FX);
			int FY = op(current.token, ZPrologOperator.FY);

			// FIXME unary operators like - and +
			// exprC(n) ::= '-' integer | '-' float
			if (operator.isMinus()) {
				advance();

				// exprC(n) ::= '-' integer
				if (current.isInteger()) {
					BigInteger number = new BigInteger(current.getToken());
					advance();
					if (number.longValue() > MIN_VALUE && number.longValue() < MAX_VALUE) {
						return new ZPrologTerm(provider, number.intValue() * -1);
					} else {
						return new ZPrologTerm(provider, number.longValue() * -1);
					}
				}

				// exprC(n) ::= '-' float
				else if (current.isFloat()) {
					double number = Double.parseDouble(current.getToken());
					advance();
					return new ZPrologTerm(provider, number);
				}
			}

			// exprC(n) ::= '+' integer | '+' float
			if (operator.isPlus()) {
				advance();

				// exprC(n) ::= '+' integer
				if (current.isInteger()) {
					BigInteger number = new BigInteger(current.getToken());
					advance();
					if (number.longValue() > MIN_VALUE && number.longValue() < MAX_VALUE) {
						return new ZPrologTerm(provider, number.intValue());
					} else {
						return new ZPrologTerm(provider, number.longValue());
					}
				}

				// exprC(n) ::= '+' float
				else if (current.isFloat()) {
					double number = Double.parseDouble(current.getToken());
					advance();
					return new ZPrologTerm(provider, number);
				}
			}

			// check valid priority
			if (FY > maxPriority)
				FY = -1;
			if (FX > maxPriority)
				FX = -1;

			// FX has priority over FY
			if (FX >= FY && FX >= LOW) {

				// op(fx, n) exprA(n - 1)
				ZPrologTerm found = expr(FX - 1, commaIsEndMarker);
				if (found != null) {
					return new ZPrologTerm(provider, current.token, found);
				}
			}

			// FY has priority over FX, or FX has failed
			else if (FY >= LOW) {

				// op(fy,n) exprA(1200) | op(fy,n) expr(n)
				ZPrologTerm found = expr(FY, commaIsEndMarker);
				if (found != null) {
					return new ZPrologTerm(provider, current.token, found);
				}
			}

			// FY has priority over FX, but FY failed
			else if (FX >= LOW) {

				// op(fx, n) exprA(n - 1)
				ZPrologTerm found = expr(FX - 1, commaIsEndMarker);

				if (found != null) {
					return new ZPrologTerm(provider, current.token, found);
				}
			}

		}

		// exprA(1200)
		return exprA(HIGH);
	}

	private void advance() {
		try {
			current = scanner.getNextToken();
			while (current.isWhiteSpace()) {
				current = scanner.getNextToken();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void reset() {
		varIndex = 0;
		variables = new HashMap<String, ZPrologTerm>();
	}

}
