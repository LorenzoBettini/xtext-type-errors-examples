package org.example.expressions.typing

import com.google.inject.Inject
import org.eclipse.xtext.util.IResourceScopeCache
import org.example.expressions.ExpressionsModelUtil
import org.example.expressions.expressions.And
import org.example.expressions.expressions.BoolConstant
import org.example.expressions.expressions.Comparison
import org.example.expressions.expressions.Equality
import org.example.expressions.expressions.Expression
import org.example.expressions.expressions.IntConstant
import org.example.expressions.expressions.Minus
import org.example.expressions.expressions.MulOrDiv
import org.example.expressions.expressions.Not
import org.example.expressions.expressions.Or
import org.example.expressions.expressions.Plus
import org.example.expressions.expressions.StringConstant
import org.example.expressions.expressions.VariableRef

class ExpressionsTypeSystem {
	public static val STRING_TYPE = new StringType
	public static val INT_TYPE = new IntType
	public static val BOOL_TYPE = new BoolType

	@Inject extension ExpressionsModelUtil

	@Inject IResourceScopeCache cache

	def isStringType(ExpressionsType type) {
		type === STRING_TYPE
	}

	def isIntType(ExpressionsType type) {
		type === INT_TYPE
	}

	def isBoolType(ExpressionsType type) {
		type === BOOL_TYPE
	}

	def ExpressionsType inferredType(Expression e) {
		switch (e) {
			// trivial cases
			StringConstant: STRING_TYPE
			IntConstant: INT_TYPE
			BoolConstant: BOOL_TYPE
			Not: BOOL_TYPE
			MulOrDiv: INT_TYPE
			Minus: INT_TYPE
			Comparison: BOOL_TYPE
			Equality: BOOL_TYPE
			And: BOOL_TYPE
			Or: BOOL_TYPE
			// recursive case
			Plus: {
				val leftType = e.left.inferredType
				val rightType = e.right?.inferredType
				if (leftType.isStringType || rightType.isStringType)
					STRING_TYPE
				else
					INT_TYPE
			}
			// variable reference case
			VariableRef: {
				// avoid possible infinite recursion
				if (e.isVariableDefinedBefore) {
					val variable = e.variable
					// use a pair as the key, not to conflict with the
					// use of cache we make in ExpressionsModelUtil
					return cache.get("type" -> variable, variable.eResource) [
						variable.expression.inferredType
					]
				}
			}
		}
	}

	/**
	 * Is type1 conformant to type2, that is, is type1 subtype of type2
	 */
	def boolean isConformantTo(ExpressionsType type1, ExpressionsType type2) {
		return type2.isStringType || type1 === type2
	}

	/**
	 * The expected type or null if there's no expectation
	 */
	def ExpressionsType expectedType(Expression exp) {
		val container = exp.eContainer
		switch (container) {
			Not: BOOL_TYPE
			MulOrDiv: INT_TYPE
			Minus: INT_TYPE
			And: BOOL_TYPE
			Or: BOOL_TYPE
			Plus: {
				val leftType = container.left.inferredType;
				val rightType = container.right?.inferredType;
				if (!leftType.isStringType && !rightType.isStringType) {
					INT_TYPE
				} else {
					STRING_TYPE
				}
			}
		}
	}
}
