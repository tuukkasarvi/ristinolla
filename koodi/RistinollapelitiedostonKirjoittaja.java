/**
 * RistinollapelitiedostonKirjoittaja-luokan avulla voi kirjoittaa
 * .xo-päätteisiä tiedostoja, jotka sisältävä Ristinollapeli-
 * olioita. Kirjoittaja lisää annetun ristinollapelin tiedot tiedoston
 * loppuun. Yksi tiedosto voi siis sisältää monta eri ristinollapeliä.
 * Tiedoston muoto on seuraava:
 * rastipelaajan nimi
 * rastipelaajan tunniste (ihminen tai tietokonepelaajan tyyppi)
 * rengaspelaajan nimi
 * rengaspelaajan tunniste
 * kentän leveys
 * kentän korkeus
 * 1,1 (rastin 1. siirto)
 * 1,2 (renkaan 1. siirto)
 * 3,4
 * ...
 * ---------- (erotinrivi)
 * (toinen peli vastaavasti)
 * jne.
 * @author Tuukka Sarvi
 */

import java.io.Writer;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class RistinollapelitiedostonKirjoittaja {

    private static final String TUNNISTE_IHMINEN = "(ihminen)";
    private static final String TUNNISTE_REISKA_RANDOM = "(Reiska Random)";
    private static final String TUNNISTE_NIILO_NEUVOKAS = "(Niilo Neuvokas)";

    private static final String EROTINRIVI = "----------";

    private Writer tiedostonKirjoittaja;
    private BufferedWriter puskuroituVirta;

    /**
     * Luo uuden RistinollapelitiedostonKirjoittajan.
     * @param tiedostopolku tiedosto, johon halutaan kirjoittaa
     * @throws IOException tiedoston avaaminen ei onnistu
     */
    public RistinollapelitiedostonKirjoittaja(String tiedostopolku) throws IOException {
	this.tiedostonKirjoittaja = new FileWriter(tiedostopolku, true); // append = true
	this.puskuroituVirta = new BufferedWriter(this.tiedostonKirjoittaja);
    }

    /**
     * Tallettaa annetun Ristinollapeli-olion tiedoston loppuun. Jos tiedostoa ei
     * aikaisemmin ollut olemassa, luodaan uusi tiedosto.
     * @param peli Ristinollapeli-olio
     * @throws IOException tiedostoon kirjoittamien ei onnistu
     */
    public void tallenna(Ristinollapeli peli) throws IOException{
	puskuroituVirta.write(peli.kerroRastipelaaja().kerroNimi());
	puskuroituVirta.newLine();
	puskuroituVirta.write(this.kerroTunniste(peli.kerroRastipelaaja()));
	puskuroituVirta.newLine();
	puskuroituVirta.write(peli.kerroRengaspelaaja().kerroNimi());
	puskuroituVirta.newLine();
	puskuroituVirta.write(this.kerroTunniste(peli.kerroRengaspelaaja()));
	puskuroituVirta.newLine();
	puskuroituVirta.write(Integer.toString(peli.kerroPelikentta().kerroLeveys()));
	puskuroituVirta.newLine();
	puskuroituVirta.write(Integer.toString(peli.kerroPelikentta().kerroKorkeus()));
	puskuroituVirta.newLine();

	for (int c=0; c < peli.kerroSiirtojenMaara(); c++){
	    puskuroituVirta.write(peli.kerroSiirto(c).kerroX() + "," + peli.kerroSiirto(c).kerroY());
	    puskuroituVirta.newLine();	
	}   

	puskuroituVirta.write(this.EROTINRIVI);
	puskuroituVirta.newLine();

	puskuroituVirta.flush();
    }

    /**
     * Palauttaa annetun Pelaaja-luokan aliluokan tunnisteen.
     * @param pelaaja Pelaaja-luokan aliluokka
     * @return tunnistemerkkijono
     */
    private String kerroTunniste(Pelaaja pelaaja){
	if (pelaaja instanceof Ihmispelaaja)
	    return RistinollapelitiedostonKirjoittaja.TUNNISTE_IHMINEN;

	else if (pelaaja instanceof PelaajaReiskaRandom)
	    return RistinollapelitiedostonKirjoittaja.TUNNISTE_REISKA_RANDOM;

	else if (pelaaja instanceof PelaajaNiiloNeuvokas)
	    return RistinollapelitiedostonKirjoittaja.TUNNISTE_NIILO_NEUVOKAS;

	else // käytännössä mahdoton tilanne
	    return null;
    }

    /**
     * Sulkee tiedostonkirjoittajan. Tämän jälkeen ei ole enää mahdollista
     * kutsua tallenna()-metodia.
     * @throws IOException tiedoston sulkeminen ei onnistu
     */
    public void sulje() throws IOException {
	this.puskuroituVirta.close();
	this.tiedostonKirjoittaja.close();
    } 

}
