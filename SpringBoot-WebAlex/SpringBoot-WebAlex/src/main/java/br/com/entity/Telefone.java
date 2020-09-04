package br.com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;



@Entity
@Table(name="telefone")
public class Telefone {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
	private Long id;
	private String numero;
	private String tipo;
	//muitos telefone para uma pessoa
	@ForeignKey(name="pessoa_id")
	@ManyToOne
	private Pessoa pessoa;
	
	public Telefone() {
		// TODO Auto-generated constructor stub
	}
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
}
