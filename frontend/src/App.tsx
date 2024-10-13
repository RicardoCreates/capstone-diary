import {useEffect, useState} from 'react';
import axios from "axios";
import {Route, Routes} from "react-router-dom";
import LandingPage from "./pages/LandingPage.tsx";
import ContentPage from "./pages/ContentPage.tsx";

type Entry = {
    id: string;
    description: string;
    status: string;
};

export default function App() {
    const [entries, setEntries] = useState<Entry[]>([]);
    const [description, setDescription] = useState<string>("");

// Get Entries
    useEffect(() => {
        axios.get("/api/diary")
            .then(response => setEntries(response.data))
            .catch(error => console.log(error));
    }, []);


// Add Entry
    function addEntry() {
        const newTodo: Partial<Entry> = {
            description: description,
            status: "OPEN"
        };

        axios.post("/api/diary", newTodo)
            .then(response => {
                setEntries([...entries, response.data]);
                setDescription("");
            })
            .catch(error => console.log(error));
    }

// Update Entry
    function updateEntry(id: string, updatedDescription: string) {
        const entryToUpdate = entries.find(entry => entry.id === id);

        if (!entryToUpdate) return;

        const updatedEntry = {
            id: id,
            description: updatedDescription,
            status: entryToUpdate.status
        };

        axios.put(`/api/diary/${id}`, updatedEntry)
            .then(response => {
                setEntries(entries.map(entry =>
                    entry.id === id ? response.data : entry
                ));
            })
            .catch(error => console.log(error));
    }

// delete
    function deleteEntry(id: string) {
        axios.delete(`/api/diary/${id}`)
            .then(() => {
                setEntries(entries.filter(entry => entry.id !== id))
            })
            .catch(error => console.log(error));
    }

// description input
    function handleDescriptionChange(id: string, newDescription: string) {
        setEntries(entries.map(entry =>
            entry.id === id ? {...entry, description: newDescription} : entry
        ));
    }

// Status
    function handelStatusChange(id: string, newStatus: string) {
        setEntries(entries.map(entry =>
            entry.id === id ? {...entry, status: newStatus} : entry
        ));
    }


    return (
        <>
            <Routes>
                <Route path="/" element={<LandingPage />} />
                <Route path="/diary" element={
                    <ContentPage
                        entries={entries}
                        description={description}
                        setDescription={setDescription}
                        handelStatusChange={handelStatusChange}
                        handleDescriptionChange={handleDescriptionChange}
                        deleteEntry={deleteEntry}
                        updateEntry={updateEntry}
                        addEntry={addEntry}
                    />
                } />
            </Routes>
        </>
    );
}
