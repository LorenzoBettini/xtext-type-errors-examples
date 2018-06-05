package org.example.expressions.performance.tests

import com.google.inject.Provider
import com.google.inject.Singleton
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.util.IResourceScopeCache
import org.example.expressions.ExpressionsRuntimeModule
import org.example.expressions.tests.ExpressionsInjectorProvider

/**
 * Injector provider for testing without cache.
 * 
 * @author Lorenzo Bettini
 */
class ExpressionsWithoutCacheInjectorProvider extends ExpressionsInjectorProvider {

	@Singleton
	static class NoCache implements IResourceScopeCache {
		
		override clear(Resource res) {

		}
		
		override <T> get(Object key, Resource res, Provider<T> provider) {
			provider.get
		}
		
	}

	override protected createRuntimeModule() {
		new ExpressionsRuntimeModule() {
			def Class<? extends IResourceScopeCache> bindIResourceScopeCache() {
				NoCache
			}
		}
	}

}
