package com.fronchak.petshop.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pet")
public class Pet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Double weightInKg;
	private Double heightInCm;
	
	@ManyToOne
	private Animal animal;
	
	@ManyToMany
	@JoinTable(
			name = "pet_color",
			joinColumns = @JoinColumn(name = "id_pet"),
			inverseJoinColumns = @JoinColumn(name = "id_color")
			)
	private List<Color> colors = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getWeightInKg() {
		return weightInKg;
	}

	public void setWeightInKg(Double weightInKg) {
		this.weightInKg = weightInKg;
	}

	public Double getHeightInCm() {
		return heightInCm;
	}

	public void setHeightInCm(Double heightInCm) {
		this.heightInCm = heightInCm;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public List<Color> getColors() {
		return colors;
	}
	
	public void addColor(Color color) {
		colors.add(color);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pet other = (Pet) obj;
		return Objects.equals(id, other.id);
	}
}
