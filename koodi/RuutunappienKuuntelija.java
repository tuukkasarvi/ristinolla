/**
 * Luokka toimii Ruutunappi-olioiden toimintotapahtumien kuuntelijana.
 * Sen tehtävänä on hoitaa reagoida ruudukon nappien painalluksiin
 * päivittämällä graafisen käyttöliittymän komponentit (Ruutunappi-oliot) ja
 * pelin tiedot (Ristinollapeli-olio) uuden tilanteen mukaiseksi.
 * @author Tuukka Sarvi
 */
import javax.swing.event.*;
import java.awt.event.*;

public class RuutunappienKuuntelija implements ActionListener {

    private Paaikkuna paaikkuna;
    private Ristinollapeli peli;

    /**
     * Luo uuden RuutunappienKuuntelija-olion.
     * @param paaikkuna viittaus Paaikkuna-olioon, johon tämä RuutunappienKuuntelija-olio kuuluu    
     * @param peli viittaus Ristinollapeli-olioon, jota parhaillaan pelataan
     */
    public RuutunappienKuuntelija(Paaikkuna paaikkuna, Ristinollapeli peli){
	this.paaikkuna = paaikkuna;    
	this.peli = peli;
    }

    /**
     * Määrittaa mitä tapahtuu, kun jotain tämän luokan kuuntelemaa Ruutunappia painetaan.
     * Tarkemmin: päivittää käyttöliittymäkomponetit ja pelin tiedot uutta tilannetta 
     * vastaaviksi
     * @param tapahtuma tiedot tapahtumasta 
     */
    public void actionPerformed(ActionEvent tapahtuma){
	Koordinaatit paikka = ((Ruutunappi)tapahtuma.getSource()).kerroPaikka();

	if (this.peli.kerroVuorossaolija()=='x')
	    this.peli.kerroRastipelaaja().siirra(new Siirto(paikka.kerroX(),paikka.kerroY()));
	else // renkaan vuoro
	    this.peli.kerroRengaspelaaja().siirra(new Siirto(paikka.kerroX(), paikka.kerroY()));

	this.paaikkuna.paivitaPelitilanne();
    }

}

