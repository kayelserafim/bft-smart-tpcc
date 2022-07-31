set key right top font "Helvetica, 14"
set xlabel "Throughput (kops/sec)"
set ylabel "Latency (ms)"
set yrange [0:20]
set xrange [0:350]
set ytics (1,3,5,10,15,20)
set xtics (50,100,150,200,250,300)


set terminal pdf size 4, 2.5

#set x2tics (60,120,180,240)


set xlabel font "Helvetica,16"
set ylabel font "Helvetica,16"
set xtics font "Helvetica, 14" 
set ytics font "Helvetica, 14" 

set grid ytics xtics lt 0 lw 1 lc 0

#set grid x2tics lt 0 lw 2 lc 0

set output 'lat_thr_M12.pdf'

#set style line 1 lc rgb '#000000' lt 1 lw 1.5 pt 7 ps 1
#set style line 2 lc rgb '#AA0000' lt 5 lw 0.5 pt 7 ps 1

plot "static_12T_M" using 1:2 title "early, 12 workers" with linespoints ls 6, \
     "dynamic_12T_M" using 1:2 title "late, 12 workers" with linespoints ls 8
