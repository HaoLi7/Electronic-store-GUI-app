package model;

/**
 * This class is the base class for all products the store will sell, and it implements Comparable interface for comparing
 * products' sold quantity
 */
public abstract class Product implements Comparable{
    private double price;
    private int stockQuantity;
    private int soldQuantity;
    private int quantityInCart;

    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        stockQuantity = initQuantity;
        soldQuantity = 0;
        quantityInCart = 0;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public double getPrice() {
        return price;
    }
    public int getQuantityInCart() {return quantityInCart;}
    public void setQuantityInCart(int quantityInCart) { this.quantityInCart = quantityInCart; }

    /**
     * This method is to sell units of one product, and change its sold quantity
     * @param amount amount of units to sell
     * @return the revenue of selling these units
     */
    public double sellUnits(int amount) {
        soldQuantity += amount;
        return price * amount;
    }

    /**
     * Override the compareTo method to compare two products' sold quantity
     */
    @Override
    public int compareTo(Object p) {
        if (this.soldQuantity > ((Product) p).soldQuantity)
            return -1;
        return 1;
    }
}