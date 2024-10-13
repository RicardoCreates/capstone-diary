type DiaryEntry = {
    id: string;
    description: string;
    status: string;
};

type ContentPageProps = {
    entries: DiaryEntry[];
    description: string;
    setDescription: (value: string) => void;
    handelStatusChange: (id: string, newStatus: string) => void;
    handleDescriptionChange: (id: string, newDescription: string) => void;
    deleteEntry: (id: string) => void;
    updateEntry: (id: string, updatedDescription: string) => void;
    addEntry: () => void;
};


export default function ContentPage({
                                        entries,
                                        description,
                                        setDescription,
                                        handelStatusChange,
                                        handleDescriptionChange,
                                        deleteEntry,
                                        updateEntry,
                                        addEntry
                                    }: ContentPageProps) {
    return (
        <>
            <h1>Diary Entries List</h1>
            <p>Test for Deploy 2</p>
            <p>failcheck</p>
            {/*das ist ein Test für Sonar*/}
            <ul>
                {entries.map((entry) => (
                    <li key={entry.id}>
                        <input
                            type="text"
                            value={entry.description}
                            onChange={(event) => handleDescriptionChange(entry.id, event.target.value)}
                        />
                        <select
                            value={entry.status}
                            onChange={(event) => handelStatusChange(entry.id, event.target.value)}
                        >
                            <option value={"OPEN"}>OPEN</option>
                            <option value={"IN_PROGRESS"}>IN PROGRESS</option>
                            <option value={"DONE"}>DONE</option>
                        </select>
                        <button onClick={() => updateEntry(entry.id, entry.description)}>
                            Save Changes
                        </button>
                        <button onClick={() => deleteEntry(entry.id)}>
                            Delete
                        </button>
                    </li>
                ))}
            </ul>

            <h2>Neues Entry hinzufügen</h2>
            <input
                type={"text"}
                value={description}
                onChange={event => setDescription(event.target.value)}
                placeholder={"Entry eingeben"}
            />
            <button onClick={addEntry}>Hinzufügen</button>
            <a href={"/"}>Go back</a>
        </>
    )
}