package fr.riverjach.virus;

import java.io.Serializable;

public class Coup implements Serializable {

	private static final long serialVersionUID = 1L;

	private Character couleur;
	private Integer x;
	private Integer y;

	public Character getCouleur() {
		return couleur;
	}

	public void setCouleur(Character couleur) {
		this.couleur = couleur;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

}
