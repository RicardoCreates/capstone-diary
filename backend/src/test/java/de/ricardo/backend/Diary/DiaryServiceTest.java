package de.ricardo.backend.Diary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiaryServiceTest {

    private DiaryRepository diaryRepository;
    private DiaryService diaryService;

    @BeforeEach
    void setUp() {
        diaryRepository = mock(DiaryRepository.class);
        diaryService = new DiaryService(diaryRepository);
    }

    @Test
    void getAll() {
        List<Diary> expectedDiaryList = List.of(
                new Diary("1", "Test entry 1", DiaryStatus.OPEN),
                new Diary("2", "Test entry 2", DiaryStatus.DONE)
        );
        when(diaryRepository.findAll()).thenReturn(expectedDiaryList);

        List<Diary> actualDiaryList = diaryService.getAll();

        assertEquals(expectedDiaryList, actualDiaryList);
        verify(diaryRepository).findAll();
    }

    @Test
    void save() {
        String generatedId = UUID.randomUUID().toString();
        Diary diaryToSave = new Diary("Test entry", DiaryStatus.OPEN);
        Diary savedDiary = new Diary(generatedId, "Test entry", DiaryStatus.OPEN);

        ArgumentCaptor<Diary> diaryCaptor = ArgumentCaptor.forClass(Diary.class);
        when(diaryRepository.save(any(Diary.class))).thenReturn(savedDiary);

        Diary result = diaryService.save(diaryToSave);

        verify(diaryRepository).save(diaryCaptor.capture());
        Diary capturedDiary = diaryCaptor.getValue();
        assertEquals("Test entry", capturedDiary.description());
        assertNotNull(capturedDiary.id());
        assertEquals(DiaryStatus.OPEN, capturedDiary.status());
        assertEquals(savedDiary, result);
    }

    @Test
    void getById() {
        String id = "1";
        Diary expectedDiary = new Diary(id, "Test entry", DiaryStatus.OPEN);
        when(diaryRepository.findById(id)).thenReturn(Optional.of(expectedDiary));

        Diary actualDiary = diaryService.getById(id);

        assertEquals(expectedDiary, actualDiary);
        verify(diaryRepository).findById(id);
    }

    @Test
    void getById_throwsException_whenDiaryNotFound() {
        String id = "non-existent-id";
        when(diaryRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> diaryService.getById(id));
        assertEquals("404 NOT_FOUND \"Diary entry not found\"", exception.getMessage());
        verify(diaryRepository).findById(id);
    }

    @Test
    void update() {
        Diary diaryToUpdate = new Diary("1", "Updated entry", DiaryStatus.IN_PROGRESS);
        when(diaryRepository.save(diaryToUpdate)).thenReturn(diaryToUpdate);

        Diary updatedDiary = diaryService.update(diaryToUpdate);

        assertEquals(diaryToUpdate, updatedDiary);
        verify(diaryRepository).save(diaryToUpdate);
    }

    @Test
    void delete() {
        String id = "1";
        when(diaryRepository.existsById(id)).thenReturn(true);

        diaryService.delete(id);

        verify(diaryRepository).deleteById(id);
    }

    @Test
    void delete_throwsException_whenDiaryNotFound() {
        String id = "non-existent-id";
        when(diaryRepository.existsById(id)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> diaryService.delete(id));
        assertEquals("404 NOT_FOUND \"Diary entry not found\"", exception.getMessage());
        verify(diaryRepository, never()).deleteById(id);
    }
}
