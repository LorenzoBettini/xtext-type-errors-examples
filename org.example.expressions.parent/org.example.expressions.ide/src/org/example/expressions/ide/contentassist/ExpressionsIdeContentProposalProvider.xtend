package org.example.expressions.ide.contentassist

import com.google.inject.Inject
import org.eclipse.xtext.CrossReference
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider
import org.example.expressions.ExpressionsModelUtil
import org.example.expressions.expressions.Expression
import org.example.expressions.typing.ExpressionsTypeSystem

class ExpressionsIdeContentProposalProvider extends IdeContentProposalProvider {

	@Inject extension ExpressionsModelUtil
	@Inject extension ExpressionsTypeSystem

	override protected getCrossrefFilter(CrossReference reference, ContentAssistContext context) {
		val elem = context.currentModel
		if (elem instanceof Expression) {
			val elemType = elem.inferredType
			val valid = elem.variablesDefinedBefore
				.filter[
					variable |
					variable.expression.inferredType.isConformantTo(elemType)
				].toSet
			return [ 
				e |
				valid.contains(e.EObjectOrProxy)
			]
		}
		return super.getCrossrefFilter(reference, context)
	}

}
