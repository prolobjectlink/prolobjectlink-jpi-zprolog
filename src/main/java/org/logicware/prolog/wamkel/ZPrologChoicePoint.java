/*
 * #%L
 * prolobjectlink-db-zprolog
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

/**
 * A choice point is a block information that essentially characterize a
 * interpretation process state. A choice point remains intact the previous
 * interpretation process state from which the current state is derived, so that
 * it may be used later for reconstruct the previous interpretation process
 * state. The process of reconstruction of interpretation process state is known
 * as backtracking. A choice point is instance with the contents of various
 * interpreter register such as program pointer, the goal to resolve and the
 * substitution set for this state. Program pointer is used for restore the
 * prolog program position for backtrack. The goal register is to restore the
 * goal to resolve immediately after backtracking and the substitution set is to
 * restore the previous substitution.
 * 
 * @author Jose Zalacain.
 * @since 1.0
 */
final class ZPrologChoicePoint {

	private final ZPrologGoal current_goal;
	private final ZPrologGoal next_goal;

	ZPrologChoicePoint(ZPrologGoal current_goal) {
		this(current_goal, null);
	}

	ZPrologChoicePoint(ZPrologGoal current_goal, ZPrologGoal next_goal) {
		this.current_goal = current_goal;
		this.next_goal = next_goal;
	}

	public ZPrologGoal get_current_goal() {
		return current_goal;
	}

	public ZPrologGoal get_next_goal() {
		return next_goal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((current_goal == null) ? 0 : current_goal.hashCode());
		result = prime * result + ((next_goal == null) ? 0 : next_goal.hashCode());
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
		ZPrologChoicePoint other = (ZPrologChoicePoint) obj;
		if (current_goal == null) {
			if (other.current_goal != null)
				return false;
		} else if (!current_goal.equals(other.current_goal))
			return false;
		if (next_goal == null) {
			if (other.next_goal != null)
				return false;
		} else if (!next_goal.equals(other.next_goal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ZPrologChoicePoint [current_goal=" + current_goal + ", next_goal=" + next_goal + "]";
	}

}
