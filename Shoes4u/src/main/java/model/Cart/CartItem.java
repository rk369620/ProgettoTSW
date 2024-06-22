package model.Cart;



import model.Product.Product;

public class CartItem {
    private int id;
    private int cartId;
    private int productId;
    private String productName;
    private double productPrice;
    private int quantity;
    private Product product; 
    

    public CartItem(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

	public CartItem() {
		// TODO Auto-generated constructor stub
	}

	public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

	// Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Setter for Product
    public void setProduct(Product product) {
        this.product = product;
    }

    // Getter for Product
    public Product getProduct() {
        return product;
    }
}
