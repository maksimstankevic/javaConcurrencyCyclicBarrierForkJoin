package local.concurrent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


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

    class ForkJoinEncryptDecrypt extends RecursiveAction {

        private int start;
        private int end;
        private int seed;
        private String in;
        private String out;
        private List<Path> files;

        public ForkJoinEncryptDecrypt(int start, int end, List<Path> files, int seed, String in, String out) {
            this.start = start;
            this.end = end;
            this.seed = seed;
            this.in = in;
            this.out = out;
            this.files = files;
        }

        protected void compute() {
            if (end - start <= 1) {

                String fileToWrite = (files.get(start)).toString().replace (in, out);
                StringBuilder accumulator = new StringBuilder();

                System.out.println("Thread name: " + Thread.currentThread().getName() + ((seed > 0) ? " ENCODING " + (files.get(start)).toString(): " DECODING " + (files.get(start)).toString()));

                try (PrintWriter writer = new PrintWriter(fileToWrite)) {


                    try {
                        Files.lines(files.get(start))
                                .map(s -> {
                                    for (int i = 0; i < s.length(); i++) {

                                        accumulator.append((char) (s.charAt(i) + seed));

                                    }
                                    writer.println(accumulator.toString());
                                    accumulator.setLength(0);
                                    return null;
                                })
                                .forEach(s -> {return;});
                    } catch (IOException e) {
                        System.out.println("COULD NOT READ" + files.get(start));
                    }

                    writer.flush();

                    System.out.println("Thread name: " + Thread.currentThread().getName() + " FINISHED!!!!!!!!");


                } catch (FileNotFoundException e) {
                    System.out.println("ISSUES OPENING WRITER FOR: " + fileToWrite + " !!!!!!!!!!!");
                }
            } else {

                int middle = start + ((end - start) / 2);
                //System.out.println("start:" + start + " middle:" + middle + " end:" + end);
                invokeAll(new ForkJoinEncryptDecrypt(start, middle, files, seed, in, out), new ForkJoinEncryptDecrypt(middle, end, files, seed, in, out));

            }

        }
    }



    private void encryptDecryptWithForkJoin(int parallelism, String in, int seed, String out){


        List<Path> files = null;
        Path data = Paths.get("/mnt/OS/Users/adm/IdeaProjects/javaConcurrencyCyclicBarrierForkJoin/" + in);


        try {
            files = Files.walk(data, 1)
                    .filter(s -> s.toString().contains(".txt"))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {

        }



//        if (files == null) return;
//
//
//        files.forEach(System.out::println);


        ForkJoinTask<?> task = new ForkJoinEncryptDecrypt(0, files.size(), files, seed, in, out);
        ForkJoinPool pool = new ForkJoinPool(parallelism);
        startTime = System.currentTimeMillis();
        pool.invoke(task);
        pool.shutdown();
        boolean result = false;
        try {
            result = pool.awaitTermination(10L, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("POOL AWAITTERMINATION INTERRUPTED!!!!!!!!1");
        }
        if (result) {
            double time = (System.currentTimeMillis() - startTime)/1000.0;
            System.out.println("Completed in: " + time + " sec.");
        } else System.out.println("10 MINUTES WAS NOT ENOUGH!!!!");


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


        ////////////////////////////////////////////////////


        //ENCODING(positive seed to add to every char in the file)

        CB demo6 = new CB();

        demo6.encryptDecryptWithForkJoin(1, "data", 3, "dataOut");

        demo6 = null;
        System.gc();


        CB demo7 = new CB();

        demo7.encryptDecryptWithForkJoin(2, "data", 3, "dataOut");

        demo7 = null;
        System.gc();


        CB demo8 = new CB();

        demo8.encryptDecryptWithForkJoin(3, "data", 3, "dataOut");

        demo8 = null;
        System.gc();


        CB demo9 = new CB();

        demo9.encryptDecryptWithForkJoin(4, "data", 3, "dataOut");

        demo9 = null;
        System.gc();


        /////////////////////////////////////////////////////

        //DECODING(negative seed to subtract from every char in the file)

        CB demo10 = new CB();

        demo10.encryptDecryptWithForkJoin(1, "dataOut", -3, "dataDecoded");

        demo10 = null;
        System.gc();


        CB demo11 = new CB();

        demo11.encryptDecryptWithForkJoin(4, "dataOut", -3, "dataDecoded");

        demo11 = null;
        System.gc();




    }





}

