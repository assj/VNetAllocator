# -*- coding: utf-8 -*-
import string
import json
import random
delay_min = 50
delay_max = 100
vel_min = 0.5
vel_max = 2
m_min = 0.5 #ram
m_max = 5   #ram
p_min = 0.3 #processador
p_max = 3   #processador
s_min = 10  #disco
s_max = 100 #disco
n_min = 3   #número de máquinas
n_max = 6   #número de máquinas

maquina = {'id':0, 'amountOfMemory':1, 'processingCapacity':1, 'amountOfDisk':1}


def set_capacidade(node):
    node['amountOfMemory'] = random.uniform(m_min,m_max)
    node['processingCapacity'] = random.uniform(p_min,p_max)
    node['amountOfDisk'] = random.uniform(s_min,s_max)


def id_maquinas(nodes):
    lista_id = []
    for maq in nodes:
        lista_id.append(maq['id'])
    return lista_id


def set_links(nodes):
    id_maqs = id_maquinas(nodes)
    links = []                                   # links = {'source':'0','target':'1','velocidade':1,'delay':2}
    if len(id_maqs) == 3:
        for maq in id_maqs:
            n = random.randint(1,len(nodes))
            targets = random.sample(id_maqs, n)
            if targets.count(maq) > 0:
                targets.remove(maq)
            for targ in targets:
                aux = [maq,targ]
                aux.sort()                                    # Ordenar os ids dos pontos alfabeticamente
                links.append([aux[0],aux[1]])                 # ids dos pontos, delay máximo e velocidade
        aux_links = list(links)
        for link in links:
            while aux_links.count(link) > 1:
                aux_links.remove(link)
        links = list(aux_links)

        links.sort()
        for link in links:
            delay = random.randint(delay_min, delay_max)
            vel = random.uniform(vel_min, vel_max)
            link.append(vel)
            link.append(delay)

        aux_json = {'node1': 0, 'node2': 1, 'velocidade': 1, 'delay': 2}
        link_json = []
        for link in links:
            aux_json['node1'] = link[0]
            aux_json['node2'] = link[1]
            aux_json['velocidade'] = link[2]
            aux_json['delay'] = link[3]
            link_json.append(aux_json.copy())
        return link_json

    if len(id_maqs) > 3:
        if len(id_maqs)%2 == 0:
            n_min_link = 2.5*len(id_maqs) - 7        # número de links que garante a não existência de ilhas
        else:
            n_min_link = 4.5*len(id_maqs) - 13.5     # número de links que garante a não existência de ilhas
        i = 0
        while len(links) < n_min_link:
            links = []
            for maq in id_maqs:
                n = random.randint(1, len(nodes))
                targets = random.sample(id_maqs, n)
                if targets.count(maq) > 0:
                    targets.remove(maq)
                for targ in targets:
                    aux = [maq, targ]
                    aux.sort()  # Ordenar os ids dos pontos alfabeticamente
                    links.append([aux[0], aux[1]])  # ids dos pontos, delay máximo e velocidade
            aux_links = list(links)
            for link in links:
                while aux_links.count(link) > 1:
                    aux_links.remove(link)
            links = list(aux_links)

            links.sort()
            for link in links:
                delay = random.randint(delay_min, delay_max)
                vel = random.uniform(vel_min, vel_max)
                link.append(vel)
                link.append(delay)

            i+=1
        aux_json = {'node1': '0', 'node2': '1', 'velocidade': 1, 'delay': 2}
        link_json = []
        for link in links:
            aux_json['node1'] = link[0]
            aux_json['node2'] = link[1]
            aux_json['velocidade'] = link[2]
            aux_json['delay'] = link[3]
            link_json.append(aux_json.copy())
        return link_json


n_req = 10       # número de requisições a serem geradas, cada uma será salva em um arquivo
for n in  range(0,n_req):
    nodes = []
    i = random.randint(n_min, n_max)     #Numero de maquinas entre 3 e 5
    for k in range(0,i):
        id = k+1
        node = maquina.copy()
        node['id'] = id
        set_capacidade(node)
        nodes.append(node)

    links = set_links(nodes)

    net = []
    net.append(nodes)
    net.append(links)

    jason = json.dumps({'nodes':net[0],'links':net[1]})
    print jason

    '''
    filename = str(n)+'.txt'
    f = open(filename, "w")
    print >> f,jason
    f.close()
    '''