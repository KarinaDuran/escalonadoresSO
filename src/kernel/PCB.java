package kernel;
import operacoes.Operacao;

public class PCB {
	public enum Estado {NOVO, PRONTO, EXECUTANDO, ESPERANDO, TERMINADO};
	public int idProcesso;
	public Estado estado = Estado.NOVO;
	public int[] registradores = new int[5];
	public int contadorDePrograma;
	public Operacao[] codigo;

}
