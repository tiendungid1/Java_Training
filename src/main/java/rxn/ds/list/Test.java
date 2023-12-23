package rxn.ds.list;

public class Test {
    public static void main(String[] args) {
        testAddFunction();
    }

    private static void testAddFunction() {
        List<Integer> list = new ArrayList<>(2);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.set(1, 10);
        list.add(2, 6);
        list.add(6, 12);
    }

    private static void testColFunction() {
        ArrayList<Integer> list = new ArrayList<>(2);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.set(1, 10);
        list.add(2, 6);
        list.add(6, 12);
        java.util.ArrayList<Integer> temp = new java.util.ArrayList<>();
        temp.add(1);
        temp.add(3);

        System.out.println(list.containsAll(temp));
        temp.add(40);
        temp.add(44);
        System.out.println(list.containsAll(temp));

        list.addAll(temp);
        //list.print(true);
        System.out.println();

        temp.remove(0);
        temp.remove(0);
        temp.add(10);
        temp.add(5);
        list.removeAll(temp);
        //list.print(true);
        System.out.println();
        list.remove(0);
        list.remove(0);
        list.remove(0);
        list.remove(0);
        list.remove(0);
        //list.print(true);
    }
}
