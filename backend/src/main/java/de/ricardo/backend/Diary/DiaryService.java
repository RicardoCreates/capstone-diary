package de.ricardo.backend.Diary;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    DiaryService(DiaryRepository diaryRepository){
        this.diaryRepository = diaryRepository;
    }

    List<Diary> getAll() {
        return DiaryRepository.getAll();
    }

    public Diary save(Diary diary) {
        String id = UUID.randomUUID().toString();

        Diary todoToSave = diary.withId(id);

        return diaryRepository.save(todoToSave);
    }

    public Diary getById(String id) {
        return diaryRepository.getById(id);
    }

    public Diary update(Diary diary) {
        return diaryRepository.update(diary);
    }

    public void delete(String id) {
        diaryRepository.delete(id);
    }
}
