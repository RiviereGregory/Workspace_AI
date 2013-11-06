package fr.riverjach.virus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Virus virus = new Virus();
		List<Coup> listeCoups = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			virus.affiche();

			listeCoups = virus.coupsLegaux('@');
			if (listeCoups.isEmpty()) {
				System.out.println("Vous ne pouvez plus jouer de coup @");
				gagnant(virus);
				break;
			}

			System.out.println("donner votre coup : ");
			Coup coup = new Coup();
			coup.setCouleur('@');
			do {
				coup.setX(scanner.nextInt());
				coup.setY(scanner.nextInt());
			} while (!virus.coupLegal(coup));
			virus.joue(coup);
			virus.affiche();

			listeCoups = virus.coupsLegaux('O');
			if (listeCoups.isEmpty()) {
				System.out.println("Vous ne pouvez plus jouer de coup O");
				gagnant(virus);
				break;
			}

			Integer meilleureEvaluation = -virus.getTaille() * virus.getTaille() - 1;
			for (Coup coup2 : listeCoups) {

				Virus v = new Virus();
				v.setDamier(v.getDamier());
				v.setEval(virus.getEval());

				v.joue(coup2);

				Integer eval = v.evaluation('O');
				if (eval > meilleureEvaluation) {
					meilleureEvaluation = eval;
					coup = coup2;
				}
			}

			System.out.println("Je joue en " + coup.getX() + " " + coup.getY());
			virus.joue(coup);
		}

		scanner.close();
	}

	public static void gagnant(Virus virus) {
		int joueur1 = 0;
		int joueur2 = 0;
		Character damier[][] = new Character[virus.getTaille()][virus.getTaille()];
		damier = virus.getDamier();
		for (int i = 0; i < virus.getTaille(); i++) {
			for (int j = 0; j < virus.getTaille(); j++) {
				if (damier[i][j].equals('@')) {
					joueur1++;
				} else if (damier[i][j].equals('O')) {
					joueur2++;
				} else if (damier[i][j].equals('+')) {
					if (i < 6 && j < 6 && i > 0 && j > 0) {
						if (damier[i - 1][j].equals('@') || damier[i][j - 1].equals('@')
								|| damier[i - 1][j - 1].equals('@') || damier[i + 1][j].equals('@')
								|| damier[i][j + 1].equals('@') || damier[i + 1][j + 1].equals('@')
								|| damier[i - 1][j + 1].equals('@')
								|| damier[i + 1][j - 1].equals('@')) {
							joueur1++;
						} else if (damier[i - 1][j].equals('O') || damier[i][j - 1].equals('O')
								|| damier[i - 1][j - 1].equals('O') || damier[i + 1][j].equals('O')
								|| damier[i][j + 1].equals('O') || damier[i + 1][j + 1].equals('O')
								|| damier[i - 1][j + 1].equals('O')
								|| damier[i + 1][j - 1].equals('O')) {
							joueur2++;
						}
					} else if (i == 6) {
						if (damier[i - 1][j].equals('@') || damier[i][j - 1].equals('@')
								|| damier[i - 1][j - 1].equals('@') || damier[i][j + 1].equals('@')
								|| damier[i - 1][j + 1].equals('@')) {
							joueur1++;
						} else if (damier[i - 1][j].equals('O') || damier[i][j - 1].equals('O')
								|| damier[i - 1][j - 1].equals('O') || damier[i][j + 1].equals('O')
								|| damier[i - 1][j + 1].equals('O')) {
							joueur2++;
						}
					} else if (j == 6) {
						if (damier[i - 1][j].equals('@') || damier[i][j - 1].equals('@')
								|| damier[i - 1][j - 1].equals('@') || damier[i + 1][j].equals('@')

								|| damier[i + 1][j - 1].equals('@')) {
							joueur1++;
						} else if (damier[i - 1][j].equals('O') || damier[i][j - 1].equals('O')
								|| damier[i - 1][j - 1].equals('O') || damier[i + 1][j].equals('O')
								|| damier[i + 1][j - 1].equals('O')) {
							joueur2++;
						}
					} else if (i == 0) {
						if (damier[i][j - 1].equals('@') || damier[i + 1][j].equals('@')
								|| damier[i][j + 1].equals('@') || damier[i + 1][j + 1].equals('@')
								|| damier[i + 1][j - 1].equals('@')) {
							joueur1++;
						} else if (damier[i][j - 1].equals('O') || damier[i + 1][j].equals('O')
								|| damier[i][j + 1].equals('O') || damier[i + 1][j + 1].equals('O')
								|| damier[i + 1][j - 1].equals('O')) {
							joueur2++;
						}
					} else if (j == 0) {
						if (damier[i - 1][j].equals('@') || damier[i + 1][j].equals('@')
								|| damier[i][j + 1].equals('@') || damier[i + 1][j + 1].equals('@')
								|| damier[i - 1][j + 1].equals('@')) {
							joueur1++;
						} else if (damier[i - 1][j].equals('O') || damier[i + 1][j].equals('O')
								|| damier[i][j + 1].equals('O') || damier[i + 1][j + 1].equals('O')
								|| damier[i - 1][j + 1].equals('O')) {
							joueur2++;
						}
					}
				}
			}
		}

		if (joueur1 > joueur2) {
			System.out.println("Joueur @ vous avez gagné avec " + joueur1 + " - " + joueur2);
		} else {
			System.out.println("Joueur O vous avez gagné avec " + joueur2 + " - " + joueur1);
		}

	}
}
