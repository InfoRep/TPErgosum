package com.epul.ergosum.metier;

// Generated 24 avr. 2014 11:38:54 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Categorie generated by hbm2java
 */
public class Categorie implements java.io.Serializable {

	private String codecateg;
	private String libcateg;
	private Set jouets = new HashSet(0);

	public Categorie() {
	}

	public Categorie(String codecateg) {
		this.codecateg = codecateg;
	}

	public Categorie(String codecateg, String libcateg, Set jouets) {
		this.codecateg = codecateg;
		this.libcateg = libcateg;
		this.jouets = jouets;
	}

	public String getCodecateg() {
		return this.codecateg;
	}

	public void setCodecateg(String codecateg) {
		this.codecateg = codecateg;
	}

	public String getLibcateg() {
		return this.libcateg;
	}

	public void setLibcateg(String libcateg) {
		this.libcateg = libcateg;
	}

	public Set getJouets() {
		return this.jouets;
	}

	public void setJouets(Set jouets) {
		this.jouets = jouets;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		
		if (obj instanceof Categorie)
		{
			Categorie c = (Categorie)obj;
			return c.getCodecateg().contentEquals(this.getCodecateg());
		}
		
		return false;
	}

}
