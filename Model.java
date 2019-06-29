package THG.common;

public class Model {
  private double[][] a; // tableaux
  private int numberOfConstraints; // nombre de contraintes
  private int numberOfOriginalVariables; // nombre des variables originales

  public Model(double[][] leftMember,double[] rightMember, Constraint[] sign, double[] objectiveFunction) {
   
	  numberOfConstraints = rightMember.length;
	  numberOfOriginalVariables = objectiveFunction.length;
	  //le tableau est de taille m+1*(m+n+1) car on ajoute la fonction objectif et le membre droit
	  a = new double[numberOfConstraints + 1][numberOfOriginalVariables+ numberOfConstraints + 1];

   // initialiser les contraintes
   for (int i = 0; i < numberOfConstraints; i++) {
    for (int j = 0; j < numberOfOriginalVariables; j++) {
     a[i][j] = leftMember[i][j];
    }
   }
   
   for (int i = 0; i < numberOfConstraints; i++)
    a[i][numberOfConstraints + numberOfOriginalVariables] = rightMember[i];

   // ajout des variables d'ecart
   for (int i = 0; i < numberOfConstraints; i++) {
    int ecart = 0;
    switch (sign[i]) {
    case greaterThan:
    ecart = -1;
     break;
    case lessThan:
    ecart = 1;
     break;
    default:
    }
    a[i][numberOfOriginalVariables + i] = ecart;
   }

   // initialiser la fonction objectif
   for (int j = 0; j < numberOfOriginalVariables; j++)
    a[numberOfConstraints][j] = objectiveFunction[j];
  }

  public double[][] getTableaux() {
   return a;
  }
  public int getNumberOfConstraint() {
   return numberOfConstraints;
  }

  public int getNumberOfOriginalVariable() {
   return numberOfOriginalVariables;
  }
  public static Constraint[] convertToConstraint(String s[]) {
	  Constraint[] constraints=new Constraint[s.length];
	  for(int i=0;i<s.length;i++) {
	  Constraint constraint = null;
	  
	  if(s[i].equals(new String("<="))) {
		  constraint=Constraint.lessThan;
	  }
	  else if(s[i].equals(new String(">="))) {
		  constraint=Constraint.greaterThan;
	  }
	  else{
		  constraint=Constraint.equal;
	}
	 constraints[i]=constraint;
	}
	  return constraints;
  }
  public String convertToString(Constraint constr) {
	  String string=new String("");
	  switch(constr) {
	  case lessThan:
		  string="<=";
		  break;
	  case greaterThan:
		  string=">=";
		  break;
	  case equal:
		  string="=";
		  break;
		  default :
			  string="=";
			  break;
	  }
	  return string;
  }
 }