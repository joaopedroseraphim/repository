package frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.Dao;
import java.awt.Cursor;

public class savePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtBusca;
	private JTextField txtNome;
	private JTextField txtArtista;
	private JTextField txtPublicacao;
	private JLabel lblFoto;
	private JButton btnAdicionar;
	private JButton btnAlterar;
	private JButton btnDeletar;
	private JButton btnImagem;
	private JTextPane textInfo;
	private JTextField txtEstado;

	/**
	 * Create the panel.
	 */
	public savePanel() {
		
		lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon(savePanel.class.getResource("/icons/imagempadrao.png")));
		lblFoto.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		btnImagem = new JButton("Adicionar Foto");
		btnImagem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagem();
			}
		});
		btnImagem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaptionBorder);
		
		JLabel lblSelecionarUmDisco = new JLabel("Selecionar um disco:");
		lblSelecionarUmDisco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtBusca = new JTextField();
		txtBusca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pesquisa();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					txtNome.requestFocus();
				}
			}
		});
		txtBusca.setToolTipText("nome ou id do disco");
		txtBusca.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtBusca.setColumns(10);
		
		JButton btnLimpar = new JButton("limpar\r\n");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setToolTipText("limpa todos os campos de escrita");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLimpar.setIcon(new ImageIcon(savePanel.class.getResource("/icons/seta-pequena-esquerda.png")));
		
		btnBusca = new JButton("");
		btnBusca.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBusca.setToolTipText("pesquisar disco");
		btnBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisa();
			}
		});
		btnBusca.setIcon(new ImageIcon(savePanel.class.getResource("/icons/procurar.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSelecionarUmDisco, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtBusca, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnBusca, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLimpar)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 34, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnBusca, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLimpar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblSelecionarUmDisco)
							.addComponent(txtBusca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		JLabel lblNewLabel_1 = new JLabel("Nome do disco:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtArtista.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (txtBusca.isEnabled() == true) {
						txtBusca.requestFocus();
					}
				}
 			}
		});
		txtNome.setColumns(10);
		
		txtArtista = new JTextField();
		txtArtista.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtPublicacao.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					txtNome.requestFocus();
				}
			}
		});
		txtArtista.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Artista:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtPublicacao = new JTextField();
		txtPublicacao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtEstado.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					txtArtista.requestFocus();
				}
			}
		});
		txtPublicacao.setColumns(10);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Data de publicação:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtEstado = new JTextField();
		txtEstado.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) {
					textInfo.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					txtPublicacao.requestFocus();
				}
			}
		});
		txtEstado.setColumns(10);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Estado de conservação");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Informação adicional:");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textInfo = new JTextPane();
		textInfo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					txtEstado.requestFocus();
				}
			}
		});
		
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setToolTipText("adicionar novo registro");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(savePanel.class.getResource("/icons/adicionar-ficheiro.png")));
		btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		btnDeletar = new JButton("Deletar");
		btnDeletar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeletar.setEnabled(false);
		btnDeletar.setToolTipText("deletar registro selecionado");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletar();
			}
		});
		btnDeletar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDeletar.setIcon(new ImageIcon(savePanel.class.getResource("/icons/botao-apagar.png")));
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAlterar.setEnabled(false);
		btnAlterar.setToolTipText("salvar alteraçao no registro");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnAlterar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAlterar.setIcon(new ImageIcon(savePanel.class.getResource("/icons/alterar.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textInfo, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1_1_1_1_1, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtEstado, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1_1_1_1, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtPublicacao, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtArtista, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnImagem, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblFoto, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
							.addGap(2)))
					.addGap(21))
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnAdicionar, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
					.addGap(11)
					.addComponent(btnAlterar, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDeletar, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblFoto, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnImagem, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(txtArtista, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(txtPublicacao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1_1_1_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(txtEstado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1_1_1_1_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textInfo, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnAlterar, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnDeletar, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnAdicionar, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		setLayout(groupLayout);

	}
	
	Dao dao = new Dao();
	private Connection con;
	private boolean pesquisa = false;
	private FileInputStream fis;
	private long tamanho;
	private boolean fotoinserida = false;
	private PreparedStatement pst;
	private ResultSet rs;
	private JButton btnBusca;
	
	private void smd(String texto) {JOptionPane.showMessageDialog(null, texto);}
	
	private void imagem() {
	    JFileChooser jfc = new JFileChooser();
	    jfc.setDialogTitle("Escolher Arquivo");
	    jfc.setFileFilter(
	        new FileNameExtensionFilter("Imagens (*.PNG, *.JPG, *.JPEG)", "png", "jpg", "jpeg")
	    );

	    int resultado = jfc.showOpenDialog(this);

	    if (resultado == JFileChooser.APPROVE_OPTION) {
	        File file = jfc.getSelectedFile();

	        try {
	            fis = new FileInputStream(file);
	            tamanho = file.length();

	            BufferedImage img = ImageIO.read(file);
	            if (img == null) {
	                throw new IOException("Formato de imagem não suportado");
	            }
	            Image foto = img.getScaledInstance(lblFoto.getWidth(),lblFoto.getHeight(),Image.SCALE_SMOOTH);
	            lblFoto.setIcon(new ImageIcon(foto));
	            fotoinserida = true;
	        } catch (Exception e) {
	            fotoinserida = false;
	            lblFoto.setIcon(null);
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Erro ao carregar imagem");
	        }
	    }
	}
	
	private void pesquisa() {
		if (txtBusca.getText().isEmpty()) {
			smd("campo 'pesquisa' esta vazio");
		} else {
			try {
				String item = txtBusca.getText();
				String pesquisar = "SELECT id, nome, artista, publicacao, concervacao, adicional, foto FROM discos WHERE id = ? OR nome LIKE ?";
				con = dao.conexao();
		        pst = con.prepareStatement(pesquisar);
		        pst.setString(1, item);
		        pst.setString(2, "%" + item + "%");
		        rs = pst.executeQuery();
		        if (rs.next()) {
		        	txtNome.setText(rs.getString("nome"));
		        	txtArtista.setText(rs.getString("artista"));
		        	txtPublicacao.setText(rs.getString("publicacao"));
		        	txtEstado.setText(rs.getString("concervacao"));
		        	textInfo.setText(rs.getString("adicional"));
		        	pesquisa = true;
		        	Blob blob = (Blob) rs.getBlob("foto");
		        	byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
						System.out.println(e);	
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto =  new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),lblFoto.getHeight(),Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
					
					txtBusca.setEnabled(false);
					btnBusca.setEnabled(false);
					btnAdicionar.setEnabled(false);
					btnAlterar.setEnabled(true);
					btnDeletar.setEnabled(true);
					txtNome.requestFocus();
		        } else {
		        	smd("item não encontrado");
		        }
		        con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void salvar() {
		if (txtNome.getText().isEmpty()) {
			smd("campo 'nome do disco' esta vazio");
		} else if (txtArtista.getText().isEmpty()) {
			smd("campo 'artista' esta vazio");
		} else if (txtPublicacao.getText().isEmpty()) {
			smd("campo 'data de publicação' esta vazio");
		} else if (txtEstado.getText().isEmpty()) {
			smd("campo 'estado de concervação' esta vazio");
		} else if (fotoinserida == false) {
			smd("insira uma foto para continuar");
		} else {
			String insert = "insert into discos(nome, artista, publicacao, concervacao, adicional, foto) values(?, ?, ?, ?, ?, ?)";
			try {
				con = dao.conexao();
				pst = con.prepareStatement(insert);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtArtista.getText());
				pst.setString(3, txtPublicacao.getText());
				pst.setString(4, txtEstado.getText());
				pst.setString(5, textInfo.getText());
				pst.setBinaryStream(6, fis, tamanho);
				int verifica = pst.executeUpdate();
				if (verifica == 1) {
					JOptionPane.showMessageDialog(null, "Salvo com sucesso");
					limpar();
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao salvar");
					limpar();
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	private void alterar() {
		if (pesquisa == false) {
			smd("pesquise um album primeiro");
		} else {
			if (txtNome.getText().isEmpty()) {
				smd("campo 'nome do disco' esta vazio");
			} else if (txtArtista.getText().isEmpty()) {
				smd("campo 'artista' esta vazio");
			} else if (txtPublicacao.getText().isEmpty()) {
				smd("campo 'data de publicação' esta vazio");
			} else if (txtEstado.getText().isEmpty()) {
				smd("campo 'estado de concrvação' esta vazio");
			} else {
				String item = txtBusca.getText();
				if (fotoinserida == true) {
					String update = "update discos set nome=?, artista=?, publicacao=?, concervacao=?, adicional=?, foto=? WHERE id = ? OR nome like ?";
					try {
						con = dao.conexao();
						pst = con.prepareStatement(update);
						pst.setString(1, txtNome.getText());
						pst.setString(2, txtArtista.getText());
						pst.setString(3, txtPublicacao.getText());
						pst.setString(4, txtEstado.getText());
						pst.setString(5, textInfo.getText());
						pst.setBlob(6, fis, tamanho);
						pst.setString(7, item);
						pst.setString(8, "%" + item + "%");
						int confirma = pst.executeUpdate();
						if (confirma == 1) {
							JOptionPane.showMessageDialog(null, "Alterado com sucesso");
							limpar();
						} else {
							JOptionPane.showMessageDialog(null, "Erro ao alterar");
						}
						con.close();
					} catch (Exception e) {
						System.out.println(e);
					}
				} else {
					String update = "update discos set nome=?, artista=?, publicacao=?, concervacao=?, adicional=? WHERE id = ? OR nome like ?";
					try {
						con = dao.conexao();
						pst = con.prepareStatement(update);
						pst.setString(1, txtNome.getText());
						pst.setString(2, txtArtista.getText());
						pst.setString(3, txtPublicacao.getText());
						pst.setString(4, txtEstado.getText());
						pst.setString(5, textInfo.getText());
						pst.setString(6, item);
						pst.setString(7, "%" + item + "%");
						int confirma = pst.executeUpdate();
						if (confirma == 1) {
							JOptionPane.showMessageDialog(null, "Alterado com sucesso");
							limpar();
						} else {
							JOptionPane.showMessageDialog(null, "Erro ao alterar");
						}
						con.close();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		}
	}
	
	private void deletar() {
		if (pesquisa == false) {
			smd("pesquise um album primeiro");
		} else {
			int confirmaExclusao = JOptionPane.showConfirmDialog(null, "deseja excluir o registro?", "Atenção!",JOptionPane.YES_NO_OPTION);
			if (confirmaExclusao == JOptionPane.YES_OPTION) {
				String item = txtBusca.getText();
				String delete = "DELETE FROM discos WHERE id=? OR nome like ?";
				try {
					con = dao.conexao();
					pst = con.prepareStatement(delete);
					pst.setString(1, item);
					pst.setString(2, "%" + item + "%");
					int confirma = pst.executeUpdate();
					if (confirma == 1) {
						JOptionPane.showMessageDialog(null, "Excluido com sucesso");
						limpar();					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				limpar();
			}
		}
	}
	
	private void limpar() {
		txtBusca.setText(null);
		txtNome.setText(null);
		txtArtista.setText(null);
		txtPublicacao.setText(null);
		txtEstado.setText(null);
		textInfo.setText(null);
		pesquisa = false;
		txtBusca.setEnabled(true);
		btnBusca.setEnabled(true);
		btnAdicionar.setEnabled(true);
		btnAlterar.setEnabled(false);
		btnDeletar.setEnabled(false);
		lblFoto.setIcon(new ImageIcon(savePanel.class.getResource("/icons/imagempadrao.png")));
	}
}
