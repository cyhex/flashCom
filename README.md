flashCom
========

Data transfer using build in flash light (torch) of a mobile device.
Reciving end is an Arduino with sampling rate of 1kHz 

Pulse timing:

    |-----------|  40ms     |-----------| 80 ms                |-----------|
    | 40ms      |  low (0)  | 40 ms     | high (1)             | 40 ms     |
    |           |-----------|           |----------------------|           |


Transfer rate and limitations
-----------------------------
even at this rates, android produces non consistent timing, thus creating errors in the data stream.

This was expected, as Android OS is not build for Realtime, despite the fact that build in nexus 4 LED controller (lm3551) should be able to do 1.25MHz ?

http://www.ti.com/product/lm3551

Todo
----

Access that lm3551 directly ?? :)
