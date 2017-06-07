# -*- coding: utf-8 -*-
import json
import sys


# função para eliminar rotas que não podem mais crescer
def delet_rota_morta(rotas, r):  # é a iteração atual do while, as rotas devem ter tamanho r+2
    aux_elimin = list(rotas)
    l = 0
    for k in range(0, len(rotas)):
        tam = len(rotas[k])
        if (len(rotas[k]) < r + 2):
            aux_elimin.pop(k - l)
            l += 1
    return aux_elimin


# função para eliminar rotas que tem o mesmo nó destino (duplicadas)
def delet_rotas_confl(rotas):
    l = len(rotas[0])
    aux = list(rotas)
    aux2 = []
    for rt in rotas:
        aux1 = []
        for rota in aux:
            if (rota[l - 1] == rt[l - 1]):
                aux1.append(list(rota))
        aux2.append(list(aux1[0]))

    aux = list(aux2)
    for rt in aux2:
        while aux.count(rt) > 1:
            aux.remove(rt)
    return aux


def conta_rotas(rot):
    count = 0
    for rota in rotas:
        n = len(rota)
        if rota[n - 1] == node_destino:
            count += 1
    return count


if __name__ == "__main__":
    # Carregar arquivo com a rede em json
    topology_path = sys.argv[1];
    source_node = sys.argv[2];
    target_node = sys.argv[3];

    # pra testar
    """
    topology_path = 'rnp.txt'
    source_node = '4'
    target_node = '16'
    """

    node_origem = source_node
    node_destino = target_node

    f = open(topology_path, 'r')
    rede_json = f.read()
    f.close()

    teste_load = json.loads(rede_json)  # carrega o json

    """
  print 'rnp'
  print rede_json
  print 'xxxxxxxx'
  print teste_load['links']
  """

    read_links = list(teste_load['links'])

    v0 = []
    lista_nodes = []
    i = 0
    for link in read_links:
        id = 'node' + str(i)
        lista_nodes.append(str(link['node1']))
        lista_nodes.append(str(link['node2']))
        if link['node1'] == 'v0' or link['node2'] == 'v0':
            v0.append(str(link['node2']))
        i += 1

    aux_01 = set(lista_nodes)
    lista_nodes = list(aux_01)
    lista_nodes.sort()

    # print"nodes > %s"%lista_nodes
    # cria um link reverso para cada link existente
    rede = []
    for link in read_links:
        aux = link.copy()
        aux['node1'] = link['node2']
        aux['node2'] = link['node1']
        rede.append(aux)

    for link in rede:
        read_links.append(link)

    rede = []
    for node in lista_nodes:
        globals()[node] = [0, node]
        for link in read_links:
            if str(link['node1']) == node:
                globals()[node].append(str(link['node2']))
            if link['node2'] == node:
                globals()[node].append(str(link['node1']))
        rede.append(globals()[node])

    # Seção de roteamento
    # ********************************************

    node1 = globals()[node_origem]
    node1[0] = -1

    rotas = []
    for k in range(2, len(node1)):
        for link in read_links:
            if link['node1'] == int(node1[1]) and link['node2'] == int(node1[k]):
                delay = float(link['delay'])
                break
        rotas.append([delay, node1[1], node1[k]])
        globals()[node1[k]][0] = delay

    r = 0
    find = False
    for rota in rotas:
        n = len(rota)
        if rota[n - 1] == node_destino:
            find = True
            break
    print ' '
    rotas_concluidas = []
    while not (find):
        r += 1
        # eliminação de rotas que não podem mais crescer
        rotas = delet_rota_morta(rotas, r)
        rotas.sort()
        if not(rotas):
            break
        # eliminação de rotas que chegaram ao mesmo nó, fica a que tem o menor delay
        rotas = delet_rotas_confl(rotas)
        #print 'rotas2 %s' % (rotas)
        aux = []
        for rota in rotas:
            k = 0
            nvi = 0
            n = len(rota)
            delay_rota = rota[0]
            for viz in globals()[rota[n - 1]]:  # Analisar os vizinhos do último node da rota, r indica o índice do último elemento
                if k == 1:
                    base = viz  # node a partir do qual estão sendo testados os vizinhos
                if k > 1 and (rota[n-1] != node_destino):
                    for link in read_links:
                        if link['node1'] == int(base) and link['node2'] == int(viz):
                            delay = float(link['delay'])
                            break

                    if (globals()[viz][0] == 0 or globals()[viz][0] > (delay_rota + delay)) and rota.count(viz) == 0:
                        x = list(rota)
                        x.append(viz)
                        x[0] = delay_rota + delay
                        aux.append(list(x))
                        globals()[viz][0] == delay_rota + delay
                        if viz == node_destino:
                            rotas_concluidas.append(list(x))
                    # find = True
                k += 1
        if aux:
            for lis in aux:
                rotas.append(list(lis))

        if len(rotas_concluidas) > 4:
            find = True

    for rota in rotas_concluidas:
        n = len(rota)
        rota_enc = list(rota)
        rota_link = []
        rota_link.append(rota_enc[0])  # insere o delay
        for j in range(0, len(rota_enc) - 1):
            for link in read_links:
                if link['node1'] == int(rota_enc[j]) and link['node2'] == int(rota_enc[j + 1]) and j > 0:
                    delay = float(link['delay'])
                    rota_link.append(link['id'])
                    break
        print rota_link