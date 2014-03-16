import serial
import time
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation
from collections import defaultdict
from matplotlib.lines import Line2D

ser = serial.Serial('/dev/ttyACM2',115200,timeout=1)
sample_len = 10

fig = plt.figure()
ax0 = fig.add_subplot(1,1,1) # one row, one column, first plot
ax0.set_ylabel(('value / time'))
ax0.set_ylim(0, 250)
plotter, = ax0.plot([0]*sample_len,color='red')
ax0.add_line(plotter)


def update(data):
    plotter.set_ydata(data['plot1'])
    return plotter

def readLine(data):
    line = ser.readline()

    line  = line.strip()
  
    if not line.startswith('plot'):
        return data
    
    bits = line.split(":")
    if len(bits) == 2:
        try:
            data['plot1'].append(float(bits[1]))
        except Exception, e:
            return data
        
    return data

def data_gen1():
    data = defaultdict(list)
    while 1:

        try:
            data = readLine(data)
        except Exception, e:
            continue
        
        if len(data['plot1']) == sample_len + 1 :
            data['plot1'].pop(0)
            yield data
        
   
ani = animation.FuncAnimation(fig, update, data_gen1, interval=10)
plt.show()

