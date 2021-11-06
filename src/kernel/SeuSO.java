package kernel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import kernel.PCB.Estado;
import operacoes.Operacao;
import operacoes.OperacaoES;

public class SeuSO extends SO {
	private List<Integer> processosTerminados = new ArrayList<Integer>();
	private static List<Integer> processosProntos = new ArrayList<Integer>();
	private List<Integer> processosEsperando = new ArrayList<Integer>();

	private Integer idProcessoNovo;
	private Integer trocasDeContexto = 0;
	private Escalonador esquemaEscalonador;

	private int totalTempoEspera = 0;
	private int totalTempoRetorno = 0;
	private int totalTempoResposta = 0;

	private int Quantum = 1;
	private int contadorCiclos = 0;

	private Operacao operacaoCPU;
	private PCB processoAtual;
	private PCB processoNovo;
	private PCB processoEsperaNovo;

	private List<PCB> prontoList = new ArrayList<PCB>();

	private List<PCB> filaES0 = new ArrayList<>();
	private List<PCB> filaES1 = new ArrayList<>();
	private List<PCB> filaES2 = new ArrayList<>();
	private List<PCB> filaES3 = new ArrayList<>();
	private List<PCB> filaES4 = new ArrayList<>();

	private OperacaoES operacaoES0;
	private OperacaoES operacaoES1;
	private OperacaoES operacaoES2;
	private OperacaoES operacaoES3;
	private OperacaoES operacaoES4;

	@Override
	// ATENCÃO: cria o processo mas o mesmo
	// só estará "pronto" no próximo ciclo
	protected void criaProcesso(Operacao[] codigo) {
		PCB novoProcesso = new PCB();
		novoProcesso.idProcesso = contadorCiclos;
		novoProcesso.codigo = codigo;
		novoProcesso.contadorDePrograma = 0;
		novoProcesso.escalonador = esquemaEscalonador;
		processoEsperaNovo = novoProcesso;
		idProcessoNovo = processoEsperaNovo.idProcesso;

	}

	@Override
	protected void trocaContexto(PCB pcbAtual, PCB pcbProximo) {
		trocasDeContexto++;
	}

	@Override
	protected OperacaoES proximaOperacaoES(int idDispositivo) {
		if (idDispositivo == 0) {
			return operacaoES0;
		} else if (idDispositivo == 1) {
			return operacaoES1;
		} else if (idDispositivo == 2) {
			return operacaoES2;
		} else if (idDispositivo == 3) {
			return operacaoES3;
		} else {
			return operacaoES4;
		}
	}

	protected void GerenciadorES() {
		/** Dispositivo ES0 **/
		if (operacaoES0 != null && operacaoES0.ciclos == 0) {
			PCB x = filaES0.get(0);
			x.contadorDePrograma = x.contadorDePrograma + 1;
			filaES0.remove(0);
			int y = processosEsperando.indexOf(x.idProcesso);
			processosEsperando.remove(y);
			if (x.contadorDePrograma == x.codigo.length) {
				processosTerminados.add(x.idProcesso);
			} else {
				verificaDestino(x);
			}
			if (!filaES0.isEmpty()) {
				operacaoES0 = (OperacaoES) filaES0.get(0).codigo[filaES0.get(0).contadorDePrograma];
			} else {
				operacaoES0 = null;
			}
		}
		/** Dispositivo ES1 **/
		if (operacaoES1 != null && operacaoES1.ciclos == 0) {
			PCB x = filaES1.get(0);
			x.contadorDePrograma = x.contadorDePrograma + 1;
			filaES1.remove(0);
			int y = processosEsperando.indexOf(x.idProcesso);
			processosEsperando.remove(y);
			if (x.contadorDePrograma == x.codigo.length) {
				processosTerminados.add(x.idProcesso);
			} else {
				verificaDestino(x);
			}
			if (!filaES1.isEmpty()) {
				operacaoES1 = (OperacaoES) filaES1.get(0).codigo[filaES1.get(0).contadorDePrograma];
			} else {
				operacaoES1 = null;
			}
		}
		/** Dispositivo ES2 **/
		if (operacaoES2 != null && operacaoES2.ciclos == 0) {
			PCB x = filaES2.get(0);
			x.contadorDePrograma = x.contadorDePrograma + 1;
			filaES2.remove(0);
			int y = processosEsperando.indexOf(x.idProcesso);
			processosEsperando.remove(y);
			if (x.contadorDePrograma == x.codigo.length) {
				processosTerminados.add(x.idProcesso);
			} else {
				verificaDestino(x);
			}
			if (!filaES2.isEmpty()) {
				operacaoES2 = (OperacaoES) filaES2.get(0).codigo[filaES2.get(0).contadorDePrograma];
			} else {
				operacaoES2 = null;
			}
		}
		/** Dispositivo ES3 **/
		if (operacaoES3 != null && operacaoES3.ciclos == 0) {
			PCB x = filaES3.get(0);
			x.contadorDePrograma = x.contadorDePrograma + 1;
			filaES3.remove(0);
			int y = processosEsperando.indexOf(x.idProcesso);
			processosEsperando.remove(y);
			if (x.contadorDePrograma == x.codigo.length) {
				processosTerminados.add(x.idProcesso);
			} else {
				verificaDestino(x);
			}
			if (!filaES3.isEmpty()) {
				operacaoES3 = (OperacaoES) filaES3.get(0).codigo[filaES3.get(0).contadorDePrograma];
			} else {
				operacaoES3 = null;
			}
		}
		/** Dispositivo ES4 **/
		if (operacaoES4 != null && operacaoES4.ciclos == 0) {
			PCB x = filaES4.get(0);
			x.contadorDePrograma = x.contadorDePrograma + 1;
			filaES4.remove(0);
			int y = processosEsperando.indexOf(x.idProcesso);
			processosEsperando.remove(y);
			if (x.contadorDePrograma == x.codigo.length) {
				processosTerminados.add(x.idProcesso);
			} else {
				verificaDestino(x);
			}
			if (!filaES4.isEmpty()) {
				operacaoES4 = (OperacaoES) filaES4.get(0).codigo[filaES4.get(0).contadorDePrograma];
			} else {
				operacaoES4 = null;
			}
		}

	}

	@Override
	protected Operacao proximaOperacaoCPU() {
		return operacaoCPU;
	}

	protected void verificaDestino(PCB processo) {
		if (processo.codigo[processo.contadorDePrograma] instanceof OperacaoES) {
			processo.estado = Estado.ESPERANDO;
			OperacaoES aux = (OperacaoES) processo.codigo[processo.contadorDePrograma];
			processosEsperando.add(processo.idProcesso);
			adicionaFilaES(aux.idDispositivo, processo);
		} else {
			processo.momentoPronto = contadorCiclos;
			processo.estado = Estado.PRONTO;
			prontoList.add(processo);
		}
	}

	// Pega Proximo PCB da fila de pronto e ajusta as variaveis
	protected void pegaProxPronto() {
		if (!prontoList.isEmpty()) {
			processoAtual = prontoList.get(0);
			operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
			processoAtual.contadorDePrograma = processoAtual.contadorDePrograma + 1;
			processoAtual.contadorBurst = processoAtual.contadorBurst + 1;
			processoAtual.estado = Estado.EXECUTANDO;
			prontoList.remove(0);
		} else {
			processoAtual = null;
		}
	}

	@Override
	protected void executaCicloKernel() {
		// Verifica se existe algum processo novo esperando para ir para a fila de
		// pronto
		if (processoNovo != null) {
			verificaDestino(processoNovo);
			processoNovo = null;
		}

		// Gerencia as filas.
		GerenciadorES();
		Collections.sort(prontoList);

		// Seleciona o escalonador
		if (esquemaEscalonador == Escalonador.SHORTEST_JOB_FIRST
				|| esquemaEscalonador == Escalonador.FIRST_COME_FIRST_SERVED) {
			fifoSjf();
		} else if (esquemaEscalonador == Escalonador.ROUND_ROBIN_QUANTUM_5) {
			RoundRobin();
		} else {
			srtf();
		}

		// Gerencia a chegada de um novo processo, se chegou um novo nesse ciclo,
		// manda para outra variável para ser mandado pra fila de pronto no proximo
		// ciclo.
		if (processoEsperaNovo != null) {
			processoNovo = processoEsperaNovo;
			processoEsperaNovo = null;
		} else {
			idProcessoNovo = null;
		}

		// Calculo dos tempos de espera, retorno e resposta
		/********************************************************************/
		if (!prontoList.isEmpty())
			totalTempoEspera += prontoList.size();

		if (processoAtual != null)
			totalTempoRetorno++;
		totalTempoRetorno += prontoList.size() + processosEsperando.size();

		for (PCB i : prontoList) {
			if (i.foiExecutado == false) {
				totalTempoResposta++;
			}
		}
		/********************************************************************/
		contadorCiclos++;

	}

	// Metodo para o escalonador RoundRobin com quantum = 5
	protected void RoundRobin() {
		if (processoAtual != null) {
			// Termina o processo atual
			if (processoAtual.codigo.length == processoAtual.contadorDePrograma) {
				Quantum = 1;
				processoAtual.estado = Estado.TERMINADO;
				processosTerminados.add(processoAtual.idProcesso);
				pegaProxPronto();
				if (processoAtual != null) {
					if (processoAtual.foiExecutado == true)
						trocasDeContexto++;
					processoAtual.foiExecutado = true;
				}
			} else {
				// Limita os processos a 5
				if (Quantum == 5) {
					Quantum = 1;
					verificaDestino(processoAtual);
					Collections.sort(prontoList);
					pegaProxPronto();
					processoAtual.foiExecutado = true;
					trocasDeContexto++;
				} else {
					if (processoAtual.codigo[processoAtual.contadorDePrograma] instanceof OperacaoES) {
						Quantum = 1;
						processoAtual.estado = Estado.ESPERANDO;
						// Troca de contexto para a operacao ir para ES
						OperacaoES aux = (OperacaoES) processoAtual.codigo[processoAtual.contadorDePrograma];
						processosEsperando.add(processoAtual.idProcesso);
						adicionaFilaES(aux.idDispositivo, processoAtual);
						pegaProxPronto();
						if (processoAtual != null)
							processoAtual.foiExecutado = true;
						trocaContexto(processoAtual, processoAtual);
					} else {
						// Vai pra proxima execução
						operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
						processoAtual.contadorDePrograma = processoAtual.contadorDePrograma + 1;
						Quantum++;
					}
				}
			}
		}

		// Se nao existir nenhum processo executando
		else {
			// Pega o primeiro da lista de pronto e faz os ajustes.
			if (!prontoList.isEmpty()) {
				pegaProxPronto();
				if (processoAtual != null && processoAtual.foiExecutado == true) {
					trocasDeContexto++;
				}
				processoAtual.foiExecutado = true;
			} else {
				processoAtual = null;
			}
		}

	}

	// Metodo para o Shortest Remaining Time First
	protected void srtf() {
		if (processoAtual != null) {
			if (processoAtual.codigo.length == processoAtual.contadorDePrograma) {
				// Termina o processo atual
				processosTerminados.add(processoAtual.idProcesso);
				pegaProxPronto();
				if (processoAtual != null) {
					if (processoAtual.foiExecutado == true)
						trocasDeContexto++;
					processoAtual.foiExecutado = true;
				}

			} else {
				if (processoAtual.codigo[processoAtual.contadorDePrograma] instanceof OperacaoES) {
					processoAtual.Chute = (processoAtual.Chute + processoAtual.contadorBurst) / 2;
					processoAtual.contadorBurst = 0;
					// Troca de contexto pra abrir espaco para operacao ES
					OperacaoES aux = (OperacaoES) processoAtual.codigo[processoAtual.contadorDePrograma];
					processosEsperando.add(processoAtual.idProcesso);
					processoAtual.estado = Estado.ESPERANDO;
					adicionaFilaES(aux.idDispositivo, processoAtual);
					pegaProxPronto();
					if (processoAtual != null)
						processoAtual.foiExecutado = true;
					trocaContexto(processoAtual, processoAtual);
				} else if (!prontoList.isEmpty()
						&& prontoList.get(0).Chute < (processoAtual.Chute - processoAtual.contadorBurst)) {
					processoAtual.Chute = Math.abs(processoAtual.Chute - processoAtual.contadorBurst);
					processoAtual.contadorBurst = 0;
					processoAtual.estado = Estado.PRONTO;
					prontoList.add(processoAtual);
					processoAtual = prontoList.get(0);
					prontoList.remove(0);
					Collections.sort(prontoList);
					trocasDeContexto++;
				} else {
					// Vai pra proxima execução
					operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
					processoAtual.contadorDePrograma = processoAtual.contadorDePrograma + 1;
					processoAtual.contadorBurst = processoAtual.contadorBurst + 1;
				}
			}
		} else {
			if (!prontoList.isEmpty()) {
				pegaProxPronto();
				if (processoAtual != null && processoAtual.foiExecutado == true) {
					trocasDeContexto++;
				}
				processoAtual.foiExecutado = true;
			} else {
				processoAtual = null;
			}
		}

	}

	// Metodo tanto para First Come Fist Served e Shortes Job First
	protected void fifoSjf() {
		if (processoAtual != null) {
			// Termina o processo atual
			if (processoAtual.codigo.length == processoAtual.contadorDePrograma) {
				processosTerminados.add(processoAtual.idProcesso);
				pegaProxPronto();
				if (processoAtual != null) {
					if (processoAtual.foiExecutado == true)
						trocasDeContexto++;
					processoAtual.foiExecutado = true;
				}

			} else {
				if (processoAtual.codigo[processoAtual.contadorDePrograma] instanceof OperacaoES) {
					// Ajusta o proximo chute
					processoAtual.Chute = (processoAtual.Chute + processoAtual.contadorBurst) / 2;
					processoAtual.contadorBurst = 0;
					// Coloca na fila de ES
					OperacaoES aux = (OperacaoES) processoAtual.codigo[processoAtual.contadorDePrograma];
					processosEsperando.add(processoAtual.idProcesso);
					processoAtual.estado = Estado.ESPERANDO;
					adicionaFilaES(aux.idDispositivo, processoAtual);
					pegaProxPronto();
					if (processoAtual != null)
						processoAtual.foiExecutado = true;
					trocaContexto(processoAtual, processoAtual);
				} else {
					// Vai pra proxima execução
					operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
					processoAtual.contadorDePrograma = processoAtual.contadorDePrograma + 1;
					processoAtual.contadorBurst = processoAtual.contadorBurst + 1;
				}
			}
		}
		// Se o executando estiver vazio, vai para o proximo da fila de pronto
		else {
			if (!prontoList.isEmpty()) {
				pegaProxPronto();
				if (processoAtual != null && processoAtual.foiExecutado == true) {
					trocasDeContexto++;
				}
				processoAtual.foiExecutado = true;
			} else {
				processoAtual = null;
			}
		}
	}

	// Metodo auxiliar que insere na fila de ES
	protected void adicionaFilaES(int idDispositivo, PCB processo) {
		if (idDispositivo == 0) {
			filaES0.add(processo);
			if (operacaoES0 == null)
				operacaoES0 = (OperacaoES) filaES0.get(0).codigo[filaES0.get(0).contadorDePrograma];
		} else if (idDispositivo == 1) {
			filaES1.add(processo);
			if (operacaoES1 == null)
				operacaoES1 = (OperacaoES) filaES1.get(0).codigo[filaES1.get(0).contadorDePrograma];
		} else if (idDispositivo == 2) {
			filaES2.add(processo);
			if (operacaoES2 == null)
				operacaoES2 = (OperacaoES) filaES2.get(0).codigo[filaES2.get(0).contadorDePrograma];

		} else if (idDispositivo == 3) {
			filaES3.add(processo);
			if (operacaoES3 == null)
				operacaoES3 = (OperacaoES) filaES3.get(0).codigo[filaES3.get(0).contadorDePrograma];

		} else {
			filaES4.add(processo);
			if (operacaoES4 == null)
				operacaoES4 = (OperacaoES) filaES4.get(0).codigo[filaES4.get(0).contadorDePrograma];
		}

	}

	@Override
	protected boolean temTarefasPendentes() {
		if (processosProntos.isEmpty() && processoAtual == null && processosEsperando.isEmpty()
				&& processoNovo == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected Integer idProcessoNovo() {
		return idProcessoNovo;
	}

	@Override
	protected List<Integer> idProcessosProntos() {
		processosProntos.clear();
		for (PCB x : prontoList) {
			processosProntos.add(x.idProcesso);
		}

		return processosProntos;
	}

	@Override
	protected Integer idProcessoExecutando() {
		if (processoAtual != null)
			return processoAtual.idProcesso;
		return null;
	}

	@Override
	protected List<Integer> idProcessosEsperando() {
		return processosEsperando;
	}

	@Override
	protected List<Integer> idProcessosTerminados() {
		return processosTerminados;
	}

	@Override
	protected int tempoEsperaMedio() {
		return Math.round(totalTempoEspera / processosTerminados.size());
	}

	@Override
	protected int tempoRespostaMedio() {
		return Math.round(totalTempoResposta / processosTerminados.size());
	}

	@Override
	protected int tempoRetornoMedio() {
		return Math.round(totalTempoRetorno / processosTerminados.size());
	}

	@Override
	protected int trocasContexto() {
		return trocasDeContexto;
	}

	@Override
	public void defineEscalonador(Escalonador e) {
		esquemaEscalonador = e;
	}
}
