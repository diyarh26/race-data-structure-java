# Race Data Structure (Java)

This project implements a custom data structure in Java for managing runners and their race results efficiently.  
It was developed as part of the Data Structures & Algorithms course, focusing on balanced tree operations, ranking, and exception handling.

## Features
- Add and remove runners by unique ID  
- Add and delete race results for each runner  
- Query the minimum run time of a runner  
- Query the average run time of a runner  
- Find the runner with the best average or minimum time  
- Rank runners by average or minimum times  

## Complexity Guarantees
- Initialize race — **O(1)**  
- Add runner — **O(log n)**  
- Add run time — **O(log n + log m_runner)**  
- Delete run time — **O(log n + log m_runner)**  
- Delete runner — **O(log n)**  
- Get minimum / average time — **O(log n)**  
- Get best runner (min/avg) — **O(1)**  
- Rank queries — **O(log n)**  

## Project Structure
- `Race.java` — main implementation of the data structure  
- `RunnerID.java` — abstract base class for runner identifiers  
- `main.java` — simple tester program  

## Requirements
- Java 17+  
- No external libraries  

## How to Run
Compile and run with:
```bash
javac Race.java RunnerID.java main.java
java main
