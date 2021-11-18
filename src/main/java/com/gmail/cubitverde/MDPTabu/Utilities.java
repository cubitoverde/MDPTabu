package com.gmail.cubitverde.MDPTabu;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utilities {
    static void OnEnable() {
        MDPTabu.n = 500;
        MDPTabu.m = 25;
        MDPTabu.instanceNames = GetInstances();
        MDPTabu.instanceSheets = CreateInstancesMap();

    }

    static List<String> GetInstances() {
        List<String> instances = new ArrayList<String>();
        instances.add("Amparo.xlsx");
        instances.add("Borja.xlsx");
        instances.add("Daniel.xlsx");
        instances.add("Emilio.xlsx");
        instances.add("Jose.xlsx");
        instances.add("MariaJesus.xlsx");
        instances.add("Raquel.xlsx");
        instances.add("Virginia.xlsx");
        return instances;
    }

    static Map<String, ObjInstanceSheet> CreateInstancesMap() {
        Map<String, ObjInstanceSheet> instanceSheets = new HashMap<String, ObjInstanceSheet>();

        List<String> instances = MDPTabu.instanceNames;
        for (String fileName : instances) {
            ObjInstanceSheet instanceSheet = new ObjInstanceSheet(fileName);
            Integer[][] data = new Integer[MDPTabu.n][MDPTabu.n];

            FileInputStream file;
            Workbook workbook;
            Sheet sheet;
            try {
                file = new FileInputStream(new File("Amparo.xlsx"));
                workbook = new XSSFWorkbook(file);
                sheet = workbook.getSheetAt(0);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            for (int i = 0; i < MDPTabu.n; i++) {
                for (int j = 0; j < MDPTabu.n; j++) {
                    data[i][j] = (int) sheet.getRow(i).getCell(j).getNumericCellValue();
                }
            }
            instanceSheet.setData(data);
            instanceSheets.put(fileName, instanceSheet);
        }

        return instanceSheets;
    }
}
