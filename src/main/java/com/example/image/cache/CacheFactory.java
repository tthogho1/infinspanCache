package com.example.image.cache;

import java.util.HashMap;
import java.util.Map;

import org.infinispan.Cache;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.commons.marshall.JavaSerializationMarshaller;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.CacheContainerConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class CacheFactory {

	private static EmbeddedCacheManager cacheManager = null;
	private static CacheContainerConfigurationBuilder gbuilder = GlobalConfigurationBuilder.defaultClusteredBuilder()
			.cacheContainer();
	private static String BEAN = "com.example.demo.bean.";
	private static Map<String,Cache> CacheMap = new HashMap<String,Cache>();

	public static Cache<String, Object> getCache(String name) {

		Cache<String, Object> cache = CacheMap.get(name);

		if (cache != null) {
			return cache;
		}

		if (cacheManager == null) {

			gbuilder.serialization().marshaller(new JavaSerializationMarshaller()).allowList().addRegexps(BEAN);
			gbuilder.statistics(true).metrics().gauges(true).histograms(true).jmx().enable();

			cacheManager = new DefaultCacheManager(gbuilder.build(), true);

		}

		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.clustering().cacheMode(CacheMode.DIST_ASYNC).statistics().enable();

		// this.cacheManager.defineConfiguration("local", builder.build());
		cache = cacheManager.administration().withFlags(CacheContainerAdmin.AdminFlag.VOLATILE).getOrCreateCache(name,
				builder.build());

		// over write ???
		CacheMap.put(name, cache);
		return cache;
	}
}
