package kernel;

import kernel.SO.Escalonador;
import operacoes.Operacao;

public class PCB implements Comparable<PCB> {
	public enum Estado {
		NOVO, PRONTO, EXECUTANDO, ESPERANDO, TERMINADO
	};

	public int idProcesso;
	public Estado estado = Estado.NOVO;
	public int[] registradores = new int[5];
	public int contadorDePrograma;
	public Operacao[] codigo;
	public int Chute = 5;
	public int contadorBurst = 0;
	public int momentoPronto;
	public Escalonador escalonador;

	@Override
	public int compareTo(PCB outro) {
		if (escalonador == escalonador.SHORTEST_JOB_FIRST || escalonador == escalonador.SHORTEST_REMANING_TIME_FIRST) {
			if (outro.Chute > this.Chute || (outro.Chute == this.Chute && outro.idProcesso > this.idProcesso))
				return -1;
			else
				return 1;
		} else {
			if (outro.momentoPronto > this.momentoPronto
					|| (outro.momentoPronto == this.momentoPronto && outro.idProcesso > this.idProcesso))
				return -1;
			else
				return 1;
		}
	}

}
