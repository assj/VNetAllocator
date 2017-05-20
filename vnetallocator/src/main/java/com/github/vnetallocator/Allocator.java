package com.github.vnetallocator;

import java.io.File;

public class Allocator
{
    /**
     * @param args [0] - Caminho para o arquivo .json com a topologia da rede
     */
    public static void main(String[] args)
    {
	// TODO Adicionar logs no sistema.

	if (args.length == 1)
	{
	    String jsonTopologyFilePath = args[0];
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
