package com.epul.ergosum.metier.gestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.epul.ergosum.meserreurs.MonException;
import com.epul.ergosum.metier.Catalogue;
import com.epul.ergosum.metier.Categorie;
import com.epul.ergosum.metier.Comporte;
import com.epul.ergosum.metier.ComporteId;
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
		
		String sql = "SELECT j.numero, j.codecateg, j.codetranche, j.libelle, t.agemin, t.agemax, c.libcateg "
				+ "FROM jouet j, trancheage t, categorie c "
				+ "WHERE j.codetranche = t.codetranche and j.codecateg = c.codecateg ";
		
		//WHERE
		if (categorieCode > 0)
			sql += "AND j.CODECATEG = '"+categorieCode+"' "; //recherche par cat si code > 0

		if (trancheCode > 0)
			sql += "AND j.CODETRANCHE='"+trancheCode+"' "; //ajouter tranche code
		
		sql += "ORDER BY numero asc";
		
		System.out.println(sql); 

		//CATEGORIES
		List<Categorie> categories = new ArrayList<Categorie>(); //pour mapping categorie
		List<Trancheage> tranchesages = new ArrayList<Trancheage>();//pour mapping tranche age
		
		rs = DialogueBd.lecture(sql);
		int index = 0;
		while(index < rs.size())
		{
			Jouet j = new Jouet();
	
			j.setNumero(rs.get(index+0).toString());
			j.setLibelle(rs.get(index+3).toString());

			//CATEGORIE
			//Code categorie
			String codeCateg = rs.get(index+1).toString();
			Categorie cat = null;
			for (Categorie c : categories)
				if (c.getCodecateg().contentEquals(codeCateg))
				{
					cat = c;
					break;
				}
			
			if (cat == null)
			{
				cat = new Categorie();
				cat.setCodecateg(codeCateg);
				cat.setLibcateg(rs.get(index+6).toString()); //libelle cat
			}		
			
			j.setCategorie(cat);
			cat.addJouet(j);
			
			//TRANCHE AGE
			//Code trancheage
			String codeTrancheAge = rs.get(index+2).toString();
			Trancheage tra = null;
			for (Trancheage t : tranchesages)
				if (t.getCodetranche().contentEquals(codeTrancheAge))
				{
					tra = t;
					break;
				}
			
			if (tra == null)
			{
				tra = new Trancheage();
				tra.setCodetranche(codeTrancheAge);
				tra.setAgemin(Integer.valueOf(rs.get(index+4).toString()));
				tra.setAgemax(Integer.valueOf(rs.get(index+5).toString()));
			}		
			
			j.setTrancheage(tra);
			tra.addJouet(j);
			
			mesJouets.add(j);
			index = index + 7; 
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
			String req = "SELECT c.annee, c.numero, c.quantite, cat.quantiteDistribuee  "
					+ "FROM comporte c, catalogue cat WHERE c.annee = cat.annee and c.numero = '"+numero+"'";
			System.out.println(req);
			List<Object> rsComporte = DialogueBd.lecture(req); 
			Set<Comporte> sComporte = new HashSet<Comporte>(); //set to add to jouet
			
			List<Catalogue> catalogues = new ArrayList<Catalogue>(); //pour mapping
			int index = 0;
			while(index < rsComporte.size())
			{
				int annee = Integer.valueOf(rsComporte.get(index+0).toString());
				
				Comporte c = new Comporte();
				ComporteId cid = new ComporteId(annee, numero);
				
				c.setId(cid);
				
				//CATALOGUE
				Catalogue cat = null;
				for (Catalogue cata : catalogues)
					if (cata.getAnnee() == annee)
					{
						cat = cata;
						break;
					}
				
				if (cat == null)
				{
					cat = new Catalogue();
					cat.setAnnee(annee);
					cat.setQuantiteDistribuee(Integer.valueOf(rsComporte.get(index+3).toString()));
				}		
				
				c.setCatalogue(cat);
				cat.addComporte(c);
				
				c.setJouet(jouet);
				c.setQuantite(Integer.valueOf(rsComporte.get(index+2).toString()));
				
				sComporte.add(c);
				
				index += 4;
			}
			
			jouet.setComportes(sComporte);
		} 
		
        return jouet;
	}
	
	/**
	 * Modifier un jouet
	 * @param unJouet : jouet à modifier
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
		// (Possibilité d'ajouter une quantité à une autre année)
		for (Comporte c : unJouet.getComportes())
		{			
			List<Object> rsComporte = DialogueBd.lecture( "SELECT * FROM comporte WHERE annee='"+c.getId().getAnnee()+"' and numero = '"+c.getId().getNumero()+"'");
			if (rsComporte.size() > 0)
			{
				DialogueBd.insertionBD("UPDATE comporte SET quantite = '"+c.getQuantite()+"' WHERE annee='"+c.getId().getAnnee()+"' and numero = '"+c.getId().getNumero()+"'");
			} else {
				DialogueBd.insertionBD("INSERT INTO comporte VALUES ('"+c.getId().getAnnee()+"', '"+c.getId().getNumero()+"', '"+c.getQuantite()+"')");			
			}
		}
	}

	/**
	 * Ajouter un jouet
	 * @param unJouet : jouet à insérer dans la db
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
	 * @param ids : liste des ids des jouets à effacer
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
