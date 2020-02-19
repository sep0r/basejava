public class ArrayStorage {

    Resume[] storage = new Resume[10000];
    private int size = 0;

    void save(Resume resume) {
        for (int i = 0; i <= size; i++) {
            if (storage[i] == null) {
                storage[i] = resume;
                size = i + 1;
                return;
            }
            if (storage[i].getUuid().equals(resume.getUuid())) {
                System.out.println("Resume with " + storage[i].getUuid() + "is already exist!");
                return;
            }
        }
        System.out.println("Storage full");
    }

    Resume get(String uuid) {
        if (uuid != null) {
            int i;
            for (i = 0; i < size; i++) {
                Resume a = storage[i];
                if (a.getUuid().equals(uuid)) {
                    break;
                }
            }
            return storage[i];
        } else {
            return null;
        }
    }

    void delete(String uuid) {
        try {
            for (int i = 0; i < 10000; i++) {
                Resume a = storage[i];
                if (a.getUuid().equals(uuid)) {
                    for (; i < size; i++) {
                        storage[i] = storage[i + 1];
                    }
                    size--;
                    break;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Resume with this uuid not found.");
        }
    }

    int size() {
        return size;
    }

    Resume[] getAll() {
        int i;
        for (i = 0; i < size; i++) {
            System.out.println(storage[i]);
        }
        return new Resume[i];
    }

    void clear() {
        for (int to = size, i = 0; i < to; i++)
            storage[i] = null;
        size = 0;
    }
}