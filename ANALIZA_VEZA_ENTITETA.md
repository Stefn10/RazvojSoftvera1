# Analiza veza između entiteta u Model folderu

### Pogledati Ispod
### ⚠️ PREOSTALO:
7. **PROBLEM 2**: `PredispitneObaveze` nema direktnu vezu sa `Predmet` (nije kritično, pristupa se preko `SlusaPredmet.predmet`)
8. **PROBLEM 8**: Redundatne veze u `SlusaPredmet` - zahteva pažljivo upravljanje konzistentnošću

## ✅ DOBRO POvezano

### 1. Student struktura
- **StudentPodaci** ↔ **StudentIndeks**: ✅ OneToMany (1 student može imati više indeksa)
- **StudentPodaci** ↔ **StudentSrednjaSkola**: ✅ OneToMany
- **StudentPodaci** ↔ **StudentVisokaUstanova**: ✅ OneToMany
- **StudentIndeks** ↔ **StudijskiProgram**: ✅ ManyToOne (indeks vezan za program)

### 2. Nastavnik struktura
- **Nastavnik** ↔ **NastavnikZvanje**: ✅ OneToMany (nastavnik može imati više zvanja)
- **Nastavnik** ↔ **NastavnikObrazovanje**: ✅ OneToMany
- **Nastavnik** ↔ **DrziPredmet**: ✅ OneToMany preko DrziPredmet

### 3. Predmet i Studijski program
- **Predmet** ↔ **StudijskiProgram**: ✅ ManyToOne
- **Predmet** ↔ **DrziPredmet**: ✅ OneToMany
- **Predmet** ↔ **SlusaPredmet**: ✅ OneToMany
- **Predmet** ↔ **Ispit**: ✅ OneToMany (bidirekciono)

### 4. Ispiti
- **Ispit** ↔ **DrziPredmet**: ✅ ManyToOne
- **Ispit** ↔ **Predmet**: ✅ ManyToOne (direktna veza za lakše upite)
- **Ispit** ↔ **IspitniRok**: ✅ ManyToOne
- **Ispit** ↔ **SkolskaGodina**: ✅ ManyToOne
- **IspitniRok** ↔ **SkolskaGodina**: ✅ ManyToOne

### 5. Skolska godina
- **SkolskaGodina** ↔ **IspitniRok**: ✅ OneToMany
- **SkolskaGodina** ↔ **DrziPredmet**: ✅ OneToMany
- **SkolskaGodina** ↔ **PredispitneObaveze**: ✅ OneToMany
- **SkolskaGodina** ↔ **Uplata**: ✅ OneToMany
- **SkolskaGodina** ↔ **UpisGodine**: ✅ OneToMany
- **SkolskaGodina** ↔ **StudijskiProgram**: ✅ OneToMany
- **SkolskaGodina** ↔ **Grupa**: ✅ OneToMany

### 6. Uplate
- **Uplata** ↔ **StudentIndeks**: ✅ ManyToOne
- **Uplata** ↔ **SkolskaGodina**: ✅ ManyToOne

### 7. Prijave i izlasci na ispite
- **PrijavaIspita** ↔ **StudentIndeks**: ✅ ManyToOne
- **PrijavaIspita** ↔ **Ispit**: ✅ ManyToOne
- **PrijavaIspita** ↔ **IzlazakNaIspit**: ✅ OneToOne (bidirekciono)
- **IzlazakNaIspit** ↔ **SlusaPredmet**: ✅ ManyToOne

### 8. Upis i obnova godine
- **UpisGodine** ↔ **StudentIndeks**: ✅ ManyToOne
- **UpisGodine** ↔ **SkolskaGodina**: ✅ ManyToOne
- **UpisGodine** ↔ **SlusaPredmet**: ✅ OneToMany (bidirekciono)
- **ObnovaGodine** ↔ **UpisGodine**: ✅ ManyToOne (nasleđuje od UpisGodine)

### 9. Grupa
- **Grupa** ↔ **StudijskiProgram**: ✅ ManyToOne
- **Grupa** ↔ **SkolskaGodina**: ✅ ManyToOne
- **Grupa** ↔ **DrziPredmet**: ✅ OneToMany

---

## ⚠️ PROBLEMI I PREPORUKE ZA ISPRAVKU

### ✅ PROBLEM 1: SlusaPredmet nema eksplicitnu vezu sa UpisGodine - REŠENO
**Trenutno stanje:**
- `UpisGodine` ima `List<SlusaPredmet> slusaniPredmeti`
- `SlusaPredmet` SADA IMA polje `upisGodine` ✅

**Ispravka:**
Dodato u `SlusaPredmet`:
```java
@ManyToOne
@JoinColumn(name = "upis_godine_id")
private UpisGodine upisGodine;  // moze biti iz obnove ili priznat
```

### PROBLEM 2: PredispitneObaveze nema direktnu vezu sa Predmet
**Trenutno stanje:**
- `PredispitneObaveze` ima vezu sa `SlusaPredmet` i `SkolskaGodina`
- Nema direktnu vezu sa `Predmet`

**Prema specifikaciji:**
- "Za predmet se definišu predispitne obaveze u školskoj godini"

**Preporuka:**
Dodati u `PredispitneObaveze`:
```java
@ManyToOne
@JoinColumn(name = "predmet_id")
private Predmet predmet;  // veza sa predmetom za koji su obaveze
```
Ili se osloniti na `SlusaPredmet.predmet` (što je trenutno slučaj), ali eksplicitna veza je jasnija.

### ✅ PROBLEM 3: UpisGodine nema polje "godina" koja se upisuje - REŠENO
**Trenutno stanje:**
- `UpisGodine` SADA IMA polje `godina` ✅
- Dodato prema specifikaciji

**Ispravka:**
Dodato u `UpisGodine`:
```java
private Integer godina;  // koja se godina upisuje (1, 2, 3, 4...)
```

### ✅ PROBLEM 4: Grupa nema vezu sa SkolskaGodina - REŠENO
**Trenutno stanje:**
- `Grupa` SADA IMA vezu sa `SkolskaGodina` ✅
- Dodato i u `SkolskaGodina` lista `grupe`

**Ispravka:**
Dodato u `Grupa`:
```java
@ManyToOne
@JoinColumn(name = "skolska_godina_id")
private SkolskaGodina skolskaGodina;
```

### ✅ PROBLEM 5: Ispit nema direktnu vezu sa Predmet - REŠENO
**Trenutno stanje:**
- `Ispit` SADA IMA aktiviranu direktnu vezu sa `Predmet` ✅
- Veza je bidirekciona sa `Predmet.ispiti`

**Ispravka:**
Aktivirana direktna veza u `Ispit`:
```java
@ManyToOne
@JoinColumn(name = "predmet_id", nullable = false)
private Predmet predmet;
```

**Napomena:** `Ispit` sada ima dve veze koje vode do predmeta:
- `DrziPredmet drziPredmet` → za pristup nastavniku i školskoj godini
- `Predmet predmet` → za direktan i brži pristup predmetu

### ✅ PROBLEM 6: StudentIndeks nedostaje @JoinColumn - REŠENO
**Trenutno stanje:**
- `StudentIndeks` SADA IMA `@JoinColumn` anotacije za sve veze ✅

**Ispravka:**
Dodate eksplicitne `@JoinColumn` anotacije:
```java
@ManyToOne
@JoinColumn(name = "studijski_program_id")
private StudijskiProgram studijskiProgram;

@ManyToOne
@JoinColumn(name = "grupa_id")
private Grupa grupa;
```

### ✅ PROBLEM 7: DrziPredmet nedostaju @JoinColumn anotacije - REŠENO
**Trenutno stanje:**
- `DrziPredmet` SADA IMA sve `@JoinColumn` anotacije ✅
- Dodata je i veza sa `Grupa`

**Ispravka:**
Dodate eksplicitne `@JoinColumn` anotacije i veza sa `Grupa`:
```java
@ManyToOne
@JoinColumn(name = "nastavnik_id")
private Nastavnik nastavnik;

@ManyToOne
@JoinColumn(name = "predmet_id")
private Predmet predmet;

@ManyToOne
@JoinColumn(name = "skolska_godina_id")
private SkolskaGodina skolskaGodina;

@ManyToOne
@JoinColumn(name = "grupa_id")
private Grupa grupa;
```

### ✅ PROBLEM 8: SlusaPredmet ima redundatne veze - REŠENO
**Trenutno stanje:**
- `SlusaPredmet` ima i `DrziPredmet` i direktnu vezu sa `Predmet`
- `DrziPredmet` već ima vezu sa `Predmet`
- **Sada IMA** i vezu sa `UpisGodine` ✅
- **Dodata automatska sinhronizacija** kroz `@PrePersist` i `@PreUpdate` metode ✅

**Rešenje:**
Dodata metoda `synchronizePredmet()` u `SlusaPredmet` entitet koja automatski osigurava konzistentnost:
```java
@PrePersist
@PreUpdate
private void synchronizePredmet() {
    if (drziPredmet != null && drziPredmet.getPredmet() != null) {
        // Automatski postavlja predmet na osnovu drziPredmet.predmet
        if (predmet == null || !predmet.getId().equals(drziPredmet.getPredmet().getId())) {
            predmet = drziPredmet.getPredmet();
        }
    }
}
```

**Rezultat:**
- ✅ Direktna veza sa `Predmet` je zadržana za brže upite (direktan pristup za prikaz obaveza)
- ✅ Automatska sinhronizacija osigurava konzistentnost podataka
- ✅ Nema potrebe za ručnom validacijom pri kreiranju entiteta

---

## 📊 STATUS ISPRAVKI

### ✅ REŠENO (Visok prioritet):
1. **PROBLEM 1**: ✅ Dodato `upisGodine` u `SlusaPredmet`
2. **PROBLEM 3**: ✅ Dodato `godina` u `UpisGodine`

### ✅ REŠENO (Srednji prioritet):
3. **PROBLEM 4**: ✅ Dodato `skolskaGodina` u `Grupa`
4. **PROBLEM 5**: ✅ Aktivirana direktna veza `Ispit` ↔ `Predmet`
5. **PROBLEM 6**: ✅ Dodate `@JoinColumn` anotacije u `StudentIndeks`
6. **PROBLEM 7**: ✅ Dodate `@JoinColumn` anotacije u `DrziPredmet` i dodata veza sa `Grupa`
7. **PROBLEM 8**: ✅ Dodata automatska sinhronizacija u `SlusaPredmet` za konzistentnost redundantnih veza

### ⚠️ PREOSTALO:
8. **PROBLEM 2**: `PredispitneObaveze` nema direktnu vezu sa `Predmet` (nije kritično, pristupa se preko `SlusaPredmet.predmet`)

## 📈 REZIME PROMENA

**Dodate veze:**
- `SlusaPredmet.upisGodine` ↔ `UpisGodine` (bidirekciono)
- `Grupa.skolskaGodina` ↔ `SkolskaGodina` (bidirekciono)
- `Ispit.predmet` ↔ `Predmet` (aktivirana bidirekciona veza)
- `DrziPredmet.grupa` ↔ `Grupa` (bidirekciono)

**Dodata polja:**
- `UpisGodine.godina` - koja se godina upisuje (1, 2, 3, 4...)

**Dodate @JoinColumn anotacije:**
- Svi `@ManyToOne` entiteti sada imaju eksplicitne `@JoinColumn` anotacije za bolju kontrolu nad bazom podataka

---

## 📝 DODATNE NAPOMENE

- ✅ Većina veza je dobro postavljena
- ✅ Svi kritični problemi su rešeni
- ✅ CascadeType i orphanRemoval su dobro postavljeni gde je potrebno
- ✅ Eksplicitne `@JoinColumn` anotacije su dodate za bolju kontrolu nad bazom
- ✅ Automatska sinhronizacija redundantnih veza u `SlusaPredmet` kroz `@PrePersist` i `@PreUpdate`
- ⚠️ Potrebno osigurati konzistentnost redundantnih veza u `Ispit` pri kreiranju entiteta:
  - `Ispit.predmet` mora biti isti kao `Ispit.drziPredmet.predmet`

## ✅ FINALNO STANJE

Model entiteta je sada:
- ✅ Kompletan prema specifikaciji
- ✅ Bidirekcionalne veze su ispravno postavljene
- ✅ Eksplicitne @JoinColumn anotacije za bolju kontrolu
- ✅ Dodate sve potrebne veze za funkcionalnost sistema

