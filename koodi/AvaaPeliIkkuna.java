/**
 * Apuikkuna, jonka avulla kï¿½yttï¿½jï¿½ voi avata haluamansa ristinollapelin.
 * @author Tuukka Sarvi
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class AvaaPeliIkkuna extends JDialog {

    private static final int PELILISTAN_LEVEYS = 420;
    private static final int PELILISTAN_KORKEUS = 200;

    private JList pelilista;

    private ArrayList pelit;
    private Ristinollapeli avattavaPeli;

    /**
     * Luo uuden pelinavaamisikkunan.
     * @param paaikkuna pelin pï¿½ï¿½ikkuna
     * @param tiedostopolku packavattava tiedosto
     * @throws KelvotonRistinollapelitiedosto luettavassa tiedostossa virhe
     * @throws IOException tiedoston lukeminen ei onnistu
     */
    public AvaaPeliIkkuna(Paaikkuna paaikkuna, String tiedostopolku) throws KelvotonRistinollapelitiedosto, IOException{

	super(paaikkuna, "Avaa peli", true); //modaalinen = true

	RistinollapelitiedostonLukija lukija = new RistinollapelitiedostonLukija(tiedostopolku);
	this.pelit = lukija.luePelit();
	lukija.sulje();

	JScrollPane pelilistanPaneeli = new JScrollPane();
	pelilistanPaneeli.setPreferredSize(new Dimension(this.PELILISTAN_LEVEYS,this.PELILISTAN_KORKEUS));

	String[] pelienKuvaukset = new String[this.pelit.size()];
	for (int c=0; c < this.pelit.size(); c++)
	    pelienKuvaukset[c] = ((Ristinollapeli)(this.pelit.get(c))).toString();
	
	this.pelilista = new JList(pelienKuvaukset);
	this.pelilista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	this.pelilista.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));	
	this.pelilista.setSelectedIndex(0);

	pelilistanPaneeli.setViewportView(this.pelilista);

	JButton avaaNappi = new JButton("Avaa");
	avaaNappi.setFocusPainted(false);
	avaaNappi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent tapahtuma){
		    AvaaPeliIkkuna.this.avattavaPeli = (Ristinollapeli)AvaaPeliIkkuna.this.pelit.get(AvaaPeliIkkuna.this.pelilista.getSelectedIndex());
		    AvaaPeliIkkuna.this.setVisible(false);
		}
	    });

	JButton peruutaNappi = new JButton("Peruuta");
	peruutaNappi.setFocusPainted(false);
	peruutaNappi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent tapahtuma){
		    // tï¿½ssï¿½ vaiheessa avattavaPeli = null
		    AvaaPeliIkkuna.this.setVisible(false);   
		}
	    });

	JPanel alaosa = new JPanel();
	alaosa.setLayout(new BoxLayout(alaosa, BoxLayout.X_AXIS));
	alaosa.add(avaaNappi);
	alaosa.add(Box.createRigidArea(new Dimension(20,0)));
	alaosa.add(peruutaNappi);

	JPanel sisalto = new JPanel();
	sisalto.setLayout(new BoxLayout(sisalto, BoxLayout.Y_AXIS));
	sisalto.add(pelilistanPaneeli);
	sisalto.add(alaosa);

	sisalto.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

	this.setContentPane(sisalto);
	this.setResizable(false);
	this.setLocationRelativeTo(paaikkuna);
	this.pack();
    
    }

    /**
     * Palauttaa avattavaksi valitun ristinollapelin. Jos valintaa ei tehty
     * palautetaan null.
     * @return avattavaksi valittu ristinollapeli tai null
     */
    public Ristinollapeli kerroAvattavaPeli(){
	return this.avattavaPeli;
    }

}
