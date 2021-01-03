/**
 * Ristinollapeli-luokka kuvaa yhtä ristinollapeliä. Se sisältää
 * tehdyt siirrot, nykysen pelikentän, siirtoindeksin(minkä
 * siirron kohdalle pelikenttä on päivitetty) ja molemmat pelaajat.
 * @author Tuukka Sarvi
 */
import java.util.ArrayList;

public class Ristinollapeli{

    // pelin tilaa kuvaavat vakio kentät
    public static final int TILA_KAYNNISSA = 0;
    public static final int TILA_RASTI_VOITTAA = 1;
    public static final int TILA_RENGAS_VOITTAA = 2;
    public static final int TILA_TASAPELI = 3;

    private ArrayList siirrot;
    private Pelikentta pelikentta;
    private Pelaaja rastipelaaja;
    private Pelaaja rengaspelaaja;
    private int siirtoindeksi;
    private int pelinTila; 

    /**
     * Konstruktori luo uuden ristinollapelin annettujen tietojen
     * perusteella. Siirtolista asetetaan tyhjäksi.
     * @param pelikentta pelikenttä
     * @param rastipelaaja rasteilla pelaaja
     * @param rengaspelaaja renkailla pelaaja
     */  
    public Ristinollapeli(Pelikentta pelikentta, Pelaaja rastipelaaja, Pelaaja rengaspelaaja){
	this.siirrot = new ArrayList();
	this.pelikentta = pelikentta;
	this.rastipelaaja = rastipelaaja;
	this.rengaspelaaja = rengaspelaaja;
	this.siirtoindeksi=-1;
	this.paivitaPelinTila();
    }

    /**
     * Asettaa pelikentän annetuksi Pelikentta-olioksi. 
     * @param pelikentta Pelikentta-olio
     */
    public void asetaPelikentta(Pelikentta pelikentta){
	this.pelikentta=pelikentta;
    }

    /**
     * Kertoo siirtolistan.
     * @return siirtolista
     */
    public ArrayList kerroSiirrot(){
	return this.siirrot;   
    } 

    /**
     * Kertoo annetun indeksin mukaisen siirron.
     * @param indeksi siirron indeksi
     * @return annetun indeksin mukainen Siirto-olio
     */
    public Siirto kerroSiirto(int indeksi){
	return (Siirto)this.siirrot.get(indeksi); 
    }

    /**
     * Palauttaa pelikentän.
     * @return pelikenttä
     */
    public Pelikentta kerroPelikentta(){
	return this.pelikentta;
    }

    /**
     * Kertoo rasteilla pelaavan pelaajan.
     * @return rastipelaaja
     */
    public Pelaaja kerroRastipelaaja(){
	return this.rastipelaaja;	
    }

    /**
     * Kertoo renkailla pelaavan pelaajan.
     * @return rengaspelaaja
     */
    public Pelaaja kerroRengaspelaaja(){
	return this.rengaspelaaja;	
    }

    /**
     * Kertoo siirtoindeksin (-1=ei ole tehty siirtoja, 0=ensimmäinen siirto, ja niin edelleen).
     * @return siirtoindeksi
     */
    public int kerroSiirtoindeksi(){
	return this.siirtoindeksi;
    }

    /**
     * Paluttaa vuorossa olevan pelaajan pelimerkin (vuorossaolija 
     * päätellään siirtoindeksistä).
     * @return vuorossaolijan pelimerkki ('x' tai 'o')
     */
    public char kerroVuorossaolija(){
	if ((this.siirtoindeksi % 2)==0)
	    return 'o';
	else 
	    return 'x';
    }

    /**
     * Kertoo siirtojen kokonaismäärän.
     * @return siirtojen kokonaismäärä
     */
    public int kerroSiirtojenMaara(){
	return this.siirrot.size();
    } 

    /**
     * Kertoo onko peli viimeksi tehdyn siirron kohdalla eli, että
     * onko pelitilannetta muutettu siirtoTaakse()-metodilla
     * @return totuusarvo, joka kertoo onko peli viimeksi tehdyn siirron kohdalla
     */
    public boolean onViimeisenSiirronKohdalla(){
	return this.kerroSiirtoindeksi()==(this.kerroSiirtojenMaara()-1);
    } 

    /**
     * Lisää annetun siirron siirtolistan loppuun.
     * @param siirto lisättävä siirto
     */
    public void lisaaSiirto(Siirto siirto){
	this.siirrot.add(siirto);   
    }

    /**
     * Päivittää pelikenttää yhden siirron eteenpäin. Jos
     * ollaan jo ennestään viimeisen siirron kohdalla, ei tehdä mitään.
     */
    public void siirtoEteen(){
	char vuorossaolija=this.kerroVuorossaolija();

	if (this.siirtoindeksi == this.kerroSiirtojenMaara()-1)
	    return;
	else 
	    this.siirtoindeksi++;

	this.pelikentta.asetaPelimerkki((Koordinaatit)this.siirrot.get(this.siirtoindeksi), vuorossaolija);       
    }
    
    /**
     * Päivittää pelikenttää yhden siirron taaksepäin. Jos
     * ollaan jo ennestään ensimmäisen siirron kohdalla, ei tehdä mitään.
     */
    public void siirtoTaakse(){
	if (this.siirtoindeksi == -1)
	    return;
	else 
	    this.siirtoindeksi--;

	this.pelikentta.asetaPelimerkki((Koordinaatit)this.siirrot.get(this.siirtoindeksi+1),' ');
      
    }

    /**
     * Tarkastaa onko kumpikaan pelaajista voittanut ja, että onko peli tasapeli.
     * Päivittää pelin tilamuuttujan ajan tasalle.
     */
    public void paivitaPelinTila(){
	String rivit, sarakkeet, diagonaalitVasemmaltaOikealle, diagonaalitOikealtaVasemmalle;
	
	rivit = this.pelikentta.kerroRivit();
	sarakkeet = this.pelikentta.kerroSarakkeet();
	diagonaalitVasemmaltaOikealle = this.pelikentta.kerroDiagonaalitVasemmaltaOikealle();
	diagonaalitOikealtaVasemmalle = this.pelikentta.kerroDiagonaalitOikealtaVasemmalle();


	// tarkastetaan onko jompikumpi voittanut; ainoastaan viimeksi siirron tehnyt
	// pelaaja voi voittaa, joten täytyy tutkia vain toinen tapaus (pullonkaulat pois!)

	if (this.kerroVuorossaolija() == 'o'){
	    if (rivit.indexOf("xxxxx") != -1 || sarakkeet.indexOf("xxxxx") != -1 || diagonaalitVasemmaltaOikealle.indexOf("xxxxx") != -1 || diagonaalitOikealtaVasemmalle.indexOf("xxxxx") != -1){
		this.pelinTila = Ristinollapeli.TILA_RASTI_VOITTAA;
		return;
	    }
	}

	else {
	    if (rivit.indexOf("ooooo") != -1 || sarakkeet.indexOf("ooooo") != -1 || diagonaalitVasemmaltaOikealle.indexOf("ooooo") != -1 || diagonaalitOikealtaVasemmalle.indexOf("ooooo") != -1){
		this.pelinTila = Ristinollapeli.TILA_RENGAS_VOITTAA;
		return;
	    }
	}

	// tarkistetaan seuraavaksi onko peli tasapeli:

	// jos löytyy viiden peräkkäisen tyhjän jono peli ei ole tasapeli

	if (rivit.indexOf("     ") != -1 || sarakkeet.indexOf("     ") != -1 || diagonaalitVasemmaltaOikealle.indexOf("     ") != -1 || diagonaalitOikealtaVasemmalle.indexOf("     ") != -1){
	    this.pelinTila = Ristinollapeli.TILA_KAYNNISSA; // jos ei kumpikaan voittanut ja ei tasan => kaynnissa
	    return;
	}

	// tässä vaiheessa tiedetään, että kentällä ei ole viiden tyhjän jonoa.
	// on kuitenkin vielä mahdollista, että peli ei ole tasan. tarkistetaan tilanne
	// sijoittamalla jäljelläolevien tyhjien paikalle ensin rasteja ja sitten renkaita.
	String r = rivit.replace(' ','x');
	String s = sarakkeet.replace(' ','x');
	String dvo = diagonaalitVasemmaltaOikealle.replace(' ','x');
	String dov = diagonaalitOikealtaVasemmalle.replace(' ','x');      

	if (r.indexOf("xxxxx") != -1 || s.indexOf("xxxxx") != -1 || dvo.indexOf("xxxxx") != -1 || dov.indexOf("xxxxx") != -1){
	    this.pelinTila = Ristinollapeli.TILA_KAYNNISSA;
	    return;
	}

	r = rivit.replace(' ','o');
        s = sarakkeet.replace(' ','o');
	dvo = diagonaalitVasemmaltaOikealle.replace(' ','o');
	dov = diagonaalitOikealtaVasemmalle.replace(' ','o');

	if (r.indexOf("ooooo") != -1 || s.indexOf("ooooo") != -1 || dvo.indexOf("ooooo") != -1 || dov.indexOf("ooooo") != -1){
	    this.pelinTila = Ristinollapeli.TILA_KAYNNISSA;
	    return;
	}

	//nyt tiedetään, että peli on tasapeli.
	this.pelinTila = Ristinollapeli.TILA_TASAPELI;
    }

    /**
     * Kertoo pelin tilan.
     * @return pelin tila (TILA_KAYNNISSA, TILA_RASTI_VOITTAA, TILA_RENGAS_VOITTAA tai TILA_TASAPELI)
     */
    public int kerroPelinTila(){
	return this.pelinTila;
    }

    /**
     * Palauttaa merkkijonoesityksen tästä pelistä.
     * Esitys on muotoa: "rastipelaaja nimi(tyyppi) vs. rengaspelaajan nimi(tyyppi) [leveys x korkeus], n siirtoa".
     * @return merkkijono esitys pelistä
     */
    public String toString(){
	//luodaan pelaajien tyyppitiedot; "" = ihminen, "(C)" = tietokone
	String rastipelaajanTyyppi, rengaspelaajanTyyppi;

	if (this.rastipelaaja instanceof Ihmispelaaja)
	    rastipelaajanTyyppi = "";
	else
	    rastipelaajanTyyppi = "(C)";

	if (this.rengaspelaaja instanceof Ihmispelaaja)
	    rengaspelaajanTyyppi = "";
	else
	    rengaspelaajanTyyppi = "(C)";

	return this.rastipelaaja.kerroNimi() + rastipelaajanTyyppi + " vs. " + this.rengaspelaaja.kerroNimi() + rengaspelaajanTyyppi + " [" + this.pelikentta.kerroLeveys() + " x " + this.pelikentta.kerroKorkeus() + "], " + this.kerroSiirtojenMaara() + " siirtoa";
    }


} 


