/**
 * Tietokonepelaaja, joka osaa tehdä jotain järkevää. 
 * Pelaaja-luokan aliluokka. Versio 1.0.  
 * @author Tuukka Sarvi
 */

public class PelaajaNiiloNeuvokas extends Pelaaja{

    private static final String NIMI = "Niilo Neuvokas";    
    private static final int TUTKITTAVA_ETAISYYS = 5; // määrittää monenko ruudun päähän yhdistelmien
                                                      // etsiminen ulotetaan
    private static final double ALOITUSSIIRRON_ETAISYYSKERROIN = 0.3; // määrittää kuinka kauas aloitussiirrot
                                                                      // arvotaan keskipisteestä maksimissan  
    private static final double PUOLUSTUSSIIRRON_HEIKENNYSKERROIN = 0.7; // määrittää kuinka paljon puolustussiirtoa 
                                                                         // heikennetään ennen vertailua hyökkäys
                                                                         // siirtoon

    /**
     * Luo uuden NiiloNeuvokas-pelaajan, joka pelaa annetuilla pelimerkeillä.
     * @param pelimerkki 'x' tai 'o'
     */
    public PelaajaNiiloNeuvokas(char pelimerkki){
	super(PelaajaNiiloNeuvokas.NIMI, pelimerkki);
    }

    /**
     * Laskee siirron käyttäen yhdistelmien tunnistukseen perustuvaa hermeettistä algoritmia.
     * @return siirto, joka halutaan tehdä
     */
    public Siirto mietiSiirto(){

	if (this.kerroPelattavaPeli().kerroSiirtojenMaara() == 0) { // aloitussiirto
	    return this.annaAloitussiirto();
	}
	else {

	    // seuraavassa käytetään termiä hyökkäyssiirto kuvaamaan omaa peliä rakentavaa
	    // (mahd. myös vastustajan peliä estävää) siirtoa. termiä puolustussiirto käytetään
	    // siirrosta, joka ensisijaisesti estää vastustajan peliä.

	    int[][] hyotyarvomatriisiOmalleSiirrolle = this.laskeHyotyarvomatriisi(this.kerroPelimerkki());
	    int x, y;
	    int omanSiirronSuurinHyoty = -100;
	    Siirto hyokkayssiirto = new Siirto();

	    for (y=0; y < hyotyarvomatriisiOmalleSiirrolle[0].length; y++){
		for (x=0; x < hyotyarvomatriisiOmalleSiirrolle.length; x++){
		    if (hyotyarvomatriisiOmalleSiirrolle[x][y] > omanSiirronSuurinHyoty){
			omanSiirronSuurinHyoty = hyotyarvomatriisiOmalleSiirrolle[x][y];
			hyokkayssiirto.asetaX(x);
			hyokkayssiirto.asetaY(y);
		    } 
		}
	    }

	    int[][] hyotyarvomatriisiVastustajanSiirrolle;
	    
	    if (this.kerroPelimerkki() == 'x')
		hyotyarvomatriisiVastustajanSiirrolle = this.laskeHyotyarvomatriisi('o');
	    else // oma pelimerkki = 'o'
		hyotyarvomatriisiVastustajanSiirrolle = this.laskeHyotyarvomatriisi('x');

	    int vastustajanSiirronSuurinHyoty = -100;
	    Siirto puolustussiirto = new Siirto();

	    for (y=0; y < hyotyarvomatriisiVastustajanSiirrolle[0].length; y++){
		for (x=0; x < hyotyarvomatriisiVastustajanSiirrolle.length; x++){
		    if (hyotyarvomatriisiVastustajanSiirrolle[x][y] > vastustajanSiirronSuurinHyoty){
			vastustajanSiirronSuurinHyoty = hyotyarvomatriisiVastustajanSiirrolle[x][y];
			puolustussiirto.asetaX(x);
			puolustussiirto.asetaY(y);
		    } 
		}
	    }
	    
	    // valitaan parhaiden puolustus- ja hyokkayssiirtojen välillä
	    if (omanSiirronSuurinHyoty > vastustajanSiirronSuurinHyoty*PelaajaNiiloNeuvokas.PUOLUSTUSSIIRRON_HEIKENNYSKERROIN)
		return hyokkayssiirto;

	    else 
		return puolustussiirto;
	}

    }

    /**
     * Kertoo aloitussiirron, joka tehdään vain, ja ainoastaan kun tietokoneen pitää tehdä
     * siirto pelikentän ollessa tyhjä.
     * @return aloitussiirto
     */
    private Siirto annaAloitussiirto(){
	int leveys = this.kerroPelattavaPeli().kerroPelikentta().kerroLeveys();
	int korkeus = this.kerroPelattavaPeli().kerroPelikentta().kerroKorkeus();
	double a = Math.random();

	if (a < 0.25)
	    return new Siirto(leveys/2 + (int)((leveys/2 -1)*PelaajaNiiloNeuvokas.ALOITUSSIIRRON_ETAISYYSKERROIN*Math.random()), korkeus/2 + (int)((korkeus/2 -1)*PelaajaNiiloNeuvokas.ALOITUSSIIRRON_ETAISYYSKERROIN*Math.random()));

	else if (a < 0.5)
	    return new Siirto(leveys/2 + (int)((leveys/2 -1)*PelaajaNiiloNeuvokas.ALOITUSSIIRRON_ETAISYYSKERROIN*Math.random()), korkeus/2 - (int)((korkeus/2 -1)*PelaajaNiiloNeuvokas.ALOITUSSIIRRON_ETAISYYSKERROIN*Math.random()));

	else if (a < 0.75)
	    return new Siirto(leveys/2 - (int)((leveys/2 -1)*PelaajaNiiloNeuvokas.ALOITUSSIIRRON_ETAISYYSKERROIN*Math.random()), korkeus/2 + (int)((korkeus/2 -1)*PelaajaNiiloNeuvokas.ALOITUSSIIRRON_ETAISYYSKERROIN*Math.random()));

	else
	    return new Siirto(leveys/2 - (int)((leveys/2 -1)*PelaajaNiiloNeuvokas.ALOITUSSIIRRON_ETAISYYSKERROIN*Math.random()), korkeus/2 - (int)((korkeus/2 -1)*PelaajaNiiloNeuvokas.ALOITUSSIIRRON_ETAISYYSKERROIN*Math.random()));    

    }

    /**
     * Palauttaa hyötyarvomatriisin. Hyötyarvomatriisi on kaksiulotteinen kokonaislukutaulukko, jossa 
     * kukin alkio vasta yhtä ruutua pelikentässä. Alkion suuruus, hyötyarvo, kertoo vastaavan
     * siirron hyödyllisyyden.
     * @param pelimerkki 'x' tai 'o'; määrittää kumman, rastin vai renkaan, siirtoa ajatellen matriisi lasketaan 
     * @return hyötyarvomatriisi
     */
    private int[][] laskeHyotyarvomatriisi(char pelimerkki){
	int[][] hyotyarvomatriisi = new int[this.kerroPelattavaPeli().kerroPelikentta().kerroLeveys()][this.kerroPelattavaPeli().kerroPelikentta().kerroKorkeus()];
	int x,y;

	for (y=0; y < hyotyarvomatriisi[0].length; y++){
	    for (x=0; x < hyotyarvomatriisi.length; x++){
		if (this.kerroPelattavaPeli().kerroPelikentta().kerroRuudukko()[x][y] == ' ')
		    hyotyarvomatriisi[x][y] = this.laskeHyotyarvo(x,y,pelimerkki,this.kerroPelattavaPeli().kerroPelikentta());
		else 
		    hyotyarvomatriisi[x][y] = PelaajaNiiloNeuvokas.MAHDOTON_SIIRTO;
	    }
	}
	
	return hyotyarvomatriisi;
    }

    /**
     * Palauttaa hyötyarvon, joka seuraa siitä, että annettuihin koordinaatteihin (x,y)
     * pelikentällä sijoitetaan annettu pelimerkki. Annettu pelimerkki määrittää myös 
     * kenen kannalta, rastin vai renkaan, tilannetta katsotaan. (Huom. Jotta metodia
     * voidaan kutsua, täytyy varmistua siitä, että tarkasteltava ruutu on tyhjä.) 
     * @param x - x-koordinaatti
     * @param y - y-koordinaatti
     * @param pelimerkki pelaajan pelimerkki
     * @param pelikentta pelitilanne
     * @return hyötyarvo (int), joka siirrosta seuraa (mitä suurempi arvo sen parempi siirto).
     */
    private int laskeHyotyarvo(int x, int y, char pelimerkki, Pelikentta pelikentta){
	
	char ruudukko[][] = pelikentta.kerroRuudukko();
	String rivi, sarake, diagonaaliya, diagonaaliay;
	int arvoEnnen, arvoJalkeen;

	rivi = this.kerroRuudunRivi(x,y,ruudukko);
	sarake = this.kerroRuudunSarake(x,y,ruudukko);
	diagonaaliya = this.kerroRuudunDiagonaaliYlhaaltaAlas(x,y,ruudukko);
	diagonaaliay = this.kerroRuudunDiagonaaliAlhaaltaYlos(x,y,ruudukko);

        arvoEnnen = this.kerroMerkkijononKokonaisarvo(rivi,pelimerkki) + this.kerroMerkkijononKokonaisarvo(sarake,pelimerkki) + this.kerroMerkkijononKokonaisarvo(diagonaaliya,pelimerkki) + this.kerroMerkkijononKokonaisarvo(diagonaaliay,pelimerkki);

	ruudukko[x][y] = pelimerkki;

	rivi = this.kerroRuudunRivi(x,y,ruudukko);
	sarake = this.kerroRuudunSarake(x,y,ruudukko);
	diagonaaliya = this.kerroRuudunDiagonaaliYlhaaltaAlas(x,y,ruudukko);
	diagonaaliay = this.kerroRuudunDiagonaaliAlhaaltaYlos(x,y,ruudukko);

        arvoJalkeen = this.kerroMerkkijononKokonaisarvo(rivi,pelimerkki) + this.kerroMerkkijononKokonaisarvo(sarake,pelimerkki) + this.kerroMerkkijononKokonaisarvo(diagonaaliya,pelimerkki) + this.kerroMerkkijononKokonaisarvo(diagonaaliay,pelimerkki);

	// palautetaan ruudukko entiselleen (HUOM! taulukot ovat viittaustyyppisiä olioita Javassa)
	ruudukko[x][y] = ' ';

	// palautetaan arvon muutos eli siirron hyötyarvo (aina >= 0)
	return arvoJalkeen - arvoEnnen;
    }  

    /**
     * Kertoo merkkijonon kokonaisarvon, eli erotuksen omien merkkiyhdistelmien arvon ja vastustajan
     * merkkiyhdistelmien arvon välillä.
     * @param s merkkijono, josta yhdistelmiä etsitään
     * @param pelimerkki oma pelimerkki ('x' tai 'o'); määrittää kumman puolen, rastien vai ruutujen, kannalta
     * arvo lasketaan 
     * @return merkkijonon arvo
     */
    private int kerroMerkkijononKokonaisarvo(String s, char pelimerkki){

	if (pelimerkki == 'x')
	    return this.kerroMerkkijononArvoRastille(s) - this.kerroMerkkijononArvoRenkaalle(s); 

	else // 'o'
	    return this.kerroMerkkijononArvoRenkaalle(s) - this.kerroMerkkijononArvoRastille(s);
    }

    /**
     * Kertoo merkkijonossa esiintyvien rastien yhdistelmien kokonaisarvon.
     * @param s tutkittava merkkijono
     */
    private int kerroMerkkijononArvoRastille(String s){
	int arvo=0;
	if (s.length() >= 5) {
	    arvo += this.kerroEsiintymienMaara(s, "x    ")*PelaajaNiiloNeuvokas.M____;
	    arvo += this.kerroEsiintymienMaara(s, " x   ")*PelaajaNiiloNeuvokas._M___;
	    arvo += this.kerroEsiintymienMaara(s, "  x  ")*PelaajaNiiloNeuvokas.__M__;
	    arvo += this.kerroEsiintymienMaara(s, "   x ")*PelaajaNiiloNeuvokas.___M_;
	    arvo += this.kerroEsiintymienMaara(s, "    x")*PelaajaNiiloNeuvokas.____M;
	    arvo += this.kerroEsiintymienMaara(s, "xx   ")*PelaajaNiiloNeuvokas.MM___;
	    arvo += this.kerroEsiintymienMaara(s, "x x  ")*PelaajaNiiloNeuvokas.M_M__;
	    arvo += this.kerroEsiintymienMaara(s, "x  x ")*PelaajaNiiloNeuvokas.M__M_;
	    arvo += this.kerroEsiintymienMaara(s, "x   x")*PelaajaNiiloNeuvokas.M___M;
	    arvo += this.kerroEsiintymienMaara(s, " xx  ")*PelaajaNiiloNeuvokas._MM__;
	    arvo += this.kerroEsiintymienMaara(s, " x x ")*PelaajaNiiloNeuvokas._M_M_;
	    arvo += this.kerroEsiintymienMaara(s, " x  x")*PelaajaNiiloNeuvokas._M__M;
	    arvo += this.kerroEsiintymienMaara(s, "  xx ")*PelaajaNiiloNeuvokas.__MM_;
	    arvo += this.kerroEsiintymienMaara(s, "  x x")*PelaajaNiiloNeuvokas.__M_M;
	    arvo += this.kerroEsiintymienMaara(s, "   xx")*PelaajaNiiloNeuvokas.___MM;
	    arvo += this.kerroEsiintymienMaara(s, "  xx  ")*PelaajaNiiloNeuvokas.__MM__;
	    arvo += this.kerroEsiintymienMaara(s, "xxx  ")*PelaajaNiiloNeuvokas.MMM__;
	    arvo += this.kerroEsiintymienMaara(s, "xx x ")*PelaajaNiiloNeuvokas.MM_M_;
	    arvo += this.kerroEsiintymienMaara(s, "xx  x")*PelaajaNiiloNeuvokas.MM__M;
	    arvo += this.kerroEsiintymienMaara(s, "x xx ")*PelaajaNiiloNeuvokas.M_MM_;
	    arvo += this.kerroEsiintymienMaara(s, "x x x")*PelaajaNiiloNeuvokas.M_M_M;
	    arvo += this.kerroEsiintymienMaara(s, "x  xx")*PelaajaNiiloNeuvokas.M__MM;
	    arvo += this.kerroEsiintymienMaara(s, " xxx ")*PelaajaNiiloNeuvokas._MMM_;
	    arvo += this.kerroEsiintymienMaara(s, " xx x")*PelaajaNiiloNeuvokas._MM_M;
	    arvo += this.kerroEsiintymienMaara(s, " x xx")*PelaajaNiiloNeuvokas._M_MM;
	    arvo += this.kerroEsiintymienMaara(s, "  xxx")*PelaajaNiiloNeuvokas.__MMM;
	    arvo += this.kerroEsiintymienMaara(s, "xxxx ")*PelaajaNiiloNeuvokas.MMMM_;
	    arvo += this.kerroEsiintymienMaara(s, "xxx x")*PelaajaNiiloNeuvokas.MMM_M;
	    arvo += this.kerroEsiintymienMaara(s, "xx xx")*PelaajaNiiloNeuvokas.MM_MM;
	    arvo += this.kerroEsiintymienMaara(s, "x xxx")*PelaajaNiiloNeuvokas.M_MMM;
	    arvo += this.kerroEsiintymienMaara(s, " xxxx")*PelaajaNiiloNeuvokas._MMMM;
	    arvo += this.kerroEsiintymienMaara(s, " xxxx ")*PelaajaNiiloNeuvokas._MMMM_;
	    arvo += this.kerroEsiintymienMaara(s, "xxxxx")*PelaajaNiiloNeuvokas.MMMMM;
	}

	return arvo;
    }

    /**
     * Kertoo merkkijonossa esiintyvien renkaiden yhdistelmien kokonaisarvon.
     * @param s tutkittava merkkijono
     */
    private int kerroMerkkijononArvoRenkaalle(String s){
	int arvo=0;

	if (s.length() >= 5) {
	    arvo += this.kerroEsiintymienMaara(s, "o    ")*PelaajaNiiloNeuvokas.M____;
	    arvo += this.kerroEsiintymienMaara(s, " o   ")*PelaajaNiiloNeuvokas._M___;
	    arvo += this.kerroEsiintymienMaara(s, "  o  ")*PelaajaNiiloNeuvokas.__M__;
	    arvo += this.kerroEsiintymienMaara(s, "   o ")*PelaajaNiiloNeuvokas.___M_;
	    arvo += this.kerroEsiintymienMaara(s, "    o")*PelaajaNiiloNeuvokas.____M;
	    arvo += this.kerroEsiintymienMaara(s, "oo   ")*PelaajaNiiloNeuvokas.MM___;
	    arvo += this.kerroEsiintymienMaara(s, "o o  ")*PelaajaNiiloNeuvokas.M_M__;
	    arvo += this.kerroEsiintymienMaara(s, "o  o ")*PelaajaNiiloNeuvokas.M__M_;
	    arvo += this.kerroEsiintymienMaara(s, "o   o")*PelaajaNiiloNeuvokas.M___M;
	    arvo += this.kerroEsiintymienMaara(s, " oo  ")*PelaajaNiiloNeuvokas._MM__;
	    arvo += this.kerroEsiintymienMaara(s, " o o ")*PelaajaNiiloNeuvokas._M_M_;
	    arvo += this.kerroEsiintymienMaara(s, " o  o")*PelaajaNiiloNeuvokas._M__M;
	    arvo += this.kerroEsiintymienMaara(s, "  oo ")*PelaajaNiiloNeuvokas.__MM_;
	    arvo += this.kerroEsiintymienMaara(s, "  o o")*PelaajaNiiloNeuvokas.__M_M;
	    arvo += this.kerroEsiintymienMaara(s, "   oo")*PelaajaNiiloNeuvokas.___MM;
	    arvo += this.kerroEsiintymienMaara(s, "  oo  ")*PelaajaNiiloNeuvokas.__MM__;
	    arvo += this.kerroEsiintymienMaara(s, "ooo  ")*PelaajaNiiloNeuvokas.MMM__;
	    arvo += this.kerroEsiintymienMaara(s, "oo o ")*PelaajaNiiloNeuvokas.MM_M_;
	    arvo += this.kerroEsiintymienMaara(s, "oo  o")*PelaajaNiiloNeuvokas.MM__M;
	    arvo += this.kerroEsiintymienMaara(s, "o oo ")*PelaajaNiiloNeuvokas.M_MM_;
	    arvo += this.kerroEsiintymienMaara(s, "o o o")*PelaajaNiiloNeuvokas.M_M_M;
	    arvo += this.kerroEsiintymienMaara(s, "o  oo")*PelaajaNiiloNeuvokas.M__MM;
	    arvo += this.kerroEsiintymienMaara(s, " ooo ")*PelaajaNiiloNeuvokas._MMM_;
	    arvo += this.kerroEsiintymienMaara(s, " oo o")*PelaajaNiiloNeuvokas._MM_M;
	    arvo += this.kerroEsiintymienMaara(s, " o oo")*PelaajaNiiloNeuvokas._M_MM;
	    arvo += this.kerroEsiintymienMaara(s, "  ooo")*PelaajaNiiloNeuvokas.__MMM;
	    arvo += this.kerroEsiintymienMaara(s, "oooo ")*PelaajaNiiloNeuvokas.MMMM_;
	    arvo += this.kerroEsiintymienMaara(s, "ooo o")*PelaajaNiiloNeuvokas.MMM_M;
	    arvo += this.kerroEsiintymienMaara(s, "oo oo")*PelaajaNiiloNeuvokas.MM_MM;
	    arvo += this.kerroEsiintymienMaara(s, "o ooo")*PelaajaNiiloNeuvokas.M_MMM;
	    arvo += this.kerroEsiintymienMaara(s, " oooo")*PelaajaNiiloNeuvokas._MMMM;
	    arvo += this.kerroEsiintymienMaara(s, " oooo ")*PelaajaNiiloNeuvokas._MMMM_;
	    arvo += this.kerroEsiintymienMaara(s, "ooooo")*PelaajaNiiloNeuvokas.MMMMM;
	}

	return arvo;
    }

    /**
     * Kertoo monta kertaa annettu osamerkkijono esiintyy annetussa merkkijonossa.
     * @param merkkijono
     * @param osamerkkijono (väh. 2 merkkiä)
     * @return esiintymien määrä
     */
    private int kerroEsiintymienMaara(String merkkijono, String osamerkkijono){
	int i = 0;
	int lkm = 0;
    
	while ((i = merkkijono.indexOf(osamerkkijono, i) +1) != 0)
	    lkm++;

	return lkm;
    }



    /**
     * Palautta merkkijonoesityksen rivistä, jossa annettu ruutu - koordinaatit (x,y) - sijaitsee.
     * Palautetaan maksimissaan TUTKITTAVA_ETAISYYS päässä olevia merkkejä (palautettavan
     * merkkijonon maksimipituus = 2*TUTKITTAVA_ETAISYYS + 1).
     * @param x - x-koordinaatti
     * @param y - y-koordinaatti
     * @param ruudukko char[][]-esitys pelikentästä
     * @return merkkijonoesitys rivistä
     */
    private String kerroRuudunRivi(int x, int y, char[][] ruudukko){
	int x1,x2;
	String s="";

	x1 = x - PelaajaNiiloNeuvokas.TUTKITTAVA_ETAISYYS;
	x2 = x + PelaajaNiiloNeuvokas.TUTKITTAVA_ETAISYYS;

	if (x1 < 0)
	    x1 = 0;
	if (x2 > ruudukko.length-1) // > leveys -1
	    x2 = ruudukko.length-1;

	for (int c=x1; c <= x2; c++)
	    s += String.valueOf(ruudukko[c][y]);

	return s;
    }

    /**
     * Palautta merkkijonoesityksen sarakkeesta, jossa annettu ruutu - koordinaatit (x,y) - sijaitsee.
     * Palautetaan maksimissaan TUTKITTAVA_ETAISYYS päässä olevia merkkejä (palautettavan
     * merkkijonon maksimipituus = 2*TUTKITTAVA_ETAISYYS + 1).
     * @param x - x-koordinaatti
     * @param y - y-koordinaatti
     * @param ruudukko char[][]-esitys pelikentästä
     * @return merkkijonoesitys sarakkeesta
     */
    private String kerroRuudunSarake(int x, int y, char[][] ruudukko){
	int y1,y2;
	String s="";

	y1 = y - PelaajaNiiloNeuvokas.TUTKITTAVA_ETAISYYS;
	y2 = y + PelaajaNiiloNeuvokas.TUTKITTAVA_ETAISYYS;

	if (y1 < 0)
	    y1 = 0;
	if (y2 > ruudukko[0].length-1) // > korkeus -1
	    y2 = ruudukko[0].length-1;

	for (int c=y1; c <= y2; c++)
	    s += String.valueOf(ruudukko[x][c]);

	return s;
    }

    /**
     * Palauttaa merkkijonoesityksen diagonaalista ylhäältä alas (\), jossa annettu ruutu - koordinaatit (x,y) -
     * sijaitsee. Palautetaan maksimissaan TUTKITTAVA_ETAISYYS päässä olevia merkkejä (palautettavan
     * merkkijonon maksimipituus = 2*TUTKITTAVA_ETAISYYS + 1).
     * @param x - x-koordinaatti
     * @param y - y-koordinaatti
     * @param ruudukko char[][]-esitys pelikentästä
     * @return merkkijonoesitys diagonaalista ylhäältä alas
     */
    private String kerroRuudunDiagonaaliYlhaaltaAlas(int x, int y, char[][] ruudukko){
	int x1,x2,y1,y2;
	String s="";

	x1 = x - PelaajaNiiloNeuvokas.TUTKITTAVA_ETAISYYS;
	x2 = x + PelaajaNiiloNeuvokas.TUTKITTAVA_ETAISYYS;
	y1 = y - PelaajaNiiloNeuvokas.TUTKITTAVA_ETAISYYS;
	y2 = y + PelaajaNiiloNeuvokas.TUTKITTAVA_ETAISYYS;

	if (x1 < 0 || y1 < 0){
	    if (x1 <= y1){
		y1 += (-x1);
		x1 = 0;
	    }
	    else {
		x1 += (-y1);
		y1 = 0;
	    }
	}

	if (x2 > (ruudukko.length-1) || y2 > (ruudukko[0].length-1)){
	    if ((x2-ruudukko.length) >= (y2-ruudukko[0].length)){
		y2 -= x2 - (ruudukko.length-1);
		x2 = ruudukko.length -1;
	    }	
	    else {
		x2 -= y2 - (ruudukko[0].length-1);
		y2 = ruudukko[0].length-1;
	    }
	}

	//	System.out.println("x1=" + x1 + " y1=" + y1 + " | x2=" + x2 + " y2=" + y2);

	for (int c=0; c <= (x2-x1); c++)
	    s += String.valueOf(ruudukko[x1+c][y1+c]);

	return s;
    }

    /**
     * Palauttaa merkkijonoesityksen diagonaalista alhaalta ylös (/), jossa annettu ruutu - koordinaatit (x,y) -
     * sijaitsee. Palautetaan maksimissaan TUTKITTAVA_ETAISYYS päässä olevia merkkejä (palautettavan
     * merkkijonon maksimipituus = 2*TUTKITTAVA_ETAISYYS + 1).
     * @param x - x-koordinaatti
     * @param y - y-koordinaatti
     * @param ruudukko char[][]-esitys pelikentästä
     * @return merkkijonoesitys diagonaalista alhaalta ylös
     */
    private String kerroRuudunDiagonaaliAlhaaltaYlos(int x, int y, char[][] ruudukko){
	int x1,x2,y1,y2;
	String s="";

	x1 = x - PelaajaNiiloNeuvokas.TUTKITTAVA_ETAISYYS;
	x2 = x + PelaajaNiiloNeuvokas.TUTKITTAVA_ETAISYYS;
	y1 = y + PelaajaNiiloNeuvokas.TUTKITTAVA_ETAISYYS;
	y2 = y - PelaajaNiiloNeuvokas.TUTKITTAVA_ETAISYYS;

	if (x1 < 0 || y1 > (ruudukko[0].length-1)){

	    if ((-x1) >= (y1 - (ruudukko[0].length-1))){
		y1 += x1;
		x1 = 0;
	    }
	    else {
		x1 += y1 - (ruudukko[0].length-1);
		y1=ruudukko[0].length -1;
	    }
	}

	if (x2 > (ruudukko.length-1) || y2 < 0){
	    if ((x2-(ruudukko.length-1)) >= (-y2)){
		y2 += x2 - (ruudukko.length-1);
		x2 = ruudukko.length -1;
	    }	
	    else {
		x2 += y2;
		y2 = 0;
	    }
	}

	for (int c=0; c <= (x2-x1); c++)
	    s += String.valueOf(ruudukko[x1+c][y1-c]);

	return s;
    }



// yhdistelmien arvot; M = merkki, _ = tyhjä
// huomattavaa on, että arvot suoraan anna yhdistelmän arvoa;
// arvon antaa vasta yhdistelmän ja sen osayhdistelmien summa
    private static final int M____ = 1;
    private static final int _M___ = 1;
    private static final int __M__ = 1;
    private static final int ___M_ = 1;
    private static final int ____M = 1;

    private static final int MM___ = 7;
    private static final int M_M__ = 5;
    private static final int M__M_ = 5;
    private static final int M___M = 3;
    private static final int _MM__ = 7;
    private static final int _M_M_ = 5;
    private static final int _M__M = 5;
    private static final int __MM_ = 7;
    private static final int __M_M = 5;
    private static final int ___MM = 7;
    private static final int __MM__ = 3;
 
    private static final int MMM__ = 20;
    private static final int MM_M_ = 17;
    private static final int MM__M = 17;
    private static final int M_MM_ = 17;
    private static final int M_M_M = 15;
    private static final int M__MM = 17;
    private static final int _MMM_ = 70;
    private static final int _MM_M = 17;
    private static final int _M_MM = 17;
    private static final int __MMM = 20;

    private static final int MMMM_ = 60;
    private static final int MMM_M = 55;
    private static final int MM_MM = 55;
    private static final int M_MMM = 55;
    private static final int _MMMM = 60;
    private static final int _MMMM_ = 400;

    // suurin int n. 2*10^9
    private static final int MMMMM = 15000;

    private static final int MAHDOTON_SIIRTO = -100;
}
