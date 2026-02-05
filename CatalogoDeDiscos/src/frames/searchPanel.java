package frames;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import utils.Dao;
import java.awt.Cursor;

public class searchPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField txtPesquisa;

	/**
	 * Create the panel.
	 */
	public searchPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaptionBorder);
		add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Procurar discos salvos:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtPesquisa = new JTextField();
		txtPesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pesquisar(txtPesquisa.getText());
				}
			}
		});
		txtPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPesquisa.setColumns(10);
		
		JButton btnAtualizar = new JButton("");
		btnAtualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar("");
				txtPesquisa.setText(null);
				txtPesquisa.requestFocus();
			}
		});
		btnAtualizar.setIcon(new ImageIcon(searchPanel.class.getResource("/icons/atualizar.png")));
		
		JButton btnPesquisar = new JButton("");
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar(txtPesquisa.getText());
			}
		});
		btnPesquisar.setIcon(new ImageIcon(searchPanel.class.getResource("/icons/procurar.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtPesquisa, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPesquisar, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAtualizar, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPesquisa, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(btnPesquisar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnAtualizar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "nome", "artista", "data de publica\u00E7\u00E3o", "estado de conserva\u00E7\u00E3o", "adicional"
			}
		));
		table.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) { // 1 clique
		            int linha = table.getSelectedRow();
		            int coluna = table.getColumnModel().getColumnIndex("nome");
		            String nome = table.getValueAt(linha,  coluna).toString();
		            Album album = new Album(nome);
		            album.setVisible(true);
		        }
		    }
		});

		scrollPane.setViewportView(table);
		pesquisar("");
	}
	
	Dao dao = new Dao();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	private void pesquisar(String pesquisa) {
	    String sql = "SELECT id, nome, artista, publicacao, concervacao, adicional FROM discos WHERE id= ? OR nome LIKE ? OR artista LIKE ? OR concervacao LIKE ?";
	    try {
	        con = dao.conexao();
	        pst = con.prepareStatement(sql);
	        pst.setString(1, pesquisa);
	        pst.setString(2, "%" + pesquisa + "%");
	        pst.setString(3, "%" + pesquisa + "%");
	        pst.setString(4, "%" + pesquisa + "%");
	        rs = pst.executeQuery();
	        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
	        modelo.setRowCount(0);
	        while (rs.next()) {
	            modelo.addRow(new Object[]{
	                rs.getString("id"),
	                rs.getString("nome"),
	                rs.getString("artista"),
	                rs.getString("publicacao"),
	                rs.getString("concervacao"),
	                rs.getString("adicional")
	            });
	        }
	        con.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
