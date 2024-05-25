package Abstraction;

public abstract class AbstractItem implements Item{
    private final String name;
    private final double price;

    public AbstractItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                " name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
