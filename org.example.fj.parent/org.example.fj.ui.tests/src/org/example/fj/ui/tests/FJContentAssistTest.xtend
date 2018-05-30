package org.example.fj.ui.tests

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.ui.testing.AbstractContentAssistTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(FJUiInjectorProvider))
class FJContentAssistTest extends AbstractContentAssistTest {

	@Test
	def void testMembers() {
		newBuilder.append(
		'''
		class Object {}
		class A {
			Object f;
			Object m() { return new Object(); }
			Object n() { this. '''
		).assertText('f', 'm', 'n')
	}

	@Test
	def void testProtectedMembers() {
		newBuilder.append(
		'''
		class Object {}
		class A1 {
			private Object priv;
			protected Object prot;
			public Object pub;
			
			private Object privM() { return new Object(); }
			protected Object protM() { return new Object(); }
			public Object pubM() { return new Object(); }
		}
		
		class B1 extends A1 {
			Object n() { return new A1(new Object(), new Object(), new Object()).p'''
		).assertText('(', '.', ';', 'prot', 'pub', 'protM', 'pubM')
	}

	@Test
	def void testPrivateMembers() {
		newBuilder.append(
		'''
		class Object {}
		class A1 {
			private Object priv;
			protected Object prot;
			public Object pub;
			
			private Object privM() { return new Object(); }
			protected Object protM() { return new Object(); }
			public Object pubM() { return new Object(); }
		}
		
		class B1 {
			Object n() { return new A1(new Object(), new Object(), new Object()).p'''
		).assertText('(', '.', ';', 'pub', 'pubM')
	}

}