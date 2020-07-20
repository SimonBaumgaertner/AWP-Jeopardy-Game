package entities.DataCreator;

import entities.*;
import db.DatabaseManager;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TestDataCreator {

    DatabaseManager databaseManager = new DatabaseManager(true);

    public void installTestData() {


        // installs 2 templates
        for (int k = 0; k < 2; k++) {
            List<Entity> entityList = new LinkedList<>();

            Template template = new Template("Test Template" + k);
            Category cBWP = new Category("test BWP", template);
            Category cAWP = new Category(" test AWP", template);
            Category cSozi = new Category(" test Sozi", template);
            Category cVSY = new Category("test VSY", template);
            Category cEnglisch = new Category(" test Englisch", template);
            Category cITP = new Category("test IT-P", template);
            entityList.addAll(Arrays.asList(template, cBWP, cAWP, cSozi, cVSY, cEnglisch, cITP));


            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 5; j++) {
                    Field field = new Field((Category) entityList.get(i), j);
                    Question question = new Question(field, "This is a test statement/question", "This is a test answer");
                    entityList.add(field);
                    entityList.add(question);
                }
            }


            databaseManager.openTransaction();
            for (int i = 0; i < entityList.size(); i++) {
                databaseManager.persist(entityList.get(i));
            }
            databaseManager.commitTransaction();
        }
        // demo
        List<Entity> entityList = new LinkedList<>();

        Template template = new Template("Demo");
        Category cBWP = new Category("BWP", template);
        Category cAWP = new Category("AWP", template);
        Category cSozi = new Category("Sozi", template);
        Category cVSY = new Category("VSY", template);
        Category cEnglisch = new Category("Englisch", template);
        Category cITP = new Category("IT-P", template);
        entityList.addAll(Arrays.asList(template, cBWP, cAWP, cSozi, cVSY, cEnglisch, cITP));


        String[] bwpquest = new String[] {
                "normierter Vertragstyp des deutschen Schuldrechts über die Einigung der Vertragsparteien über einen Kaufgegenstand", "Kaufvertrag", /**/
                "ein betriebswirtschaftliches Analyseverfahren. Sie teilt eine Menge von Objekten in 3 Klassen auf auf,", "ABC-Analyse", /**/
                "die systematische Sammlung, Aufarbeitung, Analyse und Interpretation von Daten über Märkte und Marktbeeinflussungsmöglichkeiten zum Zweck der Informationsgewinnung für Marketing-Entscheidungen", "Marktforschung", /**/
                "eine Mängelanzeige, mit welcher der Auftraggeber, Besteller oder Käufer den Mangel einer Kaufsache oder Dienstleistung gegenüber dem Verkäufer rügt", "Reklamation", /**/
                "hat in Verbindung mit den anderen Elementen des Marketing-Mix das Ziel, die Bedürfnisse und Wünsche der Kunden mit den Produkten und Dienstleistungen des Unternehmens zu prägen.", "Produktpolitik", /**/
        };
        String[] awpquest = new String[] {
                "die Aufteilung von Attributen (Tabellenspalten) in mehrere Relationen (Tabellen) gemäß von Regeln, so dass eine Form entsteht, die keine Redundanzen mehr enthält.", "Normalisierung", /**/
                "ein System zur elektronischen Datenverwaltung", "Datenbank", /**/
                "die weitest verbreiteste Datenbanksprache zur Definition von Datenstrukturen in relationalen Datenbanken", "SQL", /**/
                "es dient dazu, im Rahmen der semantischen Datenmodellierung den in einem gegebenen Kontext relevanten Ausschnitt der realen Welt zu bestimmen und darzustellen", "ER-Modell", /**/
                "Korrektheit der Beziehungen zwischen den Attributen in einer Relation und der Erhaltung der Eindeutigkeit des Schlüssels.", "referentielle Integrität", /**/
        };
        String[] engquest = new String[] {
                "das englische Wort für Manifest", "manifesto", /**/
                "das englische Wort für Interessensgruppe", "Stakeholer", /**/
                "das englische Wort für Klima der Schuldzuweisung", "blame culture", /**/
                "das englische Wort für Stütze", "mainstay", /**/
                "das englische Wort für gedeihen", "flourisch", /**/
        };
        String[] itpquest = new String[] {
                "ein Modell der Kommunikationspsychologie, mit dem eine Nachricht unter vier Aspekten oder Ebenen beschrieben wird", "Vier-Ohren-Modell", /**/
                "im Vier-Ohren-Modell: die beschriebene Sache („Sachinhalt“, „Worüber ich informiere“)", "Sachaspekt", /**/
                "im Vier-Ohren-Modell: dasjenige, was anhand der Nachricht über den Sprecher deutlich wird („Selbstoffenbarung“, „Was ich von mir selbst kundgebe“)", "Selbstaussage", /**/
                "im Vier-Ohren-Modell: was an der Art der Nachricht über die Beziehung offenbart wird („Beziehung“, „Was ich von dir halte oder wie wir zueinander stehen“)", "Beziehungsaspekt", /**/
                "im Vier-Ohren-Modell: dasjenige, zu dem der Empfänger veranlasst werden soll („Appell“, „Wozu ich dich veranlassen möchte“)", "Appell", /**/
        };
        String[] sozquest = new String[] {
                "das gesetzgebende Organ der Bundesrepublik Deutschland", "Bundestag", /**/
                "das wichtigste Verfassungsorgan der Bundesrepublik Deutschland.", "Bundesrat", /**/
                "das Staatsoberhaupt der Bundesrepublik Deutschland.", "Bundespräsident", /**/
                "das von der Fläche 2. größte Bundesland", "Niedersachsen", /**/
                "Wahlrecht für den Deutschen Bundestag", "Bundestagswahlrecht ", /**/
        };
        String[] vsyquest = new String[] {
                "ein Zahlensystem, das zur Darstellung von Zahlen nur zwei verschiedene Ziffern benutzt.", "Binärsystem", /**/
                "es werden Zahlen in einem Stellenwertsystem zur Basis 16 dargestellt.", "Hexadezimal", /**/
                "eine Protokoll-Familie für die Vermittlung und den Transport von Datenpaketen in einem dezentral organisierten Netzwerk.", "TCP/IP", /**/
                "der Teil einer Netzwerk-Adresse, der die Zuordnung von TCP- und UDP-Verbindungen und -Datenpaketen zu Server- und Client-Programmen durch Betriebssysteme bewirkt.", "Port", /**/
                "ist ein effizientes Verfahren zum Aufbau verlustfreier Datenübertragungen zwischen zwei Instanzen.", "3-Wege-Handshake", /**/
        };
        Category[] categories = new Category[] {cBWP,cAWP,cEnglisch,cITP,cSozi,cVSY};
        String[][] questions = new String[][] {bwpquest, awpquest,engquest,itpquest,sozquest,vsyquest};



        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Field field = new Field(categories[i], j + 1);
                Question question = new Question(field, questions[i][j*2], questions[i][j*2 + 1]);
                entityList.add(field);
                entityList.add(question);
            }
        }

        databaseManager.openTransaction();
        for (int i = 0; i < entityList.size(); i++) {
            databaseManager.persist(entityList.get(i));
        }
        databaseManager.commitTransaction();
    }
}
