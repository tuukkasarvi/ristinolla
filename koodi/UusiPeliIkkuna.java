/**
 * Kuvaa ikkunaa, jossa luodaan uusi ristinollapeli.
 * @author Tuukka Sarvi
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UusiPeliIkkuna extends JDialog {

    private static final int PELIKENTAN_MINIMILEVEYS = 5;
    private static final int PELIKENTAN_MINIMIKORKEUS = 5;
    private static final int PELIKENTAN_MAKSIMILEVEYS = 50;
    private static final int PELIKENTAN_MAKSIMIKORKEUS = 50;

    private static final String IKKUNAN_OTSIKKO = "Uusi peli";
    private static final String KUVAUS_IHMINEN = "Ihminen";
    private static final String KUVAUS_REISKA_RANDOM = "Reiska Random";
    private static final String KUVAUS_NIILO_NEUVOKAS = "Niilo Neuvokas";

    private JRadioButton rastiValintaIhminen;
    private JRadioButton rastiValintaReiskaRandom;
    private JRadioButton rastiValintaNiiloNeuvokas;
    private JRadioButton rengasValintaIhminen;
    private JRadioButton rengasValintaReiskaRandom;
    private JRadioButton rengasValintaNiiloNeuvokas;
 
    private JTextField leveyskentta;
    private JTextField korkeuskentta;

    private Ristinollapeli uusiPeli;

    /**
     * Luo uuden UusiPeliIkkuna-olion.
     * @param paaikkuna viittaus Paaikkunan 
     */
    public UusiPeliIkkuna(Paaikkuna paaikkuna){

	super(paaikkuna, UusiPeliIkkuna.IKKUNAN_OTSIKKO, true); // modaalinen = true

	// rastipelaajan tyypin valinta
	this.rastiValintaIhminen = new JRadioButton(UusiPeliIkkuna.KUVAUS_IHMINEN);
	this.rastiValintaIhminen.setSelected(true);
	this.rastiValintaReiskaRandom = new JRadioButton(UusiPeliIkkuna.KUVAUS_REISKA_RANDOM);
	this.rastiValintaNiiloNeuvokas = new JRadioButton(UusiPeliIkkuna.KUVAUS_NIILO_NEUVOKAS);

	ButtonGroup rastiVaihtoehdot = new ButtonGroup();
	rastiVaihtoehdot.add(this.rastiValintaIhminen);
	rastiVaihtoehdot.add(this.rastiValintaReiskaRandom);
	rastiVaihtoehdot.add(this.rastiValintaNiiloNeuvokas);


	JPanel rastinPuoli = new JPanel();
	rastinPuoli.setLayout(new BoxLayout(rastinPuoli, BoxLayout.Y_AXIS));
	rastinPuoli.add(new JLabel("Rastipelaaja:"));
	rastinPuoli.add(this.rastiValintaIhminen);
	rastinPuoli.add(this.rastiValintaReiskaRandom);
	rastinPuoli.add(this.rastiValintaNiiloNeuvokas);

	// rengaspelaajan tyypin valinta
	this.rengasValintaIhminen = new JRadioButton(UusiPeliIkkuna.KUVAUS_IHMINEN);
	this.rengasValintaIhminen.setSelected(true);
	this.rengasValintaReiskaRandom = new JRadioButton(UusiPeliIkkuna.KUVAUS_REISKA_RANDOM);
	this.rengasValintaNiiloNeuvokas = new JRadioButton(UusiPeliIkkuna.KUVAUS_NIILO_NEUVOKAS);

	ButtonGroup rengasVaihtoehdot = new ButtonGroup();
	rengasVaihtoehdot.add(this.rengasValintaIhminen);
	rengasVaihtoehdot.add(this.rengasValintaReiskaRandom);
	rengasVaihtoehdot.add(this.rengasValintaNiiloNeuvokas);


	JPanel renkaanPuoli = new JPanel();
	renkaanPuoli.setLayout(new BoxLayout(renkaanPuoli, BoxLayout.Y_AXIS));
	renkaanPuoli.add(new JLabel("Rengaspelaaja:"));
	renkaanPuoli.add(this.rengasValintaIhminen);
	renkaanPuoli.add(this.rengasValintaReiskaRandom);
	renkaanPuoli.add(this.rengasValintaNiiloNeuvokas);

	// ylaosa
	JPanel ylaosa = new JPanel();
	ylaosa.setLayout(new BoxLayout(ylaosa, BoxLayout.X_AXIS));
	ylaosa.add(rastinPuoli);
	ylaosa.add(renkaanPuoli);

	// leveys- ja korkeuskentät
	this.leveyskentta = new JTextField(2);
	this.leveyskentta.setScrollOffset(3);
	this.korkeuskentta = new JTextField(2);
	this.korkeuskentta.setScrollOffset(3);

	// keskiosa	
	JPanel keskiosa = new JPanel();
	keskiosa.setLayout(new BoxLayout(keskiosa, BoxLayout.X_AXIS));
	keskiosa.add(new JLabel("Kentän leveys:"));
	keskiosa.add(Box.createRigidArea(new Dimension(10,0)));
	keskiosa.add(this.leveyskentta);
	keskiosa.add(Box.createRigidArea(new Dimension(20,0)));
	keskiosa.add(new JLabel("Kentän korkeus:"));
	keskiosa.add(Box.createRigidArea(new Dimension(10,0)));
	keskiosa.add(this.korkeuskentta);

	// OK- ja Peruuta-napit
	JButton hyvaksyNappi = new JButton("OK");
	hyvaksyNappi.setFocusPainted(false);
	hyvaksyNappi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent tapahtuma){
		    try {
			int leveys = Integer.parseInt(UusiPeliIkkuna.this.leveyskentta.getText());
			int korkeus = Integer.parseInt(UusiPeliIkkuna.this.korkeuskentta.getText());
			if (leveys < UusiPeliIkkuna.this.PELIKENTAN_MINIMILEVEYS || korkeus < UusiPeliIkkuna.this.PELIKENTAN_MINIMIKORKEUS || leveys > UusiPeliIkkuna.this.PELIKENTAN_MAKSIMILEVEYS || korkeus > UusiPeliIkkuna.this.PELIKENTAN_MAKSIMIKORKEUS)
			    return;

			Pelaaja rastipelaaja = UusiPeliIkkuna.this.luoPelaaja('x');
			Pelaaja rengaspelaaja = UusiPeliIkkuna.this.luoPelaaja('o');

			UusiPeliIkkuna.this.uusiPeli = new Ristinollapeli(new Pelikentta(leveys,korkeus), rastipelaaja,rengaspelaaja);
			UusiPeliIkkuna.this.uusiPeli.kerroRastipelaaja().lisaaPeliin(UusiPeliIkkuna.this.uusiPeli);
			UusiPeliIkkuna.this.uusiPeli.kerroRengaspelaaja().lisaaPeliin(UusiPeliIkkuna.this.uusiPeli);

			UusiPeliIkkuna.this.setVisible(false);

		    }
		    catch (NumberFormatException eiKokonaisluku){
		    
		    }
		}
	    });

	JButton peruutaNappi = new JButton("Peruuta");
	peruutaNappi.setFocusPainted(false);
	peruutaNappi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent tapahtuma){
		    // tässä vaiheessa uusiPeli = null
		    UusiPeliIkkuna.this.setVisible(false);   
		}
	    });

	JPanel alaosa = new JPanel();
	alaosa.setLayout(new BoxLayout(alaosa, BoxLayout.X_AXIS));
	alaosa.add(hyvaksyNappi);
	alaosa.add(Box.createRigidArea(new Dimension(20,0)));
	alaosa.add(peruutaNappi);

	JPanel sisalto = new JPanel();
	sisalto.setLayout(new BoxLayout(sisalto, BoxLayout.Y_AXIS));
	sisalto.add(ylaosa);
	sisalto.add(Box.createRigidArea(new Dimension(0,3)));
	sisalto.add(keskiosa);
	sisalto.add(Box.createRigidArea(new Dimension(0,3)));
	sisalto.add(alaosa);

	sisalto.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

	this.setContentPane(sisalto);
	this.setResizable(false);
	this.setLocationRelativeTo(paaikkuna);
	this.pack();

    }

    /**
     * Apumetodi, joka luo pelaajan ikkunassa voimassaolevien valintojen perusteella.
     * @param pelimerkki luotavan pelaajan pelimerkki ('x' tai 'o')
     * @return pelaaja
     */
    private Pelaaja luoPelaaja(char pelimerkki){
	Pelaaja pelaaja = null;

	if (pelimerkki == 'x'){
	    if (this.rastiValintaIhminen.isSelected())
		pelaaja = new Ihmispelaaja("", 'x');

	    if (this.rastiValintaReiskaRandom.isSelected())
		pelaaja = new PelaajaReiskaRandom('x');

	    if (this.rastiValintaNiiloNeuvokas.isSelected())
		pelaaja = new PelaajaNiiloNeuvokas('x');

	}

	else { // pelimerkki = 'o'
	    if (this.rengasValintaIhminen.isSelected())
		pelaaja = new Ihmispelaaja("", 'o');

	    if (this.rengasValintaReiskaRandom.isSelected())
		pelaaja = new PelaajaReiskaRandom('o');

	    if (this.rengasValintaNiiloNeuvokas.isSelected())
		pelaaja = new PelaajaNiiloNeuvokas('o');
	}

	return pelaaja;
    }


    /**
     * Palauttaa annetun uuden ristinollapelin tiedot, mikäli tietoja ei annettu
     * palautetaan null.
     * @return uusi ristinollapeli tai null
     */
    public Ristinollapeli kerroUusiPeli(){
	return this.uusiPeli;
    }

}


 
