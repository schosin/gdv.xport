/*
 * Copyright (c) 2011 by agentes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express orimplied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * (c)reated 08.04.2011 by Oli B. (oliver.boehm@agentes.de)
 */

package gdv.xport.satz.model;

import static gdv.xport.feld.Bezeichner.ZUSAETZLICHE_SATZKENNUNG;
import static org.junit.Assert.assertEquals;
import gdv.xport.Datenpaket;
import gdv.xport.config.Config;
import gdv.xport.feld.Feld;
import gdv.xport.satz.*;

import java.io.*;
import java.util.List;

import org.apache.commons.logging.*;
import org.junit.*;


/**
 * JUnit-Test fuer Satz220.
 * 
 * @author oliver (oliver.boehm@agentes.de)
 * @since 0.6 (08.04.2011)
 */
public class Satz220Test extends AbstractSatzTest {
    
    private static final Log log = LogFactory.getLog(Satz220Test.class);

    /**
     * Der Lesbarkeit halber aktivieren wir das Zeilenende fuer jeden
     * exportierten Satz.
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        Config.setEOD("\n");
    }

    /**
     * Test method for {@link Satz220#Satz220(int)}.
     */
    @Test
    public void testSparte() {
        Satz220 rechtsschutz = new Satz220(70);
        log.info(rechtsschutz + " created.");
        assertEquals(70, rechtsschutz.getSparte());
    }

    /**
     * Da inzwischen auch Sparte 30 (Unfall) unterstuetzt wird, sollte ein Import
     * dafuer kein Problem mehr sein.
     * Der Test-Input dazu stammt von der musterdatei_041222.txt von gdv-online.
     *
     * @throws IOException
     *             sollte eigentlich nicht vorkommen, da wir von einem String
     *             importieren
     */
    @Test
    public void testSparte30() throws IOException {
        String input = "02209999  030      599999999990199990099990000011Kitzelpfutze   "
            + "               000000Kitzelpfutze                  Martina      "
            + "                 111119791000Hausfrau                      A 1EU"
            + "R0000000000000000041141010520040000000001052004          1      "
            + "\n"
            + "02209999  030      599999999990199990099990000012000000000011305"
            + "0000000000000141950000000000000000000000000000000000000000000000"
            + "0000000000000000000000000010000000000000000 00000000000000000000"
            + "000000000000000000000000000000000000000000000000000  000000    X"
            + "\n";
        assertEquals(514, input.length());
        Satz220 wagnisdaten = new Satz220(30);
        wagnisdaten.importFrom(input);
        Feld x = wagnisdaten.getFeld(ZUSAETZLICHE_SATZKENNUNG, 2);
        assertEquals("X", x.getInhalt());
        checkDatensatz(wagnisdaten, input);
    }

    /**
     * Der spezielle Teildatensatz 9 der Sparte 30 bereitet Probleme, da er
     * etwas aus der Reihe tanzt - er kann naemlich als erster Teildatensatz
     * auftreten.
     * Der Test-Input dazu stammt von der musterdatei_041222.txt von gdv-online.
     *
     * @throws IOException
     *             sollte eigentlich nicht vorkommen, da wir von einem String
     *             importieren
     */
    @Test
    public void testSparte30Teildatensatz9() throws IOException {
        String input = "02209999  030      59999999997019999009999000000001        900 M"
            + "artina Kitzekpfutze          00000                              "
            + "                                                                "
            + "                                                         9000000"
            + "\n"
            + "02209999  030      599999999970199990099990000021Kitzelpfutze   "
            + "               000000Kitzelpfutze                  Martina      "
            + "                 121219792000                              A 1EU"
            + "R0000000000000000012632010620040000000001062004          1      "
            + "\n"
            + "02209999  030      599999999970199990099990000022000000000009310"
            + "0000000000000116900000000000000000000000000000000000000000000000"
            + "0000000000000000000000000010000000000000000 00000000000000000000"
            + "000000000000000000000000000000000000000000000000000  000000    X"
            + "\n";
        assertEquals(771, input.length());
        Satz220 wagnisdaten = new Satz220(30);
        wagnisdaten.importFrom(input);
        checkDatensatz(wagnisdaten, input);
    }
    
    /**
     * Der Import von Sparte 51 scheint manchmal einfach uebergangen zu werden,
     * wenn er nur aus einem Teildatensatz besteht und danach gleich ein Satz
     * mit Sparte 53 kommt.
     * 
     * @throws IOException
     *             falls der Test-Satz nicht gelesen werden kann
     */
    @Test
    public void testImportSparte51() throws IOException {
        InputStream istream = this.getClass().getResourceAsStream("/gdv/xport/satz/Satz0220051.txt");
        try {
            Datenpaket datenpaket = new Datenpaket();
            datenpaket.importFrom(istream);
            List<Datensatz> datensaetze = datenpaket.getDatensaetze();
            assertEquals(51, datensaetze.get(0).getSparte());
            assertEquals(53, datensaetze.get(1).getSparte());
        } finally {
            istream.close();
        }
    }

}
