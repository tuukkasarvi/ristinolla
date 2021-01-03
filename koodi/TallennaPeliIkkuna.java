/**
 * Ikkuna, jossa voi tallentaa pelin. Käyttäjältä kysytään ikkunassa pelaajien nimet,
 * muut tiedot pelistä saadaan olemassa olevasta pelitilanteesta.
 * @author Tuukka Sarvi
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class TallennaPeliIkkuna extends JDialog {
 
    private static final int NIMIKENTAN_LEVEYS = 15; //leveys on merkkeinä

    private JTextField rastipelaajanNimikentta;
    private JTextField rengaspelaajanNimikentta;

    private Ristinollapeli tallennettavaPeli;
    private String tiedostopolku; // tiedosto, johon peli tallennetaan

    /**
     * Luo uuden TallennaPeliIkkuna-olion. 
     * @param paaikkuna viittaus Paaikkunan
     * @param peli tallennettava ristinollapeli 
     * @param tiedostopolku tiedosto, johon peli tallennetaan
     */
    public TallennaPeliIkkuna(Paaikkuna paaikkuna, Ristinollapeli peli, String tiedostopolku){

	super(paaikkuna, "Tallenna peli", true); // modaalinen = true

	this.tallennettavaPeli = peli;
	this.tiedostopolku = tiedostopolku;

	// näille täytyy asettaa scrollauksen poisto
	this.rastipelaajanNimikentta = new JTextField(peli.kerroRastipelaaja().kerroNimi(), this.NIMIKENTAN_LEVEYS);
	this.rastipelaajanNimikentta.setScrollOffset(3);
	// jos pelaaja on tietokone nimeä ei voi editoida
	if (!(peli.kerroRastipelaaja() instanceof Ihmispelaaja))
	    this.rastipelaajanNimikentta.setEditable(false);

	this.rengaspelaajanNimikentta = new JTextField(peli.kerroRengaspelaaja().kerroNimi(), this.NIMIKENTAN_LEVEYS);
	this.rengaspelaajanNimikentta.setScrollOffset(3);
	// jos pelaaja on tietokone nimeä ei voi editoida
	if (!(peli.kerroRengaspelaaja() instanceof Ihmispelaaja))
	    this.rengaspelaajanNimikentta.setEditable(false);
	
	JPanel[] paneelit = new JPanel[3];

	paneelit[0] = new JPanel();
	paneelit[0].setLayout(new BoxLayout(paneelit[0], BoxLayout.X_AXIS));
	paneelit[0].add(new JLabel("Rastipelaajan nimi:"));
	paneelit[0].add(Box.createRigidArea(new Dimension(10,0)));
	paneelit[0].add(this.rastipelaajanNimikentta);

	paneelit[1] = new JPanel();
	paneelit[1].setLayout(new BoxLayout(paneelit[1], BoxLayout.X_AXIS));
	paneelit[1].add(new JLabel("Rengaspelaajan nimi:"));
	paneelit[1].add(Box.createRigidArea(new Dimension(10,0)));
	paneelit[1].add(this.rengaspelaajanNimikentta);

	JButton tallennaNappi = new JButton("Tallenna");
	tallennaNappi.setFocusPainted(false);
	tallennaNappi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent tapahtuma){
		    TallennaPeliIkkuna.this.tallennettavaPeli.kerroRastipelaaja().asetaNimi(TallennaPeliIkkuna.this.rastipelaajanNimikentta.getText());
		    TallennaPeliIkkuna.this.tallennettavaPeli.kerroRengaspelaaja().asetaNimi(TallennaPeliIkkuna.this.rengaspelaajanNimikentta.getText());
		    TallennaPeliIkkuna.this.setVisible(false);

		    try {
			RistinollapelitiedostonKirjoittaja kirjoittaja = new RistinollapelitiedostonKirjoittaja(TallennaPeliIkkuna.this.tiedostopolku);
			kirjoittaja.tallenna(TallennaPeliIkkuna.this.tallennettavaPeli);
			kirjoittaja.sulje();
		    }

		    catch (IOException tiedostonkirjoittamisvirhe){
			// tässä pitäisi olla Paaikkuna (!) 
			JOptionPane.showMessageDialog(TallennaPeliIkkuna.this,"Tiedostoon kirjoittaminen epäonnistui.","Tiedostonkirjoittamisvirhe", JOptionPane.ERROR_MESSAGE);
		    }

		}
	    });

	JButton peruutaNappi = new JButton("Peruuta");
	peruutaNappi.setFocusPainted(false);
	peruutaNappi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent tapahtuma){
		    TallennaPeliIkkuna.this.setVisible(false);   
		}
	    });

	paneelit[2] = new JPanel();
	paneelit[2].setLayout(new BoxLayout(paneelit[2], BoxLayout.X_AXIS));
	paneelit[2].add(tallennaNappi);
	paneelit[2].add(Box.createRigidArea(new Dimension(20,0)));
	paneelit[2].add(peruutaNappi);

	JPanel sisalto = new JPanel();
	sisalto.setLayout(new BoxLayout(sisalto, BoxLayout.Y_AXIS));
	sisalto.add(paneelit[0]);
	sisalto.add(Box.createRigidArea(new Dimension(0,3)));
	sisalto.add(paneelit[1]);
	sisalto.add(Box.createRigidArea(new Dimension(0,3)));
	sisalto.add(paneelit[2]);

	sisalto.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

	this.setContentPane(sisalto);
	this.setResizable(false);
	this.setLocationRelativeTo(paaikkuna);
	this.pack();
    }

}
