package model.Product;



public class Product {
    private int productId;
    private String productName;
    private String brand;
    private double price;
    private String availability;
    private String category;
    private String image;
    private int quantity;

    public Product() {}

    public Product(int productId, String productName, String brand, double price, String availability, String category, String image, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.price = price;
        this.availability = availability;
        this.category = category;
        this.image = image;
        this.quantity = quantity;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

   
}
