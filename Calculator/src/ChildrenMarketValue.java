public class ChildrenMarketValue {

    private double expectedAvePrice;
    private double maxPrice;
    private double minPrice;

    public ChildrenMarketValue (){}

    public ChildrenMarketValue(double expectedAvePrice,double maxPrice,double minPrice){
        this.expectedAvePrice = expectedAvePrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

    public double getExpectedAvePrice() {
        return expectedAvePrice;
    }

    public void setExpectedAvePrice(double expectedAvePrice) {
        this.expectedAvePrice = expectedAvePrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }
}
