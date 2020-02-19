public class MainTestArrayStorage {
    public static void main(String[] args) {
        ArrayStorage arrayStorage1 = new ArrayStorage();

        arrayStorage1.save(new Resume("3be6912a-5275-11ea-8d77-2e728ce88125"));
        arrayStorage1.save(new Resume("3be693d2-5275-11ea-8d77-2e728ce88125"));
        arrayStorage1.save(new Resume("3be69526-5275-11ea-8d77-2e728ce88125"));
        arrayStorage1.save(new Resume("3be6965c-5275-11ea-8d77-2e728ce88125"));
        arrayStorage1.save(new Resume("3be69792-5275-11ea-8d77-2e728ce88125"));

        System.out.println("Get: "+arrayStorage1.get("3be69792-5275-11ea-8d77-2e728ce88125"));
        System.out.println("Size: "+arrayStorage1.size());

        arrayStorage1.getAll();
        arrayStorage1.delete("3be6912a-5275-11ea-8d77-2e728ce88125");
        System.out.println("Get deleted resume: "+arrayStorage1.get("3be6912a-5275-11ea-8d77-2e728ce88125"));
        System.out.println("Size: "+arrayStorage1.size());
        arrayStorage1.getAll();

        arrayStorage1.clear();
        System.out.println("Size: "+arrayStorage1.size());
        arrayStorage1.getAll();
    }
}
