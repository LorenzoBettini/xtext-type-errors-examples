package org.example.expressions.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.example.expressions.expressions.ExpressionsModel
import org.example.expressions.expressions.ExpressionsPackage
import org.example.expressions.typing.ExpressionsType
import org.example.expressions.validation.ExpressionsValidator
import org.junit.Test
import org.junit.runner.RunWith


import static extension org.junit.Assert.*
import static org.example.expressions.typing.ExpressionsTypeSystem.*

@RunWith(XtextRunner)
@InjectWith(ExpressionsInjectorProvider)
class ExpressionsValidatorTest {

	@Inject extension ParseHelper<ExpressionsModel>
	@Inject extension ValidationTestHelper

	@Test
	def void testForwardReferenceInExpression() {
		'''var i = 1 eval j+i var j = 10'''.parse => [
			assertError(
				ExpressionsPackage.eINSTANCE.variableRef,
				ExpressionsValidator.FORWARD_REFERENCE,
				"variable forward reference not allowed: 'j'"
			)
			// check that's the only error
			assertEquals("errors: " + validate, 1, validate.size)
		]
	}

	@Test
	def void testNoForwardReference() {
		'''var j = 10 var i = j'''.parse.assertNoErrors
	}

	@Test
	def void testWrongNotType() {
		"!10".assertType(INT_TYPE, BOOL_TYPE)
	}

	@Test
	def void testWrongMulOrDivType() {
		"10 * true".assertType(BOOL_TYPE, INT_TYPE)
		"'10' / 10".assertType(STRING_TYPE, INT_TYPE)
	}

	@Test
	def void testWrongMinusType() {
		"10 - true".assertType(BOOL_TYPE, INT_TYPE)
		"'10' - 10".assertType(STRING_TYPE, INT_TYPE)
	}

	@Test
	def void testWrongAndType() {
		"10 && true".assertType(INT_TYPE, BOOL_TYPE)
		"false && '10'".assertType(STRING_TYPE, BOOL_TYPE)
	}

	@Test
	def void testWrongOrType() {
		"10 || true".assertType(INT_TYPE, BOOL_TYPE)
		"false || '10'".assertType(STRING_TYPE, BOOL_TYPE)
	}

	@Test
	def void testWrongEqualityType() {
		"10 == true".assertSameType(INT_TYPE, BOOL_TYPE)
		"false != '10'".assertSameType(BOOL_TYPE, STRING_TYPE)
	}

	@Test
	def void testWrongComparisonType() {
		"10 < '1'".assertSameType(INT_TYPE, STRING_TYPE)
		"'10' > 10".assertSameType(STRING_TYPE, INT_TYPE)
	}

	@Test
	def void testWrongBooleanComparison() {
		"10 < true".assertNotBooleanType
		"false > 0".assertNotBooleanType
		"false > true".assertNotBooleanType
	}

	@Test
	def void testWrongBooleanPlus() {
		"10 + true".assertType(BOOL_TYPE, INT_TYPE)
		"false + 0".assertType(BOOL_TYPE, INT_TYPE)
		"false + true".assertType(BOOL_TYPE, INT_TYPE)
	}

	@Test
	def void testNoDuplicateVars() {
		'''
		var i = 0
		var j = 0
		'''.parse.assertNoErrors
	}

	@Test
	def void testDuplicateVars() {
		val prog = '''
		var i = 0
		var i = 0
		'''.parse
		val messages = prog.validate.map[message].join(",")
		"Duplicate AbstractElement 'i',Duplicate AbstractElement 'i'".assertEquals(messages)
	}

	def void assertType(CharSequence input, 
			ExpressionsType expectedWrongType,
			ExpressionsType expectedActualType) {
		("eval " + input).parse.
			assertError(ExpressionsPackage.eINSTANCE.expression,
				ExpressionsValidator.TYPE_MISMATCH,
				"expected " + expectedActualType
					+ ", but was " + expectedWrongType)
	}

	def void assertSameType(CharSequence input, 
			ExpressionsType expectedLeft,
			ExpressionsType expectedRight) {
		("eval " + input).parse.
			assertError(ExpressionsPackage.eINSTANCE.expression,
				ExpressionsValidator.TYPE_MISMATCH,
				"expected the same type, but was " + expectedLeft + ", " +
				expectedRight)
	}

	def void assertNotBooleanType(CharSequence input) {
		("eval " + input).parse.
			assertError(ExpressionsPackage.eINSTANCE.expression,
				ExpressionsValidator.TYPE_MISMATCH,
				"cannot be boolean")
	}
}
