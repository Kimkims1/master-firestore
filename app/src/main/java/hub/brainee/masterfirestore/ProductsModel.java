package hub.brainee.masterfirestore;

public class ProductsModel {
    private String item_id;
    private String name;
    private long price;

    private ProductsModel() {
    }

    private ProductsModel(String name, long price,String item_id) {
        this.name = name;
        this.price = price;
        this.item_id = item_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
