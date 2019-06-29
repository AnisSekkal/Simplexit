package THG.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import net.miginfocom.swing.MigLayout;

public class GraphDisplay extends JFrame {

	private JPanel contentPane;

	public GraphDisplay(VisualizationViewer vv) {
		vv.setPreferredSize(new Dimension(300,300));
		vv.setMaximumSize(new Dimension(350, 350));
		JLabel jLabel=new JLabel("Représentation du Graphe",SwingConstants.CENTER);
		jLabel.setFont(new Font("Calibri", Font.BOLD, 22));
		setTitle("Affichage");
		JPanel panel=new JPanel(new BorderLayout());
		JScrollPane scrollPane=new JScrollPane(panel);
		
		JLabel otherVertices =new JLabel("Ensemble des sommets");
		otherVertices.setForeground(Color.orange);
		otherVertices.setFont(new Font("Calibri", Font.BOLD, 14));
		JLabel otherVertices1 =new JLabel("hors stable");
		otherVertices1.setForeground(Color.orange);
		otherVertices1.setFont(new Font("Calibri", Font.BOLD, 14));
		
		
		JLabel stableVertices=new JLabel("Ensemble des sommets");
		stableVertices.setFont(new Font("Calibri", Font.BOLD, 14));
		stableVertices.setForeground(Color.green);
		
		JLabel stableVertices1=new JLabel("du stable");
		stableVertices1.setFont(new Font("Calibri", Font.BOLD, 14));
		stableVertices1.setForeground(Color.green);
		
		JButton fermer =new JButton("Fermer");
		fermer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		fermer.setSize(new Dimension(120, 25));
		
		JPanel panel2=new JPanel(new MigLayout("", "[]", "[][]"));
		panel2.add(otherVertices,"wrap");
		panel2.add(otherVertices1,"wrap");
		panel2.add(stableVertices, "gaptop 10,wrap");
		panel2.add(stableVertices1, "wrap");
		panel.add(panel2,BorderLayout.WEST);
		panel.add(vv,BorderLayout.CENTER);
		panel.add(jLabel,BorderLayout.NORTH);
		
		panel.setMinimumSize(new Dimension(500, 500));
		
		JPanel panel3 = new JPanel();
		panel3.add(fermer);
		panel.add(panel3, BorderLayout.SOUTH);
		
		JPanel panel4 = new JPanel();
		panel4.setSize(new Dimension(15,1));
		panel.add(panel4, BorderLayout.EAST);
		
		getContentPane().add(scrollPane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

}
