package vos;
import org.codehaus.jackson.annotate.JsonProperty;

public class Ingrediente {
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="descripcionES")
	private String descripcionES;
	
	@JsonProperty(value="descripcionEN")
	private String descripcionEN;
	
	@JsonProperty(value="idProd")
	private long idProd;
	
	@JsonProperty(value="costo")
	private long costo;
	 
	@JsonProperty(value="equivalencia")
	private String equivalencia;
	
	public Ingrediente(@JsonProperty(value="nombre")String nombre, @JsonProperty(value="descripcionES")String descripcionES,@JsonProperty(value="descripcionEN") String descripcionEN, @JsonProperty(value="idProd")long idProd, @JsonProperty(value="costo") long costo,@JsonProperty(value="equivalencia")
	String equivalencia) {
		super();
		this.nombre = nombre;
		this.descripcionES = descripcionES;
		this.descripcionEN = descripcionEN;
		this.idProd = idProd;
		this.costo= costo;
		this.equivalencia = equivalencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcionES() {
		return descripcionES;
	}

	public void setDescripcionES(String descripcionES) {
		this.descripcionES = descripcionES;
	}

	public String getDescripcionEN() {
		return descripcionEN;
	}

	public void setDescripcionEN(String descripcionEN) {
		this.descripcionEN = descripcionEN;
	}

	public long getIdProd() {
		return idProd;
	}

	public void setIdProd(long idProd) {
		this.idProd = idProd;
	}
	public void setEquivalencia(String foo) {
		this.equivalencia = foo;
	}
	public String getEquivalencia() {
		return equivalencia;
	}

	public long getCosto() {
		return costo;
	}

	public void setCosto(long costo) {
		this.costo = costo;
	}

}
