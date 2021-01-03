/**
 * Pelikentta-luokka kuvaa ristinollaruudukkoa. Ruudussa voi olla rasti ('x')
 * tai rengas ('o'), tai ruutu voi olla tyhjä (' '). Itse ruudukkoa edustaa
 * kaksiulotteinen char-taulukko (char[][] ruudukko). Pelikentällä on leveys ja
 * korkeus, jotka määrittävät ruudukon koon. Ruudukon eri alkioihin viitataan
 * koordinaattipareilla (x,y).
 * @author Tuukka Sarvi
 */


public class Pelikentta {

    public static String EROTINMERKKI = "|"; // käytetään erottamaan eri rivit tms. esim.
                                              // metodissa kerroRivit():String
    private char[][] ruudukko;
    private int leveys, korkeus;
    
    /**
     * Luo uuden Pelikentta-olion, joka sisältää pelkkiä tyhjiä ruutuja.
     * @param leveys, pelikentän leveys
     * @param korkeus, pelikentän korkeus
     */
    public Pelikentta(int leveys, int korkeus) {
	Koordinaatit paikka;

	this.leveys=leveys;
	this.korkeus=korkeus;
	this.ruudukko = new char[leveys][korkeus];

	for (paikka = new Koordinaatit(); paikka.kerroX() < leveys; paikka.asetaX(paikka.kerroX()+1)) {
	    for (paikka.asetaY(0); paikka.kerroY() < korkeus; paikka.asetaY(paikka.kerroY()+1))
		this.asetaPelimerkki(paikka, ' ');
	}
    }

    /**
     * Kertoo annetussa kohdassa olevan pelimerkin.
     * @param paikka halutun ruudun koordinaatit
     * @return ruudun sisältämä pelimerkki
     */
    public char kerroPelimerkki(Koordinaatit paikka) {
	return this.ruudukko[paikka.kerroX()][paikka.kerroY()];
    } 

    /**
     * Asettaa haluttuihin koordinaatteihin annetun pelimerkin.
     * @param paikka ruudun koordinaatit
     * @param pelimerkki 'x', 'o', tai ' '
     */
    public void asetaPelimerkki(Koordinaatit paikka, char pelimerkki) {
	this.ruudukko[paikka.kerroX()][paikka.kerroY()] = pelimerkki;
    }

    /**
     * Kertoo pelikentän leveyden.
     * @return pelikentän leveys
     */
    public int kerroLeveys() {
	return this.leveys;
    }

    /**
     * Kertoo pelikentän korkeuden.
     * @return pelikentän korkeus
     */
    public int kerroKorkeus() {
	return this.korkeus;
    }    

    /**
     * Kertoo pelikentän tiedot kaksiulotteisena char-taulukkona.
     * @return pelikentän tiedot char-taulukossa
     */
    public char[][] kerroRuudukko(){
	return this.ruudukko;
    }

    /**
     * Kertoo onko annetussa ruudussa rasti.
     * @param paikka ruudun koordinaatit
     * @return totuusarvo, joka kertoo onko ruudussa rasti
     */
    public boolean onRasti(Koordinaatit paikka){
	return this.ruudukko[paikka.kerroX()][paikka.kerroY()] == 'x';
	// suoritusjärjestys!!!    
    }

    /**
     * Kertoo onko annetussa ruudussa rengas.
     * @param paikka ruudun koordinaatit
     * @return totuusarvo, joka kertoo onko ruudussa rengas
     */
    public boolean onRengas(Koordinaatit paikka){
	return this.ruudukko[paikka.kerroX()][paikka.kerroY()] == 'o';
    }

    /**
     * Kertoo onko annettu ruutu tyhjä.
     * @param paikka ruudun koordinaatit
     * @return totuusarvo, joka kertoo onko ruutu tyhjä
     */
    public boolean onTyhja(Koordinaatit paikka) {
	return this.ruudukko[paikka.kerroX()][paikka.kerroY()] == ' ';
    }

    /**
     * Kertoo ovatko annetut koordinaatit pelikentällä.
     * @param paikka annetut koordinaatit
     * @return totuusarvo, joka kertoo ovatko annetut koordinaatit
     * pelikentalla
     */
    public boolean onPelikentalla(Koordinaatit paikka) {
	return paikka.kerroX() >= 0 && paikka.kerroX() < this.leveys && paikka.kerroY() >= 0 && paikka.kerroY() < this.korkeus;
    }

    /**
     * Muuttaa pelikentän String olioksi. Rasteja, renkaita ja tyhjiä edustavat
     * merkit 'x', 'o' ja ' '. Esim. 5x5 kenttä on String-oliona: 
     * " x   \n
     *  oxo  \n
     *   xo  \n
     *   ox  \n
     *     x \n".
     * @return merkkijono esitys pelikentästä
     */
    public String toString() {
	String s = "";
	Koordinaatit paikka;

	for (paikka=new Koordinaatit();paikka.kerroY()<this.korkeus;paikka.asetaY(paikka.kerroY()+1)){
	    for (paikka.asetaX(0);paikka.kerroX()<this.leveys;paikka.asetaX(paikka.kerroX()+1))
		s += String.valueOf(this.kerroPelimerkki(paikka));
	    s += "\n";
	}
	return s;
    } 

    /**
     * Palauttaa String-olion, joka sisältää pelikentän rivit. Rivit on
     * erotettu toisistaan EROTINMERKEILLÄ.
     * @return merkkijono, joka sisältää pelikentän rivit
     */
    public String kerroRivit() {
    	String s = "";
	Koordinaatit paikka;

	for (paikka=new Koordinaatit();paikka.kerroY()<this.korkeus;paikka.asetaY(paikka.kerroY()+1)){
	    for (paikka.asetaX(0);paikka.kerroX()<this.leveys;paikka.asetaX(paikka.kerroX()+1))
		s += String.valueOf(this.kerroPelimerkki(paikka));
	    s += this.EROTINMERKKI;
	}
	return s;
    } 
    
    /**
     * Palauttaa String-olion, joka sisältää pelikentän sarakkeet. Sarakkeet on
     * erotettu toisistaan EROTINMERKEILLÄ.
     * @return merkkijono, joka sisältää pelikentän sarakkeet
     */
    public String kerroSarakkeet() {
    	String s = "";
	Koordinaatit paikka;

	for (paikka=new Koordinaatit();paikka.kerroX()<this.leveys;paikka.asetaX(paikka.kerroX()+1)){
	    for (paikka.asetaY(0);paikka.kerroY()<this.korkeus;paikka.asetaY(paikka.kerroY()+1))
		s += String.valueOf(this.kerroPelimerkki(paikka));
	    s += this.EROTINMERKKI;
	}
	return s;
    }

    /**
     * Palauttaa String-olion, joka sisältää pelikentän diagonaalit vasemmalta
     * oikealle (suuntaan /). Diagonaalit on erotettu toisistaan EROTINMERKEILLÄ.
     * @return merkkijono, joka sisältää pelikentän diagonaalit vasemmalta
     * oikealle
     */
    public String kerroDiagonaalitVasemmaltaOikealle() {
    	String s = "";
	Koordinaatit paikka = new Koordinaatit();
	int c;
	for (c=0; c<this.leveys; c++){
	    paikka.asetaX(c);
	    paikka.asetaY(0);
	    while (this.onPelikentalla(paikka)){
		s += String.valueOf(this.kerroPelimerkki(paikka));
		paikka.asetaX(paikka.kerroX()-1);
		paikka.asetaY(paikka.kerroY()+1);
	    }
	    s += this.EROTINMERKKI;						    
	}	

	for (c=1; c<this.korkeus; c++){
	    paikka.asetaY(c);
	    paikka.asetaX(this.leveys-1);
	    while (this.onPelikentalla(paikka)){
		s += String.valueOf(this.kerroPelimerkki(paikka));
		paikka.asetaX(paikka.kerroX()-1);
		paikka.asetaY(paikka.kerroY()+1);
	    }
	    s += this.EROTINMERKKI;						    
	}	
	return s;
    }

    /**
     * Palauttaa String-olion, joka sisältää pelikentän diagonaalit oikealta
     * vasemmalle (suuntaan \). Diagonaalit on erotettu toisistaan EROTINMERKEILLÄ.
     * @return merkkijono, joka sisältää pelikentän diagonaalit oikealta
     * vasemmalle
     */
    public String kerroDiagonaalitOikealtaVasemmalle() {
    	String s = "";
	Koordinaatit paikka = new Koordinaatit();
	int c;
	for (c=this.korkeus-1; c>=0; c--){
	    paikka.asetaY(c);
	    paikka.asetaX(0);
	    while (this.onPelikentalla(paikka)){
		s += String.valueOf(this.kerroPelimerkki(paikka));
		paikka.asetaX(paikka.kerroX()+1);
		paikka.asetaY(paikka.kerroY()+1);
	    }
	    s += this.EROTINMERKKI;						    
	}	

	for (c=1; c<this.leveys; c++){
	    paikka.asetaX(c);
	    paikka.asetaY(0);
	    while (this.onPelikentalla(paikka)){
		s += String.valueOf(this.kerroPelimerkki(paikka));
		paikka.asetaX(paikka.kerroX()+1);
		paikka.asetaY(paikka.kerroY()+1);
	    }
	    s += this.EROTINMERKKI;						    
	}	
	return s;
    }
}

 
