package org.example.fj.typing

import org.example.fj.fj.FJClass
import org.eclipse.xsemantics.runtime.StringRepresentation

class FJStringRepresentation extends StringRepresentation {
	def stringRep(FJClass c) {
		return c.name
	}
}
