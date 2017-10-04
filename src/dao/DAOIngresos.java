package dao;

import org.codehaus.jackson.annotate.JsonProperty;

public class DAOIngresos {
	@JsonProperty(value="idRestaurante")
	private long idRestaurante;
	
	@JsonProperty(value="Balance")
	private long Balance;
	
	
	
	public DAOIngresos(long idRestaurante, long balance) {
		super();
		this.idRestaurante = idRestaurante;
		Balance = balance;
	}
	
	
}
