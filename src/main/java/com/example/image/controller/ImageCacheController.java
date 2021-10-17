package com.example.image.controller;

import org.infinispan.commons.dataconversion.MediaType;
//import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
//import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.image.service.ImageCacheService;

import lombok.Getter;
import lombok.Setter;

//import com.example.image.cache.service.ImageCacheService;

@RestController
public class ImageCacheController {

	@Autowired
	ImageCacheService imageCacheService;
	
	@RequestMapping(value = "/postThumnailUrl", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_TYPE)
	public String postThumnailUrl(@RequestBody Thumbnail url) {

		System.out.println(url.getUrl());
		String status;
		if (imageCacheService.putThumbnailUrl(url.getUrl())) {
			status = "ok";
		}else {
			status = "ng";
		}
		
		return status ;
	}

}

@Getter
@Setter
class Thumbnail {
  private String url;
}