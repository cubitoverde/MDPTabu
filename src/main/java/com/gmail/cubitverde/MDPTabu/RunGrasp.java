package com.gmail.cubitverde.MDPTabu;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RunGrasp implements Runnable {
    ObjInstanceSheet instanceSheet;

    public void run() {
        ObjSolution bestSolution = new ObjSolution(instanceSheet.getFileName());
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 1000 * MDPTabu.runningTime;
        int iterations = 0;
        LinkedHashMap<Double, Integer> improvementRate = new LinkedHashMap<Double, Integer>();

        System.out.println(" ");
        System.out.println("Starting GRASP for sheet: " + instanceSheet.getName());
        System.out.println("Running time (seconds): " + MDPTabu.runningTime + " - Alpha: " + MDPTabu.alphaGrasp);

        while (System.currentTimeMillis() < endTime) {
            ObjSolution solution = FunMain.MdpGrasp(instanceSheet);
            ObjSolution improvedSolution = FunMain.ImproveSolution(instanceSheet, solution);

            if (improvedSolution.getValue() > bestSolution.getValue()) {
                bestSolution = improvedSolution;

                if (MDPTabu.saveImprovement) {
                    improvementRate.put(((double) (System.currentTimeMillis() - startTime)) / (endTime - startTime) * MDPTabu.runningTime, improvedSolution.getValue());
                }
            }
            iterations++;
        }

        System.out.println(" ");
        System.out.println("" + instanceSheet.getName() + " - " + instanceSheet.getFileName() + " - Iterations: " + iterations);
        System.out.print(" -> Solution:");
        List<Integer> elements = bestSolution.getElements();
        for (int i = 0; i < elements.size(); i++) {
            System.out.print(" " + elements.get(i));
        }
        System.out.println(" - Value: " + bestSolution.getValue());

        if (MDPTabu.saveImprovement) {
            System.out.print(" -> Plotting x:");
            for (double x : improvementRate.keySet()) {
                System.out.print(" " + x);
            }
            System.out.print(" " + MDPTabu.runningTime);
            System.out.println(" ");
            System.out.print(" -> Plotting values:");
            for (double x : improvementRate.keySet()) {
                System.out.print(" " + improvementRate.get(x));
            }
            System.out.print(" " + bestSolution.getValue());
            System.out.println(" ");
        }
    }

    public RunGrasp(ObjInstanceSheet setUpSheet) {
        this.instanceSheet = new ObjInstanceSheet(setUpSheet);
    }
}
