package com.epul.ergosum.metier.gestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.epul.ergosum.meserreurs.MonException;
import com.epul.ergosum.metier.Catalogue;
import com.epul.ergosum.metier.CatalogueQuantites;
import com.epul.ergosum.metier.Categorie;
import com.epul.ergosum.metier.Jouet;
import com.epul.ergosum.metier.Trancheage;
import com.epul.ergosum.persistance.DialogueBd;

public class GestionCatalogue {
	/**
	 * Liste de tous les catalogues
	 * @return Liste de catalogue
	 * @throws MonException
	 */
	public static List<Catalogue> lister() throws MonException {
		List<Object> rs;
		List<Catalogue> mesCatalogues = new ArrayList<Catalogue>();
	

		String sql = "SELECT * FROM catalogue ORDER BY annee asc";
		
		System.out.println(sql); //TODO

		rs = DialogueBd.lecture(sql);
		int index = 0;
		while(index < rs.size())
		{
			Catalogue c = new Catalogue();
	
			c.setAnnee(Integer.valueOf(rs.get(index+0).toString()));
			c.setQuantiteDistribuee(Integer.valueOf(rs.get(index+1).toString()));
			
			//TODO Gérer Comporte ??
			
			mesCatalogues.add(c);
			
			index = index + 2; //2 champs
		}
		
		return mesCatalogues;
	}
	
	public static Catalogue rechercher(String parameter) throws MonException {
		// TODO Auto-generated method stub
		return null;
	}

		
	public static void modifier(Catalogue leCatalogue) throws MonException {
		if (leCatalogue != null)
		{
			
		}
		
	}

	public static List<CatalogueQuantites> listerCatalogueQuantites(int parseInt, int parseInt2) throws MonException {
		// TODO Auto-generated method stub
		return null;
	}

	public static HashMap<Categorie, Integer> rechercherDictionnaire(String anneeCatalogue) throws MonException {
		HashMap<Categorie, Integer> hashCatInt = new HashMap<Categorie, Integer>();
		
		List<Object> rs;
		
		String sql = "SELECT cat.codecateg, cat.libcateg, SUM(co.quantite) FROM catalogue ca, comporte co, jouet j, categorie cat ";
		sql += "WHERE ca.annee = co.annee and j.numero = co.numero and cat.codecateg = j.codecateg and ca.annee = '"+anneeCatalogue+"'";
		sql += "GROUP BY cat.codecateg ";
		
		System.out.println(sql); //TODO

		rs = DialogueBd.lecture(sql);
		int index = 0;
		while(index < rs.size())
		{
			Categorie c = new Categorie();
			c.setCodecateg(rs.get(index+0).toString());
			c.setLibcateg(rs.get(index+1).toString());
			//TODO setJouet ???
	
			hashCatInt.put(c, Integer.valueOf(rs.get(index+2).toString())); //Categorie, somme quantite
			
			index = index + 3; 
		}
		
		return hashCatInt;
	}
}
