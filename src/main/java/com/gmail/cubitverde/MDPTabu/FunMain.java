package com.gmail.cubitverde.MDPTabu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunMain {
    static ObjSolution MdpGrasp(ObjInstanceSheet sheet) {
        ObjSolution solution = new ObjSolution(sheet.getFileName());
        List<Integer> elements = new ArrayList<Integer>();

        int bestI = (int) (Math.random() * MDPTabu.n);
        elements.add(bestI);
        solution.setElements(elements);

        Map<Integer, Integer> contributions = new HashMap<Integer, Integer>();
        Utilities.UpdateContributions(contributions, sheet, bestI);

        for (int j = 1; j < MDPTabu.m; j++) {
            Utilities.AddNewElement(contributions, solution);
            elements = solution.getElements();
            Utilities.UpdateContributions(contributions, sheet, elements.get(j));
            for (int k = 0; k < elements.size(); k++) {
                contributions.put(k, 0);
            }
        }

        return solution;
    }

    static ObjSolution ImproveSolution(ObjInstanceSheet sheet, ObjSolution oldSolution) {
        ObjSolution solution = new ObjSolution(oldSolution);



        return solution;
    }
}
