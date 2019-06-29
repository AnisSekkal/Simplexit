package THG.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import THG.common.Model;
import THG.common.Simplex;
import THG.common.SimplexConstruction;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Component;

public class ResultPanel extends JDialog {
	
	List<double[][]> tabs=new ArrayList<>();
	boolean isUnbounded=false;
	double value;
	public static void main() {
		
	}
	
	public ResultPanel() {
		super();
		JPanel jPanel = new JPanel();
		jPanel.setBackground(Color.WHITE);
		JScrollPane scrollPane = new JScrollPane(jPanel);
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		getContentPane().add(scrollPane);
		
	}
	
	public ResultPanel(SimplexConstruction simplexConstruction) {
		setTitle("Simplex");
		JPanel jPanel = new JPanel();
		JButton next = new JButton("Fermer");
		next.setSize(new Dimension(150, 30));
		jPanel.setLayout(new MigLayout());
		setSize(new Dimension(550,500));
		setMinimumSize(new Dimension(350,250));
		setLocationRelativeTo(null);
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}});
		
		Model model = new Model(simplexConstruction.getA(),
				simplexConstruction.getb(),
				Model.convertToConstraint(simplexConstruction.gets()),
				simplexConstruction.getc());
		
		Simplex simplex = new Simplex(model.getTableaux(), model.getNumberOfConstraint(),model.getNumberOfOriginalVariable(),
		simplexConstruction.getMax());
		
		//mettre les tableaux dans le Panel
		tabs=simplex.getTab();
		isUnbounded=simplex.getIsUnbounded();
		value=simplex.zValue();
		
		int count=0;
		if(!isUnbounded) {
			String zValue=new String("Valeur optimale : "+value);
			JLabel zValueLabel=new JLabel(zValue);
			zValueLabel.setFont(new Font("Calibri", Font.BOLD, 14));
			
			//construction des tableaux
		for(double[][] ds :tabs) {
			
			//afficher l'index du tableau
			String tableTitle=new String("Tableau "+count);
			JLabel jTableTitle=new JLabel(tableTitle);
			jTableTitle.setFont(new Font("Calibri", Font.BOLD, 14));
			
			DefaultTableModel mod = new DefaultTableModel(0, ds[0].length)
			{
				@Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			    @Override
			    public Class getColumnClass(int column)
			    {
			        return Integer.class; // number will be displayed right aligned
			    }
			};
			
			for (int j = 0; j < ds.length; j++)
			{
			    double[] rowData = ds[j];
			    Vector<Object> row = new Vector<Object>(ds[0].length);
			    for (int k = 0; k < rowData.length; k++)
			        row.addElement(new Double(rowData[k]));
			    mod.addRow( row );
			}
			JTable table = new JTable(mod) {
				@Override
				public Component prepareRenderer(TableCellRenderer renderer,int rowIndex, int colIndex) {
					Component component = super.prepareRenderer(renderer, rowIndex, colIndex);
					//Object value = getModel().getValueAt(rowIndex, colIndex);
					
					if(colIndex==ds[0].length-1 )
					{
						if(rowIndex==ds.length-1)
						component.setBackground(Color.green);
						else
						component.setBackground(Color.YELLOW);
					}
					else {
						component.setBackground(Color.white);
					}
					return component;
				}
			};
			table.setRowHeight(25);
			table.setCellSelectionEnabled(false);
			setCellsAlignment(table,JLabel.CENTER);
			JPanel jPanel2=new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(table,BorderLayout.CENTER);
			jPanel.add(jTableTitle,"span");
			jPanel.add(jPanel2,"wrap");
			count++;
		}
		
		JLabel solLabel=new JLabel("Solution Optimale:");
		jPanel.add(solLabel,"wrap");
		double[] x = simplex.solutions();
		
		for(int i=0;i<simplexConstruction.getn();i++) {
			int j=i+1;
			JLabel valLabel=new JLabel("X"+(j)+" = "+x[i]);
				jPanel.add(valLabel,"span");
		}
		
		jPanel.add(zValueLabel,"span");
		jPanel.add(next,"gapleft 200");
		
		JScrollPane scrollPane = new JScrollPane(jPanel);
		getContentPane().add(scrollPane);
		setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null,"Systeme n'a pas de solution",
					"Erreur solution", JOptionPane.WARNING_MESSAGE, null);
		}
		
	}
	 public static void setCellsAlignment(JTable table, int alignment)
	    {
	        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	        rightRenderer.setHorizontalAlignment(alignment);

	        TableModel tableModel = table.getModel();

	        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++)
	        {
	            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
	        }
	    }
	
}
