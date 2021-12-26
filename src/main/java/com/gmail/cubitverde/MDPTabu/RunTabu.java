package com.gmail.cubitverde.MDPTabu;

import java.util.LinkedHashMap;
import java.util.List;

public class RunTabu implements Runnable {
    ObjInstanceSheet instanceSheet;

    public void run() {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 1000 * MDPTabu.runningTime;
        int iterations = 0;
        LinkedHashMap<Double, Integer> improvementRate = new LinkedHashMap<Double, Integer>();

        System.out.println(" ");
        System.out.println("Starting Tabu for sheet: " + instanceSheet.getName());
        System.out.println("Running time (seconds): " + MDPTabu.runningTime + " - Tenure: " + MDPTabu.tabuTenure);

        ObjSolution bestSolution = FunMain.ImproveSolution(instanceSheet, FunMain.MdpGrasp(instanceSheet));
        improvementRate.put(0.0, bestSolution.getValue());
        ObjSolution lastSolution = new ObjSolution(bestSolution);

        while (System.currentTimeMillis() < endTime) {
            ObjSolution newSolution = FunMain.TabuMethod(instanceSheet, lastSolution);
            lastSolution = newSolution;

            if (newSolution.getValue() > bestSolution.getValue()) {
                bestSolution = newSolution;

                if (MDPTabu.saveImprovement) {
                    improvementRate.put(((double) (System.currentTimeMillis() - startTime)) / (endTime - startTime) * MDPTabu.runningTime, newSolution.getValue());
                }
            }
            iterations += newSolution.getElements().size();
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

    public RunTabu(ObjInstanceSheet setUpSheet) {
        this.instanceSheet = new ObjInstanceSheet(setUpSheet);
    }
}
