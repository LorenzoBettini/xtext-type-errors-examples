package org.example.expressions.ide.contentassist

import com.google.inject.Inject
import org.eclipse.xtext.CrossReference
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider
import org.example.expressions.expressions.Expression

class ExpressionsIdeContentProposalProvider extends IdeContentProposalProvider {

	@Inject extension ExpressionsContentProposalHelper

	override protected getCrossrefFilter(CrossReference reference, ContentAssistContext context) {
		val elem = context.currentModel
		if (elem instanceof Expression) {
			val valid = elem.validVariableReferences.toSet
			return [ 
				e |
				valid.contains(e.EObjectOrProxy)
			]
		}
		return super.getCrossrefFilter(reference, context)
	}

}
