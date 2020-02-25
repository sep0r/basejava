import java.util.Arrays;

public class ArrayStorage {

    Resume[] storage = new Resume[10000];
    private int size = 0;

    void save(Resume resume) {
        if (size >= storage.length) {
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

    private int check_resume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i] != null) {
                if (storage[i].getUuid().equals(uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }

    Resume get(String uuid) {
        int i = check_resume(uuid);
        if (i != -1) {
            return storage[i];
        }
        return null;
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
        return Arrays.copyOfRange(storage, 0, size);
    }

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }
}