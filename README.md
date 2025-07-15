# Azul Board Game (Java)

## Project Description

This project is a digital implementation of the board game *Azul*, developed in Java with a graphical user interface using Java Swing. The aim was to apply core software engineering principles while working collaboratively in a team setting.

The application allows local gameplay for 2 to 4 players, supporting all essential rules of the Azul game. It provides interactive feedback through the GUI for illegal actions, and uses structured backend logic to ensure correct game flow.

The project emphasizes clean architecture, modularity, testing, and maintainability. It was developed using agile methodologies (SCRUM), with sprints tracked in Jira and automatic verification via a GitLab CI/CD pipeline.

## Tech Stack

- **Java 17** – Core programming language
- **Java Swing** – GUI components
- **JUnit** – Unit, integration, and system testing
- **Maven** – Build and dependency management
- **GitLab CI** – Automated build, test, and deployment pipelines
- **JaCoCo** – Code coverage metrics
- **Sigrid** – Software quality metrics analysis
- **Jira** – Agile task management and sprint planning

## Architecture Highlights

- **Modular Design**: Components like `Tile`, `Player`, `Factory`, and `Wall` are implemented in separate classes under the `components` package.
- **Design Patterns**:
    - *Singleton* for the central `Game` class
    - *Facade* to abstract complex interactions in `Game`
    - *Adapter* and *Strategy* for component interchangeability
- **Testing**: Achieved over 90% unit test coverage; includes integration and system-level tests.
- **Refactoring**: Applied principles like class extraction and method simplification to reduce code smells and increase maintainability.
