package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class mainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane abas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainFrame() {
		setPreferredSize(new Dimension(540, 500));
		setSize(new Dimension(540, 520));

	    setBackground(SystemColor.scrollbar);
	    setMinimumSize(new Dimension(630, 480));
	    setTitle("catalogador de discos vinil");
	    setIconImage(Toolkit.getDefaultToolkit().getImage(
	            mainFrame.class.getResource("/icons/vinil.png")));
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 640, 480);

	    contentPane = new JPanel();
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    contentPane.setLayout(new BorderLayout(0, 0));
	    setContentPane(contentPane);

	    abas = new JTabbedPane();
	    
	    abas.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	    contentPane.add(abas, BorderLayout.CENTER);

	    JMenuBar menuBar = new JMenuBar();
	    menuBar.setBackground(SystemColor.activeCaptionBorder);
	    setJMenuBar(menuBar);

	    JMenu menu1 = new JMenu("Ferramentas");
	    menuBar.add(menu1);

	    JMenuItem mntmNewMenuItem = new JMenuItem("Realizar alteraçoes");
	    mntmNewMenuItem.setIcon(new ImageIcon(mainFrame.class.getResource("/icons/adicionar.png")));
	    mntmNewMenuItem.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		savePanel painel = new savePanel();
	    	    String titulo = "Adicionar";
	    	    abas.addTab(titulo, painel);
	    	    int index = abas.indexOfComponent(painel);
	    	    abas.setTabComponentAt(index,new utils.TabHeader(abas, titulo, painel));
	    	    abas.setSelectedComponent(painel);
	    	}
	    });

	    menu1.add(mntmNewMenuItem);
	    
	    JMenuItem mntmNewMenuItem_1 = new JMenuItem("Procurar no sistema");
	    mntmNewMenuItem_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		searchPanel painel = new searchPanel();
	    	    String titulo = "Procurar";
	    	    abas.addTab(titulo, painel);
	    	    int index = abas.indexOfComponent(painel);
	    	    abas.setTabComponentAt(index,new utils.TabHeader(abas, titulo, painel));
	    	    abas.setSelectedComponent(painel);
	    	}
	    });
	    mntmNewMenuItem_1.setIcon(new ImageIcon(mainFrame.class.getResource("/icons/procurar.png")));
	    menu1.add(mntmNewMenuItem_1);
	}

}