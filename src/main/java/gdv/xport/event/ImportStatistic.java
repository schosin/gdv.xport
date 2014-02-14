/*
 * Copyright (c) 2009 - 2014 by Oli B. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express orimplied. See the License for the specific language
 * governing permissions and limitations under the License. (c)reated 23.10.2009
 * by Oli B. (ob@aosd.de)
 */

package gdv.xport.event;

import gdv.xport.satz.Satz;

/**
 * Dies ist ein einfaches Beispiel fuer einen {@link ImportListener}, der eine
 * kleine Statistik ueber die importierten Saetze ermittelt.
 *
 * @author oliver (oliver.boehm@gmail.com)
 * @since 0.9.4 (14.02.2014)
 */
public final class ImportStatistic implements ImportListener {

    /** The imported saetze. */
    private int importedSaetze = 0;

    /** The imported teildatensaetze. */
    private int importedTeildatensaetze = 0;

    /**
     * Ermittelt eine kleine Statistik wie die Anzahl der importierten Saetze.
     *
     * @param satz der importierte Satz
     * @see ImportListener#notice(gdv.xport.satz.Satz)
     */
    @Override
    public void notice(final Satz satz) {
        importedSaetze++;
        importedTeildatensaetze += satz.getTeildatensaetze().size();
    }

    /**
     * Liefert die Anzahl der importierten Saetze zurueck.
     *
     * @return Anzahl der importierten Saetze
     */
    public int getImportedSaetze() {
        return importedSaetze;
    }

    /**
     * Liefert die Anzahl der importieren Teildatensaetze zurueck.
     *
     * @return the imported teildatensaetze
     */
    public int getImportedTeildatensaetze() {
        return importedTeildatensaetze;
    }

    /**
     * Gibt die Statistik in Kurzform aus.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.importedSaetze + " Saetze, " + this.importedTeildatensaetze + " Teildatensaetze";
    }

}
