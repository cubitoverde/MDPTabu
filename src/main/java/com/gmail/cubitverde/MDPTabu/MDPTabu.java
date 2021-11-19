package com.gmail.cubitverde.MDPTabu;

import java.util.List;

public class MDPTabu {
    static int n; // Number of nodes
    static int m; // Number of selected elements
    static List<String> instanceNames; // Names of instance excel sheets
    static List<ObjInstanceSheet> instanceSheets; // Data of instance excel sheets
    static double alphaGrasp; // Alpha used in GRASP method

    public static void main(String args[]) {
        Utilities.OnEnable();

        for (ObjInstanceSheet instanceSheet : instanceSheets) {
            System.out.println(" ");
            System.out.println(" " + instanceSheet.getName() + " - " + instanceSheet.getFileName());

            ObjSolution solution = FunMain.MdpGrasp(instanceSheet);

            System.out.print(" -> Solution:");
            List<Integer> elements = solution.getElements();
            for (int i = 0; i < elements.size(); i++) {
                System.out.print(" " + elements.get(i));
            }
            System.out.println(" - Value: " + solution.getValue());

            ObjSolution improvedSolution = FunMain.ImproveSolution(instanceSheet, solution);

            System.out.print(" -> Improved:");
            List<Integer> improvedElements = improvedSolution.getElements();
            for (int i = 0; i < improvedElements.size(); i++) {
                System.out.print(" " + improvedElements.get(i));
            }
            System.out.println(" - Value: " + improvedSolution.getValue());
        }
    }
}
