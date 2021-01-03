/** 
 * Koodinaatit-luokka kuvaa tason koordinaatteja (x,y). 
 * Luokka perustuu 4. tehtäväkierroksen Robotti-tehtävän Koordinaatit-luokkaan.
 * @author Tuukka Sarvi
 */

public class Koordinaatit {

    private int x;
    private int y;

    /**
     * Luo uuden Koordinaatit-olion ilmentymän.
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     */
    public Koordinaatit(int x, int y) {
	this.x = x;
	this.y = y;
    }

    /** 
     * Luo uuden Koordinaatit-olion. Olio asetetaan viittaamaan origoon (0,0)
     */
    public Koordinaatit(){
	this.x = 0;
	this.y = 0;
    }

    /**
     * Kertoo x-koordinaatin.
     * @return x-koordinaatti
     */
    public int kerroX() {
	return this.x;
    }

    /**
     * Kertoo y-koordinaatin.
     * @return y-koordinaatti
     */
    public int kerroY() {
	return this.y;
    }

    /**
     * Asettaa x-koordinaatin.
     * @param x x-koordinaatti
     */
    public void asetaX(int x) {
	this.x=x;
    }

    /**
     * Asettaa y-koordinaatin.
     * @param y y-koordinaatti
     */
    public void asetaY(int y) {
	this.y=y;
    }

}
