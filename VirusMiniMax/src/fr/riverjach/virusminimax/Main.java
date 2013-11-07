package fr.riverjach.virusminimax;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Virus virus = new Virus();
		List<Coup> listeCoups = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		String line;

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
			while (true) {

				do {
					System.out.println("X : ");
					coup.setX(scanner.nextInt());
					System.out.println("Y : ");
					coup.setY(scanner.nextInt());
				} while (!(coup.getX() < virus.getTaille()) || !(coup.getY() < virus.getTaille())
						|| !(coup.getX() >= 0) || !(coup.getY() >= 0) || !virus.coupLegal(coup));
				virus.joue(coup);
				virus.affiche();
				
				scanner.nextLine();
				System.out.println("Voulez vous annuler le coup ? (y/n) : ");
				line = scanner.nextLine();

				if ("y".equalsIgnoreCase(line)) {
					virus.dejoue(coup);
				} else {
					break;
				}

			}

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
		joueur1 = virus.evaluationSiPlusDeCoupsPossibles('@');
		joueur2 = virus.evaluationSiPlusDeCoupsPossibles('O');

		if (joueur1 > joueur2) {
			System.out.println("Joueur @ vous avez gagné avec " + joueur1 + " - " + joueur2);
		} else {
			System.out.println("Joueur O vous avez gagné avec " + joueur2 + " - " + joueur1);
		}

	}
}
