package com.gmail.cubitverde.MDPTabu;

import java.util.List;

public class RunTabu implements Runnable {
    ObjInstanceSheet instanceSheet;

    public void run() {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 1000 * MDPTabu.runningTime;
        int iterations = 0;

        System.out.println(" ");
        System.out.println("Starting Tabu for sheet: " + instanceSheet.getName());
        System.out.println("Running time (seconds): " + MDPTabu.runningTime + " - Alpha: " + MDPTabu.alphaGrasp + " - Tenure: " + MDPTabu.tabuTenure);

        ObjSolution bestSolution = FunMain.ImproveSolution(instanceSheet, FunMain.MdpGrasp(instanceSheet));
        ObjSolution lastSolution = new ObjSolution(bestSolution);

        while (System.currentTimeMillis() < endTime) {
            ObjSolution newSolution = FunMain.TabuMethod(instanceSheet, lastSolution);
            lastSolution = newSolution;

            if (newSolution.getValue() > bestSolution.getValue()) {
                bestSolution = newSolution;
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
    }

    public RunTabu(ObjInstanceSheet setUpSheet) {
        this.instanceSheet = new ObjInstanceSheet(setUpSheet);
    }
}
