#!/usr/bin/gnuplot

reset

set terminal pngcairo size 410,250 enhanced font 'Verdana,9'
set terminal pdf size 4, 2.5

# define axis
# remove border on top and right and set color to gray
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
# define grid
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12

# color definitions
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 2 # --- red
set style line 3 lc rgb '#0000FF' pt 3 ps 1 lt 1 lw 2 # --- blue
set style line 4 lc rgb '#FFFF00' pt 4 ps 1 lt 1 lw 2 # --- yellow

set key left top font "Helvetica, 14"

set xlabel 'Throughput (op/s)'
set ylabel 'Latency (ms)'
set xrange [620:7350]
set yrange [2:10]

set output 'lat_thr_sem_conf_v2.pdf'

plot 'parallel_w32.dat'   u 1:2 t 'Parallel, 32 workers'   w lp ls 2, \
     'parallel_w48.dat'   u 1:2 t 'Parallel, 48 workers'   w lp ls 3, \
     'parallel_w64.dat'   u 1:2 t 'Parallel, 64 workers'   w lp ls 4
