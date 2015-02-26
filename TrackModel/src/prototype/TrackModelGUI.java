package prototype;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;

import java.util.*;

public class TrackModelGUI extends JFrame {

	private JPanel contentPane;
	private JTextField input_filename;
	private JButton btnImportTrack;
	private JPanel panel_1;
	private JPanel panel_2;
	private JScrollPane scrollPane;
	private JList list_line;
	private JScrollPane scrollPane_1;
	private JList list_blockNumber;
	private JLabel lblLines;
	private JLabel lblBlockNumber;
	private JScrollPane scrollPane_2;
	private JLabel lblBlockData;
	private JTable table_blockData;
	private TrackModel trackModel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackModelGUI frame = new TrackModelGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TrackModelGUI() {
		
		trackModel = new TrackModel();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][grow]", "[][grow]"));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 0 2 1,grow");
		
		JLabel lblFilename = new JLabel("Filename");
		panel.add(lblFilename);
		
		input_filename = new JTextField();
		input_filename.setText("green.csv");
		panel.add(input_filename);
		input_filename.setColumns(10);
		
		btnImportTrack = new JButton("Import Track");
		btnImportTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trackModel.importLineFromFile( input_filename.getText() );
				input_filename.setText( "" );
				list_line.setListData( trackModel.getLineNames() );
			}
		});
		panel.add(btnImportTrack);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[grow][grow]", "[][grow]"));
		
		lblLines = new JLabel("Lines");
		panel_1.add(lblLines, "cell 0 0");
		
		lblBlockNumber = new JLabel("Block Number");
		panel_1.add(lblBlockNumber, "cell 1 0");
		
		scrollPane = new JScrollPane();
		panel_1.add(scrollPane, "cell 0 1,grow");
		
		list_line = new JList();
		list_line.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_line.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				JList source = (JList)e.getSource();
				if ( source.getSelectedValue() != null ) {
		            String selected = source.getSelectedValue().toString();
					TrackLayout lay = trackModel.getLine( selected );
					List<TrackBlock> blocks = lay.getBlocks();
					String[] numbers = new String[blocks.size()];
					int i = 0;
					for ( TrackBlock block : blocks ) {
						numbers[i++] = String.valueOf( block.getNumber() );
					}
					list_blockNumber.setListData( numbers );
					scrollPane_2.setViewportView( null );
				}
			}
		});
		scrollPane.setViewportView(list_line);
		
		scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, "cell 1 1,grow");
		
		list_blockNumber = new JList();
		list_blockNumber.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String listLineSelection = list_line.getSelectedValue().toString();
				if ( !listLineSelection.isEmpty() ) {
					JList source = (JList)e.getSource();
					if ( source.getSelectedValue() != null ) {
			            String selected = source.getSelectedValue().toString();
			            TrackLayout lay = trackModel.getLine( listLineSelection );
						List<TrackBlock> blocks = lay.getBlocks();
			            String[][] model = blocks.get( Integer.parseInt( selected ) - 1 ).getModel();
			            table_blockData = new JTable( model, new String[] { "Attribute", "Value" } );
			            scrollPane_2.setViewportView( table_blockData );
					}
				}
			}
		});
		scrollPane_1.setViewportView(list_blockNumber);
		
		panel_2 = new JPanel();
		contentPane.add(panel_2, "cell 1 1,grow");
		panel_2.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		lblBlockData = new JLabel("Block Data");
		panel_2.add(lblBlockData, "cell 0 0");
		
		scrollPane_2 = new JScrollPane();
		panel_2.add(scrollPane_2, "cell 0 1,grow");
		
		table_blockData = new JTable();
		scrollPane_2.setViewportView(table_blockData);
	}

}
