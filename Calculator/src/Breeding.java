import java.util.HashMap;
import java.util.Properties;

public class Breeding {


    public static final String AVERAGE_PROFIT = "average";
    public static final String MAX_PROFIT = "max";
    public static final String MIN_PROFIT = "min";
    private String name;
    private Parent parent1;
    private Parent parent2;
    private ChildrenMarketValue childrenMarketValue;
    private int slpUsed;
    private int numOfChild;
    private double totalCost;
    private HashMap<String,Profit> profitMap;
    private double ethPrice;
    private double slpPrice;


    public Breeding(String name, Parent parent1, Parent parent2, ChildrenMarketValue childrenMarketValue, int numOfChild,double ethPrice, double slpPrice){
        this.name = name;
        this.parent1 =  parent1;
        this.parent2 = parent2;
        this.childrenMarketValue = childrenMarketValue;
        this.numOfChild = numOfChild;
        this.ethPrice =  ethPrice;
        this.slpPrice = slpPrice;
    }

    public void calTotalCost(){
        this.parent1 = calParentCost(this.parent1);
        this.parent2 = calParentCost(this.parent2);
        this.slpUsed = calSlpUsedPerParent(parent1,numOfChild)+calSlpUsedPerParent(parent2,numOfChild);
        double breedingCostInETH = getSlpUsed()*this.slpPrice;
        this.totalCost = this.parent1.getCostInETH()+this.parent2.getCostInETH()+breedingCostInETH;
    }

    private Parent calParentCost(Parent parent){
        double nettSellingValue = parent.getSellingPrice()*(1-PriceConstants.SELLING_PROCESSING_FEE_PERCENTAGE);
        parent.setNettSellingValue(nettSellingValue);
        parent.setUnitCost(parent.getPurchasePrice()-parent.getNettSellingValue());
        parent.setCostInETH(parent.getUnitCost()*this.ethPrice);
        return parent;
    }

    private int calSlpUsedPerParent(Parent parent,int numOfChild){
        int total = 0;
        int childNo = parent.getBredCountBefore();
        for (int i=0;i<numOfChild;i++){
            childNo++;
            switch (childNo){
                case 1:
                    total+=PriceConstants.SLP_USED_FOR_1ST_CHILD_BREEDING;
                    break;
                case 2:
                    total+=PriceConstants.SLP_USED_FOR_2ND_CHILD_BREEDING;
                    break;
                case 3:
                    total+=PriceConstants.SLP_USED_FOR_3RD_CHILD_BREEDING;
                    break;
                case 4:
                    total+=PriceConstants.SLP_USED_FOR_4TH_CHILD_BREEDING;
                    break;
                case 5:
                    total+=PriceConstants.SLP_USED_FOR_5TH_CHILD_BREEDING;
                    break;
                case 6:
                    total+=PriceConstants.SLP_USED_FOR_6TH_CHILD_BREEDING;
                    break;
                case 7:
                    total+=PriceConstants.SLP_USED_FOR_7TH_CHILD_BREEDING;
                    break;
            }
        }
        return total;
    }

    public void calProfit(){
        this.profitMap = new HashMap<>();
        this.profitMap.put(AVERAGE_PROFIT,getProfit(this.childrenMarketValue.getExpectedAvePrice(),this.ethPrice));
        this.profitMap.put(MAX_PROFIT,getProfit(this.childrenMarketValue.getMaxPrice(),this.ethPrice));
        this.profitMap.put(MIN_PROFIT,getProfit(this.childrenMarketValue.getMinPrice(),this.ethPrice));
    }

    public Profit getProfit(double sellingPrice, double ethPrice){
        Profit profit = new Profit();
        profit.setUnitProfit(sellingPrice*numOfChild*(1-PriceConstants.SELLING_PROCESSING_FEE_PERCENTAGE));
        profit.setGrossProfit(profit.getUnitProfit()*ethPrice);
        profit.setNettProfit(profit.getGrossProfit()-this.totalCost);
        profit.setProfitMargin(profit.getNettProfit()/this.totalCost);
        return profit;
    }





    public Parent getParent1() {
        return parent1;
    }

    public void setParent1(Parent parent1) {
        this.parent1 = parent1;
    }

    public Parent getParent2() {
        return parent2;
    }

    public void setParent2(Parent parent2) {
        this.parent2 = parent2;
    }

    public ChildrenMarketValue getChildrenMarketValue() {
        return childrenMarketValue;
    }

    public void setChildrenMarketValue(ChildrenMarketValue childrenMarketValue) {
        this.childrenMarketValue = childrenMarketValue;
    }

    public int getSlpUsed() {
        return slpUsed;
    }

    public void setSlpUsed(int slpUsed) {
        this.slpUsed = slpUsed;
    }

    public int getNumOfChild() {
        return numOfChild;
    }

    public void setNumOfChild(int numOfChild) {
        this.numOfChild = numOfChild;
    }


    public HashMap<String, Profit> getprofitMap() {
        return profitMap;
    }

    public void setprofitMap(HashMap<String, Profit> profitMap) {
        this.profitMap = profitMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public HashMap<String, Profit> getProfitMap() {
        return profitMap;
    }

    public void setProfitMap(HashMap<String, Profit> profitMap) {
        this.profitMap = profitMap;
    }
    public double getEthPrice() {
        return ethPrice;
    }

    public void setEthPrice(double ethPrice) {
        this.ethPrice = ethPrice;
    }

    public double getSlpPrice() {
        return slpPrice;
    }

    public void setSlpPrice(double slpPrice) {
        this.slpPrice = slpPrice;
    }

}
