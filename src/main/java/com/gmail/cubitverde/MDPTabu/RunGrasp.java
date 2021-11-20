package com.gmail.cubitverde.MDPTabu;

import java.util.List;

public class RunGrasp implements Runnable {
    ObjInstanceSheet instanceSheet;

    public void run() {
        ObjSolution bestSolution = new ObjSolution(instanceSheet.getFileName());
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 1000 * MDPTabu.runningTime;
        int iterations = 0;

        System.out.println(" ");
        System.out.println("Starting GRASP for sheet: " + instanceSheet.getName());
        System.out.println("Running time (seconds): " + MDPTabu.runningTime + " - Alpha: " + MDPTabu.alphaGrasp);

        while (System.currentTimeMillis() < endTime) {
            ObjSolution solution = FunMain.MdpGrasp(instanceSheet);
            ObjSolution improvedSolution = FunMain.ImproveSolution(instanceSheet, solution);

            if (improvedSolution.getValue() > bestSolution.getValue()) {
                bestSolution = improvedSolution;
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
    }

    public RunGrasp(ObjInstanceSheet setUpSheet) {
        this.instanceSheet = new ObjInstanceSheet(setUpSheet);
    }
}
