package com.epul.ergosum.metier.gestion;

import java.util.ArrayList;
import java.util.List;

import com.epul.ergosum.meserreurs.MonException;
import com.epul.ergosum.metier.Categorie;
import com.epul.ergosum.metier.Trancheage;
import com.epul.ergosum.persistance.DialogueBd;

public class GestionTrancheAge {
	/**
	 * Lister toutes les tranches d'âges
	 * @return Liste de tranches d'âges
	 * @throws MonException
	 */
	public static List<Trancheage> lister() throws MonException {
		List<Object> rs;
		List<Trancheage> listTa = new ArrayList<Trancheage>();
		
		String sql = "SELECT * FROM trancheage ORDER BY codetranche asc";
		System.out.println(sql);
		
		rs = DialogueBd.lecture(sql);
		int index = 0;
		while(index < rs.size())
		{
			Trancheage ta = new Trancheage();
	
			ta.setCodetranche(rs.get(index+0).toString());
			ta.setAgemin(Integer.valueOf(rs.get(index+1).toString()));
			ta.setAgemax(Integer.valueOf(rs.get(index+2).toString()));
			
			listTa.add(ta);
			
			index = index + 3; //3 champs
		}
		
		return listTa;
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
