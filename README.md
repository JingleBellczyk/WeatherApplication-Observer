# WeatherApplication-Observer

[ENG]
### An application implementing the Observer design pattern
This application allows users to register as observers of selected weather stations. The weather data is **updated every 5 seconds** and is randomly generated. The project uses serialization to and from `.json` files, and unit tests are written using `JUnit`.

---------------------------------------------
The user can:
- register
- choose the cities they want to observe
- display the current weather data in the console
- have their data saved to a .json file

---------------------------------------------
Classes:
- CSI - implements Thread, holds a list of observers which it notifies of weather data updates
- UserApp - manages user interaction, allows them to observe new weather stations, save data to a file, or print it to the console
- RandomWeatherValues - generates random temperature, humidity, and pressure values
- Menu - handles user communication
Files:
- the weatherLocations.json file contains a list of cities.

[PL]
### Aplikacja implementująca wzorzec Obserwator

Aplikacja w której użytkownik może zarejestrować się jako odbiorca danych wybranych stacji pogodowych.
Dane pogodowe są **aktualizowane co 5 sekund** i są losowo generowane.
W projekcie stosowana jest serializacja do i z plików `.json`, a także napisane są testy jednostkowe `JUnit`.


---------------------------------------------
Użytkownik może:
 - zarejestrować się
 - wybrać miasta, które chce obserwować
 - wyświetlać aktualne dane pogodowe w konsoli
 - mieć swoje dane zapisywane do pliku .json

--------------------------------------------
Klasy:
 - CSI - implementuje Thread, posiada listę obserwatorów, których powiadamia o aktualizacji danych pogodowych
 - UserApp - obsługuje użytkownika, pozwala mu obserwować nowe stacje pogodowe, zapisywać do pliku dane czy wypisywać je w konsoli
 - RandomWeatherValues - generuje losowe wartości temperatury, wilgotności i ciśnienia
 - Menu - implementuje komunikacje z użytkownikiem
 
Pliki:
 - plik weatherLocations.json zawiera listę miast
