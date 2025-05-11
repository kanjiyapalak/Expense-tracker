package javaproject.com.expensetracker.entities;

import jakarta.persistence.*;

import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity

@Table(name = "categories")
@JsonIgnoreProperties("expenses")
public class Category {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    public Category()
    {
    	
    }
    public Category(Long id, String name, List<Expense> expenses) {
  		super();
  		this.id = id;
  		this.name = name;
  		this.expenses = expenses;
  	}
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Expense> expenses;

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

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}
    
    
}

