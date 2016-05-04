/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class pract {

	public static void main(String[] args) {

		List<Developer> listDevs = getDevelopers();
		
		System.out.println("Before Sort:");
		for (Developer developer : listDevs) {
			System.out.println(developer.getName());
		}
		System.out.println();
		
		//lambda here!
		listDevs.sort((Developer o1, Developer o2)->o1.getAge()-o2.getAge());
	
		//java 8 only, lambda also, to print the List
		System.out.println("After Sort:");
		for (Developer developer : listDevs) {
			System.out.println(developer.getName());
		}
		
	}

	private static List<Developer> getDevelopers() {

		List<Developer> result = new ArrayList<Developer>();

		result.add(new Developer("mkyong",  33));
		result.add(new Developer("alvin", 20));
		result.add(new Developer("jason",  10));
		result.add(new Developer("iris", 55));
		
		return result;

	}
	
}