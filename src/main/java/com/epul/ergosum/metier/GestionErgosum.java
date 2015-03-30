package com.epul.ergosum.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.epul.ergosum.meserreurs.MonException;
import com.epul.ergosum.persistance.DialogueBd;

public class GestionErgosum {

	public List<Jouet> listerTousLesJouets(int categorieCode, int trancheCode) throws MonException {
		List<Object> rs;
		List<Jouet> mesJouets = new ArrayList<Jouet>();
		
		//Categorie > 0 rechercher la categorie
		Categorie cat = null;
		if (categorieCode > 0)
			cat = rechercherCategorie(String.valueOf(categorieCode));
		
		//Tranche age > 0 rechercher la tranche d'âge
		Trancheage trancheage = null;
		if (trancheCode > 0)
			trancheage = rechercherTrancheage(String.valueOf(trancheCode));

		String sql = "SELECT * FROM jouet ";
		
		//WHERE
		if (categorieCode > 0 || trancheCode > 0)
		{
			sql += "WHERE ";
			if (categorieCode > 0)
			{
				sql += "CODECATEG = '"+categorieCode+"' "; //recherche par cat si code > 0
				if (trancheCode > 0) 
					sql += "AND "; //ajouter le AND si les 2 paramètres sont > 0
			}
			if (trancheCode > 0)
				sql += "CODETRANCHE='"+trancheCode+"' "; //ajouter tranche code
		}
		
		sql += "ORDER BY numero asc";
		
		System.out.println(sql);

		rs = DialogueBd.lecture(sql);
		
		int index = 0;
		/*while(index < rs.size())
		{
			Jouet j = new Jouet();
	
			j.setNumero(rs.get(index+0).toString());
			j.setLibelle(rs.get(index+3).toString());

			if (categorieCode > 0)
				j.setCategorie(cat);
			else {
				j.setCategorie(new Categorie(rs.get(index+1).toString()));
			}
			
			if (trancheCode > 0)
				j.setTrancheage(trancheage);
			else {
				j.setTrancheage(new Trancheage(rs.get(index+2).toString()));
			}
		}*/
		
		return mesJouets;
	}
	
	public List<Jouet> listerTousLesJouets() throws MonException {
		return listerTousLesJouets(0, 0);
	}
	
	public Jouet rechercherJouet(String id) throws MonException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void modifier(Jouet unJouet) throws MonException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Ajouter un jouet
	 * @param unJouet
	 * @throws MonException
	 */
	public void ajouter(Jouet unJouet) throws MonException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Effacer des jouets
	 * @param ids
	 */
	public void effacer(String[] ids) throws MonException {
		// TODO Auto-generated method stub
		
	}

	public Object listerToutesLesCategories() throws MonException  {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Categorie rechercherCategorie(String parameter) throws MonException {
		List<Object> rs;
		
		//Requete SQL Categorie
		rs = DialogueBd.lecture("SELECT * FROM categorie WHERE codecateg ='"+parameter+"'");
		
		Categorie c = null;
		
		int index = 0;
		while(index < rs.size())
		{
			c = new Categorie();
			c.setCodecateg(rs.get(index+0).toString());
			c.setLibcateg(rs.get(index+1).toString());
		}
		
		return c;
	}

	public Object listerToutesLesTranches() throws MonException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Trancheage rechercherTrancheage(String parameter) throws MonException {
		List<Object> rs;
		
		//Requete SQL Categorie
		rs = DialogueBd.lecture("SELECT * FROM trancheage WHERE codetranche ='"+parameter+"'");
		
		Trancheage t = null;
		
		int index = 0;
		while(index < rs.size())
		{
			t = new Trancheage();
			t.setCodetranche(rs.get(index+0).toString());
			t.setAgemin(Integer.valueOf(rs.get(index+1).toString()));
			t.setAgemax(Integer.valueOf(rs.get(index+1).toString()));
		}
		
		return t;
	}

	public Object listerTousLesCatalogues() throws MonException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Catalogue rechercherCatalogue(String parameter) throws MonException {
		// TODO Auto-generated method stub
		return null;
	}


	public void modifierCatalogue(Catalogue leCatalogue) throws MonException {
		// TODO Auto-generated method stub
		
	}

	public Object listerCatalogueQuantites(int parseInt, int parseInt2) throws MonException {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<Categorie, Integer> rechercherDictionnaire(String parameter) throws MonException {
		// TODO Auto-generated method stub
		return null;
	}

}
