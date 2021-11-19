package com.gmail.cubitverde.MDPTabu;

import java.util.List;

public class RunGrasp implements Runnable {
    ObjInstanceSheet instanceSheet;

    public void run() {
        ObjSolution bestSolution = new ObjSolution(instanceSheet.getFileName());
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 1000 * MDPTabu.runningTime;

        System.out.println(" ");
        System.out.println(" Starting GRASP for sheet: " + instanceSheet.getName());

        while (System.currentTimeMillis() < endTime) {
            ObjSolution solution = FunMain.MdpGrasp(instanceSheet);
            ObjSolution improvedSolution = FunMain.ImproveSolution(instanceSheet, solution);

            if (improvedSolution.getValue() > bestSolution.getValue()) {
                bestSolution = improvedSolution;
            }
        }

        System.out.println(" ");
        System.out.println(" " + instanceSheet.getName() + " - " + instanceSheet.getFileName());
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
