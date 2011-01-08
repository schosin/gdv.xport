/*
 * Copyright (c) 2010 by agentes
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
 * (c)reated 23.11.2010 by Oli B. (oliver.boehm@agentes.de)
 */

package gdv.xport.util;

import static org.junit.Assert.*;
import gdv.xport.Datenpaket;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.logging.*;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * JUnit-Test fuer HtmlFormatter.
 * 
 * @author oliver (oliver.boehm@agentes.de)
 * @since 0.5.0 (23.11.2010)
 */
public class HtmlFormatterTest extends AbstractFormatterTest {
    
    private static final Log log = LogFactory.getLog(HtmlFormatter.class);
    
    /**
     * Tested den Export eines Datenpakets als HTML.
     *
     * @throws XMLStreamException falls was schiefgelaufen ist
     * @throws SAXException falls was schiefgelaufen ist
     * @throws IOException falls was schiefgelaufen ist
     */
    @Test
    public void testWriteDatenpaket() throws XMLStreamException, SAXException, IOException {
        Datenpaket datenpaket = new Datenpaket();
        String htmlString = HtmlFormatter.toString(datenpaket);
        log.info(datenpaket + " as HTML:\n" + htmlString);
        XmlFormatterTest.checkXML(htmlString);
        assertTrue("no <html> inside", htmlString.contains("<html"));
        assertTrue("no </html> inside", htmlString.contains("</html"));
    }
    
    /**
     * Fuer Umlaute sollte die HTML-Ersatzdarstellung verwendet werden, um
     * Encoding-Probleme zu vermeiden. Da dies der verwendete XMLStreamWriter
     * nicht zulaesst, wurde diese Methode wieder auskommentiert.
     *
     * @throws XMLStreamException falls was schiefgelaufen ist
     * @throws SAXException falls was schiefgelaufen ist
     * @throws IOException falls was schiefgelaufen ist
     */
    //@Test
    public void testUmlauts() throws XMLStreamException, SAXException, IOException {
        String absender = "\u00dcb\u00e4rraschung-AG";
        Datenpaket datenpaket = new Datenpaket();
        datenpaket.setAbsender(absender);
        String htmlString = HtmlFormatter.toString(datenpaket);
        assertFalse("Umlauts in '" + absender + "' not replaced!", htmlString.contains(absender));
    }
    
    /**
     * Tested die Formattierung der Musterdatei als HTML.
     *
     * @throws XMLStreamException falls was schiefgelaufen ist
     * @throws IOException falls was schiefgelaufen ist
     */
    @Test
    public void testMusterdatei() throws IOException, XMLStreamException {
        exportMusterdatei(new HtmlFormatter(), "musterdatei_041222.html");
    }

}
