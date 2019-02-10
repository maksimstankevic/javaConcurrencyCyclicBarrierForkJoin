# javaConcurrencyCyclicBarrierForkJoin

Calculating factorial of 15000 with 1 to 4 threads using cyclic barrier:


Spawning 1 worker threads to compute 15000 numbers partialFactorial out of total: 15000
pool-1-thread-1 waiting for others to reach barrier.
pool-1-thread-1: Final result = 2.746599E56129
Completed in: 0.319 sec.

!!!!!! 2 times with one thread to account for butn-in

Spawning 1 worker threads to compute 15000 numbers partialFactorial out of total: 15000
pool-2-thread-1 waiting for others to reach barrier.
pool-2-thread-1: Final result = 2.746599E56129
Completed in: 0.171 sec.
Spawning 2 worker threads to compute 7500 numbers partialFactorial out of total: 15000
pool-3-thread-1 waiting for others to reach barrier.
pool-3-thread-2 waiting for others to reach barrier.
pool-3-thread-2: Final result = 2.746599E56129
Completed in: 0.139 sec.
Spawning 3 worker threads to compute 5000 numbers partialFactorial out of total: 15000
pool-4-thread-1 waiting for others to reach barrier.
pool-4-thread-3 waiting for others to reach barrier.
pool-4-thread-2 waiting for others to reach barrier.
pool-4-thread-2: Final result = 2.746599E56129
Completed in: 0.062 sec.
Spawning 4 worker threads to compute 3750 numbers partialFactorial out of total: 15000
pool-5-thread-1 waiting for others to reach barrier.
pool-5-thread-2 waiting for others to reach barrier.
pool-5-thread-3 waiting for others to reach barrier.
pool-5-thread-4 waiting for others to reach barrier.
pool-5-thread-4: Final result = 2.746599E56129
Completed in: 0.086 sec.


************************************************

ENCODING/DECODING large alphanumeric files ~100MB (every char +/- int 3 - seed) using RecursiveAction based ForkJoinPool
with 1-4 threads


Thread name: ForkJoinPool-1-worker-1 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/4.txt
Thread name: ForkJoinPool-1-worker-1 FINISHED!!!!!!!!
Thread name: ForkJoinPool-1-worker-1 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/5.txt
Thread name: ForkJoinPool-1-worker-1 FINISHED!!!!!!!!
Thread name: ForkJoinPool-1-worker-1 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/7.txt
Thread name: ForkJoinPool-1-worker-1 FINISHED!!!!!!!!
Thread name: ForkJoinPool-1-worker-1 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/9.txt
Thread name: ForkJoinPool-1-worker-1 FINISHED!!!!!!!!
Completed in: 7.846 sec.
Thread name: ForkJoinPool-2-worker-1 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/4.txt
Thread name: ForkJoinPool-2-worker-0 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/7.txt
Thread name: ForkJoinPool-2-worker-1 FINISHED!!!!!!!!
Thread name: ForkJoinPool-2-worker-1 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/5.txt
Thread name: ForkJoinPool-2-worker-0 FINISHED!!!!!!!!
Thread name: ForkJoinPool-2-worker-0 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/9.txt
Thread name: ForkJoinPool-2-worker-0 FINISHED!!!!!!!!
Thread name: ForkJoinPool-2-worker-1 FINISHED!!!!!!!!
Completed in: 4.085 sec.
Thread name: ForkJoinPool-3-worker-2 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/7.txt
Thread name: ForkJoinPool-3-worker-3 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/5.txt
Thread name: ForkJoinPool-3-worker-1 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/4.txt
Thread name: ForkJoinPool-3-worker-3 FINISHED!!!!!!!!
Thread name: ForkJoinPool-3-worker-3 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/9.txt
Thread name: ForkJoinPool-3-worker-1 FINISHED!!!!!!!!
Thread name: ForkJoinPool-3-worker-2 FINISHED!!!!!!!!
Thread name: ForkJoinPool-3-worker-3 FINISHED!!!!!!!!
Completed in: 4.089 sec.
Thread name: ForkJoinPool-4-worker-1 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/4.txt
Thread name: ForkJoinPool-4-worker-0 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/9.txt
Thread name: ForkJoinPool-4-worker-2 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/7.txt
Thread name: ForkJoinPool-4-worker-3 ENCODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/data/5.txt
Thread name: ForkJoinPool-4-worker-0 FINISHED!!!!!!!!
Thread name: ForkJoinPool-4-worker-3 FINISHED!!!!!!!!
Thread name: ForkJoinPool-4-worker-1 FINISHED!!!!!!!!
Thread name: ForkJoinPool-4-worker-2 FINISHED!!!!!!!!
Completed in: 3.348 sec.
Thread name: ForkJoinPool-5-worker-1 DECODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/dataOut/4.txt
Thread name: ForkJoinPool-5-worker-1 FINISHED!!!!!!!!
Thread name: ForkJoinPool-5-worker-1 DECODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/dataOut/5.txt
Thread name: ForkJoinPool-5-worker-1 FINISHED!!!!!!!!
Thread name: ForkJoinPool-5-worker-1 DECODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/dataOut/7.txt
Thread name: ForkJoinPool-5-worker-1 FINISHED!!!!!!!!
Thread name: ForkJoinPool-5-worker-1 DECODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/dataOut/9.txt
Thread name: ForkJoinPool-5-worker-1 FINISHED!!!!!!!!
Completed in: 7.181 sec.
Thread name: ForkJoinPool-6-worker-1 DECODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/dataOut/4.txt
Thread name: ForkJoinPool-6-worker-3 DECODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/dataOut/5.txt
Thread name: ForkJoinPool-6-worker-2 DECODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/dataOut/7.txt
Thread name: ForkJoinPool-6-worker-0 DECODING /mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/dataOut/9.txt
Thread name: ForkJoinPool-6-worker-0 FINISHED!!!!!!!!
Thread name: ForkJoinPool-6-worker-1 FINISHED!!!!!!!!
Thread name: ForkJoinPool-6-worker-3 FINISHED!!!!!!!!
Thread name: ForkJoinPool-6-worker-2 FINISHED!!!!!!!!
Completed in: 3.204 sec.
