# Boyer–Moore US State Search (Java CLI)

A simple **Java command-line program** that demonstrates the **Boyer–Moore string search algorithm** by searching for user-provided patterns within a list of U.S. state names.

This was built as an academic / practice project and is included for algorithm and CLI fundamentals.

## Overview

The program stores a list of U.S. states, concatenates them into a single searchable text string, and provides a console menu that lets you:

1. Display the full list of states
2. Search for a substring pattern using Boyer–Moore
3. Exit

Search is case-insensitive (input and searchable text are lowercased).

## How It Works

- The states are stored in an array in `Main.java`
- A single searchable string is created using:

  `String allStates = String.join(" ", states).toLowerCase();`

- The search uses Boyer–Moore with a **bad character table**:
  - `buildBadCharTable(pattern)` precomputes last-seen positions for ASCII chars
  - `boyerMooreSearch(text, pattern)` scans and shifts using the bad character rule

## Output

When a search is performed, the program prints:

- **Match indices** within the concatenated `allStates` string (separated by semicolons)
- A **human-friendly list of matching state names** (states that contain the pattern)

## Project Structure

- `src/Main.java` — CLI menu + Boyer–Moore implementation

## Requirements

- Java (JDK 8+)
- Works in any terminal or IDE (IntelliJ / Eclipse / VS Code)

## Running

### Compile + Run (Terminal)

From the project root (adjust paths if needed):

```bash
javac -d out src/Main.java
java -cp out Main
