/*
 * generated by Xtext 2.14.0
 */
package org.example.expressions.ide

import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider
import org.example.expressions.ide.contentassist.ExpressionsIdeContentProposalProvider

/**
 * Use this class to register ide components.
 */
class ExpressionsIdeModule extends AbstractExpressionsIdeModule {
	def Class<? extends IdeContentProposalProvider> bindIdeContentProposalProvider() {
		return ExpressionsIdeContentProposalProvider
	}
}
