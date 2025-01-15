# assessment-amqp
Entrega de assessment #8

TODO:
    En cada movimiento enviar el board + id de game por cola de mensajería que el consumer reciba y evalúe estado,
    si corresponde finalizarlo, lo hace y setea ganador si corresponde y fecha fin, sino ok. Además agregar controles 
    al iniciar partida de que no hayan partidas existentes sin finalizar.