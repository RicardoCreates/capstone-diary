package de.ricardo.backend.Diary;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DiaryRepository {

    private static final Map<String, Diary> entries = new HashMap<>(Map.of("1", new Diary("1", "Test", DiaryStatus.OPEN)));

    public static List<Diary> getAll() {
        return new ArrayList<>(entries.values());
    }

    public Diary save(Diary entryToSave) {
        entries.put(entryToSave.id(), entryToSave);
        return entryToSave;
    }

    public Diary getById(String id) {
        return entries.get(id);
    }

    public Diary update(Diary diary) {
        entries.put(diary.id(), diary);
        return diary;
    }

    public void delete(String id) {
        entries.remove(id);
    }
}
