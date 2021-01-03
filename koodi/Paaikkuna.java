/**
 * Paaikkuna-luokka edustaa pelin graafisen käyttöjärjestelmän pääikkunaa.
 * Se sisältää peliruudukon, valikon, tekstikentän ja napit: 'siirto eteen'
 * ja 'siirto taakse'. 
 * @author Tuukka Sarvi
 */

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Paaikkuna extends JFrame {

    private static final String IKKUNAN_OTSIKKO = "Ristinolla v. 1.0";
    private static final int PELIKENTAN_OLETUSLEVEYS = 15;
    private static final int PELIKENTAN_OLETUSKORKEUS = 15;
    private static final int TIETOKONEPELAAJAN_PAINALLUKSEN_KESTO = 100; // millisekunteja   
    private static final String TIETOJA_TEKSTI="Kyseessä on perinteinen jätkänshakki, jossa tavoitteena on muodostaa\n"
                                              +"viiden suora. Peli tarjoaa mahdollisuudet ristinollapelien avaamiseen,\n"
                                              +"tallentamiseen ja tutkimiseen siirto siirrolta. Pelissä on kaksi\n"
                                              +"tietokonevastustajaa, Reiska Random ja Niilo Neuvokas. Reiska Random\n"
                                              +"pelaa täysin satunnaisesti. Niilo Neuvokas perustuu hermeettiseen\n"
                                              +"algoritmiin ja tarjoaa kohtalaisen vastuksen ihmispelaajallekkin.\n\n"
                                              +"-- Ristinolla v. 1.0 ja Niilo Neuvokas v. 1.0 (c) Tuukka Sarvi 2002-03 --";

	// tiedostonimet
	private static String IKKUNA_IKONI_TIEDOSTONIMI = "/kuvat/ikoninkuva.gif";
	private static String RASTIN_KUVA_TIEDOSTONIMI = "/kuvat/cross.gif";
	private static String RENKAAN_KUVA_TIEDOSTONIMI = "/kuvat/not.gif";
	private static String TYHJAN_KUVA_TIEDOSTONIMI = "/kuvat/tyhja.gif";
	private static String SIIRTO_ETEEN_KUVA_TIEDOSTONIMI = "/kuvat/siirtoeteen.gif";
	private static String SIIRTO_TAAKSE_KUVA_TIEDOSTONIMI = "/kuvat/siirtotaakse.gif";

	// kuvat
	private ImageIcon IKKUNAN_IKONI;
	private ImageIcon RASTIN_KUVA;
	private ImageIcon RENKAAN_KUVA;
	private ImageIcon TYHJAN_KUVA;
	private ImageIcon SIIRTO_ETEEN_KUVA;
	private ImageIcon SIIRTO_TAAKSE_KUVA;

    // komponentit
    private Ruutunappi[][] nappiruudukko;
    private JTextField tekstikentta;
    private JButton siirtoEteenNappi;
    private JButton siirtoTaakseNappi;

    // nappiruudukon kuuntelijaolio
    private RuutunappienKuuntelija ruudukonKuuntelija;

    // käytetään valitsemaan haluttu tiedosto peliä avattaessa tallennettaessa
    private JFileChooser tiedostonValitsija;  // on sen takia Paaikkunan oma kenttä, eikä metodin
                                              // sisäinen kenttä, että näin hakemisto, jossa työskenneltiin
                                              // säilyy muistissa seuraavaa kertaa varten.
    // pelattava ristinollapeli
    private Ristinollapeli peli;

    /**
     * Luo uuden Paaikkuna-luokan.
     */
    public Paaikkuna(){

	super(Paaikkuna.IKKUNAN_OTSIKKO);

	// ladataan kuvat
	this.IKKUNAN_IKONI = new ImageIcon(getClass().getResource(Paaikkuna.IKKUNA_IKONI_TIEDOSTONIMI));
	this.RASTIN_KUVA = new ImageIcon(getClass().getResource(Paaikkuna.RASTIN_KUVA_TIEDOSTONIMI));
	this.RENKAAN_KUVA = new ImageIcon(getClass().getResource(Paaikkuna.RENKAAN_KUVA_TIEDOSTONIMI));
	this.TYHJAN_KUVA = new ImageIcon(getClass().getResource(Paaikkuna.TYHJAN_KUVA_TIEDOSTONIMI));
	this.SIIRTO_ETEEN_KUVA = new ImageIcon(getClass().getResource(Paaikkuna.SIIRTO_ETEEN_KUVA_TIEDOSTONIMI));
	this.SIIRTO_TAAKSE_KUVA = new ImageIcon(getClass().getResource(Paaikkuna.SIIRTO_TAAKSE_KUVA_TIEDOSTONIMI));

	// asetetaan ikkunan ominaisuudet
	Dimension naytonKoko = Toolkit.getDefaultToolkit().getScreenSize();
       	this.setLocation(naytonKoko.width/3, naytonKoko.height/3);
       	this.setResizable(true);
	this.setIconImage(this.IKKUNAN_IKONI.getImage());

	// luodaan oletusarvoinen uusi peli
	this.peli = new Ristinollapeli(new Pelikentta(this.PELIKENTAN_OLETUSLEVEYS,this.PELIKENTAN_OLETUSKORKEUS), new Ihmispelaaja("",'x'), new PelaajaNiiloNeuvokas('o'));
	this.peli.kerroRastipelaaja().lisaaPeliin(this.peli);
	this.peli.kerroRengaspelaaja().lisaaPeliin(this.peli);

	this.alustaKomponentit();
	this.paivitaKomponentit();
	this.alustaValikko();
	this.pack();

	// alustetaan tiedoston valitsija osoittamaan käyttäjän kotihakemistoon 
	this.tiedostonValitsija = new JFileChooser();

	// pistetään peli käyntiin
	this.paivitaPelitilanne();
   }

    /**
     * Alustaa ikkunan komponentit lukuunottamatta nappiruudukkoa.
     */
    private void alustaKomponentit(){

	//ikkunan sulkeminen sulkee koko ohjelman
	this.addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent ikkunaTapahtuma){
		    Paaikkuna.this.lopeta();
		}
	    });

	this.tekstikentta = new JTextField("");
	this.tekstikentta.setEditable(false);

	this.siirtoEteenNappi = new JButton(this.SIIRTO_ETEEN_KUVA);
	this.siirtoEteenNappi.setFocusPainted(false);
	this.siirtoEteenNappi.setMargin(new Insets(0,0,0,0));
	this.siirtoTaakseNappi = new JButton(this.SIIRTO_TAAKSE_KUVA);
	this.siirtoTaakseNappi.setFocusPainted(false);
	this.siirtoTaakseNappi.setMargin(new Insets(0,0,0,0));

      
	this.siirtoEteenNappi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent tapahtuma){
		    Paaikkuna.this.peli.siirtoEteen();
		    Paaikkuna.this.paivitaPelitilanne();
		}
	    });
	this.siirtoTaakseNappi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent tapahtuma){
		    Paaikkuna.this.peli.siirtoTaakse();
		    Paaikkuna.this.paivitaPelitilanne();
		}
	    });
    }

    /**
     * Päivittää ikkunan komponentit (nappiruudukko ja tekstikentta) pelattavaa ristinollapelia 
     * vastaaviksi.
     */
    private void paivitaKomponentit(){
	
	// asetetaan tekstikenttä tyhjäksi
	this.tekstikentta.setText("");

	// annetaan viittaus Paaikkunaan ja pelattavaan Ristinollapeliin
	// parametrina RuutunappienKuuntelija-olion konstruktorille
	this.ruudukonKuuntelija = new RuutunappienKuuntelija(this,this.peli);

	// luodaan nappiruudukko ja asetetaan se pelikenttää vastaavaksi
	this.nappiruudukko = new Ruutunappi[this.peli.kerroPelikentta().kerroLeveys()][this.peli.kerroPelikentta().kerroKorkeus()];
	int x,y;
	for (y=0; y < this.peli.kerroPelikentta().kerroKorkeus(); y++){
	    for (x=0; x < this.peli.kerroPelikentta().kerroLeveys(); x++){
		this.nappiruudukko[x][y] = new Ruutunappi(new Koordinaatit(x,y),this.TYHJAN_KUVA);
		this.nappiruudukko[x][y].addActionListener(this.ruudukonKuuntelija);
	    }
	}

	// luodaan joukko sisäkkäisiä paneeleja. lopuksi päivitetään
	// ikkunan sisältöpaneeli:
	JPanel ruudukkopaneeli = new JPanel(new GridLayout(this.peli.kerroPelikentta().kerroKorkeus(),this.peli.kerroPelikentta().kerroLeveys())); // korkeus,leveys ~ rows,columns

	for (y=0; y < this.peli.kerroPelikentta().kerroKorkeus(); y++){
	    for (x=0; x < this.peli.kerroPelikentta().kerroLeveys(); x++)
		ruudukkopaneeli.add(this.nappiruudukko[x][y]);
	}    

	JPanel ylaosa = new JPanel();
	ylaosa.setLayout(new BoxLayout(ylaosa, BoxLayout.Y_AXIS));
	ylaosa.add(ruudukkopaneeli);
	ylaosa.add(this.tekstikentta);

	JPanel alaosa = new JPanel();
	alaosa.setLayout(new BoxLayout(alaosa, BoxLayout.X_AXIS));
	alaosa.add(this.siirtoTaakseNappi);
	alaosa.add(this.siirtoEteenNappi);

	JPanel ikkunanSisalto = new JPanel();
	ikkunanSisalto.setLayout(new BoxLayout(ikkunanSisalto, BoxLayout.Y_AXIS));
	ikkunanSisalto.add(ylaosa);
	ikkunanSisalto.add(alaosa);

	this.setContentPane(ikkunanSisalto);
    }

    /**
     * Alustaa valikon.
     */
    private void alustaValikko(){

	JMenuBar valikko = new JMenuBar();
	this.setJMenuBar(valikko);

	//peli-valikko (uusi peli, avaa peli, ..)
	JMenu pelivalikko = new JMenu("Peli");	 
	valikko.add(pelivalikko);

	JMenuItem uusiPeliValinta = new JMenuItem("Uusi peli");       
	pelivalikko.add(uusiPeliValinta);

	uusiPeliValinta.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent uusiPeliValittu){
		    Paaikkuna.this.uusiPeli();
		}
	    });

	JMenuItem avaaPeliValinta = new JMenuItem("Avaa peli");	
	pelivalikko.add(avaaPeliValinta);

	avaaPeliValinta.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent avaaPeliValittu){
		    Paaikkuna.this.avaaPeli();
		}
	    });

	JMenuItem tallennaPeliValinta = new JMenuItem("Tallenna peli");	
	pelivalikko.add(tallennaPeliValinta);

	tallennaPeliValinta.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent tallennaPeliValittu){
		    Paaikkuna.this.tallennaPeli();
		}
	    });

	JMenuItem lopetaValinta = new JMenuItem("Lopeta");	
	pelivalikko.add(lopetaValinta);

	lopetaValinta.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent lopetaValittu){
		    Paaikkuna.this.lopeta();
		}
	    });

	//ohje-valikko sisältää vain kohdan "Tietoja"
	JMenu ohjevalikko = new JMenu("Ohje");
	valikko.add(ohjevalikko);

	JMenuItem tietojaValinta = new JMenuItem("Tietoja");
	ohjevalikko.add(tietojaValinta);

	tietojaValinta.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent tietojaValittu){
		    JOptionPane.showMessageDialog(Paaikkuna.this, Paaikkuna.TIETOJA_TEKSTI, "Tietoja pelistä", JOptionPane.PLAIN_MESSAGE);

		}
	    });

    }

    /**
     * Kysyy uuden pelin tiedot ja alustaa käyttöjärjestelmän niiden
     * mukaiseksi.
     */
    private void uusiPeli(){
	// kysytään uuden pelin tiedot
	UusiPeliIkkuna uusiPeliIkkuna = new UusiPeliIkkuna(this);
	uusiPeliIkkuna.setVisible(true);

	// otetaan uusi peli käyttöön
	if (uusiPeliIkkuna.kerroUusiPeli()!=null){
	    this.peli = uusiPeliIkkuna.kerroUusiPeli();
	    this.paivitaKomponentit();
	    this.pack();
	    this.paivitaPelitilanne();
	}
    }

    /**
     * Luo ikkunan, josta käyttäjä valitsee avattavan pelin.
     * Tämän jälkeen käyttöjärjestelmä päivitetään avattua peliä vastaavaksi.
     */
    private void avaaPeli(){

	this.tiedostonValitsija.setDialogTitle("Avaa peli");

	// jos käyttäjä ei valitse mitään tiedostoa palataan takaisin
	if (this.tiedostonValitsija.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
	    return;

	try {
	    AvaaPeliIkkuna avaaPeliIkkuna = new AvaaPeliIkkuna(this, this.tiedostonValitsija.getSelectedFile().getPath());
	    avaaPeliIkkuna.setVisible(true);

	    // otetaan avattu peli käyttöön
	    if (avaaPeliIkkuna.kerroAvattavaPeli()!=null){
		this.peli = avaaPeliIkkuna.kerroAvattavaPeli();
		this.paivitaKomponentit();
		this.pack();
		this.paivitaPelitilanne();
	    }
	}

	catch (IOException tiedostonlukemisvirhe){
	    JOptionPane.showMessageDialog(this,"Tiedoston lukeminen epäonnistui.","Tiedostonlukemisvirhe", JOptionPane.ERROR_MESSAGE);
	}

	catch (KelvotonRistinollapelitiedosto virheellinenTietue){
	    JOptionPane.showMessageDialog(this,"Tiedosto ei ole ristinollapelitiedosto. (" + virheellinenTietue.getMessage() + ")","Tiedostonlukemisvirhe", JOptionPane.ERROR_MESSAGE);
	}
      
    }

    /**
     * Tallentaa käynnissaolevan ristinollapelin.
     */
    private void tallennaPeli(){

	this.tiedostonValitsija.setDialogTitle("Tallenna peli");

	if (this.tiedostonValitsija.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
	    return;

	// yritetään lukea valitun tiedoston sisältö. jos lukeminen onnistuu,
	// on tiedosto toimiva ristinollapelitiedosto ja peli voidaan tallentaa siihen.
	// jos lukeminen ei onnistu, pyydetään käyttäjältä lisävarmistus tallennuksen suorittamisesta. 
	try {
	    RistinollapelitiedostonLukija lukija = new RistinollapelitiedostonLukija(this.tiedostonValitsija.getSelectedFile().getPath());
	    ArrayList pelit = lukija.luePelit();
	    lukija.sulje();
	}

	// tiedoston puuttuminen ei aiheuta toimenpiteitä: tiedostonkirjoittaja luo uuden tiedoston.
	catch (FileNotFoundException uusiTiedosto){
	    
	}	

	catch (Exception eiToimivaRistinollapelitiedosto){
	    Object[] vaihtoehdot = {"Kyllä", "Ei"};

	    int valinta = JOptionPane.showOptionDialog(this, "Tiedosto ei ole ristinollapelitiedosto. Tallennetaako peli siitä huolimatta tiedoston loppuun?", "Tallennuksen varmistus", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, vaihtoehdot, vaihtoehdot[1]);

	    if (valinta == JOptionPane.NO_OPTION)
		return;

	} 

	TallennaPeliIkkuna tallennaPeliIkkuna = new TallennaPeliIkkuna(this, this.peli, this.tiedostonValitsija.getSelectedFile().getPath());
	tallennaPeliIkkuna.setVisible(true);
    }
 
    /**
     * Lopettaa ohjelman.
     */
    private void lopeta(){
	System.exit(0);
    }

    /**
     * Päivittää nappiruudukon olemassa olevaa pelitilannetta vastaavaksi.
     */
    private void paivitaNappiruudukko(){
	Koordinaatit paikka;  

	// asetetaan ruuduille oikeat ikonit
	// asetetaan rastit, ruudut = disabled ja 
	// tyhja = enabled, jos viimeinen siirto; muutoin tyhja = disabled 	
        for (paikka=new Koordinaatit(); paikka.kerroY()<this.peli.kerroPelikentta().kerroKorkeus(); paikka.asetaY(paikka.kerroY()+1)) {
	    for (paikka.asetaX(0); paikka.kerroX()<this.peli.kerroPelikentta().kerroLeveys(); paikka.asetaX(paikka.kerroX()+1)) {
		switch(this.peli.kerroPelikentta().kerroPelimerkki(paikka)){
		    case 'x':
			    this.nappiruudukko[paikka.kerroX()][paikka.kerroY()].asetaKuva(this.RASTIN_KUVA);
			    this.nappiruudukko[paikka.kerroX()][paikka.kerroY()].setEnabled(false);
			break;

		    case 'o':
			    this.nappiruudukko[paikka.kerroX()][paikka.kerroY()].asetaKuva(this.RENKAAN_KUVA);
			    this.nappiruudukko[paikka.kerroX()][paikka.kerroY()].setEnabled(false);
			break;

		    case ' ':
			this.nappiruudukko[paikka.kerroX()][paikka.kerroY()].asetaKuva(this.TYHJAN_KUVA);
			if (this.peli.onViimeisenSiirronKohdalla() && this.peli.kerroPelinTila() == Ristinollapeli.TILA_KAYNNISSA)			
				this.nappiruudukko[paikka.kerroX()][paikka.kerroY()].setEnabled(true);
			else 
			    this.nappiruudukko[paikka.kerroX()][paikka.kerroY()].setEnabled(false);

			break;

		    default:

		    }
	    }
	}
    }

    /**
     * Päivittää tekstikentän.
     */
    private void paivitaTekstikentta(){

	switch (this.peli.kerroPelinTila()){
	    case Ristinollapeli.TILA_KAYNNISSA:
		break;

	    case Ristinollapeli.TILA_RASTI_VOITTAA:
		this.tekstikentta.setText("Rasti voittaa.");
		break;

	    case Ristinollapeli.TILA_RENGAS_VOITTAA:
		this.tekstikentta.setText("Rengas voittaa.");
		break;

	    case Ristinollapeli.TILA_TASAPELI:
		this.tekstikentta.setText("Tasapeli.");
		break;

	    default: // ei tapahdu milloinkaan
	}
    }

    /**
     * Päivittää ikkunan (nappiruudukon ja tekstikentän) pelitilannetta
     * vastaavaksi sekä vastaa vuoron siirtymisestä seuraavalle pelaajalle.
     */
    public void paivitaPelitilanne(){
	
	if (this.peli.onViimeisenSiirronKohdalla() && this.peli.kerroPelinTila() == Ristinollapeli.TILA_KAYNNISSA)
	    this.peli.paivitaPelinTila();
	
	this.paivitaTekstikentta();

	this.paivitaNappiruudukko();    

	if (this.peli.onViimeisenSiirronKohdalla() && this.peli.kerroPelinTila() == Ristinollapeli.TILA_KAYNNISSA){

	    if (this.peli.kerroVuorossaolija() == 'x'){
		Siirto siirto;
		if ((siirto = this.peli.kerroRastipelaaja().mietiSiirto()) != null) // tietokonepelaaja
		    this.nappiruudukko[siirto.kerroX()][siirto.kerroY()].doClick(this.TIETOKONEPELAAJAN_PAINALLUKSEN_KESTO);
	    }
	    else { // vuorossaolija = 'o'
		Siirto siirto;
		if ((siirto = this.peli.kerroRengaspelaaja().mietiSiirto()) != null) // tietokonepelaaja
		    this.nappiruudukko[siirto.kerroX()][siirto.kerroY()].doClick(this.TIETOKONEPELAAJAN_PAINALLUKSEN_KESTO);
	    }
	}
														   
    }

}
