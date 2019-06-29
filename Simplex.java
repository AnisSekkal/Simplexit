package THG.common;

import java.util.ArrayList;
import java.util.List;

public class Simplex {

 private double[][] tableaux; // tableaux
 private int numberOfConstraints; // nombre de contraintes
 private int numberOfOriginalVariables; // nombre de variables originales
 private boolean isUnbounded=false;
 List<double[][]>tabs=new ArrayList<>();
 private boolean maximizeOrMinimize;


 private int[] basis; // basis[i] = basic variable corresponding to row i

 public Simplex(double[][] tableaux, int numberOfConstraint,
  int numberOfOriginalVariable, boolean maximizeOrMinimize) {
  this.maximizeOrMinimize = maximizeOrMinimize;
  this.numberOfConstraints = numberOfConstraint;
  this.numberOfOriginalVariables = numberOfOriginalVariable;
  this.tableaux = tableaux;
  //this.tabs=tabs;

  basis = new int[numberOfConstraints];
  for (int i = 0; i < numberOfConstraints; i++)
  basis[i] = numberOfOriginalVariables + i;
  solve();
 }

 // lancement de simplex
 public void solve() {
  while (true) { 
   double[][] tab=new double[tableaux.length][tableaux[0].length];
   copieTab(tab, tableaux);
   this.tabs.add(tab);
   
   //for debug
  /* for(int j=0;j<tab.length;j++) {
		for(int k=0;k<tab[j].length;k++) {
			System.out.print(" "+tab[j][k]+" ");
		}
		System.out.print("\n");
	}*/
   show();
   int q = 0;
   
   // trouver la variable entrante
   if (maximizeOrMinimize) {
    q = enteringColumn();
   } else {
    q = negativeEnteringColumn();
   }
   if (q == -1)
    break; // optimale

   // trouver la variable sortante
   int p = findMinBi(q);
   if (p == -1) {
	this.setIsUnbounded(true);
	//throw new ArithmeticException("Linear program is unbounded");
	break;
   }
    // pivot
   pivotage(p, q);

   // update basis
   basis[p] = q;

  }
 }

 // index de la colonne non de base avec le cout le plus elevé
 private int enteringColumn() {
  int q = 0;
  for (int j = 1; j < numberOfConstraints + numberOfOriginalVariables; j++)
   if (tableaux[numberOfConstraints][j] > tableaux[numberOfConstraints][q])
    q = j;
  if (tableaux[numberOfConstraints][q] <= 0)
   return -1; // optimale
  else
   return q;
 }

 // index de la colonne non de base avec le cout le plus bas
 private int negativeEnteringColumn() {
  int q = 0;
  for (int j = 1; j < numberOfConstraints + numberOfOriginalVariables; j++)
   if (tableaux[numberOfConstraints][j] < tableaux[numberOfConstraints][q])
    q = j;

  if (tableaux[numberOfConstraints][q] >= 0)
   return -1; // optimal
  else
   return q;
 }

 //Trouver la variable sortante
 private int findMinBi(int q) {
  int p = -1;
  for (int i = 0; i < numberOfConstraints; i++) {
   if (tableaux[i][q] <= 0)
    continue;
   else if (p == -1)
    p = i;
   else if ((tableaux[i][numberOfConstraints
     + numberOfOriginalVariables] / tableaux[i][q]) < (tableaux[p][numberOfConstraints
     + numberOfOriginalVariables] / tableaux[p][q]))
    p = i;
  }
  return p;
 }

 // pivoter par rapport a (p,q)
 private void pivotage(int p, int q) {

  // pivoter tout sauf la ligne p et la colonne q
  for (int i = 0; i <= numberOfConstraints; i++)
   for (int j = 0; j <= numberOfConstraints
     + numberOfOriginalVariables; j++)
    if (i != p && j != q)
     tableaux[i][j] -= tableaux[p][j] * tableaux[i][q]
       / tableaux[p][q];

  // mettre des zero a la colonne q
  for (int i = 0; i <= numberOfConstraints; i++)
   if (i != p)
    tableaux[i][q] = 0.0;

  for (int j = 0; j <= numberOfConstraints + numberOfOriginalVariables; j++)
   if (j != q)
    tableaux[p][j] /= tableaux[p][q];
  	tableaux[p][q] = 1.0;
 }

 // retourner valeur optimale
 public double zValue() {
  return -tableaux[numberOfConstraints][numberOfConstraints

                                        + numberOfOriginalVariables];
 }

 // retourner la solution optimale
 public double[] solutions() {
  double[] x = new double[numberOfOriginalVariables];
  for (int i = 0; i < numberOfConstraints; i++)
   if (basis[i] < numberOfOriginalVariables)
    x[basis[i]] = tableaux[i][numberOfConstraints+numberOfOriginalVariables];
  return x;
 }

 // affichage du tableau
 public void show() {
  System.out.println("M = " + numberOfConstraints);
  System.out.println("N = " + numberOfOriginalVariables);
  for (int i = 0; i <= numberOfConstraints; i++) {
   for (int j = 0; j <= numberOfConstraints
     + numberOfOriginalVariables; j++) {
    System.out.printf("%7.2f ", tableaux[i][j]);
   }
   System.out.println();
  }
  System.out.println("value = " + zValue());
  for (int i = 0; i < numberOfConstraints; i++)
   if (basis[i] < numberOfOriginalVariables)
    System.out.println("x_"
      + basis[i]
      + " = "
      + tableaux[i][numberOfConstraints
        + numberOfOriginalVariables]);
  System.out.println();
 }
 public List<double[][]> getTab(){
	 return this.tabs;
 }
 public void copieTab(double[][] a,double[][] b){
	 for(int i=0;i<b.length;i++) {
		 for(int j=0;j<b[0].length;j++) {
			 a[i][j]=b[i][j];
		 }
	 }
 }

public boolean getIsUnbounded() {
	return this.isUnbounded;
}

public void setIsUnbounded(boolean isUnbounded) {
	this.isUnbounded = isUnbounded;
}
}
 