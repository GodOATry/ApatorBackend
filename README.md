# Apator
Recruitment project: Spring application for demonstration and evaluation purposes.

Instrukcja obsługi

System kolejkowy

Uruchomienie

•	Backend (Spring)
o	Pobierz projekt z GitHuba: https://github.com/GodOATry/ApatorBackend.git 
o	Upewnij się, że masz zainstalowaną Java 17.
o	Otwórz projekt w IDE (np. IntelliJ IDEA).
o	IDE powinno automatycznie pobrać wszystkie zależności projektu.
o	Uruchom projekt.
•	Frontend (React)
o	Pobierz projekt z GitHuba: https://github.com/GodOATry/ApatorFrontend.git 
o	Upewnij się, że masz zainstalowany Node.js: https://nodejs.org/en  
o	Otwórz projekt w IDE (np. Visual Studio Code).
o	W terminalu uruchom komendę: „npm install”
o	W terminalu uruchom komendę: „npm start”
o	Aplikacja frontendowa powinna zostać uruchomiona.








Obsługa
Backend
•	Endpoint clients
o	Pobiera aktualną kolejkę.
o	Parametry: brak.
o	Wynik: JSON z listą klientów w kolejce.
•	Endpoint clientToMonitor
o	Pobiera informacje o kliencie na podstawie przekazanego imienia lub identyfikatora.
o	Parametry:
	name - imię klienta.
lub
	clientNumber - identyfikator klienta.
o	Wynik: JSON z informacjami o kliencie.
•	Endpoint placeOrder
o	Dodaje klienta do systemu na podstawie przekazanych informacji.
o	Parametry:
	name - imię klienta.
	isVip - rodzaj klienta.
	isUrgent - rodzaj klienta.
	Pin – pin przekazany przez klienta.
	Wynik: JSON z informacjami o dodanym kliencie.










Frontend
•	Strona internetowa
o	Dostępna pod adresem http://localhost:3000/ 
o	AppBar
	Client List - kliknięcie na ten przycisk przenosi użytkownika do listy klientów w kolejce.
	Add Client - kliknięcie na ten przycisk przenosi użytkownika do formularza dodawania klienta.
	Monitoring - kliknięcie na ten przycisk przenosi użytkownika do strony monitorowania kolejki.
	
•	Lista klientów w kolejce
o	Wyświetla aktualną kolejkę klientów.
o	Kolejka jest automatycznie odświeżana.
•	Formularz dodawania klienta
o	Umożliwia dodanie nowego klienta do systemu.
o	Wymaga podania imienia i rodzaju klienta.
•	Strona monitorowania kolejki
o	Wyświetla informacje o kolejce.
o	W tym informacje o:
	liczbie osób przed nami w kolejce,
	czasie oczekiwania na obsługę.
Uwagi
Szczegółowe informacje o API systemu znajdują się w Swaggerze.
Aby skorzystać z Swaggera, uruchom serwer backendowy i przejdź do adresu:
http://localhost:8080/swagger-ui/index.html 
