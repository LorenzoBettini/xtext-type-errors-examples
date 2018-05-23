/*
 * generated by Xtext 2.13.0
 */
package org.example.expressions.validation

import com.google.inject.Inject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.validation.Check
import org.example.expressions.ExpressionsModelUtil
import org.example.expressions.expressions.Comparison
import org.example.expressions.expressions.Equality
import org.example.expressions.expressions.Expression
import org.example.expressions.expressions.ExpressionsPackage
import org.example.expressions.expressions.VariableRef
import org.example.expressions.typing.ExpressionsType
import org.example.expressions.typing.ExpressionsTypeSystem

/**
 * This class contains custom validation rules. 
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class ExpressionsValidator extends AbstractExpressionsValidator {

	protected static val ISSUE_CODE_PREFIX = "org.example.expressions."
	public static val FORWARD_REFERENCE = ISSUE_CODE_PREFIX + "ForwardReference"
	public static val TYPE_MISMATCH = ISSUE_CODE_PREFIX + "TypeMismatch"

	@Inject extension ExpressionsModelUtil
	@Inject extension ExpressionsTypeSystem

	@Check
	def void checkForwardReference(VariableRef varRef) {
		val variable = varRef.getVariable()
		if (!varRef.isVariableDefinedBefore)
			error("variable forward reference not allowed: '" + variable.name + "'",
				ExpressionsPackage.eINSTANCE.variableRef_Variable,
				FORWARD_REFERENCE,
				variable.name)
	}

	@Check
	def void checkTypeConformance(Expression exp) {
		val actualType = exp.inferredType
		val expectedType = exp.expectedType
		
		if (expectedType !== null &&
				actualType !== null &&
				!actualType.isAssignableTo(expectedType)) {
			error("expected " +
					expectedType + ", but was " +
					actualType,
					exp.eContainer,
					exp.eContainingFeature,
					TYPE_MISMATCH)
		}
	}

	@Check def checkType(Equality equality) {
		val leftType = getTypeAndCheckNotNull(equality.left, ExpressionsPackage.Literals.EQUALITY__LEFT)
		val rightType = getTypeAndCheckNotNull(equality.right, ExpressionsPackage.Literals.EQUALITY__RIGHT)
		checkExpectedSame(leftType, rightType)
	}

	@Check def checkType(Comparison comparison) {
		val leftType = getTypeAndCheckNotNull(comparison.left, ExpressionsPackage.Literals.COMPARISON__LEFT)
		val rightType = getTypeAndCheckNotNull(comparison.right, ExpressionsPackage.Literals.COMPARISON__RIGHT)
		checkExpectedSame(leftType, rightType)
		checkNotBoolean(leftType, ExpressionsPackage.Literals.COMPARISON__LEFT)
		checkNotBoolean(rightType, ExpressionsPackage.Literals.COMPARISON__RIGHT)
	}

	def private checkExpectedSame(ExpressionsType left, ExpressionsType right) {
		if (right !== null && left !== null && right != left) {
			error("expected the same type, but was " + left + ", " + right,
				ExpressionsPackage.Literals.EQUALITY.getEIDAttribute(), TYPE_MISMATCH)
		}
	}

	def private checkNotBoolean(ExpressionsType type, EReference reference) {
		if (type.isBoolType) {
			error("cannot be boolean", reference, TYPE_MISMATCH)
		}
	}

	def private ExpressionsType getTypeAndCheckNotNull(Expression exp, EReference reference) {
		val type = exp?.inferredType
		if (type === null)
			error("null type", reference, TYPE_MISMATCH)
		return type;
	}
}
