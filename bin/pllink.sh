#!/usr/bin/bash
java -classpath "$(dirname "$(pwd)")/lib/*" io.github.prolobjectlink.prolog.zprolog.ZPrologConsole ${1+"$@"}