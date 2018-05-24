package org.example.expressions.ui.tests

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.ui.testing.AbstractOutlineTest
import org.example.expressions.ui.internal.ExpressionsActivator
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(ExpressionsUiInjectorProvider)
class ExpressionsOutlineTest extends AbstractOutlineTest {

	override protected getEditorId() {
		ExpressionsActivator.ORG_EXAMPLE_EXPRESSIONS_EXPRESSIONS
	}

	@Test
	def void testOutlineOfExpDslFile() {
		'''
			var i = 0
			var s = "a"
			eval i + s
		'''.assertAllLabels(
			'''
				test
				  i : int
				  s : string
			'''
		)
	}

}
