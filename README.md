# WeatherApplication-Observer
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
