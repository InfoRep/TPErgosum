package com.epul.ergosum.metier.gestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.epul.ergosum.meserreurs.MonException;
import com.epul.ergosum.metier.Catalogue;
import com.epul.ergosum.metier.CatalogueQuantites;
import com.epul.ergosum.metier.Categorie;
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
			
			//TODO ? comporte
			
			mesCatalogues.add(c);
			
			index = index + 2; //2 champs
		}
		
		return mesCatalogues;
	}
	
	/**
	 * Rechercher un catalogue selon son id
	 * @param parameter : annee
	 * @return Catalogue
	 * @throws MonException
	 */
	public static Catalogue rechercher(String parameter) throws MonException {
		if (parameter != null && !parameter.trim().isEmpty())
		{
			String sql = "SELECT * FROM catalogue WHERE annee='"+parameter.trim()+"'";
			System.out.println(sql);
			
			List<Object> rs = DialogueBd.lecture(sql);
			
			if (rs.size() > 0)
			{
				Catalogue c = new Catalogue();
				
				c.setAnnee(Integer.valueOf(rs.get(0).toString()));
				c.setQuantiteDistribuee(Integer.valueOf(rs.get(1).toString()));
				
				//TODO ? comporte
				
				return c;
			} 
		}
		
		return null;
	}

	/**
	 * Modifier un catalogue
	 * @param leCatalogue: Catalogue à modifier
	 * @throws MonException
	 */
	public static void modifier(Catalogue leCatalogue) throws MonException {
		if (leCatalogue != null)
		{
			String sql = "";
			
			sql += "UPDATE catalogue SET ";
			sql += "quantiteDistribuee = '"+leCatalogue.getQuantiteDistribuee()+"' ";
			sql += "WHERE annee = '"+leCatalogue.getAnnee()+"'";
			
			System.out.println(sql);
			DialogueBd.insertionBD(sql);
		}
	}

	public static List<CatalogueQuantites> listerCatalogueQuantites(int annee, int interval, String codecat) throws MonException {
		List<CatalogueQuantites> list = new ArrayList<CatalogueQuantites>();
		
		int a = annee + interval;
		
		String sql = "SELECT c.annee, c.quantiteDistribuee, co.quantite, j.numero "
				+ "FROM catalogue c, comporte co, jouet j "
				+ "WHERE c.annee = co.annee and c.annee >= '"+annee+"' and c.annee <= '"+a+"' "
						+ "and j.numero = co.numero ";
		
		if (codecat != null || (codecat != null && !codecat.trim().isEmpty()))
		{
			sql += "and j.codecateg ='"+codecat+"' ";
		}
		
		sql += "ORDER BY c.annee asc";
		
		System.out.println(sql);
		
		List<Object> rs = DialogueBd.lecture(sql);
		
		int index = 0;
		while(index < rs.size())
		{
			CatalogueQuantites cq = new CatalogueQuantites();
			cq.setId(Integer.valueOf(rs.get(index+0).toString()));
			cq.setQuantiteDistribuee(Integer.valueOf(rs.get(index+1).toString()));
			cq.setQuantite(Integer.valueOf(rs.get(index+2).toString()));
			cq.setNumeroJouet(Integer.valueOf(rs.get(index+3).toString()));
			
			list.add(cq);
			
			index = index + 4;
		} 
		
		return list;
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
