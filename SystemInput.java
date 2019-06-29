package THG.views;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import THG.common.SimplexConstruction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class SystemInput extends JDialog {
	
	JButton okButton=new JButton("ok");
	JTextField[][] leftSideField;
	JTextField[] signFields;
	JTextField[] rightSideField;
	JTextField[] objectifField;
	boolean maxmin;
	JRadioButton[] maxMinRadioButton=new JRadioButton[2];
	public SimplexConstruction simplexConstruction;
	
	//test jDialog
	public static void main(String[] args) {
		SimplexConstruction s=new SimplexConstruction();
		SystemInput si=new SystemInput(5,10,null);
		si.setSize(600, 450);
		si.setLocationRelativeTo(null);
		si.setVisible(true);
	}
	
	public SystemInput(int n, int m,SimplexConstruction simplexConstructor) {
		getContentPane().setBackground(new Color(153, 51, 255));
		this.setMinimumSize(new Dimension(400,300));
		this.setTitle("Contraintes");
		this.setLocationRelativeTo(null);
		leftSideField=new JTextField[m][n];
		rightSideField=new JTextField[m];
		signFields=new JTextField[m];
		objectifField=new JTextField[n];
		
		//pour avoir un scroll en cas d'un system tres grand
		//on ajoute tous les composants a un jPanel
		//puis on ajout le jpanel au JScrollPane
		//see JScrollPane
		//see JPanel
		JPanel jPanel=new JPanel(new MigLayout());
		jPanel.setBorder(null);
		jPanel.setBackground(Color.WHITE);
		JScrollPane scrollPane = new JScrollPane(jPanel);
		getContentPane().add(scrollPane);

		getContentPane().setLayout(new BorderLayout());
		//to add labels
	for (int i = 0; i< n; i++ ){
		int j=i+1;
		JLabel jLabel=new JLabel("X"+j);
		jLabel.setPreferredSize(new Dimension(60, 20));
		jPanel.add(jLabel);
	}
		
		//ajout des element de l'ihm
		
		JLabel jLabelSigne=new JLabel("signe");
		jLabelSigne.setPreferredSize(new Dimension(50, 20));
		jPanel.add(jLabelSigne,"gapleft 15");
		JLabel jLabelb=new JLabel("b");
		jLabelb.setPreferredSize(new Dimension(50, 20));
		jPanel.add(jLabelb,"wrap");
		
		//to add textfields
	for(int i=0; i<m; i++) {
		for(int j=0;j<n;j++) {
			JTextField jText=new JTextField();
			jText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					char c=e.getKeyChar();
					if(Character.isLetter(c) && !e.isAltDown()) {
						e.consume();
					}
				}
			});
			//jText.setText("0");
			jText.setPreferredSize(new Dimension(60, 20));
			jPanel.add(jText);
			leftSideField[i][j]=jText;
		}
		//signe textfield
		JTextField jSignText=new JTextField();
		jSignText.setText("<=");
		jSignText.setPreferredSize(new Dimension(30, 20));
		jPanel.add(jSignText,"gapleft 15");
		signFields[i]=jSignText;
		
		//membre droit
		JTextField jTextb=new JFormattedTextField();
		jTextb.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c=e.getKeyChar();
				if(Character.isLetter(c) && !e.isAltDown()) {
					e.consume();
				}
			}
		});
		//jTextb.setText("0");
		jTextb.setPreferredSize(new Dimension(40, 20));
		jPanel.add(jTextb,"wrap");
		rightSideField[i]=jTextb;
		
		
	}
	JLabel objectfiLabel=new JLabel("Fonction Objectif");
	objectfiLabel.setPreferredSize(new Dimension(60, 30));
	jPanel.add(objectfiLabel,"span");
	
	for(int i=0;i<n;i++) {
		JTextField jText=new JTextField();
		jText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c=e.getKeyChar();
				if(Character.isLetter(c) && !e.isAltDown()) {
					e.consume();
				}
			}
		});
		
		//jText.setText("0");
		jText.setPreferredSize(new Dimension(60, 20));
		if(i==n-1) {
			jPanel.add(jText,"wrap");
		}
		else
			jPanel.add(jText);
		objectifField[i]=jText;
	}
	//si l'utilisateur appuie sur ok on verifie les valeurs
	//et on rempli les matrice qu'on transmet au modele et controller
	//pour trouver la solution
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateConstraint(n, m);
				fillSimplexConstructor(n, m,simplexConstructor);
				dispose();
			}
		});
		
		//ajout des radios bouton
		JRadioButton maximiser = new JRadioButton("Maximiser", true);
		maximiser.setBackground(Color.WHITE);
        JRadioButton minimiser = new JRadioButton("Minimiser");
        minimiser.setBackground(Color.WHITE);
        ButtonGroup bgroup = new ButtonGroup();
        bgroup.add(maximiser);
        bgroup.add(minimiser);
        JPanel radioPanel = new JPanel();
        radioPanel.setBackground(Color.WHITE);
        radioPanel.setLayout(new GridLayout(1, 2));
        radioPanel.add(maximiser);
        radioPanel.add(minimiser);

        radioPanel.setBorder(BorderFactory.createTitledBorder(
                   BorderFactory.createEtchedBorder(), "Max/Min"));
        jPanel.add(radioPanel,"span");
        maxMinRadioButton[0]=maximiser;
        maxMinRadioButton[1]=minimiser;
		okButton.setSize(new Dimension(120, 30));
		jPanel.add(okButton,"span 2, growx");
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		
		JLabel titleLabel = new JLabel("Remplissez le syst\u00E8me suivant");
		titleLabel.setBackground(new Color(153, 51, 255));
		titleLabel.setPreferredSize(new Dimension(144, 30));
		titleLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 18));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setVerticalAlignment(SwingConstants.CENTER);
		getContentPane().add(titleLabel, BorderLayout.NORTH);
		//pack();
}
	//permet de remplir un objet de type SimplexContruction pour résoudre le system
	public void fillSimplexConstructor(int n,int m, SimplexConstruction scstr) {
		double[][] _A=new double[m][n];
		double[] _b=new double[m];
		String[] _sign=new String[m];
		double[] _c=new double[n];
		boolean maxMin=true;
		
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				try{
					_A[i][j]=Double.valueOf(leftSideField[i][j].getText());
					//System.out.print("\n value i,j "+i+","+j+_A[i][j]);
				}catch(Exception e){
					System.out.println(e.getMessage());
					errorInput(null, "verifier les valeurs entrés"+"\n"+e.getMessage().toString(), "erreur saisi");
				}
			}
			_sign[i]=signFields[i].getText().toString();
			try {
			_b[i]=Double.parseDouble(rightSideField[i].getText().toString());
			}catch(Exception e) {
				System.out.print(e.getMessage());
				errorInput(null, "verifier les valeurs entrés"+"\n"+e.getMessage().toString(), "erreur saisi");
			}
		}
		for(int i=0;i<n;i++) {
			try{_c[i]=Double.parseDouble(objectifField[i].getText().toString());
			}catch(Exception e) {
				System.out.print(e);
				errorInput(null, "verifier les valeurs entrés"+"\n"+e.getMessage().toString(), "erreur saisi");
			}
		}
		scstr.setA(_A);
		scstr.setb(_b);
		scstr.setc(_c);
		scstr.sets(_sign);
		scstr.setn(n);
		scstr.setm(m);
		scstr.setMax(maxMin);
		
		ResultPanel resultPanel=new ResultPanel(new SimplexConstruction(n,m,_A,_b,_c,_sign,maxMin));
		resultPanel.setVisible(true);
		
	}
	
	//permet de verifier si tous les champs ont été saisis
	public void validateConstraint(int n,int m) {
		if(maxMinRadioButton[0].isSelected()) {
			maxmin=true;
			System.out.print(maxmin);
		}
		else {
			maxmin=false;
		}
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				if(leftSideField[i][j].getText().isEmpty()) {
					System.out.print("pos i : "+i+" j : "+j);
					leftSideField[i][j].setText("0");
					
				}
				//verifier si la fonction objectif contient des blancs
				if(objectifField[j].getText().isEmpty()) {
					System.out.println("pos : "+j);
					objectifField[j].setText("0");
				}
			}
			//verifier si le membre droit contient des blancs
			if(rightSideField[i].getText().isEmpty()) {
				System.out.println("pos : "+i);
				rightSideField[i].setText("0");
			}
			//verifier s
			validateSign(signFields[i]);
		}
	}
	public void validateSign(JTextField text) {
		String t=text.getText().toString();
		if(!t.equals(new String("=")) && !t.equals(new String(">=")) && !t.equals(new String("<=")) ) {
			text.setText("=");
		}
	}
	public void errorInput(JComponent jp,String msg,String title) {
		JOptionPane.showMessageDialog(jp, msg,title, JOptionPane.WARNING_MESSAGE, null);
	}
}
