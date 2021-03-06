/*
 * generated by Xtext 2.13.0
 */
package org.example.fj.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.example.fj.fj.FJProgram
import org.junit.Test
import org.junit.runner.RunWith
import static org.example.fj.fj.FjPackage.Literals.*
import org.example.fj.typing.FJTypeSystem
import org.eclipse.emf.ecore.EObject

import static extension org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(FJInjectorProvider)
class FJValidatorTest {
	@Inject extension
	ParseHelper<FJProgram> parseHelper

	@Inject extension
	ValidationTestHelper
	
	@Test
	def void testValidCast() {
		val result = '''
			class A {}
			class B extends A {}
			(A) new B()
		'''.parse
		result.assertNoErrors
	}

	@Test
	def void testWrongCast() {
		val result = '''
			class A {}
			class B {}
			(A) new B()
		'''.parse
		result.assertError(
			FJ_CAST,
			null,
			"Cannot cast from B to A"
		)
	}

	@Test
	def void testValidMethodBody() {
		val result = '''
			class A {}
			class B extends A {
				A m() {
					return new B();
				}
			}
		'''.parse
		result.assertNoErrors
	}

	@Test
	def void testNotValidMethodBody() {
		val result = '''
			class A {}
			class B extends A {
				B m() {
					return new A();
				}
			}
		'''.parse
		result.assertError(
			FJ_NEW,
			null,
			"Type mismatch: cannot convert from A to B"
		)
	}

	@Test
	def void testNotValidMethodInvocation() {
		val result = '''
			class Object {}
			class A {
				public Object f;
				public Object m() {
					return new Object();
				}
			}
			
			new A(new Object()).f()
		'''.parse
		result.assertError(
			FJ_MEMBER_SELECTION,
			null,
			"Method invocation on a field"
		)
	}

	@Test
	def void testNotValidFieldSelection() {
		val result = '''
			class Object {}
			class A {
				public Object f;
				public Object m() {
					return new Object();
				}
			}
			
			new A(new Object()).m
		'''.parse
		result.assertError(
			FJ_MEMBER_SELECTION,
			null,
			"Field selection on a method"
		)
	}

	@Test
	def void testInvalidAccess() {
		val result = '''
			class Object {}
			class A {
				private Object privF;
				protected Object protF;
				public Object publF;
			}
			class B extends A {
				public Object m() {
					return this.privF;
				}
				public Object n() {
					return this.protF;
				}
			}
			
			new B(new Object(), new Object(), new Object()).protF
		'''.parse
		result.assertError(
			FJ_MEMBER_SELECTION,
			null,
			"The private member privF is not accessible here"
		)
		result.assertError(
			FJ_MEMBER_SELECTION,
			null,
			"The protected member protF is not accessible here"
		)
	}

	@Test
	def void testInvalidThis() {
		val result = '''
			this
		'''.parse
		result.assertError(
			FJ_THIS,
			FJTypeSystem.TTHIS,
			"'this' cannot be used in the current context"
		)
	}

	@Test
	def void testNoDuplicateElements() {
		'''
		class A {
			private A a;
		}
		class B {
			private A a;
		}
		'''.parse.assertNoErrors
	}

	@Test
	def void testDuplicateClasses() {
		'''
		class B {}
		class A {
			private A a;
		}
		class A {
			private B b;
		}
		'''.parse.assertErrorMessages("Duplicate FJClass 'A',Duplicate FJClass 'A'")
	}

	@Test
	def void testDuplicateMembers() {
		'''
		class B {}
		class A {
			private A a;
			private B a;
			private A b;
		}
		'''.parse.assertErrorMessages("Duplicate FJTypedElement 'a' in FJClass 'A',Duplicate FJTypedElement 'a' in FJClass 'A'")
	}

	def private assertErrorMessages(EObject model, CharSequence expected) {
		val messages = model.validate.map[message].join(",")
		expected.assertEquals(messages)
	}
}
