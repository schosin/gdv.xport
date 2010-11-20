/*
 * $Id$
 *
 * Copyright (c) 2009 by agentes
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
 * (c)reated 05.10.2009 by Oli B. (oliver.boehm@agentes.de)
 */

package gdv.xport.satz;

import static gdv.xport.feld.Bezeichner.*;
import gdv.xport.config.Config;
import gdv.xport.feld.*;

import java.io.IOException;
import java.util.*;

import org.apache.commons.logging.*;


/**
 * @author oliver
 */
public final class Vorsatz extends Satz {

    private static final Log log = LogFactory.getLog(Vorsatz.class);
    /** 5 Zeichen, Byte 5 - 9 */
    private final AlphaNumFeld vuNummer = new VUNummer(Config.getVUNummer(), 5);
    /** 30 Zeichen, Byte 10 - 39 */
    private final AlphaNumFeld absender = new AlphaNumFeld(ABSENDER, 30, 10);
    /** 30 Zeichen, Byte 40 - 69 */
    private final AlphaNumFeld adressat = new AlphaNumFeld(ADRESSAT, 30, 40);
    /** 8 Zeichen, Byte 70 - 77 */
    private final Datum von = new Datum(ERSTELLUNGSDATUM_ZEITRAUM_VOM, 70);
    /** 8 Zeichen, Byte 78 - 85 */
    private final Datum bis = new Datum(ERSTELLUNGSDATUM_ZEITRAUM_BIS, 78);
    /** 10 Zeichen, Byte 86 - 95 */
    private final AlphaNumFeld vermittler = new AlphaNumFeld(VERMITTLER, 10, 86);
    /** die Versionen fuer die verschiedenen Datensaetze */
    private final Map<Integer, Version> versions = new HashMap<Integer, Version>();

    /**
     * Hiermit wird ein Vorsatz mit 3 Teildatensaetzen erstellt.
     */
    public Vorsatz() {
        super("0001", 3);
        setUpTeildatensaetze();
        setUpVersions();
    }

    /**
     * @param content Inhalt des Vorsatzes
     */
    public Vorsatz(final String content) {
        this();
        try {
            this.importFrom(content);
            log.debug(this + " created from \"" + content + '"');
        } catch (IOException ioe) {
            throw new IllegalArgumentException("argument too short", ioe);
        }
    }

    private void setUpTeildatensaetze() {
        for (int i = 0; i < teildatensatz.length; i++) {
            this.setUpTeildatensatz(i+1, teildatensatz[i]);
        }
    }
    
    private void setUpTeildatensatz(final int n, final Teildatensatz tds) {
        tds.add(this.vuNummer);
        tds.add(this.absender);
        tds.add(this.adressat);
        tds.add(this.von);
        tds.add(this.bis);
        tds.add(this.vermittler);
        switch (n) {
            case 1:     // Teildatensatz 1
                tds.add(new Zeichen(ART_DES_ABSENDERS, 237));
                tds.add(new Zeichen(ART_DES_ADRESSATEN, 238));
                tds.add(new AlphaNumFeld(VU_ABRECHNUNGSSTELLE, 2, 239));
                tds.add(new AlphaNumFeld(BESTANDSFUEHRENDE_GESCHAEFTSSTELLE, 2, 241));
                tds.add(new AlphaNumFeld(LEERSTELLEN, 10, 246));
                break;
        }
    }

    private void setUpVersions() {
        addVersion(1, new Version(VERSION_VORSATZ, 96, "2.1"));
        addVersion(100, 99, "2.1");
        addVersion(200, 102, "2.2");
        addVersion(210, 50, 105, "   ");
        addVersion(220, 51, 108, "   ");
        addVersion(220, 52, 111, "   ");
        addVersion(220, 53, 114, "   ");
        addVersion(220, 54, 117, "   ");
        addVersion(220, 59, 120, "   ");
        addVersion(210, 40, 123, "   ");
        addVersion(220, 40, 126, "   ");
        addVersion(210, 30, 129, "   ");
        addVersion(220, 30, 132, "   ");
        addVersion(210, 10, 135, "   ");
        addVersion(220, 10, 138, "   ");
        addVersion(210, 130, 141, "   ");
        addVersion(220, 130, 144, "   ");
        addVersion(210, 110, 147, "   ");
        addVersion(220, 110, 150, "   ");
        addVersion(210, 140, 153, "   ");
        addVersion(220, 140, 156, "   ");
        addVersion(210, 20, 159, "   ");
        addVersion(220, 20, 162, "   ");
        addVersion(210, 70, 165, "1.4");
        addVersion(220, 70, 168, "1.4");
        addVersion(210, 171, "   ");
        addVersion(220, 174, "   ");
        addVersion(210, 510, 177, "   ");
        addVersion(220, 510, 180, "   ");
        addVersion(210, 183, "   ");
        addVersion(220, 186, "   ");
        addVersion(210, 189, "   ");
        addVersion(220, 192, "   ");
        addVersion(250, 195, "   ");
        addVersion(260, 198, "   ");
        addVersion(210, 201, "   ");
        addVersion(220, 204, "   ");
        addVersion(220, 55, 207, "   ");
        addVersion(300, 210, "   ");
        addVersion(400, 213, "   ");
        addVersion(410, 216, "   ");
        addVersion(430, 219, "   ");
        addVersion(500, 222, "   ");
        addVersion(9999, new Version(VERSION_NACHSATZ, 225, "1.1"));
        addVersion(420, 228, "   ");
        addVersion(450, 231, "   ");
        addVersion(550, 234, "   ");
        addVersion(350, 243, "   ");
    }

    private void addVersion(final Integer art, final Version version) {
        versions.put(art, version);
        add(version);
    }

    private void addVersion(final int art, final int byteadresse, final String version) {
        String s = getVersionBezeichnung(art);
        addVersion(art, new Version(s, byteadresse, version));
    }

    private void addVersion(final int art, final int sparte, final int byteadresse,
            final String version) {
        assert sparte < 1000 : "unbekannte Sparte " + sparte;
        String s = getVersionBezeichnung(art, sparte);
        addVersion(art * 1000 + sparte, new Version(s, byteadresse, version));
    }

    private static String getVersionBezeichnung(final int art) {
        return new Formatter().format("Version Satzart %04d", art).toString();
    }

    private static String getVersionBezeichnung(final int art, final int sparte) {
        return new Formatter().format("Version Satzart %04d %03d", art, sparte).toString();
    }

    /**
     * Um die VU-Nummer setzen zu koennen.
     * @param s VU-Nummer (max. 5-stellig)
     */
    public void setVuNummer(final String s) {
        assert s.length() <= 5 : s + " darf nur max. 5 Zeichen lang sein";
        this.vuNummer.setInhalt(s);
    }

    /**
     * @return VU-Nummer
     */
    public String getVuNummer() {
        return this.vuNummer.getInhalt().trim();
    }

    /**
     * Absender ist Byte 10 - 39 im Teildatensatz.
     * @param name Absender
     */
    public void setAbsender(final String name) {
        this.absender.setInhalt(name);
    }

    /**
     * @return Absender
     */
    public String getAbsender() {
        return this.absender.getInhalt().trim();
    }

    /**
     * @param name neuer Adressat
     */
    public void setAdressat(final String name) {
        this.adressat.setInhalt(name);
    }

    /**
     * @return Adressat
     */
    public String getAdressat() {
        return this.adressat.getInhalt().trim();
    }

    /**
     * @param startDatum im Format "TTMMJJJJ"
     * @param endDatum im Format "TTMMJJJJ"
     */
    public void setErstellungsZeitraum(final String startDatum, final String endDatum) {
        this.von.setInhalt(startDatum);
        this.bis.setInhalt(endDatum);
    }

    /**
     * @param s neuer Vermittler
     */
    public void setVermittler(final String s) {
        this.vermittler.setInhalt(s);
    }

    /**
     * @return Vermittler
     */
    public String getVermittler() {
        return this.vermittler.getInhalt().trim();
    }

    /**
     * Ermittelt die Version des uebergebenen Bezeichners.
     *
     * @param bezeichner
     *            z.B. VERSION_VORSATZ; hier koennen alle die
     *            Bezeichner-Konstanten gewaehlt werden, die mit "VERSION_"
     *            anfangen.
     * @return Version des gewuenschten Bezeichners
     */
    public String getVersion(final String bezeichner) {
        return this.getFeld(bezeichner).getInhalt();
    }

    /**
     * @param art Satzart
     * @return z.B. 1.1
     */
    public String getVersion(final int art) {
        return this.getVersion(getVersionBezeichnung(art));
    }

    /**
     * @param art Satzart
     * @param sparte z.B. 70 (Rechtsschutz)
     * @return z.B. 1.1
     */
    public String getVersion(final int art, final int sparte) {
        return this.getVersion(getVersionBezeichnung(art, sparte));
    }

}
