public class Parent {
    private double purchasePrice;
    private double sellingPrice;
    private int bredCountBefore;
    private double sellProcessingFee;
    private double nettSellingValue;
    private double unitCost;
    private double costInETH;

    public Parent(){}
    public Parent(double purchasePrice,double sellingPrice,int bredCountBefore){
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.bredCountBefore = bredCountBefore;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getBredCountBefore() {
        return bredCountBefore;
    }

    public void setBredCountBefore(int bredCountBefore) {
        this.bredCountBefore = bredCountBefore;
    }

    public double getSellProcessingFee() {
        return sellProcessingFee;
    }

    public void setSellProcessingFee(double sellProcessingFee) {
        this.sellProcessingFee = sellProcessingFee;
    }

    public double getNettSellingValue() {
        return nettSellingValue;
    }

    public void setNettSellingValue(double nettSellingValue) {
        this.nettSellingValue = nettSellingValue;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getCostInETH() {
        return costInETH;
    }

    public void setCostInETH(double costInETH) {
        this.costInETH = costInETH;
    }


}
