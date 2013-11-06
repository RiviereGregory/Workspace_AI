package fr.riverjach.virus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Virus implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	private final int taille = 7;
	private Character damier[][];
	private int eval;

	public Virus() {
		this.setDamier(new Character[getTaille()][getTaille()]);
		init();
	}

	public Object clone() {
		Virus virus = null;

		try {
			virus = (Virus) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return virus;
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
		eval = 0;
	}

	public Character adversaire(Character couleur) {
		if (couleur == 'O') {
			return '@';
		}
		return 'O';
	}

	public void joue(Coup m) {
		damier[m.getX()][m.getY()] = m.getCouleur();
		if (m.getCouleur() == '@') {
			eval++;
		} else {
			eval--;
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
					if (m.getCouleur() == '@') {
						eval += 2;
					} else {
						eval -= 2;
					}
				}
			}

		}
	}

	public Integer evaluation(Character couleur) {
		if (couleur == '@') {
			return eval;
		}
		return -eval;
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

	public int getEval() {
		return eval;
	}

	public void setEval(int eval) {
		this.eval = eval;
	}

	public int getTaille() {
		return taille;
	}

}
