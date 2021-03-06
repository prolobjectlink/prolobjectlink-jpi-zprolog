/*
 * #%L
 * prolobjectlink-jpi-tuprolog
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

import io.github.prolobjectlink.prolog.AbstractConsole;
import io.github.prolobjectlink.prolog.PrologConsole;

public class ZPrologConsole extends AbstractConsole implements PrologConsole {

	public ZPrologConsole() {
		super(new ZProlog());
	}

	public static void main(String[] args) {
		new ZPrologConsole().run(args);
	}

}
