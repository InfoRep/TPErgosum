package com.epul.ergosum.metier.gestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.epul.ergosum.meserreurs.MonException;
import com.epul.ergosum.metier.Categorie;
import com.epul.ergosum.metier.Comporte;
import com.epul.ergosum.metier.ComporteId;
import com.epul.ergosum.metier.Jouet;
import com.epul.ergosum.metier.Trancheage;
import com.epul.ergosum.persistance.DialogueBd;

public class GestionJouet {
	/**
	 * Lister les jouets selon les param�tres en fonction de la 
	 * categorie et de la tranche d'�ge
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
		
		//Tranche age > 0 rechercher la tranche d'�ge
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
					sql += "AND "; //ajouter le AND si les 2 param�tres sont > 0
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
				j.setCategorie(GestionCategorie.rechercher(rs.get(index+1).toString())); //rechercher la categorie associ� au jouet
			}
			
			if (trancheCode > 0)
				j.setTrancheage(trancheage);
			else {
				j.setTrancheage(GestionTrancheAge.rechercher(rs.get(index+2).toString())); // rechercher la tranche age associer au jouet 
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
	public static Jouet rechercher(String numero) throws MonException {
		String sql = "SELECT * FROM jouet WHERE numero = '"+numero+"'";
		
		List<Object> rs = DialogueBd.lecture(sql); 
		Jouet jouet = null;
		
		if (rs.size() > 0)
		{ 
			//retourner le jouet
			// il faut redecouper la liste pour retrouver les lignes
			jouet = new Jouet();
			jouet.setNumero(rs.get(0).toString());
			jouet.setCategorie(GestionCategorie.rechercher(rs.get(1).toString())); //rechercher cat dans bdd
			jouet.setTrancheage(GestionTrancheAge.rechercher(rs.get(2).toString())); //rechercher tranche age dans bdd
			jouet.setLibelle(rs.get(3).toString());
			
			//LOAD COMPORTE
			String req = "SELECT * FROM comporte WHERE numero = '"+numero+"'";
			System.out.println(req);
			List<Object> rsComporte = DialogueBd.lecture(req); 
			Set<Comporte> sComporte = new HashSet<Comporte>();
			int index = 0;
			while(index < rsComporte.size())
			{
				Comporte c = new Comporte();
				ComporteId cid = new ComporteId(Integer.valueOf(rsComporte.get(index+0).toString()), numero);
				
				c.setId(cid);
				c.setCatalogue(GestionCatalogue.rechercher(rsComporte.get(index+0).toString())); //rechercher l'object catalogue associ�
				c.setJouet(jouet);
				c.setQuantite(Integer.valueOf(rsComporte.get(index+2).toString()));
				
				sComporte.add(c);
				
				index += 3;
			}
			
			jouet.setComportes(sComporte);
		} else 
			throw new MonException("Aucun jouet trouv� avec l'id : "+numero);
		
        return jouet;
	}
	
	/**
	 * Modifier un jouet
	 * @param unJouet : jouet � modifier
	 * @throws MonException
	 */
	public static void modifier(Jouet unJouet) throws MonException {
		String sql="";
		
		sql = "UPDATE jouet SET ";
		sql += "codecateg = '"+unJouet.getCategorie().getCodecateg()+"'";
		sql += ", codetranche = '"+unJouet.getTrancheage().getCodetranche()+"'";
		sql += ", libelle = '"+unJouet.getLibelle()+"'";
		sql += " WHERE numero = '"+unJouet.getNumero()+"'";
		
		System.out.println(sql);
		
		DialogueBd.insertionBD(sql);
		
		//Update valeurs dans comportes : supprimer les lignes pour les remettres ensuites si valeur != 0
		// (Possibilit� d'ajouter une quantit� � une autre ann�e)
		for (Comporte c : unJouet.getComportes())
		{
			try {
				DialogueBd.insertionBD("INSERT INTO comporte VALUES ('"+c.getId().getAnnee()+"', '"+c.getId().getNumero()+"', '"+c.getQuantite()+"')");
			} catch (Exception e) {
				DialogueBd.insertionBD("UPDATE comporte SET quantite = '"+c.getQuantite()+"' WHERE annee='"+c.getId().getAnnee()+"' and numero = '"+c.getId().getNumero()+"'");
			}								
		}
	}

	/**
	 * Ajouter un jouet
	 * @param unJouet : jouet � ins�rer dans la db
	 * @throws MonException
	 */
	public static void ajouter(Jouet unJouet) throws MonException {
		String sql="";
		
		sql = "INSERT INTO jouet (numero, codecateg, codetranche, libelle) ";
		sql = sql + " VALUES ( '" + unJouet.getNumero() + "', '" + unJouet.getCategorie().getCodecateg() + "', ";
		sql = sql + "'" + unJouet.getTrancheage().getCodetranche() + "', " + "'" + unJouet.getLibelle() + "')";

		System.out.println(sql);
		
		DialogueBd.insertionBD(sql);
		
		//Insert valeurs dans comportes 
		for (Comporte c : unJouet.getComportes())
		{
			String req = "INSERT INTO comporte VALUES ('"+c.getId().getAnnee()+"', '"+c.getId().getNumero()+"', '"+c.getQuantite()+"')";
			
			System.out.println(req);
			DialogueBd.insertionBD(req);
		}
	}

	/**
	 * Effacer des jouets
	 * @param ids : liste des ids des jouets � effacer
	 */
	public static void effacer(String[] ids) throws MonException {
		if (ids != null && ids.length > 0)
		{
			//delete from comporte table
			String strIn = ""; //In for sql delete jouet
			for (String id : ids)
			{
				String req = "DELETE FROM comporte WHERE numero='"+id+"'";
				System.out.println(req);
				DialogueBd.insertionBD(req);
				
				strIn += "'"+id+"', "; //for delete jouet
			}
			
			strIn = strIn.substring(0, strIn.length()-2); //suppr le dernier ", "
			
			String sql = "DELETE FROM jouet WHERE numero in ("+strIn+")";
			System.out.println(sql);
			
			DialogueBd.insertionBD(sql);
		}
		
	}
}
