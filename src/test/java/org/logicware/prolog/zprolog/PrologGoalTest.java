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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.logicware.prolog.PrologClauses;
import org.logicware.prolog.PrologProgram;

public class PrologGoalTest extends PrologBaseTest {

	private PrologProgram program = new ZPrologProgram();

	@Before
	public void setUp() throws Exception {

		// parent relationship
		program.add(new ZPrologClause(provider.newStructure(parent, pam, bob)));
		program.add(new ZPrologClause(provider.newStructure(parent, tom, bob)));
		program.add(new ZPrologClause(provider.newStructure(parent, tom, liz)));
		program.add(new ZPrologClause(provider.newStructure(parent, bob, ann)));
		program.add(new ZPrologClause(provider.newStructure(parent, bob, pat)));
		program.add(new ZPrologClause(provider.newStructure(parent, pat, jim)));

		// female relationship
		program.add(new ZPrologClause(provider.newStructure(female, pam)));
		program.add(new ZPrologClause(provider.newStructure(female, liz)));
		program.add(new ZPrologClause(provider.newStructure(female, ann)));
		program.add(new ZPrologClause(provider.newStructure(female, pat)));

		// male relationship
		program.add(new ZPrologClause(provider.newStructure(male, tom)));
		program.add(new ZPrologClause(provider.newStructure(male, bob)));
		program.add(new ZPrologClause(provider.newStructure(male, jim)));

		program.add(new ZPrologClause(provider.newStructure(offspring, x, y), provider.newStructure(parent, x, y)));

		program.add(new ZPrologClause(provider.newStructure(mother, x, y),
				provider.newStructure(",", provider.newStructure(parent, x, y), provider.newStructure(female, x))));

		program.add(new ZPrologClause(provider.newStructure(grandparent, x, z),
				provider.newStructure(",", provider.newStructure(parent, x, y), provider.newStructure(parent, y, z))));

		program.add(new ZPrologClause(provider.newStructure(sister, x, y),
				provider.newStructure(",", provider.newStructure(parent, z, x),
						provider.newStructure(",", provider.newStructure(parent, z, y), provider.newStructure(",",
								provider.newStructure(female, x), provider.newStructure(different, x, y))))));

		program.add(new ZPrologClause(provider.newStructure(different, x, x),
				provider.newStructure(",", provider.prologCut(), provider.prologFail())));

		program.add(new ZPrologClause(provider.newStructure(different, x, y)));

		program.add(new ZPrologClause(provider.newStructure(predecessor, x, z), provider.newStructure(parent, x, z)));

		program.add(new ZPrologClause(provider.newStructure(predecessor, x, z), provider.newStructure(",",
				provider.newStructure(parent, x, y), provider.newStructure(predecessor, y, z))));

		// employee relationship
		program.add(new ZPrologClause(provider.newStructure(employee, mcardon, one, five)));
		program.add(new ZPrologClause(provider.newStructure(employee, treeman, two, three)));
		program.add(new ZPrologClause(provider.newStructure(employee, chapman, one, two)));
		program.add(new ZPrologClause(provider.newStructure(employee, claessen, four, one)));
		program.add(new ZPrologClause(provider.newStructure(employee, petersen, five, eight)));
		program.add(new ZPrologClause(provider.newStructure(employee, cohn, one, seven)));
		program.add(new ZPrologClause(provider.newStructure(employee, duffy, one, nine)));

		// department relationship
		program.add(new ZPrologClause(provider.newStructure(department, one, board)));
		program.add(new ZPrologClause(provider.newStructure(department, two, human_resources)));
		program.add(new ZPrologClause(provider.newStructure(department, three, production)));
		program.add(new ZPrologClause(provider.newStructure(department, four, technical_services)));
		program.add(new ZPrologClause(provider.newStructure(department, five, administration)));

		// salary relationship
		program.add(new ZPrologClause(provider.newStructure(salary, one, thousand)));
		program.add(new ZPrologClause(provider.newStructure(salary, two, thousandFiveHundred)));
		program.add(new ZPrologClause(provider.newStructure(salary, three, twoThousand)));
		program.add(new ZPrologClause(provider.newStructure(salary, four, twoThousandFiveHundred)));
		program.add(new ZPrologClause(provider.newStructure(salary, five, threeThousand)));
		program.add(new ZPrologClause(provider.newStructure(salary, six, threeThousandFiveHundred)));
		program.add(new ZPrologClause(provider.newStructure(salary, seven, fourThousand)));
		program.add(new ZPrologClause(provider.newStructure(salary, eight, fourThousandFiveHundred)));
		program.add(new ZPrologClause(provider.newStructure(salary, nine, fiveThousand)));

		// zoo relationships
		program.add(new ZPrologClause(provider.newStructure("big", bear)));
		program.add(new ZPrologClause(provider.newStructure("big", elephant)));
		program.add(new ZPrologClause(provider.newStructure("small", cat)));
		program.add(new ZPrologClause(provider.newStructure("brown", bear)));
		program.add(new ZPrologClause(provider.newStructure("black", cat)));
		program.add(new ZPrologClause(provider.newStructure("gray", elephant)));

		// dark rules
		program.add(new ZPrologClause(provider.newStructure("dark", z), provider.newStructure("black", z)));

		program.add(new ZPrologClause(provider.newStructure("dark", z), provider.newStructure("brown", z)));

		//
		//
		//
		// parent relationship
		PrologClauses parents = new ZPrologClauses("parent/2");
		parents.add(new ZPrologClause(provider.parseTerm("parent( pam, bob )")));
		parents.add(new ZPrologClause(provider.parseTerm("parent( tom, bob )")));
		parents.add(new ZPrologClause(provider.parseTerm("parent( tom, liz )")));
		parents.add(new ZPrologClause(provider.parseTerm("parent( bob, ann )")));
		parents.add(new ZPrologClause(provider.parseTerm("parent( bob, pat )")));
		parents.add(new ZPrologClause(provider.parseTerm("parent( pat, jim )")));
		assertEquals(parents, program.get("parent/2"));

		// female relationship
		PrologClauses females = new ZPrologClauses("female/1");
		females.add(new ZPrologClause(provider.newStructure(female, pam)));
		females.add(new ZPrologClause(provider.newStructure(female, liz)));
		females.add(new ZPrologClause(provider.newStructure(female, ann)));
		females.add(new ZPrologClause(provider.newStructure(female, pat)));
		assertEquals(females, program.get("female/1"));

		// male relationship
		PrologClauses males = new ZPrologClauses("male/1");
		males.add(new ZPrologClause(provider.newStructure(male, tom)));
		males.add(new ZPrologClause(provider.newStructure(male, bob)));
		males.add(new ZPrologClause(provider.newStructure(male, jim)));
		assertEquals(males, program.get("male/1"));

		PrologClauses offsprings = new ZPrologClauses("offspring/2");
		offsprings.add(new ZPrologClause(provider.newStructure(offspring, x, y), provider.newStructure(parent, x, y)));
		assertEquals(offsprings, program.get("offspring/2"));

		PrologClauses mothers = new ZPrologClauses("mother/2");
		mothers.add(new ZPrologClause(provider.newStructure(mother, x, y),
				provider.newStructure(",", provider.newStructure(parent, x, y), provider.newStructure(female, x))));
		assertEquals(mothers, program.get("mother/2"));

		PrologClauses grandparents = new ZPrologClauses("grandparent/2");
		grandparents.add(new ZPrologClause(provider.newStructure(grandparent, x, z),
				provider.newStructure(",", provider.newStructure(parent, x, y), provider.newStructure(parent, y, z))));
		assertEquals(grandparents, program.get("grandparent/2"));

		PrologClauses sisters = new ZPrologClauses("sister/2");
		sisters.add(new ZPrologClause(provider.newStructure(sister, x, y),
				provider.newStructure(",", provider.newStructure(parent, z, x),
						provider.newStructure(",", provider.newStructure(parent, z, y), provider.newStructure(",",
								provider.newStructure(female, x), provider.newStructure(different, x, y))))));
		assertEquals(sisters, program.get("sister/2"));

		PrologClauses differents = new ZPrologClauses("different/2");
		differents.add(new ZPrologClause(provider.newStructure(different, x, x),
				provider.newStructure(",", provider.prologCut(), provider.prologFail())));

		differents.add(new ZPrologClause(provider.newStructure(different, x, y)));
		assertEquals(differents, program.get("different/2"));

		PrologClauses predecessors = new ZPrologClauses("predecessor/2");
		predecessors
				.add(new ZPrologClause(provider.newStructure(predecessor, x, z), provider.newStructure(parent, x, z)));

		predecessors.add(new ZPrologClause(provider.newStructure(predecessor, x, z), provider.newStructure(",",
				provider.newStructure(parent, x, y), provider.newStructure(predecessor, y, z))));
		assertEquals(predecessors, program.get("predecessor/2"));

		// employee relationship
		PrologClauses employees = new ZPrologClauses("employee/3");
		employees.add(new ZPrologClause(provider.newStructure(employee, mcardon, one, five)));
		employees.add(new ZPrologClause(provider.newStructure(employee, treeman, two, three)));
		employees.add(new ZPrologClause(provider.newStructure(employee, chapman, one, two)));
		employees.add(new ZPrologClause(provider.newStructure(employee, claessen, four, one)));
		employees.add(new ZPrologClause(provider.newStructure(employee, petersen, five, eight)));
		employees.add(new ZPrologClause(provider.newStructure(employee, cohn, one, seven)));
		employees.add(new ZPrologClause(provider.newStructure(employee, duffy, one, nine)));
		assertEquals(employees, program.get("employee/3"));

		// department relationship
		PrologClauses departments = new ZPrologClauses("department/2");
		departments.add(new ZPrologClause(provider.newStructure(department, one, board)));
		departments.add(new ZPrologClause(provider.newStructure(department, two, human_resources)));
		departments.add(new ZPrologClause(provider.newStructure(department, three, production)));
		departments.add(new ZPrologClause(provider.newStructure(department, four, technical_services)));
		departments.add(new ZPrologClause(provider.newStructure(department, five, administration)));
		assertEquals(departments, program.get("department/2"));

		// salary relationship
		PrologClauses salaries = new ZPrologClauses("salary/2");
		salaries.add(new ZPrologClause(provider.newStructure(salary, one, thousand)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, two, thousandFiveHundred)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, three, twoThousand)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, four, twoThousandFiveHundred)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, five, threeThousand)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, six, threeThousandFiveHundred)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, seven, fourThousand)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, eight, fourThousandFiveHundred)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, nine, fiveThousand)));
		assertEquals(salaries, program.get("salary/2"));

		// big relationships
		PrologClauses bigs = new ZPrologClauses("big/1");
		bigs.add(new ZPrologClause(provider.newStructure("big", bear)));
		bigs.add(new ZPrologClause(provider.newStructure("big", elephant)));
		assertEquals(bigs, program.get("big/1"));

		// small relationships
		PrologClauses smalls = new ZPrologClauses("small/1");
		smalls.add(new ZPrologClause(provider.newStructure("small", cat)));
		assertEquals(smalls, program.get("small/1"));

		// brown relationships
		PrologClauses browns = new ZPrologClauses("brown/1");
		browns.add(new ZPrologClause(provider.newStructure("brown", bear)));
		assertEquals(browns, program.get("brown/1"));

		// black relationships
		PrologClauses blacks = new ZPrologClauses("black/1");
		blacks.add(new ZPrologClause(provider.newStructure("black", cat)));
		assertEquals(blacks, program.get("black/1"));

		// gray relationships
		PrologClauses grays = new ZPrologClauses("gray/1");
		grays.add(new ZPrologClause(provider.newStructure("gray", elephant)));
		assertEquals(grays, program.get("gray/1"));

		// dark rules
		PrologClauses darks = new ZPrologClauses("dark/1");
		darks.add(new ZPrologClause(provider.newStructure("dark", z), provider.newStructure("black", z)));

		darks.add(new ZPrologClause(provider.newStructure("dark", z), provider.newStructure("brown", z)));
		assertEquals(darks, program.get("dark/1"));

	}

	@After
	public void tearDown() throws Exception {
		program.clear();
	}

	@Test
	public void testResolve() {

		//
		// family
		//

		// parent relationship
		PrologClauses parents = new ZPrologClauses("parent/2");
		parents.add(new ZPrologClause(provider.parseTerm("parent( pam, bob )")));
		parents.add(new ZPrologClause(provider.parseTerm("parent( tom, bob )")));
		parents.add(new ZPrologClause(provider.parseTerm("parent( tom, liz )")));
		parents.add(new ZPrologClause(provider.parseTerm("parent( bob, ann )")));
		parents.add(new ZPrologClause(provider.parseTerm("parent( bob, pat )")));
		parents.add(new ZPrologClause(provider.parseTerm("parent( pat, jim )")));
		assertEquals(new ZPrologGoal(provider.newStructure(parent, x, y), parents),
				new ZPrologGoal(provider.newStructure(parent, x, y)).resolve(program, ZPrologRuntime.builtins));

		// female relationship
		PrologClauses females = new ZPrologClauses("female/1");
		females.add(new ZPrologClause(provider.newStructure(female, pam)));
		females.add(new ZPrologClause(provider.newStructure(female, liz)));
		females.add(new ZPrologClause(provider.newStructure(female, ann)));
		females.add(new ZPrologClause(provider.newStructure(female, pat)));
		assertEquals(new ZPrologGoal(provider.newStructure(female, x), females),
				new ZPrologGoal(provider.newStructure(female, x)).resolve(program, ZPrologRuntime.builtins));

		// male relationship
		PrologClauses males = new ZPrologClauses("male/1");
		males.add(new ZPrologClause(provider.newStructure(male, tom)));
		males.add(new ZPrologClause(provider.newStructure(male, bob)));
		males.add(new ZPrologClause(provider.newStructure(male, jim)));
		assertEquals(new ZPrologGoal(provider.newStructure(male, x), males),
				new ZPrologGoal(provider.newStructure(male, x)).resolve(program, ZPrologRuntime.builtins));

		// offspring relationship
		PrologClauses offsprings = new ZPrologClauses("offspring/2");
		offsprings.add(new ZPrologClause(provider.newStructure(offspring, x, y), provider.newStructure(parent, x, y)));
		assertEquals(new ZPrologGoal(provider.newStructure(offspring, x, y), offsprings),
				new ZPrologGoal(provider.newStructure(offspring, x, y)).resolve(program, ZPrologRuntime.builtins));

		// mother relationship
		PrologClauses mothers = new ZPrologClauses("mother/2");
		mothers.add(new ZPrologClause(provider.newStructure(mother, x, y),
				provider.newStructure(",", provider.newStructure(parent, x, y), provider.newStructure(female, x))));
		assertEquals(new ZPrologGoal(provider.newStructure(mother, x, y), mothers),
				new ZPrologGoal(provider.newStructure(mother, x, y)).resolve(program, ZPrologRuntime.builtins));

		// grandparent relationship
		PrologClauses grandparents = new ZPrologClauses("grandparent/2");
		grandparents.add(new ZPrologClause(provider.newStructure(grandparent, x, z),
				provider.newStructure(",", provider.newStructure(parent, x, y), provider.newStructure(parent, y, z))));
		assertEquals(new ZPrologGoal(provider.newStructure(grandparent, x, y), grandparents),
				new ZPrologGoal(provider.newStructure(grandparent, x, y)).resolve(program, ZPrologRuntime.builtins));

		// sisters relationship
		PrologClauses sisters = new ZPrologClauses("sister/2");
		sisters.add(new ZPrologClause(provider.newStructure(sister, x, y),
				provider.newStructure(",", provider.newStructure(parent, z, x),
						provider.newStructure(",", provider.newStructure(parent, z, y), provider.newStructure(",",
								provider.newStructure(female, x), provider.newStructure(different, x, y))))));
		assertEquals(new ZPrologGoal(provider.newStructure(sister, x, y), sisters),
				new ZPrologGoal(provider.newStructure(sister, x, y)).resolve(program, ZPrologRuntime.builtins));

		// different relationship
		PrologClauses differents = new ZPrologClauses("different/2");
		differents.add(new ZPrologClause(provider.newStructure(different, x, x),
				provider.newStructure(",", provider.prologCut(), provider.prologFail())));
		differents.add(new ZPrologClause(provider.newStructure(different, x, y)));
		assertEquals(new ZPrologGoal(provider.newStructure(different, x, y), differents),
				new ZPrologGoal(provider.newStructure(different, x, y)).resolve(program, ZPrologRuntime.builtins));

		// predecessor relationship
		PrologClauses predecessors = new ZPrologClauses("predecessor/2");
		predecessors
				.add(new ZPrologClause(provider.newStructure(predecessor, x, z), provider.newStructure(parent, x, z)));
		predecessors.add(new ZPrologClause(provider.newStructure(predecessor, x, z), provider.newStructure(",",
				provider.newStructure(parent, x, y), provider.newStructure(predecessor, y, z))));
		assertEquals(new ZPrologGoal(provider.newStructure(predecessor, x, y), predecessors),
				new ZPrologGoal(provider.newStructure(predecessor, x, y)).resolve(program, ZPrologRuntime.builtins));

		//
		// company
		//

		// employee relationship
		PrologClauses employees = new ZPrologClauses("employee/3");
		employees.add(new ZPrologClause(provider.newStructure(employee, mcardon, one, five)));
		employees.add(new ZPrologClause(provider.newStructure(employee, treeman, two, three)));
		employees.add(new ZPrologClause(provider.newStructure(employee, chapman, one, two)));
		employees.add(new ZPrologClause(provider.newStructure(employee, claessen, four, one)));
		employees.add(new ZPrologClause(provider.newStructure(employee, petersen, five, eight)));
		employees.add(new ZPrologClause(provider.newStructure(employee, cohn, one, seven)));
		employees.add(new ZPrologClause(provider.newStructure(employee, duffy, one, nine)));
		assertEquals(new ZPrologGoal(provider.newStructure(employee, x, y, z), employees),
				new ZPrologGoal(provider.newStructure(employee, x, y, z)).resolve(program, ZPrologRuntime.builtins));

		// department relationship
		PrologClauses departments = new ZPrologClauses("department/2");
		departments.add(new ZPrologClause(provider.newStructure(department, one, board)));
		departments.add(new ZPrologClause(provider.newStructure(department, two, human_resources)));
		departments.add(new ZPrologClause(provider.newStructure(department, three, production)));
		departments.add(new ZPrologClause(provider.newStructure(department, four, technical_services)));
		departments.add(new ZPrologClause(provider.newStructure(department, five, administration)));
		assertEquals(new ZPrologGoal(provider.newStructure(department, x, y), departments),
				new ZPrologGoal(provider.newStructure(department, x, y)).resolve(program, ZPrologRuntime.builtins));

		// salary relationship
		PrologClauses salaries = new ZPrologClauses("salary/2");
		salaries.add(new ZPrologClause(provider.newStructure(salary, one, thousand)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, two, thousandFiveHundred)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, three, twoThousand)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, four, twoThousandFiveHundred)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, five, threeThousand)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, six, threeThousandFiveHundred)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, seven, fourThousand)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, eight, fourThousandFiveHundred)));
		salaries.add(new ZPrologClause(provider.newStructure(salary, nine, fiveThousand)));
		assertEquals(new ZPrologGoal(provider.newStructure(salary, x, y), salaries),
				new ZPrologGoal(provider.newStructure(salary, x, y)).resolve(program, ZPrologRuntime.builtins));

		//
		// zoo
		//

		// big relationships
		PrologClauses bigs = new ZPrologClauses("big/1");
		bigs.add(new ZPrologClause(provider.newStructure("big", bear)));
		bigs.add(new ZPrologClause(provider.newStructure("big", elephant)));
		assertEquals(new ZPrologGoal(provider.newStructure("big", x), bigs),
				new ZPrologGoal(provider.newStructure("big", x)).resolve(program, ZPrologRuntime.builtins));

		// small relationships
		PrologClauses smalls = new ZPrologClauses("small/1");
		smalls.add(new ZPrologClause(provider.newStructure("small", cat)));
		assertEquals(new ZPrologGoal(provider.newStructure("small", x), smalls),
				new ZPrologGoal(provider.newStructure("small", x)).resolve(program, ZPrologRuntime.builtins));

		// brown relationships
		PrologClauses browns = new ZPrologClauses("brown/1");
		browns.add(new ZPrologClause(provider.newStructure("brown", bear)));
		assertEquals(new ZPrologGoal(provider.newStructure("brown", x), browns),
				new ZPrologGoal(provider.newStructure("brown", x)).resolve(program, ZPrologRuntime.builtins));

		// black relationships
		PrologClauses blacks = new ZPrologClauses("black/1");
		blacks.add(new ZPrologClause(provider.newStructure("black", cat)));
		assertEquals(new ZPrologGoal(provider.newStructure("black", x), blacks),
				new ZPrologGoal(provider.newStructure("black", x)).resolve(program, ZPrologRuntime.builtins));

		// gray relationships
		PrologClauses grays = new ZPrologClauses("gray/1");
		grays.add(new ZPrologClause(provider.newStructure("gray", elephant)));
		assertEquals(new ZPrologGoal(provider.newStructure("gray", x), grays),
				new ZPrologGoal(provider.newStructure("gray", x)).resolve(program, ZPrologRuntime.builtins));

		// dark rules
		PrologClauses darks = new ZPrologClauses("dark/1");
		darks.add(new ZPrologClause(provider.newStructure("dark", z), provider.newStructure("black", z)));
		darks.add(new ZPrologClause(provider.newStructure("dark", z), provider.newStructure("brown", z)));
		assertEquals(darks, program.get("dark/1"));
		assertEquals(new ZPrologGoal(provider.newStructure("dark", x), darks),
				new ZPrologGoal(provider.newStructure("dark", x)).resolve(program, ZPrologRuntime.builtins));

	}

}
