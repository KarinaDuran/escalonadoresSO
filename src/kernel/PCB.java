package kernel;
import operacoes.Operacao;

public class PCB {
	public enum Estado {NOVO, PRONTO, EXECUTANDO, ESPERANDO, TERMINADO};
	public int idProcesso;
	public Estado estado = Estado.NOVO;
	public int[] registradores = new int[5];
	public int contadorDePrograma;
	public Operacao[] codigo;
	public int ciclosRestantes; 

	
	public int compareTo(PCB outro){
		if(outro.ciclosRestantes > this.ciclosRestantes || (outro.ciclosRestantes == this.ciclosRestantes && this.idProcesso < outro.idProcesso ))
		return 1;
		else return 0;
	}

}
