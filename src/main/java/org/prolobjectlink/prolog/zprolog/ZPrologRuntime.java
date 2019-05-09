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
package org.prolobjectlink.prolog.zprolog;

import static org.prolobjectlink.prolog.PrologTermType.ATOM_TYPE;
import static org.prolobjectlink.prolog.PrologTermType.STRUCTURE_TYPE;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.AFTER;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.AFTER_EQUALS;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.BEFORE;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.BEFORE_EQUALS;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.BITWISE_AND;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.BITWISE_COMPLEMENT;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.BITWISE_OR;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.BITWISE_SHIFT_LEFT;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.BITWISE_SHIFT_RIGHT;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.BITWISE_XOR;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.COMMA;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.DIV;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.EQUAL;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.EQUIVALENT;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.FLOAT_POW;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.GREATER;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.GREATER_EQUAL;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.IF_THEN;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.INTEGER_DIV;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.INT_FLOAT_POW;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.IS;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.LESS;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.LESS_EQUAL;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.MINUS;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.MOD;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.NECK;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.NOT;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.NOT_EQUAL;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.NOT_EQUIVALENT;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.NOT_UNIFY;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.PLUS;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.QUERY;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.REM;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.SEMICOLON;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TIMES;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_DIV;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_EQUAL;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_GREATER;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_GREATER_EQUAL;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_IS;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_LESS;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_LESS_EQUAL;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_MINUS;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_MOD;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_NOT_EQUAL;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_NOT_UNIFY;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_PLUS;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_TIMES;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_UNIFY;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.TOKEN_USER_DEFINED;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.UNIFY;
import static org.prolobjectlink.prolog.zprolog.ZPrologOperator.UNIV;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_CALL;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_COMMA;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_CURRENT_TIME;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_CUT;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_E;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_ENSURE_LOADED_BUILTIN;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_FAIL;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_FALSE;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_HALT_BUILTIN;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_IF_THEN;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_INITIALIZATION_BUILTIN;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_NL;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_SEMICOLON;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_THROW_BUILTIN;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_TRUE;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_UNIFY_WITH_OCCURS_CHECK_BUILTIN;
import static org.prolobjectlink.prolog.zprolog.ZPrologToken.TOKEN_WRITE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.prolobjectlink.prolog.ArrayStack;
import org.prolobjectlink.prolog.PrologClause;
import org.prolobjectlink.prolog.PrologClauses;
import org.prolobjectlink.prolog.PrologIndicator;
import org.prolobjectlink.prolog.PrologLogger;
import org.prolobjectlink.prolog.PrologOperator;
import org.prolobjectlink.prolog.PrologProgram;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;

abstract class ZPrologRuntime extends ZPrologMachine {

	// stack maximum size to grow
	protected static int MAX_STACK_SIZE = Integer.MAX_VALUE;

	// maximum arguments number allowed
	protected static int MAX_ARITY = Integer.MAX_VALUE;

	// prolog file extension .pl by default
	protected static String PROLOG_FILE_TYPE = ".pl";

	// parsed maximum variable number
	protected static int MAX_VAR_NUM = 8;

	// private static final Logger logger = Logger.getAnonymousLogger();

	// unification flag represent fail state
	// if allow_unify is true system unify
	// if allow_unify is false system not unify and do backtrack
	private volatile boolean allow_unify = true;

	// goal to be resolved
	protected ZPrologGoal current_goal;
	protected ZPrologGoal next_goal;

	// stack for unifications and choice points
	// use for store states for backtracks
	protected ZPrologStack stack = new ZPrologStack();

	// prolog program object
	protected PrologProgram program = new ZPrologProgram();

	// flag that indicate if the query has solution
	protected boolean hasSolution;

	// flag that indicate if more solutions are possible
	protected boolean hasMoreSolution;

	// key of dynamics predicates
	protected final ArrayStack<File> systemPath = new ArrayStack<File>();

	// statistics collector map
	protected final Map<PrologTerm, PrologTerm> statistics = new HashMap<PrologTerm, PrologTerm>();

	protected static final PrologProvider provider = new ZProlog();

	//
	private static final ZPrologGoal FAIL = new ZPrologGoal(ZPrologTerm.FAIL_TERM);
	private static final ZPrologGoal SUCCESS = new ZPrologGoal(ZPrologTerm.TRUE_TERM);

	// character map for conversion
	protected static final Map<Character, Character> characters;

	static {

		characters = new TreeMap<Character, Character>();
		for (char i = 0; i < 256; i++) {
			characters.put(i, i);
		}

	}

	// built-ins data base
	protected static final Map<String, PrologClauses> builtins;

	static {

		builtins = new HashMap<String, PrologClauses>();

		// 7.4 directives
		builtins.put("include/1", ZPrologBuiltin.include1(provider));
		builtins.put("ensure_loaded/1", ZPrologBuiltin.ensureLoaded1(provider));
		builtins.put("initialization/1", ZPrologBuiltin.initialization1(provider));

		// 7.8 control constructs
		builtins.put("!/0", new ZPrologClauses("!/0", new ZPrologClause(ZPrologTerm.CUT_TERM)));
		builtins.put("nil/0", new ZPrologClauses("nil/0", new ZPrologClause(ZPrologTerm.NIL_TERM)));
		builtins.put("fail/0", new ZPrologClauses("fail/0", new ZPrologClause(ZPrologTerm.FAIL_TERM)));
		builtins.put("true/0", new ZPrologClauses("true/0", new ZPrologClause(ZPrologTerm.TRUE_TERM)));
		builtins.put("false/0", new ZPrologClauses("false/0", new ZPrologClause(ZPrologTerm.FALSE_TERM)));
		builtins.put("->/2", new ZPrologClauses("->/2", new ZPrologClause(new ZPrologTerm(TOKEN_IF_THEN, STRUCTURE_TYPE,
				provider, "->", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put("call/1", new ZPrologClauses("call/1", new ZPrologClause(new ZPrologTerm(TOKEN_CALL,
				STRUCTURE_TYPE, provider, ZPrologBuiltin.CALL, new ZPrologTerm(provider, "X", 0)))));
		builtins.put("throw/1", new ZPrologClauses("throw/1", new ZPrologClause(new ZPrologTerm(TOKEN_THROW_BUILTIN,
				STRUCTURE_TYPE, provider, ZPrologBuiltin.THROW, new ZPrologTerm(provider, "X", 0)))));
		/*
		 * builtins.put("catch/3", new ZPrologClauses("catch/3", new ZPrologClause(new
		 * ZPrologTerm(TOKEN_CATCH, STRUCTURE_TYPE, provider, "catch", new
		 * ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1), new
		 * ZPrologTerm(provider, "Z", 2)))));
		 */

		builtins.put(",/2", new ZPrologClauses(",/2", new ZPrologClause(new ZPrologTerm(TOKEN_COMMA, STRUCTURE_TYPE,
				provider, ",", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put(";/2", new ZPrologClauses(";/2", new ZPrologClause(new ZPrologTerm(TOKEN_SEMICOLON, STRUCTURE_TYPE,
				provider, ";", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));

		// 8.2 term unification OK
		builtins.put("=/2", new ZPrologClauses("=/2", new ZPrologClause(new ZPrologTerm(TOKEN_UNIFY, STRUCTURE_TYPE,
				provider, "=", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put("\\=/2",
				new ZPrologClauses("\\=/2", new ZPrologClause(new ZPrologTerm(TOKEN_NOT_UNIFY, STRUCTURE_TYPE, provider,
						"\\=", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put("unify_with_occurs_check/2",
				new ZPrologClauses("unify_with_occurs_check/2",
						new ZPrologClause(new ZPrologTerm(TOKEN_UNIFY_WITH_OCCURS_CHECK_BUILTIN, STRUCTURE_TYPE,
								provider, "unify_with_occurs_check", new ZPrologTerm(provider, "X", 0),
								new ZPrologTerm(provider, "Y", 1)))));

		// 8.3 type testing

		// 8.4 term comparison

		// 8.5 term creation and decomposition

		// 8.6 arithmetics evaluation OK
		builtins.put("is/2", new ZPrologClauses("is/2", new ZPrologClause(new ZPrologTerm(TOKEN_IS, STRUCTURE_TYPE,
				provider, "is", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));

		// 8.7 arithmetic comparison OK
		builtins.put("=:=/2", new ZPrologClauses("=:=/2", new ZPrologClause(new ZPrologTerm(TOKEN_EQUAL, STRUCTURE_TYPE,
				provider, "=:=", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put("=\\=/2",
				new ZPrologClauses("=\\=/2", new ZPrologClause(new ZPrologTerm(TOKEN_NOT_EQUAL, STRUCTURE_TYPE,
						provider, "=\\=", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put("</2", new ZPrologClauses("</2", new ZPrologClause(new ZPrologTerm(TOKEN_LESS, STRUCTURE_TYPE,
				provider, "<", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put("=</2",
				new ZPrologClauses("=</2", new ZPrologClause(new ZPrologTerm(TOKEN_LESS_EQUAL, STRUCTURE_TYPE, provider,
						"=<", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put(">/2", new ZPrologClauses(">/2", new ZPrologClause(new ZPrologTerm(TOKEN_GREATER, STRUCTURE_TYPE,
				provider, ">", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put(">=/2",
				new ZPrologClauses(">=/2", new ZPrologClause(new ZPrologTerm(TOKEN_GREATER_EQUAL, STRUCTURE_TYPE,
						provider, ">=", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));

		// 8.8 clause retrieval and information

		// 8.9 clause creation and destruction

		// 8.10 All solutions

		// 8.11

		// 8.12
		builtins.put("nl/0", new ZPrologClauses("nl/0",
				new ZPrologClause(new ZPrologTerm(TOKEN_NL, ATOM_TYPE, provider, ZPrologBuiltin.NL))));

		// 8.14 Term input/output
		builtins.put("write/1", new ZPrologClauses("write/1", new ZPrologClause(new ZPrologTerm(TOKEN_WRITE,
				STRUCTURE_TYPE, provider, ZPrologBuiltin.WRITE, new ZPrologTerm(provider, "X", 0)))));

		// 8.17 Implementation defined hooks
		builtins.put("halt/0", new ZPrologClauses("halt/0",
				new ZPrologClause(new ZPrologTerm(TOKEN_HALT_BUILTIN, STRUCTURE_TYPE, provider, ZPrologBuiltin.HALT))));
		builtins.put("halt/1", new ZPrologClauses("halt/1", new ZPrologClause(new ZPrologTerm(TOKEN_HALT_BUILTIN,
				STRUCTURE_TYPE, provider, ZPrologBuiltin.HALT, new ZPrologTerm(provider, "X", 0)))));

		// 9.1 simple arithmetic functors
		builtins.put("+/2", new ZPrologClauses("+/2", new ZPrologClause(new ZPrologTerm(TOKEN_PLUS, STRUCTURE_TYPE,
				provider, "+", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put("-/2", new ZPrologClauses("-/2", new ZPrologClause(new ZPrologTerm(TOKEN_MINUS, STRUCTURE_TYPE,
				provider, "-", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put("*/2", new ZPrologClauses("*/2", new ZPrologClause(new ZPrologTerm(TOKEN_TIMES, STRUCTURE_TYPE,
				provider, "*", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put("//2", new ZPrologClauses("//2", new ZPrologClause(new ZPrologTerm(TOKEN_DIV, STRUCTURE_TYPE,
				provider, "/", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));
		builtins.put("mod/2", new ZPrologClauses("mod/2", new ZPrologClause(new ZPrologTerm(TOKEN_MOD, STRUCTURE_TYPE,
				provider, "mod", new ZPrologTerm(provider, "X", 0), new ZPrologTerm(provider, "Y", 1)))));

		// 9.X valuable functors
		builtins.put("e/0", new ZPrologClauses("e/0",
				new ZPrologClause(new ZPrologTerm(TOKEN_E, ATOM_TYPE, provider, ZPrologBuiltin.E))));

		// non ISO
		builtins.put("current_time/1",
				new ZPrologClauses("current_time/1", new ZPrologClause(new ZPrologTerm(TOKEN_CURRENT_TIME,
						STRUCTURE_TYPE, provider, ZPrologBuiltin.CURRENT_TIME, new ZPrologTerm(provider, "X", 0)))));

	}

	// prolog operators map
	protected static final Map<String, PrologOperator> operators;

	static {

		operators = new HashMap<String, PrologOperator>();

		// operators
		operators.put("=", UNIFY);
		operators.put("\\=", NOT_UNIFY);
		operators.put("<", LESS);
		operators.put(">", GREATER);
		operators.put("=<", LESS_EQUAL);
		operators.put(">=", GREATER_EQUAL);
		operators.put("is", IS);
		operators.put("=:=", EQUAL);
		operators.put("=\\=", NOT_EQUAL);
		operators.put("+", PLUS);
		operators.put("-", MINUS);
		operators.put("*", TIMES);
		operators.put("/", DIV);
		operators.put("//", INTEGER_DIV);
		operators.put("mod", MOD);
		operators.put("rem", REM);
		operators.put("**", FLOAT_POW);
		operators.put("^", INT_FLOAT_POW);
		operators.put("/\\", BITWISE_AND);
		operators.put("\\/", BITWISE_OR);
		operators.put("><", BITWISE_XOR);
		operators.put("<<", BITWISE_SHIFT_LEFT);
		operators.put(">>", BITWISE_SHIFT_RIGHT);
		operators.put("\\", BITWISE_COMPLEMENT);
		operators.put("==", EQUIVALENT);
		operators.put("\\==", NOT_EQUIVALENT);
		operators.put("@<", BEFORE);
		operators.put("@>", AFTER);
		operators.put("@=<", BEFORE_EQUALS);
		operators.put("@>=", AFTER_EQUALS);
		operators.put("=..", UNIV);
		operators.put("?-", QUERY);
		operators.put("\\+", NOT);
		operators.put("->", IF_THEN);
		operators.put(",", COMMA);
		operators.put(";", SEMICOLON);
		operators.put(":-", NECK);

	}

	// clause to match with the goal
	private PrologClause clause;

	//
	private ZPrologTerm variables[];

	// stack mark for
	// cut computation
	private int stackMark;

	// statistics
	// private int gctime;
	private float cputime;
	private int inferences;
	private int backtracks;
	private int unifications;

	protected ZPrologRuntime(PrologProvider provider) {
		super(provider);
	}

	protected final int op(String token, int specifier) {
		ZPrologOperator operator = (ZPrologOperator) operators.get(token);
		if (operator != null && operator.position == specifier) {
			return operator.priority;
		}
		return 0;
	}

	/**
	 * ensure_loaded logic load and execute all directives and the initializations
	 * if exists.
	 */
	protected final void load(String path) {
		long startTime = System.currentTimeMillis();
		ZPrologParser parser = new ZPrologParser(provider);
		String message = "The source file " + path + " not created.";
		try {

			// if location is not in the path, add
			if (systemPath.isEmpty()) {
				systemPath.add(new File(path));
			}

			// getting the current work file
			File currentFile = systemPath.peek();

			// resolve in current path
			if (path.startsWith(".")) {
				String currentPath = currentFile.getParent();
				String relative = path.substring(1);
				path = currentPath + relative;
			}

			// resolve in parent path
			else if (path.startsWith("..")) {
				File parentFile = currentFile.getParentFile();
				String parentPath = parentFile.getParent();
				String relative = path.substring(2);
				path = parentPath + relative;
			}

			// set default extension if was omitted
			int lastExtensionIndex = path.lastIndexOf('.');
			int lastSeparatorIndex = path.lastIndexOf('/');
			if (lastExtensionIndex < lastSeparatorIndex) {
				path = path + PROLOG_FILE_TYPE;
			}

			File file = new File(path);
			if (!file.exists() && !file.createNewFile()) {
				getLogger().error(getClass(), message);
			}

			if (systemPath.isEmpty()) {
				systemPath.add(file);
			}

			// parse prolog program from file
			// program = parser.parseProgram(file);
			program.add(parser.parseProgram(file));

			// execute all directives
			ZPrologGoal[] directives = program.getDirectives().toArray(new ZPrologGoal[0]);
			for (ZPrologGoal directive : directives) {
				current_goal = directive.resolve(program, builtins);
				program.removeDirective(directive);
				solve();
			}

			// execute all initializations goals
			ZPrologGoal[] goals = program.getGoals().toArray(new ZPrologGoal[0]);
			for (ZPrologGoal goal : goals) {
				current_goal = goal.resolve(program, builtins);
				program.removeGoal(goal);
				solve();
			}

		} catch (FileNotFoundException e) {
			// stdout.println(message);
			// throw new PrologError(getClass(), message);
		} catch (IOException e) {
			// stdout.println(message);
			// throw new PrologError(getClass(), message);
		} finally {
			long endTime = System.currentTimeMillis();
			cputime = (endTime - startTime) / 1000F;
		}
	}

	/**
	 * ensure_loaded logic load and execute all directives and the initializations
	 * if exists.
	 */
	protected final void load(Reader reader) {
		// TODO
	}

	protected final void save(String path) {
		long startTime = System.currentTimeMillis();
		try {
			PrintWriter writer = new PrintWriter(path);
			writer.print(program);
			writer.close();
		} catch (FileNotFoundException e) {
			getLogger().error(getClass(), PrologLogger.FILE_NOT_FOUND, e);
		} finally {
			long endTime = System.currentTimeMillis();
			cputime = (endTime - startTime) / 1000F;
		}
	}

	protected final boolean run(ZPrologGoal goal) {
		current_goal = goal.resolve(program, builtins);
		stack.clear();
		return solve();
	}

	/** inference algorithm */
	protected final boolean solve() {

		long startTime = System.currentTimeMillis();

		while (current_goal != null) {

			inferences++;

			if (current_goal.hasNextClause()) {

				clause = current_goal.nextClause();

				variables = new ZPrologTerm[MAX_VAR_NUM];
				ZPrologTerm goal_term = (ZPrologTerm) current_goal.getTerm();
				ZPrologTerm clause_term = ((ZPrologTerm) clause.getTerm()).refresh(variables);

				// choice point elimination optimization
				while (!goal_term.unify(clause_term) && current_goal.hasNextClause()) {
					clause = current_goal.nextClause();
					goal_term = (ZPrologTerm) current_goal.getTerm();
					clause_term = ((ZPrologTerm) current_goal.getTerm()).refresh(variables);
				}

				// choice point creation if it is possible
				if (current_goal.hasNextClause()) {
					stackMark = stack.size();
					stack.push(new ZPrologChoicePoint(current_goal, next_goal));
				}

				// logger.info("GOAL: " + goal_term + " <==> HEAD: " +
				// clause_term);

				if (allow_unify && clause_term.unify(goal_term, stack)) {

					// logger.info(" ==>> Unify ");

					unifications++;

					if (clause_term.isBuiltin()) {

						switch (clause_term.id) {

						// 7.4
						case TOKEN_ENSURE_LOADED_BUILTIN:
							ZPrologTerm path = clause_term.arguments[0].dereference();
							load(unquoted(path.functor));
							current_goal = next_goal;
							next_goal = null;
							break;
						case TOKEN_INITIALIZATION_BUILTIN:
							ZPrologTerm goal = clause_term.arguments[0].dereference();
							program.addGoal(new ZPrologGoal(goal));
							current_goal = next_goal;
							next_goal = null;
							break;

						// 7.8
						case TOKEN_FAIL:
							allow_unify = false;
							break;
						case TOKEN_FALSE:
							allow_unify = false;
							break;
						case TOKEN_TRUE:
							current_goal = next_goal;
							next_goal = null;
							break;
						case TOKEN_CALL:
							goal = clause_term.arguments[0].dereference();
							current_goal = new ZPrologGoal(goal);
							break;
						case TOKEN_CUT:
							int size = stack.size();
							ZPrologStack s = new ZPrologStack();
							while (size > stackMark) {
								Object object = stack.pop();
								if (object instanceof ZPrologChoicePoint) {
									ZPrologChoicePoint choicePoint = (ZPrologChoicePoint) object;

									// current_goal =
									// choicePoint.get_current_goal();
									// next_goal = choicePoint.get_next_goal();

									current_goal = choicePoint.getNextGoal();

								} else if (object instanceof PrologTerm) {
									s.push(object);
								}
								size--;
							}
							while (!s.empty()) {
								stack.push(s.pop());
							}
							break;
						case TOKEN_COMMA:
						case TOKEN_IF_THEN:
							current_goal = new ZPrologGoal(clause_term.arguments[0].dereference());
							if (next_goal != null) {
								next_goal = new ZPrologGoal(new ZPrologTerm(TOKEN_COMMA, STRUCTURE_TYPE, provider, ",",
										clause_term.arguments[1].dereference(), (ZPrologTerm) next_goal.term));
							} else {
								next_goal = new ZPrologGoal(clause_term.arguments[1].dereference());
							}
							break;
						case TOKEN_SEMICOLON:
							System.out.println(clause_term);
							break;

						// 8.2
						case TOKEN_UNIFY:
							ZPrologTerm left = ((ZPrologTerm) clause_term.getLeft()).dereference();
							ZPrologTerm right = ((ZPrologTerm) clause_term.getRight()).dereference();
							current_goal = left.unify(right, stack) ? SUCCESS : FAIL;
							break;
						case TOKEN_NOT_UNIFY:
							left = ((ZPrologTerm) clause_term.getLeft()).dereference();
							right = ((ZPrologTerm) clause_term.getRight()).dereference();
							current_goal = !(left.unify(right, stack)) ? SUCCESS : FAIL;
							break;
						case TOKEN_UNIFY_WITH_OCCURS_CHECK_BUILTIN:
							left = ((ZPrologTerm) clause_term.getLeft()).dereference();
							right = ((ZPrologTerm) clause_term.getRight()).dereference();
							if (!left.occurs(right)) {
								current_goal = left.unify(right, stack) ? SUCCESS : FAIL;
							}
							current_goal = FAIL;
							break;

						// 8.3
						// 8.4
						// 8.5
						// 8.6
						case TOKEN_IS:
							left = ((ZPrologTerm) clause_term.getLeft()).dereference();
							right = ((ZPrologTerm) clause_term.getRight()).dereference();
							current_goal = left.unify(right.getValue(), stack) ? SUCCESS : FAIL;
							break;

						// 8.7
						case TOKEN_EQUAL:
							double leftNumber = ((ZPrologTerm) clause_term.getLeft()).dereference().getDoubleValue();
							double rightNumber = ((ZPrologTerm) clause_term.getRight()).dereference().getDoubleValue();
							current_goal = leftNumber == rightNumber ? SUCCESS : FAIL;
							break;
						case TOKEN_NOT_EQUAL:
							leftNumber = ((ZPrologTerm) clause_term.getLeft()).dereference().getDoubleValue();
							rightNumber = ((ZPrologTerm) clause_term.getRight()).dereference().getDoubleValue();
							current_goal = leftNumber != rightNumber ? SUCCESS : FAIL;
							break;
						case TOKEN_LESS:
							leftNumber = ((ZPrologTerm) clause_term.getLeft()).dereference().getDoubleValue();
							rightNumber = ((ZPrologTerm) clause_term.getRight()).dereference().getDoubleValue();
							current_goal = leftNumber < rightNumber ? SUCCESS : FAIL;
							break;
						case TOKEN_LESS_EQUAL:
							leftNumber = ((ZPrologTerm) clause_term.getLeft()).dereference().getDoubleValue();
							rightNumber = ((ZPrologTerm) clause_term.getRight()).dereference().getDoubleValue();
							current_goal = leftNumber <= rightNumber ? SUCCESS : FAIL;
							break;
						case TOKEN_GREATER:
							leftNumber = ((ZPrologTerm) clause_term.getLeft()).dereference().getDoubleValue();
							rightNumber = ((ZPrologTerm) clause_term.getRight()).dereference().getDoubleValue();
							current_goal = leftNumber > rightNumber ? SUCCESS : FAIL;
							break;
						case TOKEN_GREATER_EQUAL:
							leftNumber = ((ZPrologTerm) clause_term.getLeft()).dereference().getDoubleValue();
							rightNumber = ((ZPrologTerm) clause_term.getRight()).dereference().getDoubleValue();
							current_goal = leftNumber >= rightNumber ? SUCCESS : FAIL;
							break;

						// 8.8
						// 8.9
						// 8.10
						// 8.11

						// 8.12
						case TOKEN_NL:
							System.out.println();
							current_goal = SUCCESS;
							break;

						// 8.13
						// 8.14
						case TOKEN_WRITE:
							ZPrologTerm t = clause_term.arguments[0].dereference();
							System.out.println(t);
							current_goal = SUCCESS;
							break;

						// 8.15
						// 8.16
						// 8.17
						case TOKEN_HALT_BUILTIN:
							if (clause_term.arity == 1) {
								ZPrologTerm arg0 = clause_term.arguments[0];
								arg0 = arg0.dereference();
								// check that the arguments is an integer
								System.exit(arg0.getIntegerValue());
							} else if (clause_term.arity == 0) {
								System.exit(0);
							}
							break;

						// non ISO
						case TOKEN_CURRENT_TIME:
							long time = System.currentTimeMillis();
							ZPrologTerm n = new ZPrologTerm(provider, time);
							ZPrologTerm x = clause_term.arguments[0].dereference();
							current_goal = x.unify(n, stack) ? SUCCESS : FAIL;
							break;
						}

					}

					// the goal was resolved
					// and follow with the next
					else if (clause.isFact()) {
						current_goal = next_goal;
						next_goal = null;
					}

					// replace goal by clause body
					else if (clause.isRule()) {
						current_goal = current_goal != null ? current_goal.replace(clause, variables, stackMark) : null;
					}

					// if goal is not null search in the program
					current_goal = current_goal != null ? current_goal.resolve(program, builtins, next_goal) : null;

				}

				// unify failed - backtrack ...
				else if (!backtrack()) {

					long endTime = System.currentTimeMillis();
					cputime = (endTime - startTime) / 1000F;
					return false;

				}

			}
		}

		long endTime = System.currentTimeMillis();
		cputime = (endTime - startTime) / 1000F;
		return true;
	}

	/**
	 * Returns true if choice point was found, false in other case
	 * 
	 * @return true if choice point was found, false in other case
	 */
	protected final boolean backtrack() {

		// logger.info(" <<== Backtrack ");

		backtracks++;
		allow_unify = true;
		while (!stack.empty()) {
			Object object = stack.pop();
			if (object instanceof ZPrologChoicePoint) {
				ZPrologChoicePoint choicePoint = (ZPrologChoicePoint) object;
				current_goal = choicePoint.getCurrentGoal();
				next_goal = choicePoint.getNextGoal();
				return true;
			} else if (object instanceof ZPrologTerm) {
				ZPrologTerm term = (ZPrologTerm) object;
				term.unbind();
			}
		}
		return false;
	}

	protected final String unquoted(String functor) {
		String newFunctor = "";
		newFunctor += functor.substring(1, functor.length() - 1);
		return newFunctor;
	}

	public final void operator(int priority, String specifier, String operator) {
		ZPrologOperator op = new ZPrologOperator(TOKEN_USER_DEFINED, operator, priority, specifier);
		operators.put(operator, op);
	}

	public final Set<PrologOperator> currentOperators() {
		int size = operators.size();
		Set<PrologOperator> ops = new HashSet<PrologOperator>(size);
		for (PrologOperator prologOperator : operators.values()) {
			ops.add(prologOperator);
		}
		return ops;
	}

	public final boolean currentPredicate(String functor, int arity) {
		String key = functor + "/" + arity;
		return builtins.get(key) != null || program.get(key) != null;
	}

	public final boolean currentOperator(int priority, String specifier, String operator) {
		ZPrologOperator op = (ZPrologOperator) operators.get(operator);
		return op != null && priority == op.priority && specifier.equals(op.getSpecifier());
	}

	public final Set<PrologIndicator> getPredicates() {
		Set<PrologIndicator> pis = new HashSet<PrologIndicator>(program.size());
		for (PrologClauses clauses : program.getClauses().values()) {
			for (PrologClause c : clauses) {
				String functor = c.getFunctor();
				int arity = c.getArity();
				PrologIndicator pi = new ZPrologIndicator(functor, arity);
				pis.add(pi);
			}
		}
		return pis;
	}

	public final Set<PrologIndicator> getBuiltIns() {
		Set<PrologIndicator> pis = new HashSet<PrologIndicator>(builtins.size());
		for (PrologClauses clauses : builtins.values()) {
			for (PrologClause c : clauses) {
				String functor = c.getFunctor();
				int arity = c.getArity();
				PrologIndicator pi = new ZPrologIndicator(functor, arity);
				pis.add(pi);
			}
		}
		return pis;
	}

	public final Iterator<PrologClause> iterator() {
		Collection<PrologClauses> list = program.getClauses().values();
		Collection<PrologClause> clauses = new LinkedList<PrologClause>();
		for (PrologClauses prologClauses : list) {
			for (PrologClause prologClause : prologClauses) {
				clauses.add(prologClause);
			}
		}
		return new PrologProgramIterator(clauses);
	}

}
