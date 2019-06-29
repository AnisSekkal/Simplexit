//Auteur Anis Sekkal
//Créé le 25-06-2019
//En vu de l'obtention de la licence En RO

package THG.views;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFormattedTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import THG.common.SimplexConstruction;

import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JTextField;


//main class to 
public class MainJF extends JFrame {
	private JPanel mainPanel;
	private JFormattedTextField nbVar;
	private JFormattedTextField nbContr;
	private int n;
	private int m;
	private int n1;
	private int m1;
	private JPanel jPanel;
	private SimplexConstruction simplexConstructor;
	private JTextField nbSommet1;
	private JTextField nbSommet2;

	public static void main(String[] args) {
		MainJF g = new MainJF();
		g.setLocationRelativeTo(null);
		g.setMinimumSize(new Dimension(720,380));
		g.setMaximumSize(new Dimension(900,500));;
		g.setVisible(true);
	}
	
	public MainJF() {
		simplexConstructor=new SimplexConstruction();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Simplexit");
		System.out.println(this.getName());
		this.setSize(720,380);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		jPanel = new JPanel();
		getContentPane().add(jPanel);
		jPanel.setLayout(new BorderLayout(0, 0));
		
		mainPanel = new JPanel();
		jPanel.add(mainPanel);
		mainPanel.setLayout(new CardLayout(0, 0));
		CardLayout cardLayout =(CardLayout) mainPanel.getLayout();
		
		JPanel card1 = new JPanel();
		card1.setBackground(Color.WHITE);
		mainPanel.add(card1, "card1");
		
		JLabel lblNombreDeVariables = new JLabel("Nombre de variables");
		lblNombreDeVariables.setFont(new Font("Calibri", Font.BOLD, 14));
		
		JLabel lblNombreDeContraintes = new JLabel("Nombre de contraintes");
		lblNombreDeContraintes.setFont(new Font("Calibri", Font.BOLD, 14));
		
		nbVar = new JFormattedTextField(NumberFormat.getIntegerInstance());
		nbVar.setText("1");
		nbVar.setColumns(10);
		
		nbContr = new JFormattedTextField(NumberFormat.getIntegerInstance());
		nbContr.setText("1");
		nbContr.setColumns(10);
		
		//bouton pour saisir les valeurs
		JButton nextc1 = new JButton("suivant");
		nextc1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nextc1.setBackground(new Color(204, 204, 255));
		nextc1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					n=Integer.parseInt(nbVar.getText());
					m=Integer.parseInt(nbContr.getText());
				}catch(Exception e) {
				 	System.out.print("erreur saisi nb var ou nb contr\n");
				 	JOptionPane.showMessageDialog(getContentPane(), "le nombre de variables et de contraintes doit etre un entier positif","erreur saisi",JOptionPane.ERROR_MESSAGE,null);
				}
				finally {
					nbVar.setText("1");
					nbContr.setText("1");
				}
				if(n>=1 && m>=1) {
					SystemInput systemInput=new SystemInput(n, m, simplexConstructor);
					systemInput.setVisible(true);
					
				if(n<1) {
					n=1;
					nbVar.setText("1");
						
				}
				if(m<1) {
					m=1;
					nbContr.setText("1");
					
				}
				System.out.print("n : "+n+" m : "+m+"\n");
			}
				}
		});
		
		JLabel lblAide = new JLabel("Aide :");
		lblAide.setFont(new Font("Calibri", Font.PLAIN, 18));
		
		JLabel lblNewLabel = new JLabel("*Il est requit de remplir les champs ci-dessus avec des valeur entieres positives");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 12));
		
		JLabel lblsiUneVariable = new JLabel("*Si une variable du system lin\u00E9aire est nulle, elle sera automatiquement \u00E9gale a 0");
		lblsiUneVariable.setFont(new Font("Calibri", Font.PLAIN, 12));
		
		JLabel lbltoutesLesVariables = new JLabel("*<= (>=) Pour des contraintes inf\u00E9rieur (superieur) et = pour une \u00E9galit\u00E9");
		lbltoutesLesVariables.setFont(new Font("Calibri", Font.PLAIN, 12));
		
		JLabel lblAlgorithmeDuSimplex = new JLabel("Algorithme du Simplex");
		lblAlgorithmeDuSimplex.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 18));
		
		GroupLayout gl_card1 = new GroupLayout(card1);
		gl_card1.setHorizontalGroup(
			gl_card1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_card1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_card1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNombreDeVariables, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNombreDeContraintes))
					.addGap(18)
					.addGroup(gl_card1.createParallelGroup(Alignment.LEADING, false)
						.addComponent(nbVar, 0, 0, Short.MAX_VALUE)
						.addComponent(nbContr, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
					.addContainerGap(246, Short.MAX_VALUE))
				.addGroup(gl_card1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_card1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAide)
						.addGroup(gl_card1.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_card1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblsiUneVariable)
								.addComponent(lblNewLabel)
								.addComponent(lbltoutesLesVariables))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_card1.createSequentialGroup()
					.addContainerGap(298, Short.MAX_VALUE)
					.addComponent(nextc1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addGap(47))
				.addGroup(gl_card1.createSequentialGroup()
					.addGap(137)
					.addComponent(lblAlgorithmeDuSimplex)
					.addContainerGap(149, Short.MAX_VALUE))
		);
		gl_card1.setVerticalGroup(
			gl_card1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_card1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAlgorithmeDuSimplex)
					.addGap(18)
					.addGroup(gl_card1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNombreDeVariables, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(nbVar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_card1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNombreDeContraintes)
						.addComponent(nbContr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(19)
					.addComponent(nextc1)
					.addGap(17)
					.addComponent(lblAide)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblsiUneVariable)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lbltoutesLesVariables)
					.addContainerGap(77, Short.MAX_VALUE))
		);
		card1.setLayout(gl_card1);
		
		JPanel card2 = new JPanel();
		card2.setBackground(Color.WHITE);
		mainPanel.add(card2, "card2");
		
		JLabel lblRechercherUnStable = new JLabel("Rechercher un stable maximum dans un graphe biparti");
		lblRechercherUnStable.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 18));
		
		JLabel lblNombreDeSommets = new JLabel("Nombre de sommets");
		lblNombreDeSommets.setFont(new Font("Calibri", Font.BOLD, 14));
		
		nbSommet1 = new JTextField();
		nbSommet1.setColumns(10);
		
		JLabel lblNombreDeSommets_1 = new JLabel("Nombre d'ar\u00EAtes");
		lblNombreDeSommets_1.setFont(new Font("Calibri", Font.BOLD, 14));
		
		nbSommet2 = new JTextField();
		nbSommet2.setColumns(10);
		
		JButton nextc2 = new JButton("suivant");
		nextc2.setBackground(new Color(204, 204, 255));
		nextc2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int N=0;
				try {
					n1=Integer.parseInt(nbSommet1.getText());
					m1=Integer.parseInt(nbSommet2.getText());
				}catch(Exception e1) {
				 	System.out.print("erreur saisi nb var ou nb contr\n");
				 	JOptionPane.showMessageDialog(getContentPane(), "le nombre de variables et de contraintes doit etre un entier positif","erreur saisi",JOptionPane.ERROR_MESSAGE,null);
				}
				finally {
					nbSommet1.setText("1");
					nbSommet2.setText("1");
				}
				boolean erreur=false;
				N=(n1*(n1-1))/2;
				if(n1<2) {
					JOptionPane.showMessageDialog(getContentPane(), "Saisissez au moins 2 sommets","erreur saisi",JOptionPane.ERROR_MESSAGE,null);
					n1=2;
					nbSommet1.setText("1");
					erreur=true;
				}
				if(m1<1 || m1>N) {
					JOptionPane.showMessageDialog(getContentPane(), "Le nombre d'arêtes doit etre compris entre 1 et "+N,"erreur saisi",JOptionPane.ERROR_MESSAGE,null);
					m=1;
					nbSommet2.setText("1");
					erreur=true;
				}
				if(!erreur) {
					StableMax stabMax=new StableMax(n1, m1, simplexConstructor);
					stabMax.setVisible(true);
				}
			}
		});
		GroupLayout gl_card2 = new GroupLayout(card2);
		gl_card2.setHorizontalGroup(
			gl_card2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_card2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblRechercherUnStable)
					.addContainerGap(20, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_card2.createSequentialGroup()
					.addContainerGap(316, Short.MAX_VALUE)
					.addComponent(nextc2, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addGap(73))
				.addGroup(gl_card2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_card2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNombreDeSommets_1)
						.addComponent(lblNombreDeSommets))
					.addGap(31)
					.addGroup(gl_card2.createParallelGroup(Alignment.LEADING, false)
						.addComponent(nbSommet2, 0, 0, Short.MAX_VALUE)
						.addComponent(nbSommet1, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
					.addGap(289))
		);
		gl_card2.setVerticalGroup(
			gl_card2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_card2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblRechercherUnStable)
					.addGap(23)
					.addGroup(gl_card2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_card2.createSequentialGroup()
							.addGap(73)
							.addComponent(nextc2))
						.addGroup(gl_card2.createSequentialGroup()
							.addGap(3)
							.addGroup(gl_card2.createParallelGroup(Alignment.BASELINE)
								.addComponent(nbSommet1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNombreDeSommets))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_card2.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNombreDeSommets_1)
								.addComponent(nbSommet2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(187))
		);
		card2.setLayout(gl_card2);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(new Color(153, 102, 204));
		jPanel.add(menuPanel,BorderLayout.WEST);
		
		
		JButton resolutionButton = new JButton("R\u00E9soudre un system lin\u00E9aire");
		resolutionButton.setForeground(new Color(0, 0, 0));
		resolutionButton.setBackground(new Color(204, 204, 255));
		resolutionButton.setFont(new Font("Calibri", Font.BOLD, 14));
		resolutionButton.setMaximumSize(new Dimension(200, 25));
		resolutionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(mainPanel, "card1");
			}
		});
		
		JButton graphButton = new JButton("Trouver un stable max");
		graphButton.setBackground(new Color(204, 204, 255));
		graphButton.setFont(new Font("Calibri", Font.BOLD, 14));
		graphButton.setMaximumSize(new Dimension(200, 25));
		graphButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPanel, "card2");
			}
		});
		
		GroupLayout gl_menuPanel = new GroupLayout(menuPanel);
		gl_menuPanel.setHorizontalGroup(
			gl_menuPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_menuPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_menuPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(graphButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(resolutionButton, Alignment.LEADING, 0, 0, Short.MAX_VALUE))
					.addGap(13))
		);
		gl_menuPanel.setVerticalGroup(
			gl_menuPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_menuPanel.createSequentialGroup()
					.addGap(28)
					.addComponent(resolutionButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(graphButton, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(230, Short.MAX_VALUE))
		);
		menuPanel.setLayout(gl_menuPanel);
		
	}
}
