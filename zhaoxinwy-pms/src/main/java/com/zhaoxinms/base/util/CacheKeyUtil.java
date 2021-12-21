package com.zhaoxinms.base.util;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CacheKeyUtil {

    public static String DICTIONARY="dictionary_";

	public String getDictionary() {
		return DICTIONARY;
	}
}
