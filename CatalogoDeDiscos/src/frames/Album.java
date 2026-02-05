package frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import utils.Dao;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

public class Album extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNome;
	/**
	 * Create the dialog.
	 */
	public Album(String nome) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Album.class.getResource("/icons/vinil.png")));
		setTitle(nome);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblFoto = new JLabel("");
		lblFoto.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblFoto.setBounds(10, 11, 200, 200);
		contentPanel.add(lblFoto);
		
		JLabel lblNewLabel_1 = new JLabel("Nome do album:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(220, 1, 206, 14);
		contentPanel.add(lblNewLabel_1);
		
		lblNome = new JLabel("<var>");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(220, 16, 206, 24);
		contentPanel.add(lblNome);
		
		JLabel lblNewLabel_1_1 = new JLabel("Artista / Compositor:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1_1.setBounds(220, 41, 206, 14);
		contentPanel.add(lblNewLabel_1_1);
		
		lblArtista = new JLabel("<var>");
		lblArtista.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblArtista.setBounds(220, 56, 206, 24);
		contentPanel.add(lblArtista);
		
		JLabel lblNewLabel_1_2 = new JLabel("Data de publicação:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1_2.setBounds(220, 81, 206, 14);
		contentPanel.add(lblNewLabel_1_2);
		
		lblPublicacao = new JLabel("<var>");
		lblPublicacao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPublicacao.setBounds(220, 96, 206, 24);
		contentPanel.add(lblPublicacao);
		
		JLabel lblNewLabel_1_3 = new JLabel("Estado de concervação:");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1_3.setBounds(220, 121, 206, 14);
		contentPanel.add(lblNewLabel_1_3);
		
		lblEstado = new JLabel("<var>");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEstado.setBounds(220, 136, 206, 24);
		contentPanel.add(lblEstado);
		
		JLabel lblNewLabel_1_4 = new JLabel("Informação adicional:");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1_4.setBounds(220, 161, 206, 14);
		contentPanel.add(lblNewLabel_1_4);
		
		lblInfo = new JLabel("<var>");
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInfo.setBounds(220, 173, 206, 38);
		contentPanel.add(lblInfo);
		

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Fechar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		pesquisar(nome);
	}
	
	Dao dao = new Dao();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JLabel lblFoto;
	private JLabel lblArtista;
	private JLabel lblPublicacao;
	private JLabel lblEstado;
	private JLabel lblInfo;
	
	private void pesquisar(String nome) {
		try {
			String pesquisar = "SELECT id, nome, artista, publicacao, concervacao, adicional, foto FROM discos WHERE nome LIKE ?";
			con = dao.conexao();
	        pst = con.prepareStatement(pesquisar);
	        pst.setString(1, "%" + nome + "%");
	        rs = pst.executeQuery();
	        if (rs.next()) {
	        	lblNome.setText(rs.getString("nome"));
	        	lblArtista.setText(rs.getString("artista"));
	        	lblPublicacao.setText(rs.getString("publicacao"));
	        	lblEstado.setText(rs.getString("concervacao"));
	        	lblInfo.setText(rs.getString("adicional"));
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
	        }
	        con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
