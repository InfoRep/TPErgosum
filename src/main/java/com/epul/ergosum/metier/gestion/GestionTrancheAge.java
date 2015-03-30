package com.epul.ergosum.metier.gestion;

import java.util.List;

import com.epul.ergosum.meserreurs.MonException;
import com.epul.ergosum.metier.Trancheage;
import com.epul.ergosum.persistance.DialogueBd;

public class GestionTrancheAge {
	/**
	 * Lister toutes les tranches d'âges
	 * @return Liste de tranches d'âges
	 * @throws MonException
	 */
	public static List<Trancheage> lister() throws MonException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Rechercher une tranche d'âge selon son code
	 * @param parameter : codetrancheage
	 * @return TrancheAge correspondante
	 * @throws MonException
	 */
	public static Trancheage rechercher(String parameter) throws MonException {
		List<Object> rs;
		
		//Requete SQL Categorie
		rs = DialogueBd.lecture("SELECT * FROM trancheage WHERE codetranche ='"+parameter+"'");
		
		Trancheage t = null;
		
		if (rs.size() > 0)
		{
			t = new Trancheage();
			t.setCodetranche(rs.get(0).toString());
			t.setAgemin(Integer.valueOf(rs.get(1).toString()));
			t.setAgemax(Integer.valueOf(rs.get(2).toString()));
		}
		
		return t;
	}
}
