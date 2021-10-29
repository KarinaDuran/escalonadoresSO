package kernel;
import operacoes.Operacao;

public class PCB implements Comparable<PCB>{
	public enum Estado {NOVO, PRONTO, EXECUTANDO, ESPERANDO, TERMINADO};
	public int idProcesso;
	public Estado estado = Estado.NOVO;
	public int[] registradores = new int[5];
	public int contadorDePrograma;
	public Operacao[] codigo;
	public int Chute = 5;
	public int momentoPronto;

	
	public int compareTo(PCB outro){
		//if(outro.Chute > this.Chute || (outro.Chute == this.Chute && this.idProcesso < outro.idProcesso ))
		//return 1;
		//else return 0;
		if(outro.momentoPronto > this.momentoPronto || (outro.momentoPronto == this.momentoPront && outro.idProcesso>this.idProcesso)) return 1
			else return 0;
	}

}
