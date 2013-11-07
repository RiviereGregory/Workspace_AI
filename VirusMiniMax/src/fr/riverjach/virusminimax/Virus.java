package fr.riverjach.virusminimax;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Virus implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int taille = 7;
	private Character damier[][];
	private int nbBlanc;
	private int nbNoir;
	private int nbModification;
	private int pileModifications[];

	public Virus() {
		this.setDamier(new Character[getTaille()][getTaille()]);
		this.setPileModifications(new int[20 * getTaille() * getTaille()]);
		init();
	}

	public void init() {
		for (int i = 0; i < getTaille(); i++) {
			for (int j = 0; j < getTaille(); j++) {
				damier[i][j] = '+';
			}
		}
		damier[0][0] = '@';
		damier[getTaille() - 1][getTaille() - 1] = '@';
		damier[getTaille() - 1][0] = 'O';
		damier[0][getTaille() - 1] = 'O';
		nbBlanc = 2;
		nbNoir = 2;
		nbModification = 0;
	}

	public Character adversaire(Character couleur) {
		if (couleur == 'O') {
			return '@';
		}
		return 'O';
	}

	public void joue(Coup m) {
		damier[m.getX()][m.getY()] = m.getCouleur();
		pileModifications[nbModification] = m.getX();
		nbModification++;
		pileModifications[nbModification] = m.getY();
		nbModification++;

		int nbSwaps = 0;

		if (m.getCouleur() == '@') {
			nbNoir++;
		} else {
			nbBlanc++;
		}

		Character autre = adversaire(m.getCouleur());

		int debutX = m.getX() - 1;
		int finX = m.getX() + 1;
		int debutY = m.getY() - 1;
		int finY = m.getY() + 1;

		if (debutX < 0) {
			debutX = 0;
		}
		if (debutY < 0) {
			debutY = 0;
		}
		if (finX > getTaille() - 1) {
			finX = getTaille() - 1;
		}
		if (finY > getTaille() - 1) {
			finY = getTaille() - 1;
		}

		for (int i = debutX; i <= finX; i++) {
			for (int j = debutY; j <= finY; j++) {
				if (damier[i][j] == autre) {
					damier[i][j] = m.getCouleur();
					pileModifications[nbModification] = i;
					nbModification++;
					pileModifications[nbModification] = j;
					nbModification++;
					nbSwaps++;
					if (m.getCouleur() == '@') {
						nbNoir++;
						nbBlanc--;
					} else {
						nbNoir--;
						nbBlanc++;
					}
				}
			}
		}
		pileModifications[nbModification] = nbSwaps;
		nbModification++;
	}

	public void dejoue(Coup m) {
		int x, y, nbSwaps;
		Character autre = adversaire(m.getCouleur());
		nbModification--;
		nbSwaps = pileModifications[nbModification];

		for (int i = 0; i < nbSwaps; i++) {
			nbModification--;
			y = pileModifications[nbModification];
			nbModification--;
			x = pileModifications[nbModification];
			damier[x][y] = autre;
			if (m.getCouleur() == '@') {
				nbNoir--;
				nbBlanc++;
			} else {
				nbNoir++;
				nbBlanc--;
			}
		}
		nbModification--;
		y = pileModifications[nbModification];
		nbModification--;
		x = pileModifications[nbModification];
		damier[x][y] = '+';
		if (m.getCouleur() == '@') {
			nbNoir--;
		} else {
			nbBlanc--;
		}
	}

	public Integer evaluation(Character couleur) {
		if (couleur == '@') {
			System.out.println(nbNoir);
			System.out.println(nbBlanc);
			return nbNoir - nbBlanc;
		}
		return nbBlanc - nbNoir;
	}

	public Integer evaluationSiPlusDeCoupsPossibles(Character couleur) {
		if (couleur == '@') {
			return nbNoir - (getTaille() * getTaille() - nbNoir);
		}
		return nbBlanc - (getTaille() * getTaille() - nbBlanc);
	}

	public Boolean coupLegal(Coup coup) {
		if (damier[coup.getX()][coup.getY()] != '+') {
			return false;
		}
		for (int x = Math.max(coup.getX() - 1, 0); x <= Math.min(coup.getX() + 1, getTaille() - 1); x++) {
			for (int y = Math.max(coup.getY() - 1, 0); y <= Math.min(coup.getY() + 1,
					getTaille() - 1); y++) {
				if (damier[x][y] == coup.getCouleur()) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Coup> coupsLegaux(Character couleur) {
		List<Coup> liste = new ArrayList<>();
		Coup coup = new Coup();
		coup.setCouleur(couleur);
		for (int i = 0; i < getTaille(); i++) {
			for (int j = 0; j < getTaille(); j++) {
				coup.setX(i);
				coup.setY(j);
				if (coupLegal(coup)) {
					Coup coupTmp = new Coup();
					coupTmp.setCouleur(couleur);
					coupTmp.setX(i);
					coupTmp.setY(j);
					liste.add(coupTmp);
				}
			}
		}
		return liste;
	}

	public void affiche() {
		System.out.print("  ");
		for (int i = 0; i < getTaille(); i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < getTaille(); i++) {
			System.out.print(i + " ");
			for (int j = 0; j < getTaille(); j++) {
				System.out.print(damier[j][i] + " ");
			}
			System.out.println();
		}

		System.out.println("evaluation pour @ = " + evaluation('@'));

	}

	public Character[][] getDamier() {
		return damier;
	}

	public void setDamier(Character damier[][]) {
		this.damier = damier;
	}

	public int getTaille() {
		return taille;
	}

	public int[] getPileModifications() {
		return pileModifications;
	}

	public void setPileModifications(int pileModifications[]) {
		this.pileModifications = pileModifications;
	}

	public int getNbBlanc() {
		return nbBlanc;
	}

	public void setNbBlanc(int nbBlanc) {
		this.nbBlanc = nbBlanc;
	}

	public int getNbNoir() {
		return nbNoir;
	}

	public void setNbNoir(int nbNoir) {
		this.nbNoir = nbNoir;
	}

	public int getNbModification() {
		return nbModification;
	}

	public void setNbModification(int nbModification) {
		this.nbModification = nbModification;
	}

}
