import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = System.getProperty("user.dir");

        String inputPath = path+"\\BreedingCalculatorInput.xlsx";
        System.out.println("Input Path : "+ inputPath);

        List<Breeding> preList= readBreedingListFromXlxs(inputPath);
        List<Breeding> postList = new ArrayList<>();
        for (Breeding breeding: preList){
            breeding.calTotalCost();
            breeding.calProfit();
            postList.add(breeding);
        }
        String outputPath = path+"\\BreedingCalculatorResult.xlsx";
        System.out.println("Output Path : "+ outputPath);
        generateResultXlxs(postList,outputPath);





    }

    static List<Breeding> readBreedingListFromXlxs(String path) throws IOException {
        List<Breeding> breedingList = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        int i=0;
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            if (i==0){
                i++;
                continue;
            }
            String species = "";
            double ethPrice = 0;
            double slpPrice =  0;
            Parent parent1 = new Parent();
            Parent parent2 = new Parent();
            ChildrenMarketValue childrenMarketValue = new ChildrenMarketValue();
            int numOfChild = 0;
            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex();
                switch (columnIndex) {
                    case 0:
                        species = nextCell.getStringCellValue();
                        break;
                    case 1:
                        ethPrice = nextCell.getNumericCellValue();
                        break;
                    case 2:
                        slpPrice = nextCell.getNumericCellValue();
                        break;
                    case 3:
                        parent1.setPurchasePrice(nextCell.getNumericCellValue());
                        break;
                    case 4:
                        parent1.setSellingPrice(nextCell.getNumericCellValue());
                        break;
                    case 5:
                        parent1.setBredCountBefore((int)nextCell.getNumericCellValue());
                        break;
                    case 6:
                        parent2.setPurchasePrice(nextCell.getNumericCellValue());
                        break;
                    case 7:
                        parent2.setSellingPrice(nextCell.getNumericCellValue());
                        break;
                    case 8:
                        parent2.setBredCountBefore((int)nextCell.getNumericCellValue());
                        break;
                    case 9:
                        childrenMarketValue.setExpectedAvePrice(nextCell.getNumericCellValue());
                        break;
                    case 10:
                        childrenMarketValue.setMaxPrice(nextCell.getNumericCellValue());
                        break;
                    case 11:
                        childrenMarketValue.setMinPrice(nextCell.getNumericCellValue());
                        break;
                    case 12:
                        numOfChild = (int)nextCell.getNumericCellValue();
                        break;
                }
             }
            Breeding breeding = new Breeding(species,parent1,parent2,childrenMarketValue,numOfChild,ethPrice,slpPrice);
            breedingList.add(breeding);
        }
        workbook.close();
        inputStream.close();
        return breedingList;

    }

    static void generateResultXlxs(List<Breeding> breedingList,String path){
        XSSFWorkbook workbook = new XSSFWorkbook();
        for (Breeding breeding:breedingList) {
            XSSFSheet sheet = workbook.createSheet(breeding.getName());
            int rowCount = 0;

            ArrayList<String> cellValues;
            Row row = sheet.createRow(rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("ETH Price");
            cellValues.add(String.format("%.2f", breeding.getEthPrice()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("SLP Price");
            cellValues.add(String.format("%.3f", breeding.getSlpPrice()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Parent 1 purchase price");
            cellValues.add(String.format("%.2f", breeding.getParent1().getPurchasePrice()));
            cellValues.add(" ");
            cellValues.add("Parent 2 purchase price");
            cellValues.add(String.format("%.2f", breeding.getParent2().getPurchasePrice()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Parent 1 selling price");
            cellValues.add(String.format("%.2f", breeding.getParent1().getSellingPrice()));
            cellValues.add(" ");
            cellValues.add("Parent 2 selling price");
            cellValues.add(String.format("%.2f", breeding.getParent2().getSellingPrice()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Parent 1 breed count before");
            cellValues.add(Integer.toString(breeding.getParent1().getBredCountBefore()));
            cellValues.add(" ");
            cellValues.add("Parent 2 breed count before");
            cellValues.add(Integer.toString(breeding.getParent2().getBredCountBefore()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Children Market");
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Average Selling Price");
            cellValues.add(String.format("%.2f", breeding.getChildrenMarketValue().getExpectedAvePrice()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Max Selling Price");
            cellValues.add(String.format("%.2f", breeding.getChildrenMarketValue().getMaxPrice()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Min Selling Price");
            cellValues.add(String.format("%.2f", breeding.getChildrenMarketValue().getMinPrice()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Breeding Cost");
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Item");
            cellValues.add("Purchase");
            cellValues.add("Selling");
            cellValues.add("SLP cost");
            cellValues.add("Unit cost");
            cellValues.add("Cost (ETH)");
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Parent 1");
            cellValues.add(String.format("%.2f", breeding.getParent1().getPurchasePrice()));
            cellValues.add(String.format("%.2f", breeding.getParent1().getNettSellingValue()));
            cellValues.add("");
            cellValues.add(String.format("%.2f", breeding.getParent1().getUnitCost()));
            cellValues.add(String.format("%.2f", breeding.getParent1().getCostInETH()));
            writeNewRow(row, cellValues);


            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Parent 2");
            cellValues.add(String.format("%.2f", breeding.getParent2().getPurchasePrice()));
            cellValues.add(String.format("%.2f", breeding.getParent2().getNettSellingValue()));
            cellValues.add("");
            cellValues.add(String.format("%.2f", breeding.getParent2().getUnitCost()));
            cellValues.add(String.format("%.2f", breeding.getParent2().getCostInETH()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("SLP");
            cellValues.add("");
            cellValues.add("");
            cellValues.add(Integer.toString(breeding.getSlpUsed()));
            cellValues.add("");
            cellValues.add(String.format("%.2f", breeding.getSlpUsed()*breeding.getSlpPrice()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("TOTAL COST");
            cellValues.add("");
            cellValues.add("");
            cellValues.add("");
            cellValues.add("");
            cellValues.add(String.format("%.2f",breeding.getTotalCost()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Profit Calculation");
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("");
            cellValues.add("Unit Profit");
            cellValues.add("Gross Profit");
            cellValues.add("Nett Profit");
            cellValues.add("Profit Margin");
            writeNewRow(row, cellValues);

            HashMap<String,Profit> map = breeding.getprofitMap();
            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Ave Profit");
            cellValues.add(String.format("%.2f",map.get(Breeding.AVERAGE_PROFIT).getUnitProfit()));
            cellValues.add(String.format("%.2f",map.get(Breeding.AVERAGE_PROFIT).getGrossProfit()));
            cellValues.add(String.format("%.2f",map.get(Breeding.AVERAGE_PROFIT).getNettProfit()));
            cellValues.add(String.format("%.2f",map.get(Breeding.AVERAGE_PROFIT).getProfitMargin()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Max Profit");
            cellValues.add(String.format("%.2f",map.get(Breeding.MAX_PROFIT).getUnitProfit()));
            cellValues.add(String.format("%.2f",map.get(Breeding.MAX_PROFIT).getGrossProfit()));
            cellValues.add(String.format("%.2f",map.get(Breeding.MAX_PROFIT).getNettProfit()));
            cellValues.add(String.format("%.2f",map.get(Breeding.MAX_PROFIT).getProfitMargin()));
            writeNewRow(row, cellValues);

            row = sheet.createRow(++rowCount);
            cellValues = new ArrayList<>();
            cellValues.add("Min Profit");
            cellValues.add(String.format("%.2f",map.get(Breeding.MIN_PROFIT).getUnitProfit()));
            cellValues.add(String.format("%.2f",map.get(Breeding.MIN_PROFIT).getGrossProfit()));
            cellValues.add(String.format("%.2f",map.get(Breeding.MIN_PROFIT).getNettProfit()));
            cellValues.add(String.format("%.2f",map.get(Breeding.MIN_PROFIT).getProfitMargin()));
            writeNewRow(row, cellValues);
        }
        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void writeNewRow(Row row, ArrayList<String> cellValues){
        for (int i=0;i<cellValues.size();i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(cellValues.get(i));
        }
    }
}
