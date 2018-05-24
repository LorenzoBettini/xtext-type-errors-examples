package org.example.expressions.ui.hover

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider
import org.example.expressions.expressions.Expression
import org.example.expressions.expressions.Variable
import org.example.expressions.typing.ExpressionsTypeSystem

class ExpressionsEObjectHoverProvider extends DefaultEObjectHoverProvider {
	@Inject extension ExpressionsTypeSystem
//	@Inject extension ExpressionsInterpreter

	override getHoverInfoAsHtml(EObject o) {
		switch (o) {
			Expression: {
				'''
					<p>
					expression <b>«o.eClass.name»</b> <br>
					type  : <b>«o.inferredType.toString»</b> <br>
					</p>
				'''
			}
			Variable: {
				'''
					<p>
					type  : <b>«o.expression.inferredType.toString»</b> <br>
					</p>
				'''
			}
			default: super.getHoverInfoAsHtml(o)
		}
	}

//	def programHasNoError(EObject o) {
//		Diagnostician.INSTANCE.validate(o.rootContainer).children.empty
//	}
}
