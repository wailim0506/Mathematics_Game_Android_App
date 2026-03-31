

# Mathematics Game Android App

## Demo Video
[Watch the demo video here](https://youtu.be/n9tFsDTRpgE)

---

## Overview
Mathematics Game Android App is an interactive and engaging educational application designed to help users improve their arithmetic skills through gamified challenges. Developed with professional standards, the app features a user-friendly interface, animated characters, and sound effects to enhance the learning experience.

## Features
- User authentication and login system
- Multiple game modes and difficulty levels
- Animated character states and sound effects
- Real-time scoring and ranking system
- User record tracking and statistics
- How-to-play instructions and intuitive UI
- Responsive design for various Android devices

## Project Structure
- **app/src/main/java/com/example/itp4501_assignment/**: Main Java source code
- **app/src/main/res/**: Resources (layouts, drawables, animations, etc.)
- **app/src/main/AndroidManifest.xml**: Application manifest
- **build.gradle.kts**: Project build configuration

## Getting Started
### Prerequisites
- Android Studio (latest version recommended)
- JDK 17 or above
- Gradle (wrapper included)

### Installation
1. Clone the repository:
   ```sh
   git clone <repository-url>
   ```
2. Open the project in Android Studio.
3. Let Gradle sync and resolve dependencies.
4. Build and run the app on an emulator or physical device.

## Usage
- Launch the app and log in or register a new account.
- Select a game mode and start playing.
- View your scores and rankings in the main menu.
- Access the 'How to Play' section for guidance.

## Technical Details

### Architecture
The project follows a modular architecture, separating concerns between UI, business logic, and data management. Key components include:
- **Activities**: Handle user interaction and navigation (e.g., `Login`, `mainMenu`, `gamePlay`, `gameFinish`, `howToPlay`).
- **Services**: Manage background tasks such as music and sound effects (`BackgroundMusicService`, `correctSoundEffectService`, `knifeSoundEffectService`, `loseSoundEffectService`).
- **Adapters**: Custom adapter (`MyAdapter`) for displaying dynamic lists.
- **State Classes**: Manage character states and animations (`characterState`, `knightState`).
- **Database Control**: Handles user data, scores, and rankings (`datebaseControl`).

### Technologies Used
- **Language**: Java (Android SDK)
- **Build System**: Gradle (Kotlin DSL)
- **Minimum SDK**: API Level 21 (Android 5.0 Lollipop)
- **UI**: XML layouts, custom animations, and drawable resources
- **Persistence**: SQLite (via custom database control class)
- **Sound & Media**: Android MediaPlayer and SoundPool APIs

### Implementation Highlights
- **User Authentication**: Simple login system with local data storage.
- **Game Logic**: Arithmetic question generation, answer validation, and scoring.
- **Animations**: XML-based animations for character and UI transitions.
- **Sound Effects**: Managed via dedicated services for background music and effects.
- **Ranking System**: Stores and displays user scores and rankings.
- **Extensibility**: Modular codebase allows for easy addition of new features or game modes.

## User Guide

### Getting Started
1. **Install the App**: Build and install the application using Android Studio or deploy the APK to your Android device.
2. **Launch**: Open the Mathematics Game app from your device.
3. **Login/Register**: Enter your credentials to log in or register a new account.

### Main Menu
- **Start Game**: Begin a new game session and select your preferred difficulty.
- **Ranking**: View the leaderboard and your personal ranking.
- **User Record**: Check your game history and statistics.
- **How to Play**: Access instructions and tips for gameplay.

### Gameplay
1. **Answer Questions**: Solve arithmetic problems presented on the screen.
2. **Use Controls**: Interact with on-screen buttons to submit answers or navigate.
3. **Sound & Animation**: Enjoy background music, sound effects, and animated characters.
4. **Game Finish**: View your score and ranking at the end of each session.

### Additional Tips
- Ensure your device’s sound is enabled for the best experience.
- Progress and scores are saved locally on your device.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
