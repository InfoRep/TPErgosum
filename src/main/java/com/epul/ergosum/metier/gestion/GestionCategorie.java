package com.epul.ergosum.metier.gestion;

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
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Rechercher une cat�gorie selon son code
	 * @param parameter : code cat�gorie � rechercher
	 * @return Cat�gorie
	 * @throws MonException
	 */
	public static Categorie rechercher(String parameter) throws MonException {
		List<Object> rs;
		
		//Requete SQL Categorie
		rs = DialogueBd.lecture("SELECT * FROM categorie WHERE codecateg ='"+parameter+"'");
		
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
