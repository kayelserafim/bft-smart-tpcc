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
set style line 5 lc rgb '#800080' pt 5 ps 1 lt 1 lw 2 # --- purple
set style line 6 lc rgb '#FF00FF' pt 6 ps 1 lt 1 lw 2 # --- Magenta
set style line 7 lc rgb '#00CC00' pt 7 ps 1 lt 1 lw 2 # --- green
set style line 8 lc rgb '#FF0000' pt 8 ps 1 lt 1 lw 2 # --- red

set key center top font "Helvetica, 10"

set xlabel 'Throughput (op/s)'
set ylabel 'Latency (ms)'
set xrange [250:1800]
set yrange [2:35]

set output 'lat_thr_com_conf_5_perc.pdf'

plot 'sequential_plot.dat' u 1:2 t 'Sequential' w lp ls 1, \
     'parallel_w02.dat'   u 1:2 t 'Parallel, 02 workers'   w lp ls 2, \
     'parallel_w04.dat'   u 1:2 t 'Parallel, 04 workers'   w lp ls 3, \
     'parallel_w06.dat'   u 1:2 t 'Parallel, 06 workers'   w lp ls 4, \
     'parallel_w08.dat'   u 1:2 t 'Parallel, 08 workers'   w lp ls 5, \
     'parallel_w16.dat'   u 1:2 t 'Parallel, 16 workers'   w lp ls 6, \
     'parallel_w32.dat'   u 1:2 t 'Parallel, 32 workers'   w lp ls 7, \
     'parallel_w64.dat'   u 1:2 t 'Parallel, 64 workers'   w lp ls 8