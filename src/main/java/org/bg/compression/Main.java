package org.bg.compression;

import org.bg.compression.compressors.AbstractCompressorDecompressor;
import org.bg.compression.compressors.CompressorFactory;
import org.bg.compression.compressors.CompressorType;
import org.bg.compression.compressors.RunResult;
import org.bg.compression.datatype.Bill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String args[]) {
        Comparator<Map.Entry<AbstractCompressorDecompressor<Bill>, RunResult>> c = new Comparator<Map.Entry<AbstractCompressorDecompressor<Bill>, RunResult>>() {
            @Override
            public int compare(Map.Entry<AbstractCompressorDecompressor<Bill>, RunResult> o1, Map.Entry<AbstractCompressorDecompressor<Bill>, RunResult> o2) {
                return Float.compare(o1.getValue().getAvgRuntime(), o2.getValue().getAvgRuntime());
            }
        };

        HashMap<Integer, CompressorType> bestCompressorForRetries = new HashMap<>();

        HashMap<AbstractCompressorDecompressor<Bill>, RunResult> compressorToPerformance = new HashMap<>();

        ArrayList<Integer> repeats = new ArrayList<>();
        repeats.add(100);
        repeats.add(1000);
        repeats.add(10000);
//        repeats.add(100000);
//        repeats.add(1000000);
//        repeats.add(10000000);
//        repeats.add(100000000);
//        repeats.add(1000000000);
//        repeats.add(1000000000);

        for (CompressorType value : CompressorType.values()) {
            compressorToPerformance.put(CompressorFactory.getCompressor(value), new RunResult());
        }

        for (int i = 0; i < repeats.size(); i++) {
            int numberOfRepeats = repeats.get(i);

            for (Map.Entry<AbstractCompressorDecompressor<Bill>, RunResult> entry : compressorToPerformance.entrySet()) {
                entry.setValue(getRunResultForCompressor(entry.getKey(), numberOfRepeats));
            }

            System.out.println("================================");
            compressorToPerformance.entrySet().stream().sorted(c).forEach(abstractCompressorDecompressorRunResultEntry -> {
                System.out.println(abstractCompressorDecompressorRunResultEntry.getKey().getCompressorType() + ": " + abstractCompressorDecompressorRunResultEntry.getValue());
            });

            Map.Entry<AbstractCompressorDecompressor<Bill>, RunResult> firstValue =
                    compressorToPerformance.entrySet().stream().sorted(c).findFirst().get();
            bestCompressorForRetries.put(numberOfRepeats, firstValue.getKey().getCompressorType());
        }

        System.out.println("=============");
        System.out.println("Total: ");
        bestCompressorForRetries.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(integerCompressorTypeEntry ->
                        System.out.println(integerCompressorTypeEntry.getKey() + ": "
                                + integerCompressorTypeEntry.getValue()));
    }

    private static RunResult getRunResultForCompressor(AbstractCompressorDecompressor<Bill> compressorDecompressor, int numberOfRepeats) {
        long totalTime = 0;
        int errorCounter = 0;
        for (int i = 0; i < numberOfRepeats; i++) {
            Bill b1 = new Bill("176BU" + i, "Abhishek Gupta" + i);
            long startTime = System.currentTimeMillis();
            try {
                // Define the compressor
                byte[] compressedValue = compressorDecompressor.compress(b1);
                Bill uncompressedValue = compressorDecompressor.uncompress(compressedValue);
                if (!uncompressedValue.equals(b1)) {
                    System.out.println("ERROR");
                }

                long endTime = System.currentTimeMillis();
                long totalMillis = endTime - startTime;
                totalTime += totalMillis;
                if (totalMillis > 2) {
//                    System.out.println("Error, took too much time, took: " + totalMillis + ", iteration num: " + i);
                    errorCounter++;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        return new RunResult(numberOfRepeats, errorCounter, (float) totalTime / (float) numberOfRepeats, ((float) errorCounter / (float) numberOfRepeats) * 100);
    }


}
