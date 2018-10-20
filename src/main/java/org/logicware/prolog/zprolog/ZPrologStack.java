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

import org.logicware.platform.ArrayStack;
import org.logicware.platform.Stack;

final class ZPrologStack {

	private int maxVarNumber;
	private final Stack<Object> stack;

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		if (stack != null) {
			for (int i = stack.size() - 1; i >= 0; i--) {
				string.append(stack.get(i));
				string.append("\n");
			}
		}
		return "" + string + "";
	}

	ZPrologStack() {
		maxVarNumber = 0;
		stack = new ArrayStack<Object>();
	}

	Object pop() {
		Object object = stack.pop();
		if (object instanceof ZPrologTerm) {
			ZPrologTerm variable = (ZPrologTerm) object;
			if (!variable.isAnonymous()) {
				maxVarNumber--;
			}
		}
		return object;
	}

	void push(Object object) {
		// check Overflow
		stack.push(object);
		if (object instanceof ZPrologTerm) {
			ZPrologTerm variable = (ZPrologTerm) object;
			if (!variable.isAnonymous()) {
				maxVarNumber++;
			}
		}
	}

	Object get(int index) {
		return stack.get(index);
	}

	int size() {
		return stack.size();
	}

	void clear() {
		stack.clear();
	}

	boolean empty() {
		return stack.empty();
	}

	Object remove(int poistion) {
		return stack.remove(poistion);
	}

	boolean remove(Object object) {
		return stack.remove(object);
	}

	boolean contains(Object object) {
		return stack.contains(object);
	}

	int getMaxVarNumber() {
		return maxVarNumber;
	}

}
