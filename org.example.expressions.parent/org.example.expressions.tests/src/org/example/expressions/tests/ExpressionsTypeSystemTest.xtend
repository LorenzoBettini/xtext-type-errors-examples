/*
 * generated by Xtext 2.10.0
 */
package org.example.expressions.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.example.expressions.expressions.Expression
import org.example.expressions.expressions.ExpressionsModel
import org.example.expressions.typing.ExpressionsType
import org.junit.Test
import org.junit.runner.RunWith


import static extension org.junit.Assert.*
import static org.example.expressions.typing.ExpressionsTypeSystem.*
import org.example.expressions.typing.ExpressionsTypeSystem
import org.example.expressions.expressions.EvalExpression
import org.example.expressions.expressions.Variable

@RunWith(XtextRunner)
@InjectWith(ExpressionsInjectorProvider)
class ExpressionsTypeSystemTest {

	@Inject extension ParseHelper<ExpressionsModel>
	@Inject extension ExpressionsTypeSystem

	@Test def void intConstant() { "10".assertEvalType(INT_TYPE) }
	@Test def void stringConstant() { "'foo'".assertEvalType(STRING_TYPE) }
	@Test def void boolConstant() { "true".assertEvalType(BOOL_TYPE) }

	@Test def void notExp() { "!true".assertEvalType(BOOL_TYPE) }

	@Test def void multiExp() { "1 * 2".assertEvalType(INT_TYPE) }
	@Test def void divExp() { "1 / 2".assertEvalType(INT_TYPE) }

	@Test def void minusExp() { "1 - 2".assertEvalType(INT_TYPE) }

	@Test def void comparisonExp() { "1 < 2".assertEvalType(BOOL_TYPE) }
	@Test def void equalityExp() { "1 == 2".assertEvalType(BOOL_TYPE) }
	@Test def void andExp() { "true && false".assertEvalType(BOOL_TYPE) }
	@Test def void orExp() { "true || false".assertEvalType(BOOL_TYPE) }

	@Test def void numericPlus() { "1 + 2".assertEvalType(INT_TYPE) }
	@Test def void stringPlus() { "'a' + 'b'".assertEvalType(STRING_TYPE) }
	@Test def void numAndStringPlus() { "'a' + 2".assertEvalType(STRING_TYPE) }
	@Test def void numAndStringPlus2() { "2 + 'a'".assertEvalType(STRING_TYPE) }
	@Test def void boolAndStringPlus() { "'a' + true".assertEvalType(STRING_TYPE) }
	@Test def void boolAndStringPlus2() { "false + 'a'".assertEvalType(STRING_TYPE) }

	@Test def void incompletePlusRight() { "1 + ".assertEvalType(INT_TYPE) }

	@Test def void varWithExpression() { "var i = 0".assertType(INT_TYPE) }

	@Test def void varRef() { "var i = 0 eval i".assertType(INT_TYPE) }
	@Test def void varRefToVarDefinedAfter() { "var i = j var j = i".assertType(null) }

	@Test def void testIsInt() { 
		(ExpressionsTypeSystem.INT_TYPE).isIntType.assertTrue
	}

	@Test def void testIsString() { 
		(ExpressionsTypeSystem.STRING_TYPE).isStringType.assertTrue
	}

	@Test def void testIsBool() { 
		(ExpressionsTypeSystem.BOOL_TYPE).isBoolType.assertTrue
	}

	@Test def void testNotIsInt() { 
		(ExpressionsTypeSystem.STRING_TYPE).isIntType.assertFalse
	}

	@Test def void testNotIsString() { 
		(ExpressionsTypeSystem.INT_TYPE).isStringType.assertFalse
	}

	@Test def void testNotIsBool() { 
		(ExpressionsTypeSystem.INT_TYPE).isBoolType.assertFalse
	}

	@Test def void testEverythingIsAssignableToString() {
		INT_TYPE.isAssignableTo(STRING_TYPE).assertTrue
		BOOL_TYPE.isAssignableTo(STRING_TYPE).assertTrue
		STRING_TYPE.isAssignableTo(STRING_TYPE).assertTrue
	}

	@Test def void testOnlyIntIsAssignableToInt() {
		INT_TYPE.isAssignableTo(INT_TYPE).assertTrue
		BOOL_TYPE.isAssignableTo(INT_TYPE).assertFalse
		STRING_TYPE.isAssignableTo(INT_TYPE).assertFalse
	}

	@Test def void testOnlyBooleanIsAssignableToBoolean() {
		INT_TYPE.isAssignableTo(BOOL_TYPE).assertFalse
		BOOL_TYPE.isAssignableTo(BOOL_TYPE).assertTrue
		STRING_TYPE.isAssignableTo(BOOL_TYPE).assertFalse
	}

	@Test def void mulExpectsInt() { 
		"true * false".assertExpectedType(INT_TYPE)
	}

	@Test def void divExpectsInt() { 
		"true / false".assertExpectedType(INT_TYPE)
	}

	@Test def void minusExpectsInt() { 
		"true - false".assertExpectedType(INT_TYPE)
	}

	@Test def void andExpectsBoolean() { 
		"true && 0".assertExpectedType(BOOL_TYPE)
	}

	@Test def void orExpectsBoolean() { 
		"true || 0".assertExpectedType(BOOL_TYPE)
	}

	@Test def void notExpectsBoolean() { 
		"!0".assertExpectedType(BOOL_TYPE)
	}

	@Test def void plusWithoutStringsExpectsInt() { 
		"true + false".assertExpectedType(INT_TYPE)
	}

	@Test def void plusWithStringsHasNoExpectation() { 
		'"a string" + false'.assertExpectedType(null)
		'false + "a string"'.assertExpectedType(null)
	}

	@Test def void noExpectation() { 
		("var i = 0".parse.elements.last as Variable).expression.expectedType.assertNull
	}

	def private assertExpectedType(CharSequence input, ExpressionsType expectation) {
		val lastElement = ("eval " + input).parse.elements.last as EvalExpression
		val rightMostExpression = lastElement.eAllContents.filter(Expression).last
		
		expectation.assertEquals
			(rightMostExpression.expectedType)
	}

	def private assertEvalType(CharSequence input, ExpressionsType expectedType) {
		("eval " + input).assertType(expectedType)
	}

	def private assertType(CharSequence input, ExpressionsType expectedType) {
		input.parse.elements.last.
			expression.assertType(expectedType)
	}

	def private assertType(Expression e, ExpressionsType expectedType) {
		expectedType.assertSame(e.inferredType)
	}

}
