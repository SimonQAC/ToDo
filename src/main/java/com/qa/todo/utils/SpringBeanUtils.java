package com.qa.todo.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.annotation.Bean;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SpringBeanUtils {
	

	public static void mergeObject(Object source, Object target) {
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
	}
	
	private static String [] getNullPropertyNames(Object source) {
		final BeanWrapper wrappedSourceObject = new BeanWrapperImpl(source);
		
		Set<String> propertyNames = new HashSet<>();
		for(PropertyDescriptor propertyDescriptors : wrappedSourceObject.getPropertyDescriptors()) {
			if (wrappedSourceObject.getPropertyValue(propertyDescriptors.getName()) == null)
				propertyNames.add(propertyDescriptors.getName());
		}
		return propertyNames.toArray(new String[propertyNames.size()]);
	}
	
	   @Bean
	   public ModelMapper modelMapper() {
	      ModelMapper modelMapper = new ModelMapper();
	      return modelMapper;
	   }
}
