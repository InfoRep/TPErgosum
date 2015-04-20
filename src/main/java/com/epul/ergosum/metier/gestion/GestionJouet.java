package com.epul.ergosum.metier.gestion;

import java.util.ArrayList;
import java.util.List;

import com.epul.ergosum.meserreurs.MonException;
import com.epul.ergosum.metier.Categorie;
import com.epul.ergosum.metier.Jouet;	
import com.epul.ergosum.metier.Trancheage;
import com.epul.ergosum.persistance.DialogueBd;

public class GestionJouet {
	/**
	 * Lister les jouets selon les paramètres en fonction de la 
	 * categorie et de la tranche d'âge
	 * @param categorieCode : 0 = all
	 * @param trancheCode : 0 = all
	 * @return Liste de jouets
	 * @throws MonException
	 */
	public static List<Jouet> lister(int categorieCode, int trancheCode) throws MonException {
		List<Object> rs;
		List<Jouet> mesJouets = new ArrayList<Jouet>();
		
		//Categorie > 0 rechercher la categorie
		Categorie cat = null;
		if (categorieCode > 0)
			cat = GestionCategorie.rechercher(String.valueOf(categorieCode));
		
		//Tranche age > 0 rechercher la tranche d'âge
		Trancheage trancheage = null;
		if (trancheCode > 0)
			trancheage = GestionTrancheAge.rechercher(String.valueOf(trancheCode));

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
		
		System.out.println(sql); //TODO

		rs = DialogueBd.lecture(sql);
		int index = 0;
		while(index < rs.size())
		{
			Jouet j = new Jouet();
	
			j.setNumero(rs.get(index+0).toString());
			j.setLibelle(rs.get(index+3).toString());

			if (categorieCode > 0)
				j.setCategorie(cat);
			else {
				//TODO Chaque jouet a une cat différente, rechercher la cat associer au jouet 
				j.setCategorie(new Categorie(rs.get(index+1).toString())); //A modif
			}
			
			if (trancheCode > 0)
				j.setTrancheage(trancheage);
			else {
				//TODO Chaque jouet a une tranche age différente, rechercher la tranche age associer au jouet 
				j.setTrancheage(new Trancheage(rs.get(index+2).toString())); //A modif
			}
			
			mesJouets.add(j);
			
			index = index + 4; //4 champs
		}
		
		return mesJouets;
	}
	
	public static List<Jouet> lister() throws MonException {
		return lister(0, 0);
	}
	
	/**
	 * Rerchercher un jouet selon son numero
	 * @param id : numero du jouet
	 * @return Jouet
	 * @throws MonException
	 */
	public static Jouet rechercher(String id) throws MonException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Modifier un jouet
	 * @param unJouet : jouet à modifier
	 * @throws MonException
	 */
	public static void modifier(Jouet unJouet) throws MonException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Ajouter un jouet
	 * @param unJouet : jouer à insérer dans la db
	 * @throws MonException
	 */
	public static void ajouter(Jouet unJouet) throws MonException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Effacer des jouets
	 * @param ids : liste des ids des jouets à effacer
	 */
	public static void effacer(String[] ids) throws MonException {
		if (ids != null && ids.length > 0)
		{
			String strIn = "";
			for (String id : ids)
				strIn += "'"+id+"', ";
			strIn = strIn.substring(0, strIn.length()-1); //suppr le dernier ", "
			
			String sql = "DELETE FROM jouet WHERE numero in ("+strIn+")";
			System.out.println(sql);
			
			DialogueBd.insertionBD(sql);
		}
		
	}
}
