package com.aashif.kitchen.exception;

public class ResourceNotFoundException extends RuntimeException{

		 String resourceName;
		 String fieldName;
		 long fieldValue;
		 String fieldvalue;
		public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
			super(String.format("%s found with %s: %s",resourceName,fieldName, fieldValue));
			this.resourceName = resourceName;
			this.fieldName = fieldName;
			this.fieldValue = fieldValue;
		}

		public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
			super(String.format("%s not found with %s: %s",resourceName,fieldName, fieldValue));
			this.resourceName = resourceName;
			this.fieldName = fieldName;
			this.fieldvalue = fieldvalue;
		}	
	
}
