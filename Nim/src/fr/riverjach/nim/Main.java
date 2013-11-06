package fr.riverjach.nim;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Integer tas[] = { 7, 5, 3, 1 };
		int t, nombre;

		while (true) {
			System.out.println(tas[0] + " " + tas[1] + " " + tas[2] + " " + tas[3]);
			if ((tas[0] + tas[1] + tas[2] + tas[3]) == 0) {
				System.out.println("j'ai gagné !");
				break;
			}

			do {
				System.out.println("Donnez le tas (entre 0 et 3) :");
				t = scanner.nextInt();

				System.out.println("Donnez le nombre : ");
				nombre = scanner.nextInt();

			} while ((t < 0) || (t > 3) || (tas[t] < nombre) || (nombre < 0) || (tas[t] == 0));

			tas[t] -= nombre;
			System.out.println(tas[0] + " " + tas[1] + " " + tas[2] + " " + tas[3]);

			for (int i = 0; i < tas.length; i++) {
				for (int j = 1; j <= tas[i]; j++) {
					tas[i] -= j;
					if ((tas[0] ^ tas[1] ^ tas[2] ^ tas[3]) == 0) {
						t = i;
						nombre = j;
					}
					tas[i] += j;
				}
			}
			System.out.println("je prend " + nombre + " dans le tas " + t);
			tas[t] -= nombre;
		}

		scanner.close();
	}

}
