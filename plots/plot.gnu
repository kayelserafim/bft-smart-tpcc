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
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 2 # --- green

set key right top font "Helvetica, 14"

set xlabel 'Throughput'
set ylabel 'Latency (ms)'
set xrange [0:150]
set yrange [0:50]

set output 'lat_thr_M12.pdf'

plot 'sequential_plot.dat' u 1:2 t 'Sequential' w lp ls 1, \
     'parallel_plot.dat'   u 1:2 t 'Parallel'   w lp ls 2
