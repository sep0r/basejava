public class ArrayStorage {

    Resume[] storage = new Resume[10000];
    private int size = 0;

    void save(Resume resume) {
        if (size >= 10000) {
            System.out.println("Storage is full");
        } else if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(resume.getUuid())) {
                    System.out.println("Resume with " + storage[i].getUuid() + "is already exist!");
                    break;
                } else {
                    storage[size] = resume;
                    size++;
                    break;
                }
            }
        } else {
            storage[size] = resume;
            size++;
        }
    }

    Resume get(String uuid) {
        int i;
        if (uuid != null) {
            for (i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    break;
                }
            }
            return storage[i];
        } else {
            return null;
        }
    }

    void delete(String uuid) {
        int i = 0;
        do {
            if (storage[i].getUuid().equals(uuid)) {
                for (; i < size; i++) {
                    storage[i] = storage[i + 1];
                }
                size--;
                return;
            }
            i++;
        } while (i < size);
        System.out.println("Resume with this uuid not found.");
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
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }
}