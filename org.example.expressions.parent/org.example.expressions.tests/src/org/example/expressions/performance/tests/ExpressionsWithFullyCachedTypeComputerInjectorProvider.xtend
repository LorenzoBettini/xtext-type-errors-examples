package org.example.expressions.performance.tests

import com.google.inject.Inject
import org.eclipse.xtext.util.IResourceScopeCache
import org.example.expressions.ExpressionsRuntimeModule
import org.example.expressions.expressions.Expression
import org.example.expressions.tests.ExpressionsInjectorProvider
import org.example.expressions.typing.ExpressionsTypeSystem

/**
 * Injector provider for testing the fully cached type computer.
 * 
 * @author Lorenzo Bettini
 */
class ExpressionsWithFullyCachedTypeComputerInjectorProvider extends ExpressionsInjectorProvider {

	static class ExpressionsFullyCachedTypeComputer extends ExpressionsTypeSystem {

		@Inject IResourceScopeCache cache

		override inferredType(Expression e) {
			return cache.get("type" -> e, e.eResource) [
				super.inferredType(e)
			]
		}

	}

	override protected createRuntimeModule() {
		new ExpressionsRuntimeModule() {
			def Class<? extends ExpressionsTypeSystem> bindExpressionsTypeSystem() {
				ExpressionsFullyCachedTypeComputer
			}
		}
	}

}
