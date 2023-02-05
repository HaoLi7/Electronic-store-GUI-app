package model;

import java.util.ArrayList;

/**
 * Class representing an electronic store, it has an array of products that represent the items the store can sell,
 * and has an ArrayList for customer cart
 */
public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private double revenue;
    private ArrayList<Product> cart; //Put cart attributes in this class because sell products from the cart is related to the store
    private int numSales; //To record the total times of sale

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        cart = new ArrayList<Product>();
        numSales = 0;
    }

    public String getName() {
        return name;
    }
    public Product[] getStock() { return stock; }
    public ArrayList<Product> getCart() { return cart; }
    public double getRevenue() { return revenue; }
    public int getNumSales() { return numSales; }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }

    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }

    /**
     * Add one particular product to the cart, and change the cart list
     * @param p product to add
     */
    public void addToCart(Product p) {
        //If this product has not been added to cart, add it to the list first, otherwise this step is ignored
        if (!cart.contains(p))
            cart.add(p);
        //If there are at least one unit of this product in stock, then change its stock quantity and quantity in cart by one
        if (p.getStockQuantity() > 0) {
            p.setStockQuantity(p.getStockQuantity() - 1);
            p.setQuantityInCart(p.getQuantityInCart() + 1);
        }
    }

    /**
     * Remove one particular product from the cart, and change the cart list
     * @param p product to remove
     */
    public void removeFromCart(Product p) {
        p.setStockQuantity(p.getStockQuantity() + 1);
        p.setQuantityInCart(p.getQuantityInCart() - 1);
        //If there is no unit of this product in the cart, then remove this product from the cart list
        if (p.getQuantityInCart() == 0)
            cart.remove(p);
    }

    /**
     * Calculate the total price of products in the cart
     * @return the total price of products in the cart
     */
    public double calculateCart() {
        double total = 0;
        for (Product p: cart)
            total += p.getQuantityInCart() * p.getPrice();
        return total;
    }

    /**
     * Simulate the process of checking out
     */
    public void sellProducts() {
        if (!cart.isEmpty()) {
            for (Product p : cart) {
                revenue += p.sellUnits(p.getQuantityInCart());
                p.setQuantityInCart(0);
            }
            cart = new ArrayList<Product>(); //Reset the cart list
            numSales++;
        }
    }
}