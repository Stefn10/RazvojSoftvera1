package org.raflab.studsluzba;

import org.raflab.studsluzba.model.*;
import org.raflab.studsluzba.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

//CommandLineRunner je Spring Boot interfejs koji omogućava da se izvrši kod odmah nakon pokretanja aplikacije,
// tj. nakon što je Spring konteјner kompletno podignut.
//Koristi se najčešće za inicijalizaciju podataka, testiranje, pokretanje skripti ili bilo koji startup zadatak.
@Component
@AllArgsConstructor
public class Seeder implements CommandLineRunner {

    private final StudijskiProgramRepository studijskiProgramRepository;
    private final PredmetRepository predmetRepository;
    private final NastavnikRepository nastavnikRepository;
    private final NastavnikZvanjeRepository nastavnikZvanjeRepository;
    private final NastavnikObrazovanjeRepository nastavnikObrazovanjeRepository;
    private final StudentPodaciRepository studentPodaciRepository;
    private final StudentIndeksRepository studentIndeksRepository;
    private final DrziPredmetRepository drziPredmetRepository;
    private final SlusaPredmetRepository slusaPredmetRepository;
    private final GrupaRepository grupaRepository;
    private final SkolskaGodinaRepository skolskaGodinaRepository;
    private final UpisGodineRepository upisGodineRepository;
    private final ObnovaGodineRepository obnovaGodineRepository;
    private final IspitniRokRepository ispitniRokRepository;
    private final IspitRepository ispitRepository;
    private final PrijavaIspitaRepository prijavaIspitaRepository;
    private final IzlazakNaIspitRepository izlazakNaIspitRepository;
    private final PredispitneObavezeRepository predispitneObavezeRepository;
    private final UplataRepository uplataRepository;
    private final SrednjaSkolaRepository srednjaSkolaRepository;
    private final StudentSrednjaSkolaRepository studentSrednjaSkolaRepository;
    private final VisokoskolskaUstanovaRepository visokoskolskaUstanovaRepository;
    private final StudentVisokaUstanovaRepository studentVisokaUstanovaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Skip seeding if data already exists
        if (studijskiProgramRepository.count() > 0) {
            System.out.println("Database already contains data. Skipping seeder.");
            return;
        }

        System.out.println("Starting database seeding...");

        // 1. KREIRANJE ŠKOLSKIH GODINA (aktivnih i neaktivnih)
        List<SkolskaGodina> skolskaGodinaList = new ArrayList<>();
        String[] godine = {"2020/2021", "2021/2022", "2022/2023", "2023/2024", "2024/2025"};
        boolean[] aktivne = {false, false, false, false, true};
        
        for (int i = 0; i < godine.length; i++) {
            SkolskaGodina sg = new SkolskaGodina();
            sg.setNaziv(godine[i]);
            sg.setAktivna(aktivne[i]);
            skolskaGodinaList.add(skolskaGodinaRepository.save(sg));
        }
        SkolskaGodina aktivnaGodina = skolskaGodinaList.get(3); // 2023/2024

        // 2. KREIRANJE STUDIJSKIH PROGRAMA
        List<StudijskiProgram> spList = new ArrayList<>();
        String[] oznake = {"RN", "RM", "SI", "IT", "EE"};
        String[] nazivi = {"Računarske nauke", "Računarsko mašinstvo", "Softversko inženjerstvo", 
                          "Informacione tehnologije", "Elektrotehnika"};
        String[] zvanja = {"Dipl. inž. računara", "Dipl. inž. računara", "Dipl. inž. softvera",
                          "Dipl. inž. informacionih tehnologija", "Dipl. inž. elektrotehnike"};
        
        for (int i = 0; i < oznake.length; i++) {
            StudijskiProgram sp = new StudijskiProgram();
            sp.setOznaka(oznake[i]);
            sp.setNaziv(nazivi[i]);
            sp.setGodinaAkreditacije(2015 + i);
            sp.setZvanje(zvanja[i]);
            sp.setTrajanjeGodina(4);
            sp.setTrajanjeSemestara(8);
            sp.setVrstaStudija("OAS");
            sp.setUkupnoEspb(240);
            sp.setSkolskaGodina(aktivnaGodina);
            spList.add(studijskiProgramRepository.save(sp));
        }

        // 3. KREIRANJE PREDMETA
        List<Predmet> predmetList = new ArrayList<>();
        String[] sifre = {"RN101", "RN102", "RN201", "RM101", "SI101", "IT101", "EE101", "RN301", "RM201", "SI201"};
        String[] predmetNazivi = {"Osnove programiranja", "Strukture podataka", "Baze podataka",
                                 "Arhitektura računara", "Softverski paterni", "Mreže", "Elektronika",
                                 "Algoritmi", "Sistemski softver", "Web razvoj"};
        int[] espb = {6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
        
        for (int i = 0; i < sifre.length; i++) {
            Predmet p = new Predmet();
            p.setSifra(sifre[i]);
            p.setNaziv(predmetNazivi[i]);
            p.setOpis("Opis predmeta: " + predmetNazivi[i]);
            p.setEspb(espb[i]);
            p.setStudProgram(spList.get(i % spList.size()));
            p.setObavezan(i % 2 == 0);
            predmetList.add(predmetRepository.save(p));
        }

        // 4. KREIRANJE NASTAVNIKA
        List<Nastavnik> nastavnikList = new ArrayList<>();
        String[] imena = {"Marko", "Jovan", "Ana", "Petar", "Marija", "Stefan", "Jelena", "Nikola"};
        String[] prezimena = {"Petrović", "Jovanović", "Marković", "Nikolić", "Stojanović", 
                              "Đorđević", "Ilić", "Popović"};
        
        for (int i = 0; i < imena.length; i++) {
            Nastavnik n = new Nastavnik();
            n.setIme(imena[i]);
            n.setPrezime(prezimena[i]);
            n.setSrednjeIme("Srednje" + i);
            n.setEmail(imena[i].toLowerCase() + "." + prezimena[i].toLowerCase() + "@raf.rs");
            n.setBrojTelefona("060" + (1000000 + i));
            n.setAdresa("Adresa nastavnika " + (i + 1));
            n.setDatumRodjenja(LocalDate.of(1975 + i, (i % 12) + 1, (i % 28) + 1));
            n.setPol(i % 2 == 0 ? 'M' : 'F');
            n.setJmbg("7501012345" + i);
            nastavnikList.add(nastavnikRepository.save(n));
        }

        // 5. KREIRANJE ZVANJA NASTAVNIKA
        List<NastavnikZvanje> zvanjeList = new ArrayList<>();
        String[] zvanjaNastavnika = {"Docent", "Vanredni profesor", "Redovni profesor", "Asistent", 
                                     "Docent", "Vanredni profesor", "Asistent", "Docent"};
        String[] naucneOblasti = {"Računarske nauke", "Softversko inženjerstvo", "Informacione tehnologije",
                                 "Računarske nauke", "Mreže", "Elektrotehnika", "Softversko inženjerstvo",
                                 "Računarske nauke"};
        
        for (int i = 0; i < nastavnikList.size(); i++) {
            NastavnikZvanje nz = new NastavnikZvanje();
            nz.setDatumIzbora(LocalDate.of(2015 + i, 6, 15));
            nz.setNaucnaOblast(naucneOblasti[i]);
            nz.setUzaNaucnaOblast("Uza oblast " + (i + 1));
            nz.setZvanje(zvanjaNastavnika[i]);
            nz.setAktivno(true);
            nz.setNastavnik(nastavnikList.get(i));
            zvanjeList.add(nastavnikZvanjeRepository.save(nz));
        }

        // 6. KREIRANJE OBRAZOVANJA NASTAVNIKA
        for (int i = 0; i < nastavnikList.size(); i++) {
            NastavnikObrazovanje no = new NastavnikObrazovanje();
            no.setNastavnik(nastavnikList.get(i));
            no.setUstanova(i % 2 == 0 ? "Univerzitet u Beogradu" : "RAF");
            no.setStepenStudija(i < 4 ? "Doktorske" : "Master");
            no.setGodinaZavrsetka(2010 + i);
            no.setZvanje("Dipl. inž.");
            nastavnikObrazovanjeRepository.save(no);
        }

        // 7. KREIRANJE SREDNJIH ŠKOLA
        List<SrednjaSkola> srednjaSkolaList = new ArrayList<>();
        String[] skoleNazivi = {"Gimnazija Jovan Jovanović Zmaj", "Tehnička škola", "Ekonomska škola",
                               "Gimnazija Vuk Karadžić", "Muzička škola"};
        SrednjaSkola.VrstaSkole[] vrste = {SrednjaSkola.VrstaSkole.GIMNAZIJA, SrednjaSkola.VrstaSkole.TEHNICKA,
                                           SrednjaSkola.VrstaSkole.STRUCNA, SrednjaSkola.VrstaSkole.GIMNAZIJA,
                                           SrednjaSkola.VrstaSkole.MUZICKA};
        
        for (int i = 0; i < skoleNazivi.length; i++) {
            SrednjaSkola ss = new SrednjaSkola();
            ss.setNaziv(skoleNazivi[i]);
            ss.setVrsta(vrste[i]);
            ss.setMesto("Beograd");
            ss.setNapomena("Srednja škola " + (i + 1));
            srednjaSkolaList.add(srednjaSkolaRepository.save(ss));
        }

        // 8. KREIRANJE VISOKOŠKOLSKIH USTANOVA
        List<VisokoskolskaUstanova> visokaUstanovaList = new ArrayList<>();
        String[] ustNazivi = {"Univerzitet u Beogradu", "Univerzitet u Novom Sadu", 
                            "Univerzitet u Nišu", "Megatrend"};
        String[] tipovi = {"Državni univerzitet", "Državni univerzitet", "Državni univerzitet", "Privatni"};
        
        for (int i = 0; i < ustNazivi.length; i++) {
            VisokoskolskaUstanova vu = new VisokoskolskaUstanova();
            vu.setNaziv(ustNazivi[i]);
            vu.setTipSkole(tipovi[i]);
            visokaUstanovaList.add(visokoskolskaUstanovaRepository.save(vu));
        }

        // 9. KREIRANJE STUDENATA
        List<StudentPodaci> studentPodaciList = new ArrayList<>();
        String[] studentImena = {"Milan", "Jovana", "Stefan", "Ana", "Marko", "Jelena", "Nikola", "Marija",
                                 "Petar", "Sara", "Luka", "Milica", "Đorđe", "Tijana", "Filip"};
        String[] studentPrezimena = {"Milić", "Stanković", "Đukić", "Radić", "Tomić", "Janković", "Milošević",
                                     "Kostić", "Pavlović", "Mladenović", "Stojanović", "Nikolić", "Đorđević",
                                     "Ilić", "Perić"};
        
        for (int i = 0; i < studentImena.length; i++) {
            StudentPodaci s = new StudentPodaci();
            s.setIme(studentImena[i]);
            s.setPrezime(studentPrezimena[i]);
            s.setSrednjeIme("Srednje");
            s.setJmbg("001010" + String.format("%05d", i + 1));
            s.setDatumRodjenja(LocalDate.of(2000 + (i % 5), (i % 12) + 1, (i % 28) + 1));
            s.setMestoRodjenja("Beograd");
            s.setMestoPrebivalista("Beograd");
            s.setMestoStanovanja("Beograd");
            s.setDrzavaRodjenja("Srbija");
            s.setDrzavljanstvo("Srbija");
            s.setNacionalnost("Srpska");
            s.setPol(i % 2 == 0 ? 'M' : 'F');
            s.setAdresa("Adresa studenta " + (i + 1));
            s.setAdresaStanovanja("Adresa stanovanja " + (i + 1));
            s.setBrojTelefonaMobilni("061" + String.format("%07d", i + 1));
            s.setBrojTelefonaFiksni("011" + String.format("%07d", i + 1));
            s.setEmail(studentImena[i].toLowerCase() + "." + studentPrezimena[i].toLowerCase() + "@student.raf.rs");
            s.setBrojLicneKarte("123456789");
            s.setLicnuKartuIzdao("MUP");
            studentPodaciList.add(studentPodaciRepository.save(s));
        }

        // 10. KREIRANJE STUDENT-SREDNJA ŠKOLA
        for (int i = 0; i < studentPodaciList.size() && i < srednjaSkolaList.size(); i++) {
            StudentSrednjaSkola sss = new StudentSrednjaSkola();
            sss.setStudent(studentPodaciList.get(i));
            sss.setSrednjaSkola(srednjaSkolaList.get(i % srednjaSkolaList.size()));
            sss.setDatumUpisa(LocalDate.of(2015, 9, 1));
            sss.setDatumZavrsetka(LocalDate.of(2019, 6, 30));
            sss.setUspehSaPrijemnog(85.5 + i);
            sss.setUspehIzSrednjeSkole(4.5 + (i * 0.1));
            studentSrednjaSkolaRepository.save(sss);
        }

        // 11. KREIRANJE STUDENT-VISOKA USTANOVA
        for (int i = 0; i < studentPodaciList.size() && i < visokaUstanovaList.size(); i++) {
            StudentVisokaUstanova svu = new StudentVisokaUstanova();
            svu.setStudent(studentPodaciList.get(i));
            svu.setVisokaUstanova(visokaUstanovaList.get(i % visokaUstanovaList.size()));
            svu.setDatumUpisa(LocalDate.of(2019, 10, 1));
            svu.setDatumZavrsetka(null);
            svu.setNapomena("Prenos iz druge ustanove");
            studentVisokaUstanovaRepository.save(svu);
        }

        // 12. KREIRANJE STUDENTSKIH INDEKSA
        List<StudentIndeks> indeksList = new ArrayList<>();
        for (int i = 0; i < studentPodaciList.size(); i++) {
            StudentIndeks si = new StudentIndeks();
            si.setBroj(i + 1);
            si.setGodina(2023);
            si.setStudProgramOznaka(spList.get(i % spList.size()).getOznaka());
            si.setNacinFinansiranja(i % 2 == 0 ? "Budzet" : "Samofinansiranje");
            si.setAktivan(true);
            si.setVaziOd(LocalDate.of(2023, 10, (i % 28) + 1));
            si.setStudent(studentPodaciList.get(i));
            si.setStudijskiProgram(spList.get(i % spList.size()));
            si.setOstvarenoEspb(0);
            indeksList.add(studentIndeksRepository.save(si));
        }

        // 13. KREIRANJE DRŽI PREDMET
        List<DrziPredmet> drziPredmetList = new ArrayList<>();
        for (int i = 0; i < predmetList.size(); i++) {
            DrziPredmet dp = new DrziPredmet();
            dp.setNastavnik(nastavnikList.get(i % nastavnikList.size()));
            dp.setPredmet(predmetList.get(i));
            dp.setSkolskaGodina(aktivnaGodina);
            drziPredmetList.add(drziPredmetRepository.save(dp));
        }

        // 14. KREIRANJE GRUPA
        for (int i = 0; i < spList.size(); i++) {
            Grupa g = new Grupa();
            g.setStudijskiProgram(spList.get(i));
            g.setPredmeti(predmetList.subList(i * 2, Math.min((i + 1) * 2, predmetList.size())));
            g.setSkolskaGodina(aktivnaGodina);
            grupaRepository.save(g);
        }

        // 15. KREIRANJE UPIS GODINE
        List<UpisGodine> upisGodineList = new ArrayList<>();
        for (int i = 0; i < indeksList.size(); i++) {
            UpisGodine ug = new UpisGodine();
            ug.setDatumUpisa(LocalDate.of(2023, 10, (i % 28) + 1));
            ug.setGodina(1 + (i % 4));
            ug.setPrenetiESPB(i > 0 ? (i * 6) : 0);
            ug.setStudentIndeks(indeksList.get(i));
            ug.setSkolskaGodina(aktivnaGodina);
            upisGodineList.add(upisGodineRepository.save(ug));
        }

        // 16. KREIRANJE OBNOVA GODINE
        for (int i = 0; i < 3 && i < upisGodineList.size(); i++) {
            ObnovaGodine og = new ObnovaGodine();
            og.setDatumUpisa(LocalDate.of(2024, 10, (i % 28) + 1));
            og.setGodina(upisGodineList.get(i).getGodina());
            og.setPrenetiESPB(upisGodineList.get(i).getPrenetiESPB());
            og.setStudentIndeks(indeksList.get(i));
            og.setSkolskaGodina(aktivnaGodina);
            og.setPrethodniUpis(upisGodineList.get(i));
            og.setRazlogObnove("Nedovoljno ESPB bodova");
            obnovaGodineRepository.save(og);
        }

        // 17. KREIRANJE SLUŠA PREDMET
        List<SlusaPredmet> slusaPredmetList = new ArrayList<>();
        List<StudentVisokaUstanova> svuList = new ArrayList<>();
        studentVisokaUstanovaRepository.findAll().forEach(svuList::add);
        
        for (int i = 0; i < indeksList.size() && i < drziPredmetList.size(); i++) {
            SlusaPredmet sl = new SlusaPredmet();
            sl.setStudentIndeks(indeksList.get(i));
            sl.setDrziPredmet(drziPredmetList.get(i % drziPredmetList.size()));
            sl.setUpisGodine(upisGodineList.get(i));
            if (i < svuList.size()) {
                sl.setStudentVisokaUstanova(svuList.get(i));
            }
            slusaPredmetList.add(slusaPredmetRepository.save(sl));
        }

        // 18. KREIRANJE PREDISPITNIH OBAVEZA
        for (int i = 0; i < slusaPredmetList.size(); i++) {
            for (PredispitneObaveze.Vrsta vrsta : PredispitneObaveze.Vrsta.values()) {
                if (vrsta == PredispitneObaveze.Vrsta.KOL1 || vrsta == PredispitneObaveze.Vrsta.KOL2) {
                    PredispitneObaveze po = new PredispitneObaveze();
                    po.setVrsta(vrsta);
                    po.setMaxBodova(30);
                    po.setOsvojeniBodovi(15 + (i % 15));
                    po.setSlusaPredmet(slusaPredmetList.get(i % slusaPredmetList.size()));
                    po.setSkolskaGodina(aktivnaGodina);
                    predispitneObavezeRepository.save(po);
                }
            }
        }

        // 19. KREIRANJE ISPITNIH ROKOVA
        List<IspitniRok> ispitniRokList = new ArrayList<>();
        IspitniRok.TipRoka[] tipoviRoka = {IspitniRok.TipRoka.JANUAR, IspitniRok.TipRoka.JUN, 
                                          IspitniRok.TipRoka.SEPTEMBAR};
        LocalDate[] pocetci = {LocalDate.of(2024, 1, 15), LocalDate.of(2024, 6, 15), 
                              LocalDate.of(2024, 9, 1)};
        LocalDate[] zavrsetci = {LocalDate.of(2024, 2, 15), LocalDate.of(2024, 7, 15), 
                                LocalDate.of(2024, 9, 30)};
        
        for (int i = 0; i < tipoviRoka.length; i++) {
            IspitniRok ir = new IspitniRok();
            ir.setDatumPocetka(pocetci[i]);
            ir.setDatumZavrsetka(zavrsetci[i]);
            ir.setTipRoka(tipoviRoka[i]);
            ir.setSkolskaGodina(aktivnaGodina);
            ispitniRokList.add(ispitniRokRepository.save(ir));
        }

        // 20. KREIRANJE ISPITA
        List<Ispit> ispitList = new ArrayList<>();
        for (int i = 0; i < drziPredmetList.size() && i < ispitniRokList.size(); i++) {
            Ispit ispit = new Ispit();
            ispit.setDatumOdrzavanja(LocalDate.of(2024, 1, 20 + i));
            ispit.setVremePocetka(LocalTime.of(10, 0));
            ispit.setVremeZavrsetka(LocalTime.of(12, 0));
            ispit.setDrziPredmet(drziPredmetList.get(i));
            ispit.setPredmet(drziPredmetList.get(i).getPredmet());
            ispit.setIspitniRok(ispitniRokList.get(i % ispitniRokList.size()));
            ispit.setSkolskaGodina(aktivnaGodina);
            ispit.setZakljucen(i % 3 == 0);
            ispitList.add(ispitRepository.save(ispit));
        }

        // 21. KREIRANJE PRIJAVA ISPITA
        List<PrijavaIspita> prijavaList = new ArrayList<>();
        for (int i = 0; i < Math.min(indeksList.size(), ispitList.size()); i++) {
            PrijavaIspita pi = new PrijavaIspita();
            pi.setStudentIndeks(indeksList.get(i));
            pi.setIspit(ispitList.get(i));
            pi.setDatumPrijave(LocalDate.of(2024, 1, 10 + i));
            prijavaList.add(prijavaIspitaRepository.save(pi));
        }

        // 22. KREIRANJE IZLAZAK NA ISPIT
        // Važno: IzlazakNaIspit mora biti povezan sa SlusaPredmet koji odgovara predmetu iz ispita
        for (int i = 0; i < prijavaList.size(); i++) {
            PrijavaIspita prijava = prijavaList.get(i);
            Ispit ispit = prijava.getIspit();
            Predmet predmetIzIspita = ispit.getPredmet();
            
            // Pronađi SlusaPredmet koji odgovara studentu i predmetu
            SlusaPredmet odgovarajuciSlusaPredmet = null;
            for (SlusaPredmet sp : slusaPredmetList) {
                if (sp.getStudentIndeks().getId().equals(prijava.getStudentIndeks().getId()) &&
                    sp.getDrziPredmet().getPredmet().getId().equals(predmetIzIspita.getId())) {
                    odgovarajuciSlusaPredmet = sp;
                    break;
                }
            }
            
            // Ako nema odgovarajućeg SlusaPredmet, koristimo prvi dostupan za tog studenta
            if (odgovarajuciSlusaPredmet == null) {
                for (SlusaPredmet sp : slusaPredmetList) {
                    if (sp.getStudentIndeks().getId().equals(prijava.getStudentIndeks().getId())) {
                        odgovarajuciSlusaPredmet = sp;
                        break;
                    }
                }
            }
            
            // Kreiraj izlazak samo ako postoji odgovarajući SlusaPredmet
            if (odgovarajuciSlusaPredmet != null) {
                IzlazakNaIspit izlazak = new IzlazakNaIspit();
                izlazak.setPrijava(prijava);
                izlazak.setSlusaPredmet(odgovarajuciSlusaPredmet);
                izlazak.setOsvojeniPredispitniPoeni(25 + (i % 5));
                izlazak.setOsvojeniIspitniPoeni(40 + (i % 20));
                izlazak.setUkupnoPoena(izlazak.getOsvojeniPredispitniPoeni() + izlazak.getOsvojeniIspitniPoeni());
                izlazak.setOcena(izlazak.getUkupnoPoena() >= 51 ? 6 : 
                               (izlazak.getUkupnoPoena() >= 41 ? 7 : 
                               (izlazak.getUkupnoPoena() >= 31 ? 8 : 
                               (izlazak.getUkupnoPoena() >= 21 ? 9 : 10))));
                izlazak.setPonistio(false);
                izlazak.setNapomena("Ispit položio");
                izlazakNaIspitRepository.save(izlazak);
            }
        }

        // 23. KREIRANJE UPLATA
        for (int i = 0; i < indeksList.size(); i++) {
            Uplata uplata = new Uplata();
            uplata.setStudentIndeks(indeksList.get(i));
            uplata.setSkolskaGodina(aktivnaGodina);
            uplata.setDatumUplate(LocalDateTime.of(2023, 10, (i % 28) + 1, 10, 0));
            uplata.setIznosRsd(i % 2 == 0 ? 0 : 360000); // Budzet = 0, Samofinansiranje = 3000 EUR * 120
            uplata.setSrednjiKurs(120.0);
            uplata.setSkolarinaUEur(3000);
            uplata.setTip(i % 2 == 0 ? Uplata.TipUplate.SKOLARINA : Uplata.TipUplate.SKOLARINA);
            uplata.setStatus(i % 3 == 0 ? Uplata.StatusUplate.POTVRĐENA : 
                            (i % 3 == 1 ? Uplata.StatusUplate.U_TOKU : Uplata.StatusUplate.OTKAZANA));
            uplata.setPozivNaBroj("123456789" + i);
            uplata.setOpis("Uplata školarine za " + aktivnaGodina.getNaziv());
            uplataRepository.save(uplata);
        }

        System.out.println("Database seeding completed successfully!");
        System.out.println("Created: " + skolskaGodinaList.size() + " school years, " +
                         spList.size() + " study programs, " + predmetList.size() + " subjects, " +
                         nastavnikList.size() + " teachers, " + studentPodaciList.size() + " students");
    }
}
