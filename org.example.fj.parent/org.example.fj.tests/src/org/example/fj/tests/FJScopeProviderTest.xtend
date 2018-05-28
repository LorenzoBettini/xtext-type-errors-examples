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

@RunWith(XtextRunner)
@InjectWith(FJInjectorProvider)
class FJScopeProviderTest {
	@Inject extension
	ParseHelper<FJProgram> parseHelper

	@Inject extension
	ValidationTestHelper
	
	@Test
	def void testScopeForMembers() {
		val result = '''
			class Object {}
			class A extends Object {
				public Object m() {
					return new Object();
				}
			}
			class B extends A {
				public B n() {
					return new B();
				}
			}
			new B().n().m()
		'''.parse
		result.assertNoErrors
	}

	@Test
	def void testScopeForMembersOnThis() {
		val result = '''
			class Object {
				
			}
			
			class Pair extends Object {
				private Object fst;
				private Object snd;
				
				public Object getFst() {
					return this.fst;
				}
				public Object getSnd() {
					return (Object) this.snd;
				}
			}
			
			new Pair(new Object(), new Object()).getFst()
		'''.parse
		result.assertNoErrors
	}
}
