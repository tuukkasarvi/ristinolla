/**
 * Lukee .xo-päätteisiä ristinollapelitiedostoja.
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
import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class RistinollapelitiedostonLukija {

    private static final String TUNNISTE_IHMINEN = "(ihminen)";
    private static final String TUNNISTE_REISKA_RANDOM = "(Reiska Random)";
    private static final String TUNNISTE_NIILO_NEUVOKAS = "(Niilo Neuvokas)";

    private static final String EROTINRIVI = "----------";

    private Reader tiedostonLukija;
    private BufferedReader puskuroituVirta;

    /**
     * Luo uuden RistinollapelitiedostonLukija-olion.
     * @param tiedostopolku luettava tiedosto
     * @throws IOException tiedoston avaaminen ei onnistu
     */
    public RistinollapelitiedostonLukija(String tiedostopolku) throws IOException{
	this.tiedostonLukija = new FileReader(tiedostopolku);
	this.puskuroituVirta = new BufferedReader(this.tiedostonLukija);
    }


    /**
     * Lukee tiedostosta seuraavan siirron.
     * @return seuraava siirto tai null, jos tiedosto EROTINRIVI saavutettiin
     * @throws KelvotonRistinollapelitiedosto virhe tiedostossa
     * @throws IOException tiedoston lukeminen epäonnistui
     */
    private Siirto lueSeuraavaSiirto() throws KelvotonRistinollapelitiedosto, IOException {
	String s;
	int pilkunIndeksi,x,y;

	s = this.puskuroituVirta.readLine();

	if (s == null)
	    throw new KelvotonRistinollapelitiedosto("Tietue loppui kesken tiedostoa luettaessa.");

	if (s.equals(this.EROTINRIVI))
	    return null;

	pilkunIndeksi = s.indexOf(",");
	if (pilkunIndeksi==-1)
	    throw new KelvotonRistinollapelitiedosto("Siirron lukeminen tiedostosta epäonnistui.");

	try {
	    x = Integer.parseInt(s.substring(0,pilkunIndeksi));
	}
	catch (NumberFormatException eiKokonaisluku){
	    throw new KelvotonRistinollapelitiedosto("Siirron lukeminen tiedostosta epäonnistui.");
	}
	
	try {
	    y = Integer.parseInt(s.substring(pilkunIndeksi+1));
	}
	catch (NumberFormatException eiKokonaisluku){
	    throw new KelvotonRistinollapelitiedosto("Siirron lukeminen tiedostosta epäonnistui.");
	}

	// tässä ei tarkisteta koordinaattien positiivisuutta

	return new Siirto(x,y);			 

    }

    /**
     * Lukee seuraavan tiedostossa olevan ristinollapelin.
     * @return Ristinollapeli-olio tai, jos tiedosto on lopussa, null
     * @throws KelvotonRistinollapelitiedosto virhe luettavassa tiedostossa
     * @throws IOException tiedoston luku ei onnistu
     */
    private Ristinollapeli lueSeuraavaPeli() throws KelvotonRistinollapelitiedosto, IOException {
	String rastipelaajanNimi, rengaspelaajanNimi,s;
	String rastipelaajanTunniste, rengaspelaajanTunniste;
	int leveys, korkeus;
	Siirto siirto;
	Pelaaja rastipelaaja, rengaspelaaja;
	Ristinollapeli peli;

	if ((rastipelaajanNimi=this.puskuroituVirta.readLine())==null) // tiedoston loppu
	    return null;
	if ((rastipelaajanTunniste=this.puskuroituVirta.readLine())==null)
	    throw new KelvotonRistinollapelitiedosto("Tietue loppui kesken tiedostoa luettaessa.");
	if ((rengaspelaajanNimi=this.puskuroituVirta.readLine())==null)
	    throw new KelvotonRistinollapelitiedosto("Tietue loppui kesken tiedostoa luettaessa.");
	if ((rengaspelaajanTunniste=this.puskuroituVirta.readLine())==null)
	    throw new KelvotonRistinollapelitiedosto("Tietue loppui kesken tiedostoa luettaessa.");

	// yritetään luoda pelaajat
	rastipelaaja = this.luoPelaaja(rastipelaajanNimi, 'x', rastipelaajanTunniste);
	rengaspelaaja = this.luoPelaaja(rengaspelaajanNimi, 'x', rengaspelaajanTunniste);
	
	try {
	    if ((s=this.puskuroituVirta.readLine())==null)
		throw new KelvotonRistinollapelitiedosto("Tietue loppui kesken tiedostoa luettaessa.");	
	    leveys = Integer.parseInt(s);
	}
	catch (NumberFormatException eiKokonaisluku){
	    throw new KelvotonRistinollapelitiedosto("Pelikentän leveys ei kokonaisluku.");
	}

	if (leveys <= 0)
	    throw new KelvotonRistinollapelitiedosto("Pelikentän leveys ei posit. kokonaisluku.");
	
	try {
	    if ((s=this.puskuroituVirta.readLine())==null)
		throw new KelvotonRistinollapelitiedosto("Tietue loppui kesken tiedostoa luettaessa.");	
	    korkeus = Integer.parseInt(s);
	}
	catch (NumberFormatException eiKokonaisluku){
	    throw new KelvotonRistinollapelitiedosto("Pelikentän korkeus ei kokonaisluku.");
	}

	if (korkeus <= 0)
	    throw new KelvotonRistinollapelitiedosto("Pelikentän korkeus ei posit. kokonaisluku.");

	peli = new Ristinollapeli(new Pelikentta(leveys,korkeus), rastipelaaja, rengaspelaaja);
	peli.kerroRastipelaaja().lisaaPeliin(peli);
	peli.kerroRengaspelaaja().lisaaPeliin(peli);

	while ((siirto=this.lueSeuraavaSiirto())!=null)
	    peli.lisaaSiirto(siirto);
	
	for (int c=0; c < peli.kerroSiirtojenMaara(); c++) // kelataan peli loppuun
	    peli.siirtoEteen(); 	

	peli.paivitaPelinTila(); // päivitetään tilamuuttuja

	return peli;
    }

    /**
     * Luo uuden pelaajan annettujen tietojen perusteella.
     * @param nimi pelaajan nimi
     * @param pelimerkki 'x' tai 'o'
     * @param tunniste tunnistemerkkijono
     * @return Pelaaja-olio
     * @throws KelvotonRistinollapelitiedosto tunnistetiedot virheellisiä
     */
    private Pelaaja luoPelaaja(String nimi, char pelimerkki, String tunniste) throws KelvotonRistinollapelitiedosto {

	if (tunniste.equals(RistinollapelitiedostonLukija.TUNNISTE_IHMINEN))
	    return new Ihmispelaaja(nimi,pelimerkki);
	else if (tunniste.equals(RistinollapelitiedostonLukija.TUNNISTE_REISKA_RANDOM))
	    return new PelaajaReiskaRandom(pelimerkki);
	else if (tunniste.equals(RistinollapelitiedostonLukija.TUNNISTE_NIILO_NEUVOKAS))
	    return new PelaajaNiiloNeuvokas(pelimerkki);
	else 
	    throw new KelvotonRistinollapelitiedosto("Pelaajien tunnistetiedot virheellisiä.");
    }

    /**
     * Lukee kaikki tiedoston pelit ja palauttaa ne sijoitettuna ArrayList-olioon.
     * @return kaikki tiedoston pelit Arraylist-olissa
     * @throws KelvotonRistinollapelitiedosto tiedosto epäkelpo
     * @throws IOException lukeminen ei onnistu
     */
    public ArrayList luePelit() throws KelvotonRistinollapelitiedosto, IOException{
	ArrayList pelit = new ArrayList();
	Ristinollapeli peli;

	while ((peli=this.lueSeuraavaPeli())!=null)
	    pelit.add(peli);

	return pelit;
    }

    /**
     * Sulkee tiedostonlukijan. Tämän jälkeen muita metodeja ei enää voida kutsua.
     * @throws IOException tiedoston sulkeminen ei onnistu
     */
    public void sulje() throws IOException{
	this.puskuroituVirta.close();
	this.tiedostonLukija.close();
    }     



}  
