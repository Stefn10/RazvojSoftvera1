package org.raflab.studsluzba;

import org.raflab.studsluzba.model.*;
import org.raflab.studsluzba.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
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
    private final StudentPodaciRepository studentPodaciRepository;
    private final StudentIndeksRepository studentIndeksRepository;
    private final DrziPredmetRepository drziPredmetRepository;
    private final SlusaPredmetRepository slusaPredmetRepository;
    private final GrupaRepository grupaRepository;
    private final SkolskaGodinaRepository skolskaGodinaRepository;
    private final UpisGodineRepository upisGodineRepository;

    @Override
    public void run(String... args) throws Exception {
        // Skip seeding if data already exists
        if (studijskiProgramRepository.count() > 0) {
            System.out.println("Database already contains data. Skipping seeder.");
            return;
        }

        // Create SkolskaGodina first (required by many entities)
        SkolskaGodina skolskaGodina = new SkolskaGodina();
        skolskaGodina.setNaziv("2023/2024");
        skolskaGodina.setAktivna(true);
        skolskaGodina = skolskaGodinaRepository.save(skolskaGodina);

        List<StudijskiProgram> spList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            StudijskiProgram sp = new StudijskiProgram();
            sp.setOznaka("SP" + i);
            sp.setNaziv("Program " + i);
            sp.setGodinaAkreditacije(2020 + i);
            sp.setZvanje("Zvanje " + i);
            sp.setTrajanjeGodina(4);
            sp.setTrajanjeSemestara(8);
            sp.setVrstaStudija("OAS");
            sp.setUkupnoEspb(240);
            sp.setSkolskaGodina(skolskaGodina);
            spList.add(studijskiProgramRepository.save(sp));
        }

        List<Predmet> predmetList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Predmet p = new Predmet();
            p.setSifra("PR" + i);
            p.setNaziv("Predmet " + i);
            p.setOpis("Opis predmeta " + i);
            p.setEspb(6 + i);
            p.setStudProgram(spList.get((i - 1) % spList.size()));
            p.setObavezan(i % 2 == 0);
            predmetList.add(predmetRepository.save(p));
        }

        List<Nastavnik> nastavnikList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Nastavnik n = new Nastavnik();
            n.setIme("Nastavnik" + i);
            n.setPrezime("Prezime" + i);
            n.setSrednjeIme("Srednje" + i);
            n.setEmail("nastavnik" + i + "@example.com");
            n.setBrojTelefona("06012345" + i);
            n.setAdresa("Adresa " + i);
            n.setDatumRodjenja(LocalDate.of(1980 + i, i, i));
            n.setPol(i % 2 == 0 ? 'M' : 'F');
            n.setJmbg("80010123456" + i);
            nastavnikList.add(nastavnikRepository.save(n));
        }

        List<NastavnikZvanje> zvanjeList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            NastavnikZvanje nz = new NastavnikZvanje();
            nz.setDatumIzbora(LocalDate.of(2020 + i, i, i));
            nz.setNaucnaOblast("Oblast " + i);
            nz.setUzaNaucnaOblast("Uza oblast " + i);
            nz.setZvanje("Zvanje " + i);
            nz.setAktivno(i % 2 == 0);
            nz.setNastavnik(nastavnikList.get(i - 1));
            zvanjeList.add(nastavnikZvanjeRepository.save(nz));
        }

        List<StudentPodaci> studentPodaciList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            StudentPodaci s = new StudentPodaci();
            s.setIme("Student" + i);
            s.setPrezime("Prezime" + i);
            s.setSrednjeIme("Srednje" + i);
            s.setJmbg("00101012345" + i);
            s.setDatumRodjenja(LocalDate.of(2000 + i, i, i));
            s.setMestoRodjenja("Mesto" + i);
            s.setMestoPrebivalista("Prebivaliste" + i);
            s.setDrzavaRodjenja("Srbija");
            s.setDrzavljanstvo("Srbija");
            s.setNacionalnost("Srpska");
            s.setPol(i % 2 == 0 ? 'F' : 'M');
            s.setAdresa("Adresa " + i);
            s.setBrojTelefonaMobilni("06123456" + i);
            s.setEmail("student" + i + "@example.com");
            studentPodaciList.add(studentPodaciRepository.save(s));
        }

        List<StudentIndeks> indeksList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            StudentIndeks si = new StudentIndeks();
            si.setBroj(i);
            si.setGodina(2023);
            si.setStudProgramOznaka(spList.get(i - 1).getOznaka());
            si.setNacinFinansiranja(i % 2 == 0 ? "Budzet" : "Samofinansiranje");
            si.setAktivan(true);
            si.setVaziOd(LocalDate.of(2023, 10, i));
            si.setStudent(studentPodaciList.get(i - 1));
            si.setStudijskiProgram(spList.get(i - 1));
            si.setOstvarenoEspb(0);
            indeksList.add(studentIndeksRepository.save(si));
        }

        List<DrziPredmet> drziPredmetList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            DrziPredmet dp = new DrziPredmet();
            dp.setNastavnik(nastavnikList.get(i - 1));
            dp.setPredmet(predmetList.get(i - 1));
            dp.setSkolskaGodina(skolskaGodina);
            drziPredmetList.add(drziPredmetRepository.save(dp));
        }

        // Create UpisGodine for each student
        List<UpisGodine> upisGodineList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            UpisGodine ug = new UpisGodine();
            ug.setDatumUpisa(LocalDate.of(2023, 10, i));
            ug.setGodina(1);
            ug.setPrenetiESPB(0);
            ug.setStudentIndeks(indeksList.get(i - 1));
            ug.setSkolskaGodina(skolskaGodina);
            upisGodineList.add(upisGodineRepository.save(ug));
        }

        List<SlusaPredmet> slusaPredmetList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            SlusaPredmet sl = new SlusaPredmet();
            sl.setStudentIndeks(indeksList.get(i - 1));
            sl.setDrziPredmet(drziPredmetList.get(i - 1));
            sl.setUpisGodine(upisGodineList.get(i - 1));
            slusaPredmetList.add(slusaPredmetRepository.save(sl));
        }

        for (int i = 1; i <= 5; i++) {
            Grupa g = new Grupa();
            g.setStudijskiProgram(spList.get(i - 1));
            g.setPredmeti(Collections.singletonList(predmetList.get(i - 1)));
            g.setSkolskaGodina(skolskaGodina);
            grupaRepository.save(g);
        }
    }
}