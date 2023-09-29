package es.exmaster.expass.test;

import es.exmaster.expass.password.Password;

public class TestPassword {
	public static void main(String[] args) {
		Password p = new Password("jomaamga@gmail.com","Google","123456789.<Ab>");
		print(p.getUser());
		print(p.getPassword());
		print(p.getSite());
		print(p.getStrength());
	}
	
	private static void print(Object o) {
		System.out.println(o);
	}
}
