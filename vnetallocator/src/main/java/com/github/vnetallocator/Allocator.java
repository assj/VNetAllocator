package com.github.vnetallocator;

import java.io.File;

public class Allocator
{
    /**
     * @param args [0] - Tamanho da população
     * @param args [1] - Taxa de "cross-over" (0 - 1)
     * @param args [2] - Taxa de mutação (0 - 1)
     * @param args [3] - Taxa de seleção para reprodução (0 - 1)
     * @param args [4] - Caminho para o arquivo .json com a topologia da rede
     */
    public static void main(String[] args)
    {
	// TODO Adicionar logs no sistema.

	if (args.length == 5)
	{
	    int populationSize = Integer.parseInt(args[0]);
	    double crossingOverRate = Double.parseDouble(args[1]);
	    double mutationRate = Double.parseDouble(args[2]);
	    double reproductionSelectionRate = Double.parseDouble(args[3]);
	    String jsonTopologyFilePath = args[4];
	    File jsonTopologyFile = new File(jsonTopologyFilePath);

	    if (jsonTopologyFile.exists() && jsonTopologyFile.isFile())
	    {
		// TODO Ler o arquivo .json e converter para o formato de dados
		// usado

		// TODO Rodar o simulador de requisições (retorno - string no
		// formato json)

		// TODO Alocar cada requisição

		// TODO [OPICIONAL] Criar uma interface que mostre as alocações
		// em tempo real
	    }
	    else
	    {
		// TODO Log de erro - arquivo inválido
	    }
	}
	else
	{
	    // TODO Log de erro - quantidade de parâmetros inválida
	}
    }
}
