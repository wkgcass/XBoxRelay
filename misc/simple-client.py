#!/usr/bin/env python3

import socket

HOST = '127.0.0.1'
PORT = 15992

XY_MIN = 10000
XY_MAX = 30000

T_MIN = 50
T_MAX = 200

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    data = ('{"@type":"configure","min":{"lsbX":' + str(XY_MIN) +
            ',"lsbY":' + str(XY_MIN) +
            ',"rsbX":' + str(XY_MIN) +
            ',"rsbY":' + str(XY_MIN) +
            ',"lt":' + str(T_MIN) +
            ',"rt":' + str(T_MIN) +
            '},"max":{"lsbX":' + str(XY_MAX) +
            ',"lsbY":' + str(XY_MAX) +
            ',"rsbX":' + str(XY_MAX) +
            ',"rsbY":' + str(XY_MAX) +
            ',"lt":' + str(T_MAX) +
            ',"rt":' + str(T_MAX) +
            '}}').encode()
    length = len(data)
    lenH = (length >> 8) & 0xff
    lenL = (length & 0xff)
    lenBytes = bytearray([lenH, lenL])
    s.sendall(lenBytes)
    s.sendall(data)
    while True:
        data = s.recv(16384)
        if data:
            print(f"Received {data!r}")
        else:
            break
