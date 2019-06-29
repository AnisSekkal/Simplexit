package THG.views;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.commons.collections15.Transformer;

import THG.common.Model;
import THG.common.Simplex;
import THG.common.SimplexConstruction;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Shape;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.time.chrono.JapaneseChronology;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class StableMax extends JDialog {
	
	JButton okButton=new JButton("Résultat");
	JButton displayGraphButton=new JButton("Afficher");
	JTextField[][] leftSideField;
	JTextField[] objectifField;
	double[] x;
	
	Graph<Integer,String> graph=new SparseMultigraph<Integer,String>();
	
	public SimplexConstruction simplexConstruction;
	
	public StableMax(int n, int m,SimplexConstruction simplexConstructor) {
		
		getContentPane().setBackground(new Color(153, 51, 255));
		this.setMinimumSize(new Dimension(400,300));
		this.setTitle("Graphe");
		this.setLocationRelativeTo(null);
		leftSideField=new JTextField[m][n];
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
		//ajout des labels
		jPanel.add(new JLabel(""));
	for (int i = 0; i< n; i++ ){
		int j=i+1;
		JLabel jLabel=new JLabel("X"+j);
		jLabel.setPreferredSize(new Dimension(30, 25));
		if(i==n-1) {
			jPanel.add(jLabel,"wrap");
		}else{
			jPanel.add(jLabel);
		}
		}
		
		//to add textfields
	for(int i=0; i<m; i++) {
		jPanel.add(new JLabel("e"+(i+1)));
		for(int j=0;j<n;j++) {
			JTextField jText=new JTextField();
			
			jText.setPreferredSize(new Dimension(30, 25));
			jText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					char c=e.getKeyChar();
					if(Character.isLetter(c) && !e.isAltDown()) {
						e.consume();
					}
				}
			});
			if(j==n-1) {
			jPanel.add(jText,"wrap");
			}
			else
			{
				jPanel.add(jText);
			}
			
			leftSideField[i][j]=jText;
		}

	}
	
	JLabel objectfiLabel=new JLabel("Poids des sommets");
	objectfiLabel.setPreferredSize(new Dimension(60, 30));
	jPanel.add(objectfiLabel,"span");
	for (int i = 0; i< n; i++ ){
		int j=i+1;
		JLabel jLabel=new JLabel("X"+j);
		jLabel.setPreferredSize(new Dimension(30, 25));
		if(i==n-1) {
			jPanel.add(jLabel,"wrap");
		}else{
			jPanel.add(jLabel);
		}
		}
	
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
		jText.setPreferredSize(new Dimension(30, 25));
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
				validateConstraint(n,m);
				fillSimplexConstructor(n, m,simplexConstructor,true);
			}
		});
		
		//affichage du graphe 
		//affichage du graphes
		displayGraphButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//SimpleGraphDraw sgv = new SimpleGraphDraw(); 
				fillSimplexConstructor(n, m, simplexConstructor, false);
				 
				Layout<Integer, String> layout = new CircleLayout<Integer, String>(graph);
				layout.setSize(new Dimension(300,300)); 
				 
				VisualizationViewer<Integer,String> vv = new VisualizationViewer<Integer,String>(layout);
				 
				Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
					 public Paint transform(Integer i) {
					 return Color.GREEN;
					 }
				 };;
				 
				 
				vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
				vv.getRenderer().setVertexRenderer(new MyRenderer());
				vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
				vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
				vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
				final DefaultModalGraphMouse<String,Number> graphMouse = new DefaultModalGraphMouse<String,Number>();
				vv.setGraphMouse(graphMouse);
				vv.setBackground(Color.white);
				vv.setBorder(BorderFactory.createLineBorder(Color.black));
				graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
				
				GraphDisplay graphDisplay=new GraphDisplay(vv);
				graphDisplay.setVisible(true);
			}
		});
		
		
		okButton.setSize(new Dimension(120, 30));
		displayGraphButton.setSize(new Dimension(120,30));
		jPanel.add(okButton,"span 3, growx");
		jPanel.add(displayGraphButton,"span 3, growx");
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		
		JLabel titleLabel = new JLabel("Matrice d'incidence");
		titleLabel.setBackground(new Color(153, 51, 255));
		titleLabel.setPreferredSize(new Dimension(144, 30));
		titleLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 18));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setVerticalAlignment(SwingConstants.CENTER);
		getContentPane().add(titleLabel, BorderLayout.NORTH);
}
	//permet de remplir un objet de type SimplexContruction pour résoudre le system
	public void fillSimplexConstructor(int n,int m, SimplexConstruction scstr, boolean displayResult) {
		double[][] _A=new double[m][n];
		double[] _b=new double[m];
		String[] _sign=new String[m];
		double[] _c=new double[n];
		
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				try {
				_A[i][j]=Double.parseDouble(leftSideField[i][j].getText());
				
				//System.out.print("\n value i,j "+i+","+j+_A[i][j]);
				}catch(Exception e) {
					_A[i][j]=0f;
					System.out.print("error mat A");
					}
				}
			_b[i]=1.0f;
			_sign[i]="<=";
		}
		
		for(int i=0;i<n;i++) {
			try{
				_c[i]=Double.parseDouble(objectifField[i].getText().toString());
			}catch(Exception e)
			{
				System.out.print("error mat C");
			//	errorInput(null, "verifier les valeurs entrés"+"\n"+e.getMessage().toString(), "erreur saisi");
				break;
			}
		}
		for (int i=0;i<m;i++) {
			System.out.println();
			for(int j = 0 ; j<n;j++) {
				System.out.print(" "+_A[i][j]+" | ");
			}
		}
		scstr.setA(_A);
		scstr.setb(_b);
		scstr.setc(_c);
		scstr.sets(_sign);
		scstr.setn(n);
		scstr.setm(m);
		scstr.setMax(true);
		SimplexConstruction simplexConstruction=new SimplexConstruction(n,m,_A,_b,_c,_sign,true);
		Model model = new Model(simplexConstruction.getA(),
				simplexConstruction.getb(),
				Model.convertToConstraint(simplexConstruction.gets()),
				simplexConstruction.getc());
		
		Simplex simplex = new Simplex(model.getTableaux(), model.getNumberOfConstraint(),model.getNumberOfOriginalVariable(),
		simplexConstruction.getMax());
		double z=simplex.zValue();
		x=simplex.solutions();
		boolean b=true;
		
		for(int i =0;i<x.length;i++) {
			if(x[i]%1!=0) {
				b=false;
				break;
			}
		}
		//si on veut afficher le tableau
		if(verifyIncidence(_A) && displayResult) {
			if(b) {
				ResultPanel resultPanel=new ResultPanel(simplexConstruction);
				resultPanel.setVisible(true);
				//dispose();
			}
			else {
				errorInput(null, "Ce graphe n'est pas un graphe bipartis\nla solution n'est pas entiere", "erreur Graphe n'est pas biparti");
				dispose();
			}
		}
		
		//si on veut afficher le graphe
		else if(verifyIncidence(_A) && !displayResult) {
			buildGraph(_A, n, m);
		}
	}
	
	//permet de verifier si tous les champs ont été saisis
	public void validateConstraint(int n,int m) {
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				if(leftSideField[i][j].getText().isEmpty()) {
					//System.out.print("pos i : "+i+" j : "+j);
					leftSideField[i][j].setText("0");
				}
				//verifier si la fonction objectif contient des blancs
				if(objectifField[j].getText().isEmpty()) {
					//System.out.println("pos : "+j);
					objectifField[j].setText("0");
				}
			}
			
		}
	}
	public int[] getPos(double[] vect){
		int[] pos=new int[2];
		boolean foundFirst=false;
		for(int i=0;i<vect.length;i++) {
			if(vect[i]==1 && !foundFirst) {
				pos[0]=i;
				foundFirst=true;
			}
			if(vect[i]==1 && foundFirst) {
				pos[1]=i;
			}
		}
		System.out.println("Edge : "+pos[0]+" , "+pos[1]);
		return pos;
	}
	public void buildGraph(double[][] incidence,int n, int m){
		
		for(int i=0;i<n;i++) {
			graph.addVertex((Integer)(i+1));
		}
		for(int i=0;i<m;i++) {
			int deb=1+getPos(incidence[i])[0];
			int fin=1+getPos(incidence[i])[1];
			int j=i+1;
			graph.addEdge("e"+j,deb,fin);
		}
	}
	public boolean verifyIncidence(double[][] mat) {
		boolean b=true;
		int row=mat.length;
		int col=mat[0].length;
		int sum=0;
		for (int i =0;i<row;i++) {
			for(int j=0;j<col;j++) {
				sum+=mat[i][j];
			}
			if(sum!=2) {
				//JOptionPane.showMessageDialog(null, "Matrice n'est pas une matrice d'incidence","erreur saisi matrice", JOptionPane.WARNING_MESSAGE, null);
				//System.out.println(sum  + ": n'est pas matrice d'incidence");
				b=false;
			}
			sum=0;
		}
		return b;
	}
	
	public void errorInput(JComponent jp,String msg,String title) {
		JOptionPane.showMessageDialog(jp, msg,title, JOptionPane.WARNING_MESSAGE, null);
	}
	public class MyRenderer implements Renderer.Vertex<Integer, String> {
	    @Override
	    public void paintVertex(RenderContext<Integer, String> rc,
	    		Layout<Integer, String> layout, Integer vertex) {
	     GraphicsDecorator graphicsContext = rc.getGraphicsContext();
	    
	     Point2D center = layout.transform(vertex);
	     Color color=null;
	     Shape shape = null;
	     
	     if(findVertex(vertex)) {
	    	 shape = new Ellipse2D.Double(center.getX()-10, center.getY()-10, 20, 20);
	    	 color = Color.green;
	      
	     }
	     else {
	    	 shape = new Ellipse2D.Double(center.getX()-10, center.getY()-10, 20, 20);
	    	 color = Color.ORANGE;
	     }
	      graphicsContext.setPaint(color);
	      graphicsContext.fill(shape);
	    }
	  }
	public boolean findVertex(Integer vertex) {
		boolean b=false;
		for(int i=0;i<x.length;i++) {
			if(vertex-1==i && x[i]!=0)
				b=true;	
		}
		return b;
	}
}
