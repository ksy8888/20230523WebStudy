package com.sist.controller;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)

/*
 	@  => TYPE
 	class class_name {
 		@ => FIELD
 		private A a;
 		
 		@ => CONSTRUCTOR
 		public class_name() {
 		}
 		@METHOD 
 		public void display() {
 		}
 	}
 
 Ex)
 	@RequestMapping("list.do") >> 구분이 있어야 display(i) 메소드 찾음
 	public void display1() {
 	}
 	@RequestMapping
	public void display2() {
 	}
 	@RequestMapping
 	public void display3() {
 	}
 	@RequestMapping
 	public void display4() {
 	}
 	@RequestMapping
 	public void display5() {
 	} 		
 */

public @interface RequestMapping {
	public String value();
}
