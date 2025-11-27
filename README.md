# F2 Cat Café o((>ω< ))o

A small Java 21 console application that simulates a cozy cat café where customers can meet cats, order drinks/snacks, and make bookings. o((>ω< ))o☆*: .｡. o(≧▽≦)o .｡.:*☆

## Features

- View all café cats and their breeds  
- View the menu (drinks and snacks with prices)  
- Make a booking:
  - Choose number of guests  
  - Select any 2 cats  
  - Order multiple drinks and snacks (for example, 2 Coffees and 1 Tea)  
- See a detailed bill summary  
- Rate your experience from 1–5 stars  
  - 1 star shows a sad cat message and an apology from the café  
- View all existing bookings  

## Technologies

- Java 21  
- Object-oriented programming:
  - Classes, inheritance, interfaces, polymorphism  
- Modern Java features:
  - Sealed classes  
  - Records  
  - Enums  
  - Lambdas and method references  
  - `StringBuilder`, `List/ArrayList`, `LocalDateTime`  

## How to Run

1. Open the project in Eclipse (or another Java 21-compatible IDE).  
2. Make sure the project is using JDK 21.  
3. Run the `CatCafeApp` class.  
4. Follow the console menu to explore the café:
   - 1 = view cats  
   - 2 = view menu  
   - 3 = make a booking  
   - 4 = view all bookings  
   - 0 = exit  

## Project Structure

- `CatCafeApp` – main class, menu and user interaction  
- `CafeManager` – manages cats, menu items and bookings  
- `Cat`, `PersianCat`, `SiameseCat` – cat hierarchy  
- `MenuItem`, `MenuCategory` – café menu model  
- `Booking`, `BookingReceipt`, `BookingException` – booking and billing  
- `Pettable`, `Orderable` – behaviour and pricing interfaces  
- `CatBreed` – enumeration of cat breeds  
