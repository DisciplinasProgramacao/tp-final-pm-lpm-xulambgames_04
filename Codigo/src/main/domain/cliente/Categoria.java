package main.domain.cliente;

public enum Categoria implements ICategoria{
	EMPOLGADO(10.0),
	FANATICO(25.0),
	CADASTRADO(0);
	
	private double mensalidade;
	

	Categoria(double valor) {
		this.mensalidade = valor;
	}

	public double getValor() {
		return this.mensalidade;
	}
	
	public double pctDesconto() {
		switch(this) {
		case EMPOLGADO:
			return 0.10;
		case FANATICO: 
			return 0.30;
		default:
			return 0;
		}
	}
	
	

	
	
}
