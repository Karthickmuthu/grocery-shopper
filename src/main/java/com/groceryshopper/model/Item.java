package com.groceryshopper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "notes")
	private String notes;

	@Column(name = "purchased", nullable = false)
	private boolean purchased;

	public Item(String title, String notes, boolean purchased) {
		super();
		this.title = title;
		this.notes = notes;
		this.purchased = purchased;
	}	
	
}
