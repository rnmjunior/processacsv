package br.com.cwi.processacsv.model;

public class Conta {

	private String agencia;
	private String conta;
	private Double saldo;
	private String status;

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta.replace("-", "");
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Conta{" +
				"agencia='" + agencia + '\'' +
				", conta='" + conta + '\'' +
				", saldo='" + saldo + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
