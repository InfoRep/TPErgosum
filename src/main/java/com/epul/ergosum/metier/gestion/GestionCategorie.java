package com.epul.ergosum.metier.gestion;

import java.util.ArrayList;
import java.util.List;

import com.epul.ergosum.meserreurs.MonException;
import com.epul.ergosum.metier.Categorie;
import com.epul.ergosum.persistance.DialogueBd;

public class GestionCategorie {
	/**
	 * Lister toutes les cat�gories
	 * @return liste de cat�gorie
	 * @throws MonException
	 */
	public static List<Categorie> lister() throws MonException  {
		List<Object> rs;
		List<Categorie> listCats = new ArrayList<Categorie>();
		
		String sql = "SELECT * FROM categorie ORDER BY codecateg asc";
		System.out.println(sql);
		
		rs = DialogueBd.lecture(sql);
		int index = 0;
		while(index < rs.size())
		{
			Categorie c = new Categorie();
	
			c.setCodecateg(rs.get(index+0).toString());
			c.setLibcateg(rs.get(index+1).toString());
			
			listCats.add(c);
			
			index = index + 2; //2 champs
		}
		
		return listCats;
	}
	
	/**
	 * Rechercher une cat�gorie selon son code
	 * @param parameter : code cat�gorie � rechercher
	 * @return Cat�gorie
	 * @throws MonException
	 */
	public static Categorie rechercher(String parameter) throws MonException {
		if (parameter == null || (parameter != null && parameter.trim().isEmpty())) return null;
		
		List<Object> rs;
		
		//Requete SQL Categorie
		String sql = "SELECT * FROM categorie WHERE codecateg ='"+parameter+"'";
		//System.out.println(sql);
		rs = DialogueBd.lecture(sql);
		
		
		Categorie c = null;
		
		if (rs.size() > 0)
		{
			c = new Categorie();
			c.setCodecateg(rs.get(0).toString());
			c.setLibcateg(rs.get(1).toString());
		}
		
		return c;
	}
}
