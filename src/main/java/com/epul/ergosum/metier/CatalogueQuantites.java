package com.epul.ergosum.metier;

public class CatalogueQuantites {
	private int id;
	private int numeroJouet;
	private int quantiteDistribuee;
	private int quantite;

	public CatalogueQuantites(int id, int numeroJouet, int quantiteDistribuee,
			int quantite) {
		this.id = id;
		this.numeroJouet = numeroJouet;
		this.quantiteDistribuee = quantiteDistribuee;
		this.quantite = quantite;
	}

	public CatalogueQuantites() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public int getNumeroJouet() {
		return numeroJouet;
	}

	public void setNumeroJouet(int numeroJouet) {
		this.numeroJouet = numeroJouet;
	}

	public int getQuantiteDistribuee() {
		return quantiteDistribuee;
	}

	public void setQuantiteDistribuee(int quantiteDistribuee) {
		this.quantiteDistribuee = quantiteDistribuee;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
}
