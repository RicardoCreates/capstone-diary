import {useEffect, useState} from 'react';
import './App.css';
import axios from "axios";

function App() {
    const [entries, setEntries] = useState([]);

    useEffect(() => {
        axios.get("/api/diary")
            .then(response => setEntries(response.data))
            .catch(error => console.log(error));
    }, []);

    return (
        <>
            <h1>Diary App</h1>
            {entries.length > 0 ? (
                entries.map(entry => (
                    <div key={entry.id}>
                        <p>Description: {entry.description}</p>
                        <p>Status: {entry.status}</p>
                    </div>
                ))
            ) : (
                <p>No entries found</p>
            )}
        </>
    );
}

export default App;
