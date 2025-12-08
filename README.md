# Warehouse Management System

Ez az alkalmazás egy egyszerű raktárkezelő rendszer, amely segít nyomon követni a termékeket, ügyfeleket és a megrendeléseket.

---

## Főbb funkciók

### Termékek (Products)
Kezeli a raktáron lévő termékeket, beleértve a nevüket, kategóriájukat, árát és készletét.
* **Adatok:** Azonosító (ID), Név (Name), Kategória (Category), Ár (Price), Készlet (Stock).
* **Példa termékek:** Szentkirályi (Víz, 350.0 HUF, 125 db), Monster (Energiaital, 400.0 HUF, 10 db).

### Megrendelések (Orders)
Nyomon követi a beérkezett és feldolgozás alatt lévő megrendeléseket.
* **Adatok:** Azonosító (Order ID), Ügyfél ID (Customer ID), Dátum (Date), Státusz (Status).
* **Példa státuszok:** Completed, Shipped.

### Értékesítések (Sales)
Részletes információkat tárol a végrehajtott értékesítésekről és a hozzájuk tartozó termékekről.
* **Adatok:** Eladási Azonosító (Sale ID), Termék Azonosító (Product ID), Megrendelés Azonosító (Order ID), Mennyiség (Quantity).

### Ügyfelek (Customers)
Kezeli az ügyfél adatokat.
* **Adatok:** Ügyfél ID (Customer ID), Név (Name), Email, Telefon (Phone).
* **Példa ügyfél:** Kiss Attila (kissattila@mail.hu, 067012345678).

---

## Felhasználói felület

Az alkalmazás intuitív felhasználói felülettel rendelkezik a táblázatos adatkezeléshez:

* **Navigáció:** Négy fő fül (Products, Orders, Sales, Customers) között lehet váltani az adatok megjelenítéséhez.
* **Keresés:** Van egy keresőmező az adatok gyors szűrésére.
* **Adatkezelés (Add/Remove item):** Jobb oldalon találhatóak a mezők az új elemek hozzáadásához, valamint a meglévő elemek eltávolításához.

---

## Adatbázis séma

Az alkalmazás egy relációs adatbázis sémát használ, amely négy fő táblából áll: `customers`, `products`, `orders` és `sales`.


### Kulcsok és fő mezők

Minden tábla rendelkezik egy **elsődleges kulccsal** (`id` vagy táblánként más elnevezéssel).

* **`customers`**: Azonosítja az ügyfeleket (`id`). Főbb mezők: `name`, `email`, `phone`.
* **`products`**: Azonosítja a termékeket (`id`). Főbb mezők: `name`, `price`, `stock`.
* **`orders`**: Azonosítja a megrendeléseket (`id`). Főbb mezők: `order_date`, `status`.
* **`sales`**: Azonosítja az egyes eladási tételeket (`id`). Fő mező: `quantity`.

### Kapcsolatok

A táblák a következő **idegen kulcsokkal** kapcsolódnak egymáshoz:

1.  **Ügyfelek és Megrendelések (1:N):**
    * Az **`orders`** tábla tartalmazza a **`customer_id`** mezőt, amely a **`customers.id`** elsődleges kulcsra mutat. Ez a kapcsolat azt jelenti, hogy egy ügyfélhez több megrendelés is tartozhat.

2.  **Megrendelések és Értékesítési Tételek (1:N):**
    * A **`sales`** tábla tartalmazza az **`order_id`** mezőt, amely az **`orders.id`** elsődleges kulcsra mutat. Ez biztosítja, hogy minden értékesítési tétel egy létező megrendeléshez legyen rendelve.

3.  **Termékek és Értékesítési Tételek (1:N):**
    * A **`sales`** tábla tartalmazza a **`product_id`** mezőt, amely a **`products.id`** elsődleges kulcsra mutat. Ez a kapcsolat rögzíti, hogy melyik terméket adták el az adott tétel keretében.