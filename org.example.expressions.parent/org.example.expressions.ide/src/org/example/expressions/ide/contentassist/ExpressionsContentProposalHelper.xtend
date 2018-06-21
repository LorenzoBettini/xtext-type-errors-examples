package org.example.expressions.ide.contentassist

import com.google.inject.Inject
import org.example.expressions.ExpressionsModelUtil
import org.example.expressions.expressions.Expression
import org.example.expressions.typing.ExpressionsTypeSystem

class ExpressionsContentProposalHelper {

	@Inject extension ExpressionsModelUtil
	@Inject extension ExpressionsTypeSystem

	def getValidVariableReferences(Expression elem) {
		val elemType = elem.inferredType
		return elem.variablesDefinedBefore
			.filter[
				variable |
				variable.expression.inferredType.isConformantTo(elemType)
			]
	}

}
