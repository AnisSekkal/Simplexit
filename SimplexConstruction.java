package THG.common;

public class SimplexConstruction {
private int n;
private int m;
private double[][] A;
private double[] b;
private double[] c;
private String[] s;
private boolean max;

public SimplexConstruction() {
	this.n=0;
	this.m=0;
}
public SimplexConstruction(int n,int m,double[][]A, double[] b , double[] c,String[] s,boolean max) {
	this.n=n;
	this.m=m;
	this.A=A;
	this.b=b;
	this.c=c;
	this.s=s;
	this.max=max;
}
public void setn(int n) {
	this.n=n;
}
public void setm(int m) {
	this.m=m;
}
public int getn() {
	return this.n;
}
public int getm() {
	return this.m;
}
public double[][] getA(){
	return this.A;
}
public double[] getb() {
	return this.b;
}
public double[] getc() {
	return this.c;
	}
public void setA(double[][] A) {
	this.A=A;
}
public void setb(double[]b) {
	this.b=b;
}
public void setc(double[]c) {
	this.c=c;
}
public String[] gets() {
	return this.s;
}
public void sets(String[] s) {
	this.s=s;
}
public boolean getMax() {
	return this.max;
}
public void setMax(boolean m) {
	this.max=m;
}
}
