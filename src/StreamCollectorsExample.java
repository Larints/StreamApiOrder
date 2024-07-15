//Создайте список заказов с разными продуктами и их стоимостями.
//Группируйте заказы по продуктам.
//Для каждого продукта найдите общую стоимость всех заказов.
//Отсортируйте продукты по убыванию общей стоимости.
//Выберите три самых дорогих продукта.
//Выведите результат: список трех самых дорогих продуктов и их общая стоимость.


import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Order {
    private String product;
    private double cost;

    public Order(String product, double cost) {
        this.product = product;
        this.cost = cost;
    }

    public String getProduct() {
        return product;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "product='" + product + '\'' +
                ", cost=" + cost +
                '}';
    }
}

public class StreamCollectorsExample {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );

        Map<String, List<Double>> groupedOrders = orders.stream().collect(Collectors.groupingBy(
                Order::getProduct,
                Collectors.mapping(Order::getCost, Collectors.toList())
        ));

        Map<String, Double> groupOrders = orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct,
                        Collectors.summingDouble(Order::getCost)));

        List<Order> sortedList = orders.stream().
                sorted((o1, o2) -> Double.compare(o2.getCost(), o1.getCost())).toList();

        List<Order> threeMostSomeExpensiveProduct = orders.stream().sorted(Comparator.comparingDouble(Order::getCost)
                        .reversed()).
                limit(3).toList();

        Map<Double, List<Order>> resultMap = orders.stream()
                .sorted(Comparator.comparingDouble(Order::getCost).reversed())
                .limit(3)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> {
                            double totalCost = list.stream()
                                    .mapToDouble(Order::getCost)
                                    .sum();
                            Map<Double, List<Order>> map = new HashMap<>();
                            map.put(totalCost, list);
                            return map;
                        }
                ));

        System.out.println(resultMap);


    }

}
