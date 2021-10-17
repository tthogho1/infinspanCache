package com.example.image.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.CacheCollection;
import org.springframework.stereotype.Service;

import com.example.image.cache.CacheFactory;

@Service
public class ImageCacheService {

	private Cache<String, Object> imageCache;

	public ImageCacheService() {

		this.imageCache = CacheFactory.getCache("image");
	}

	public boolean putThumbnailUrl(String url) {
		
		/*
		 * url : https://images-webcams.windy.com/91/1107778991/daylight/thumbnail/1107778991.jpg
		*/
		String[] urlArray ;
		String jpgName ;
		String id;
 		try {
			urlArray = url.split("/");
			jpgName = urlArray[urlArray.length-1];
			id= jpgName.replace(".jpg","");	
 		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}	
		
		imageCache.put(id, url);
		
		return true;

	}
	
}