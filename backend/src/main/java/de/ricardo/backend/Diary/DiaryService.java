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
        return diaryRepository.findAll();
    }

    public Diary save(Diary diary) {
        String id = UUID.randomUUID().toString();
        Diary todoToSave = diary.withId(id);
        return diaryRepository.save(todoToSave);
    }

    public Diary getById(String id) {
        return diaryRepository.findById(id).orElse(null);
    }

    public Diary update(Diary diary) {
        return diaryRepository.save(diary);
    }

    public void delete(String id) {
        diaryRepository.deleteById(id);
    }
}
