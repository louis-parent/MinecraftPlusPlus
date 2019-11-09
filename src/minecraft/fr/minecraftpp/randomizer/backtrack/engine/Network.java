package fr.minecraftpp.randomizer.backtrack.engine;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import fr.minecraftpp.randomizer.backtrack.engine.constraints.Constraint;

/* (non-Javadoc)
 *  Choix de codage :
 *		Variable = String
 *      Valeur = Object
 *		un domaine = ArrayList<Object>
 *
 *  un réseau de contrainte (X,D,C) est représenté par :
 * 		- X les variables et D les domaines sont représentés par un ensemble de 
 *		couples (variable,domaine) utilisant une HashMap<String,ArrayList<Object>>
 *		- C les contraintes est une simple liste de contraintes : ArrayList<Constraint>
 *
 *  On pourra, pour optimiser la structure d'un réseau de contraintes, ajouter
 * une HashMap (variable, liste de contraintes) qui permet d'associer à
 * chaque variable la liste des contraintes qui portent sur elle.
 *
*/
/**
 * Structure de manipulation d'un réseau de contraintes (X,D,C)
 *
 */
public class Network
{

	private HashMap<String, ArrayList<Integer>> varDom; // associe à chaque
														// variable de X un
														// domaine de D
	private ArrayList<Constraint> constraints; // l'ensemble de contraintes C

	/**
	 * Construit un réseau de contraintes sans variable (ni contrainte)
	 */
	public Network()
	{
		varDom = new HashMap<String, ArrayList<Integer>>();
		constraints = new ArrayList<Constraint>();
	}

	/**
	 * Construit un réseau de contraintes à partir d'une représentation
	 * textuelle au format :
	 * <p>
	 * nombre de variables
	 * <p>
	 * nom de la variable 1 ; val1 ; val2 ; ...
	 * <p>
	 * nom de la variable 2 ; val1 ; val2 ; ...
	 * <p>
	 * ...
	 * <p>
	 * nombre de contraintes
	 * <p>
	 * type de la contrainte 1 (pour l'instant uniquement 'ext')
	 * <p>
	 * nom de la 1ière variable de la contrainte 1 ; nom de la 2nde var de la
	 * contrainte 1 ; ...
	 * <p>
	 * nombre de tuples de valeurs pour cette contrainte
	 * <p>
	 * val1 ; val2 ; ...
	 * <p>
	 * val1 ; val2 ; ...
	 * <p>
	 * type de la contrainte 2 (pour l'instant uniquement 'ext')
	 * <p>
	 * nom de la 1ière variable de la contrainte 2 ; nom de la 2nde var de la
	 * contrainte 2 ; ...
	 * <p>
	 * nombre de tuples de valeurs pour cette contrainte
	 * <p>
	 * val1 ; val2 ; ...
	 * <p>
	 * val1 ; val2 ; ...
	 * <p>
	 * ...
	 * 
	 * @param in
	 *            le buffer de lecture de la représentation textuelle de la
	 *            contrainte
	 * @throws Exception
	 *             en cas de problème dans l'analyse textuelle
	 */
	public Network(BufferedReader in) throws Exception
	{
		varDom = new HashMap<String, ArrayList<Integer>>();
		constraints = new ArrayList<Constraint>();
		// Les variables et domaines
		int nbVariables = Integer.parseInt(in.readLine()); // le nombre de
															// variables
		for (int i = 1; i <= nbVariables; i++)
		{
			String[] varDeclaration = in.readLine().split(";"); // Var;Val1;Val2;Val3;...
			ArrayList<Integer> dom = new ArrayList<Integer>();
			varDom.put(varDeclaration[0], dom);
			for (int j = 1; j < varDeclaration.length; j++)
				dom.add(Integer.parseInt(varDeclaration[j]));
		}
		// Les contraintes
		int nbConstraints = Integer.parseInt(in.readLine()); // le nombre de
																// contraintes
		for (int k = 0; k < nbConstraints; k++)
		{
			String type = in.readLine().trim(); // le type de la contrainte
			Constraint c = Constraint.getInstance(type, in);

			if (c == null)
			{
				System.out.println(type);
				System.err.println("Type contrainte inconnu");
			}

			addConstraint(c);
		}
	}

	public Network(String in) throws Exception
	{
		this(new BufferedReader(new StringReader(in)));
	}

	/**
	 * Ajoute une nouvelle variable dans le réseau avec un domaine vide (ne
	 * fait rien si la variable existe déjà : message d'avertissement)
	 * 
	 * @param var
	 *            la variable à ajouter
	 */
	public void addVariable(String var)
	{
		if (varDom.get(var) == null)
			varDom.put(var, new ArrayList<Integer>());
		else
			System.err.println("Variable " + var + " deja existante");
	}

	/**
	 * Ajoute une nouvelle valeur au domaine d'une variable du réseau
	 * 
	 * @param var
	 *            la variable à laquelle il faut ajouter la valeur
	 * @param val
	 *            la valeur à ajouter
	 */
	public void addValue(String var, Integer val)
	{
		if (varDom.get(var) == null)
			System.err.println("Variable " + var + " non existante");
		else
		{
			ArrayList<Integer> dom = varDom.get(var);
			if (!dom.add(val)) // add retourne vrai si la valeur était
								// effectivement nouvelle
				System.err.println("La valeur " + val + " est déjà dans le domaine de la variable " + var);
		}
	}

	/**
	 * Ajoute une contrainte dans le réseau. Les variables de la contrainte
	 * doivent déjà exister dans le réseau.
	 * 
	 * @param c
	 *            la contrainte à ajouter
	 */
	public void addConstraint(Constraint c)
	{
		// Attention !! On ne vérifie pas que les valeurs des contraintes sont
		// "compatibles" avec les domaines
		if (!varDom.keySet().containsAll(c.getVars())) // si l'une des variables
														// de la contrainte
														// n'existe pas das le
														// réseau
			System.err.println("La contrainte " + c.getName() + " contient des variables (" + c.getVars() + ") non déclarées dans le CSP dont les variables sont " + varDom.keySet());
		else
			constraints.add(c);
	}

	/**
	 * Retourne le nombre de variables du réseau
	 * 
	 * @return le nombre de variables
	 */
	public int getVarNumber()
	{
		return varDom.size();
	}

	/**
	 * Retourne la taille du domaine d'une variable du réseau.
	 * 
	 * @param var
	 *            la variable dont on veut connaître la taille de son domaine
	 * @return le nombre de valeurs de Dom(var)
	 */
	public int getDomSize(String var)
	{
		return varDom.get(var).size();
	}

	/**
	 * Retourne le nombre de contraintes du réseau
	 * 
	 * @return le nombre de contraintes
	 */
	public int getConstraintNumber()
	{
		return constraints.size();
	}

	/**
	 * Retourne la liste des variables du réseau
	 * 
	 * @return la liste des variables
	 */
	public ArrayList<String> getVars()
	{
		return new ArrayList<String>(varDom.keySet());
	}

	/**
	 * Retourne le domaine d'une variable
	 * 
	 * @param var
	 *            la variable dont on veut le domaine
	 * @return la liste des valeurs du domaine d'une variable
	 */
	public ArrayList<Integer> getDom(String var)
	{
		return varDom.get(var);
	}

	public void setDom(HashMap<String, ArrayList<Integer>> dom)
	{
		this.varDom = dom;
	}

	/**
	 * Retourne la liste des contraintes du réseau
	 * 
	 * @return la liste des contraintes
	 */
	public ArrayList<Constraint> getConstraints()
	{
		return constraints;
	}

	/**
	 * Retourne la liste des contraintes du réseau contenant une certaine
	 * variable
	 * 
	 * @param var
	 *            la variable dont on veut connaitre les contraintes
	 * @return la liste des contraintes contenant var
	 */
	public ArrayList<Constraint> getConstraints(String var)
	{
		ArrayList<Constraint> selected = new ArrayList<Constraint>();
		for (Constraint c : constraints)
			if (c.getVars().contains(var))
				selected.add(c);
		return selected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Var et Dom : " + varDom + "\nConstraints :" + constraints;
	}
}
