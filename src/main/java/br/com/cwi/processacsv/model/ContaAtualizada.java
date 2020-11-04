package br.com.cwi.processacsv.model;

public class ContaAtualizada extends Conta{

	public void init(Conta conta) {
		this.setAgencia(conta.getAgencia());
		this.setConta(conta.getConta());
		this.setSaldo(conta.getSaldo());
		this.setStatus(conta.getStatus());
	}

	private boolean retornoReceita;

	public boolean isRetornoReceita() {
		return retornoReceita;
	}

	public void setRetornoReceita(boolean retornoReceita) {
		this.retornoReceita = retornoReceita;
	}

	@Override
	public String toString() {
		return super.toString().replace('}', ' ')+
				", retornoReceita='" + retornoReceita + '\'' +
				'}';
	}
}
