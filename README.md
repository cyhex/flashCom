flashCom
========

Data transfer using build in flash light (torch) of a mobile device.
Reciving end is an Arduino with sampling rate of 1kHz 

Pulse timing:

    |-----------|  40ms     |-----------| 80 ms                |-----------|
    | 40ms      |  low (0)  | 40 ms     | high (1)             | 40 ms     |
    |           |-----------|           |----------------------|           |
