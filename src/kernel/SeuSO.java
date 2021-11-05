package kernel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import operacoes.Operacao;
import operacoes.OperacaoES;

public class SeuSO extends SO {
	private List<Integer> processosTerminados = new ArrayList<Integer>();
	private static List<Integer> processosProntos = new ArrayList<Integer>();
	private Integer processosExecutando;
	private List<Integer> processosEsperando = new ArrayList<Integer>();

	private Integer idProcessoNovo;
	private Integer trocasDeContexto = 0;
	private Escalonador esquemaEscalonador;

	private int contadorCiclos = 0;
	private int totalTempoEspera = 0;
	private int totalTempoRetorno = 0;
	private int Quantum = 1;

	private PCB processoAtual;
	private PCB processoNovo;
	private PCB processoEsperaNovo;
	private Operacao operacaoCPU;

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
		processoAtual = pcbProximo;
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
			OperacaoES aux = (OperacaoES) processo.codigo[processo.contadorDePrograma];
			processosEsperando.add(processo.idProcesso);
			adicionaFilaES(aux.idDispositivo, processo);
		} else {
			processo.momentoPronto = contadorCiclos;
			prontoList.add(processo);
		}
	}

	protected void pegaProxPronto() {
		if (!prontoList.isEmpty()) {
			trocaContexto(processoAtual, prontoList.get(0));
			operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
			processoAtual.contadorDePrograma = processoAtual.contadorDePrograma + 1;
			prontoList.remove(0);
		} else {
			processoAtual = null;
		}
		trocasDeContexto++;
	}

	@Override
	protected void executaCicloKernel() {
		if (processoNovo != null) {
			verificaDestino(processoNovo);
			processoNovo = null;
		}
		GerenciadorES();
		Collections.sort(prontoList);

		if (esquemaEscalonador == Escalonador.SHORTEST_JOB_FIRST
				|| esquemaEscalonador == Escalonador.FIRST_COME_FIRST_SERVED) {
			fifoSjf();
		} else {
			RoundRobin();

		}

		if (processoEsperaNovo != null) {
			processoNovo = processoEsperaNovo;
			processoEsperaNovo = null;
		} else {
			idProcessoNovo = null;
		}

		if (!prontoList.isEmpty()) {
			totalTempoEspera += prontoList.size();
		}

		int executando;
		if (processoAtual != null)
			executando = 1;
		else
			executando = 0;

		totalTempoRetorno += prontoList.size() + processosEsperando.size() + executando;

		contadorCiclos++;

	}

	protected void RoundRobin() {
		if (processoAtual != null) {
			if (processoAtual.codigo.length == processoAtual.contadorDePrograma) {
				Quantum = 1;
				// Termina o processo atual
				processosTerminados.add(processoAtual.idProcesso);
				pegaProxPronto();
			} else {
				if (Quantum == 5) {
					processoAtual.momentoPronto = contadorCiclos;
					Quantum = 1;
					verificaDestino(processoAtual);
					pegaProxPronto();
				} else {

					if (processoAtual.codigo[processoAtual.contadorDePrograma] instanceof OperacaoES) {
						Quantum = 1;
						// Troca de contexto pra abrir espaco para operacao ES
						OperacaoES aux = (OperacaoES) processoAtual.codigo[processoAtual.contadorDePrograma];
						processosEsperando.add(processoAtual.idProcesso);
						adicionaFilaES(aux.idDispositivo, processoAtual);
						pegaProxPronto();
					} else {
						// Vai pra proxima execução
						operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
						processoAtual.contadorDePrograma = processoAtual.contadorDePrograma + 1;
						Quantum++;
					}
				}
			}
		}

		else {
			if (!prontoList.isEmpty()) {
				processoAtual = prontoList.get(0);
				prontoList.remove(0);
				operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
				processoAtual.contadorDePrograma = processoAtual.contadorDePrograma + 1;
			} else {
				processoAtual = null;
			}
		}

	}

	protected void aa() {

		if (processoAtual != null) {
			if (processoAtual.codigo.length == processoAtual.contadorDePrograma) {
				// Termina o processo atual
				processosTerminados.add(processoAtual.idProcesso);
				if (!prontoList.isEmpty()) {
					trocaContexto(processoAtual, prontoList.get(0));
					processoAtual = prontoList.get(0);
					operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
					prontoList.remove(0);
				} else {
					processoAtual = null;
				}

			} else if (!prontoList.isEmpty() && prontoList.get(0).Chute < processoAtual.Chute) {
				prontoList.add(processoAtual);
				processoAtual = prontoList.get(0);
				prontoList.remove(0);

			} else {
				if (processoAtual.codigo[processoAtual.contadorDePrograma] instanceof OperacaoES) {

					processoAtual.Chute = (processoAtual.Chute + processoAtual.contadorBurst) / 2;
					processoAtual.contadorBurst = 0;
					// Troca de contexto pra abrir espaco para operacao ES
					OperacaoES aux = (OperacaoES) processoAtual.codigo[processoAtual.contadorDePrograma];
					processosEsperando.add(processoAtual.idProcesso);
					adicionaFilaES(aux.idDispositivo, processoAtual);
					if (!prontoList.isEmpty()) {
						trocaContexto(processoAtual, prontoList.get(0));
						prontoList.remove(0);
					} else {
						processoAtual = null;
					}
				} else {
					// Vai pra proxima execução
					operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
					processoAtual.contadorDePrograma = processoAtual.contadorDePrograma + 1;
					processoAtual.contadorBurst = processoAtual.contadorBurst + 1;
				}
			}
		}

	}

	protected void fifoSjf() {
		if (processoAtual != null) {
			if (processoAtual.codigo.length == processoAtual.contadorDePrograma) {
				// Termina o processo atual
				processosTerminados.add(processoAtual.idProcesso);
				pegaProxPronto();

			} else {
				if (processoAtual.codigo[processoAtual.contadorDePrograma] instanceof OperacaoES) {
					processoAtual.Chute = (processoAtual.Chute + processoAtual.contadorBurst) / 2;
					processoAtual.contadorBurst = 0;
					// Troca de contexto pra abrir espaco para operacao ES
					OperacaoES aux = (OperacaoES) processoAtual.codigo[processoAtual.contadorDePrograma];
					processosEsperando.add(processoAtual.idProcesso);
					adicionaFilaES(aux.idDispositivo, processoAtual);
					pegaProxPronto();
				} else {
					// Vai pra proxima execução
					operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
					processoAtual.contadorDePrograma = processoAtual.contadorDePrograma + 1;
					processoAtual.contadorBurst = processoAtual.contadorBurst + 1;
				}
			}
		}

		else {
			if (!prontoList.isEmpty()) {
				processoAtual = prontoList.get(0);
				prontoList.remove(0);
				operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
				processoAtual.contadorBurst = processoAtual.contadorBurst + 1;
				processoAtual.contadorDePrograma = processoAtual.contadorDePrograma + 1;
			} else {
				processoAtual = null;
			}
		}
	}

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
		int qtdProcesso = idProcessosTerminados().size();
		return totalTempoEspera / qtdProcesso;
	}

	@Override
	protected int tempoRespostaMedio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int tempoRetornoMedio() {
		int qtdProcesso = idProcessosTerminados().size();
		return totalTempoRetorno / qtdProcesso;
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
