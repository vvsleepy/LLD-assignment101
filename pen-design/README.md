# 🖊️ Pen System – Low Level Design (LLD)

This design explains how to build a flexible and clean Pen system using Object-Oriented principles and design patterns. The goal is to make it easy to understand, easy to extend (like adding a Pencil later), and easy to maintain.

---

## 1. Common Interface for Future Expansion

**Problem:**  
We may want to add other writing tools in the future (like a Pencil).

**Solution:**  
We create a common interface called `Writable`.

Both `Pen` and future classes like `Pencil` will implement this interface.

This ensures every writing tool has these methods:
- `start()`
- `write()`
- `close()`

👉 This keeps everything consistent and makes future additions simple.

---

## 2. Handling Different Behaviors (Strategy Pattern)

**Problem:**  
Not all pens behave the same way:
- Some use caps, others are click-based  
- Some are refillable, others are not  

**Solution:**  
We use the **Strategy Pattern** to separate these behaviors from the main `Pen` class.

### Open/Close Behavior
- Interface: `OpenCloseStrategy`
- Implementations:
  - `CapStrategy` (remove/put cap)
  - `ClickStrategy` (click button)

### Refill Behavior
- Interface: `RefillableStrategy`
- Implementations:
  - `FillInkStrategy` (used in fountain pens)
  - `ChangeRefillStrategy` (used in gel pens)
  - `NonRefillableStrategy` (used in disposable pens)

👉 The `Pen` class does not handle these directly.  
Instead, it delegates the work to these strategy classes.

👉 This allows mixing and matching behaviors easily.

---

## 3. Managing Rules with State

**Problem:**  
A pen should not write unless it is started.

**Solution:**  
We maintain a simple boolean state inside the `Pen` class:

```java
boolean isStarted = false;
```
When start() is called → isStarted = true
write() checks this value before writing

👉 This ensures correct usage without adding complexity.

---

## 4. Main Classes in the System
- Pen (Abstract Class)
Implements Writable
Contains common properties like:
PenType
colour
Holds references to:
OpenCloseStrategy
RefillableStrategy
- Concrete Classes
FountainPen
GelPen
NonRefillablePen

- Each of these:

Extends Pen
Chooses the correct strategies during creation

👉 Example:

FountainPen → uses FillInkStrategy
GelPen → uses ChangeRefillStrategy

---

## 🎯 Why This Design is Good

- Easy to extend (add new pen types or behaviors)
- Clean and modular
- No need to change existing code when adding features
- Follows good OOP and design principles

- 👉 If a new requirement comes, you just add a new class instead of modifying old ones.