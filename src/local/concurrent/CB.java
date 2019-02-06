package local.concurrent;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.*;



class CB {


    private CyclicBarrier cyclicBarrier;
    private List<BigInteger> partialResults
            = Collections.synchronizedList(new ArrayList<>());
    private Queue<String> ranges = new ConcurrentLinkedQueue<>();
    private int FACTORIAL_NUM;
    private int NUM_WORKERS;
    private long startTime;

    class VeryBusyMultiplyingThread implements Runnable {

        @Override
        public void run() {
            String thisThreadName = Thread.currentThread().getName();
            BigInteger partialResult;

            String [] startEnd = ranges.remove().split("-");
            int start = Integer.parseInt(startEnd[0]);
            int end = Integer.parseInt(startEnd[1]);

            partialResult = partialFactorial(start, end);

            partialResults.add(partialResult);

            try {
                System.out.println(thisThreadName
                        + " waiting for others to reach barrier.");
                cyclicBarrier.await();
            } catch (InterruptedException|BrokenBarrierException e) {
                // ...
            }
        }
    }

    class AggregatingThread implements Runnable {

        @Override
        public void run() {

            String thisThreadName = Thread.currentThread().getName();


            BigInteger totalFactorial = BigInteger.valueOf(1);

            int sum = 0;

            for (BigInteger threadResult : partialResults) {
                totalFactorial = totalFactorial.multiply(threadResult);
            }

            NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));
            String exp = formatter.format(totalFactorial);

            System.out.println(thisThreadName + ": Final result = " + exp);
//            System.out.println(thisThreadName + ": Final result = " + exp + " --- " + totalFactorial);
            double time = (System.currentTimeMillis() - startTime)/1000.0;
            System.out.println("Completed in: " + time + " sec.");

        }
    }

    private void runSimulation(int numWorkers, int factorialNum) throws InterruptedException {
        startTime = System.currentTimeMillis();

        partialResults.clear();

        NUM_WORKERS = numWorkers;
        FACTORIAL_NUM = factorialNum;
        int part = FACTORIAL_NUM / numWorkers;

        for (int i = 0; i < numWorkers; i++){
            int start = part * i + 1;
            int end = (i == numWorkers - 1) ? FACTORIAL_NUM : part * (i + 1);
            ranges.add("" + start + "-" + end);
        }

        cyclicBarrier = new CyclicBarrier(NUM_WORKERS, new AggregatingThread());

        System.out.println("Spawning " + NUM_WORKERS
                + " worker threads to compute "
                + part + " numbers partialFactorial out of total: " + FACTORIAL_NUM);

        ExecutorService processor = null;

        try {
            processor = Executors.newFixedThreadPool(NUM_WORKERS);
            for (int i = 0; i < NUM_WORKERS; i++) processor.submit(new VeryBusyMultiplyingThread());
        } finally {
            if (processor != null) processor.shutdown();
        }

        while(!processor.isTerminated()) {
            Thread.sleep(10);
        }



    }

    private static BigInteger partialFactorial(int start, int end) {
        BigInteger result = BigInteger.valueOf(1);

        for (long factor = start; factor <= end; factor++) {
            result = result.multiply(BigInteger.valueOf(factor));
        }

        return result;
    }


    public static void main(String[] args) throws InterruptedException{
        CB demo1 = new CB();
        CB demo2 = new CB();
        CB demo3 = new CB();
        CB demo4 = new CB();
        CB demo5 = new CB();

        demo1.runSimulation(1, 15000);
        demo1 = null;
        System.gc();
        demo2.runSimulation(1, 15000);
        demo2 = null;
        System.gc();
        demo3.runSimulation(2, 15000);
        demo3 = null;
        System.gc();
        demo4.runSimulation(3, 15000);
        demo4 = null;
        System.gc();
        demo5.runSimulation(4, 15000);
        demo5 = null;
        System.gc();




    }





}

