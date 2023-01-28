package com.example.calcamp.db;

import android.database.sqlite.SQLiteDatabase;

import java.io.StringBufferInputStream;

public class Migrations {


    private String tableTeam =  "CREATE TABLE IF NOT EXISTS team (\n" +
                                "id INTEGER PRIMARY KEY,\n" +
                                "name VARCHAR (50) NOT NULL,\n" +
                                "image BLOB,\n" +
                                "UNIQUE (\n" +
                                    "name\n" +
                                ")\n" +
                                ")";

    private String tableLeague =  "CREATE TABLE league (\n" +
            "    id                  INTEGER   PRIMARY KEY,\n" +
            "    name                TEXT (44),\n" +
            "    image BLOB,\n"+
            "    id_punctuation_type INTEGER   NOT NULL\n" +
            "                                  REFERENCES punctuation_type (id),\n" +
            "    UNIQUE (\n" +
            "        name\n" +
            "    )\n" +
            ");";

    private String tablePunctuationType = "CREATE TABLE IF NOT EXISTS punctuation_type (\n" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,\n " +
            "name TEXT (0, 44) NOT NULL,\n" +
            "image BLOB)" ;

    private String tableTeamLeague = "CREATE TABLE team_league (\n"+
            "id_team     INTEGER NOT NULL REFERENCES team (id),\n"+
            "id_league   INTEGER NOT NULL REFERENCES league (id),\n"+
            "position    INTEGER,\n"+
            "punctuation INTEGER,\n"+
            "[match]     INTEGER,\n"+
            "PRIMARY KEY (\n"+
            "id_team,\n"+
            "id_league,\n"+
            "[match]))";
    private String tablePunctuationPosition = "CREATE TABLE punctuation_position (\n" +
            "    id_punctuation_type INTEGER REFERENCES punctuation_type (id),\n" +
            "    position            INTEGER,\n" +
            "    score               INTEGER NOT NULL,\n" +
            "    PRIMARY KEY (\n" +
            "        id_punctuation_type,\n" +
            "        position\n" +
            "    )\n" +
            ");";
    private String tableClassification = "CREATE TABLE classification (\n" +
            "    id_team           INTEGER NOT NULL\n" +
            "                              REFERENCES team (id),\n" +
            "    id_league         INTEGER NOT NULL\n" +
            "                              REFERENCES league (id),\n" +
            "    position_final    INTEGER,\n" +
            "    punctuation_final INTEGER NOT NULL,\n" +
            "    PRIMARY KEY (\n" +
            "        id_team,\n" +
            "        id_league\n" +
            "    )\n" +
            ");";
    private String triggerTeamLeagueClassification = "CREATE TRIGGER trigger_team_league_classification\n" +
            "         AFTER INSERT\n" +
            "            ON team_league\n" +
            "BEGIN\n" +
            "    UPDATE team_league\n" +
            "       SET punctuation = COALESCE( (\n" +
            "                                       SELECT pp.score\n" +
            "                                         FROM punctuation_position pp\n" +
            "                                              INNER JOIN\n" +
            "                                              punctuation_type pt ON pt.id = pp.id_punctuation_type\n" +
            "                                              INNER JOIN\n" +
            "                                              league l ON l.id_punctuation_type = pt.id\n" +
            "                                              INNER JOIN\n" +
            "                                              team_league tl ON tl.id_league = l.id AND \n" +
            "                                                                tl.position = pp.position\n" +
            "                                        WHERE tl.id_league = NEW.id_league AND \n" +
            "                                              tl.id_team = NEW.id_team AND \n" +
            "                                              tl.position = NEW.position\n" +
            "                                   ), 0) \n" +
            "     WHERE id_team = NEW.id_team AND \n" +
            "           id_league = NEW.id_league AND \n" +
            "           [match] = NEW.[match];\n" +
            "    INSERT INTO classification SELECT id_team,\n" +
            "                                      id_league,\n" +
            "                                      NULL,\n" +
            "                                      punctuation\n" +
            "                                 FROM team_league\n" +
            "                                WHERE id_team = NEW.id_team AND \n" +
            "                                      id_league = NEW.id_league AND \n" +
            "                                      [match] = NEW.[match] ON CONFLICT (\n" +
            "                                   id_team,\n" +
            "                                   id_league\n" +
            "                               )\n" +
            "                               DO UPDATE SET punctuation_final = punctuation_final + (\n" +
            "                                                                                         SELECT pp.score\n" +
            "                                                                                           FROM team_league tl\n" +
            "                                                                                                INNER JOIN\n" +
            "                                                                                                league l ON tl.id_league = l.id\n" +
            "                                                                                                INNER JOIN\n" +
            "                                                                                                punctuation_position pp ON pp.id_punctuation_type = l.id_punctuation_type AND \n" +
            "                                                                                                                           pp.position = tl.position\n" +
            "                                                                                          WHERE tl.id_league = NEW.id_league AND \n" +
            "                                                                                                tl.id_team = NEW.id_team AND \n" +
            "                                                                                                tl.position = NEW.position\n" +
            "                                                                                     );\n" +
            "END;\n";
    public String getTableTeam() {
        return tableTeam;
    }

    public String getTableLeague() {
        return tableLeague;
    }

    public String getTablePunctuationType() {
        return tablePunctuationType;
    }

    public String getTableTeamLeague() {
        return tableTeamLeague;
    }

    public String getTablePunctuationPosition() {
        return tablePunctuationPosition;
    }

    public String getTableClassification() {
        return tableClassification;
    }

    public String getTriggerTeamLeagueClassification() {
        return triggerTeamLeagueClassification;
    }
}
