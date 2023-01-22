package com.example.calcamp.model.dao;

import android.content.Context;

import com.example.calcamp.db.DataBaseHelper;
import com.example.calcamp.model.implement.LeagueDaoSQLITE;
import com.example.calcamp.model.implement.PunctuationTypeDaoSQLITE;
import com.example.calcamp.model.implement.TeamDaoJDBC;
import com.example.calcamp.model.implement.TeamLeagueDaoSQLITE;

public class DAOFactory {
    public static TeamDAO createTeamDao(Context context) {
        return new TeamDaoJDBC(new DataBaseHelper(context));
    }
    public static leagueDAO createLeagueDao(Context context) {
        return new LeagueDaoSQLITE(new DataBaseHelper(context));
    }
    public static PunctuationTypeDAO createPunctuationTypeDao(Context context) {
        return new PunctuationTypeDaoSQLITE(new DataBaseHelper(context));
    }
    public static TeamLeagueDAO createTeamLeagueDao(Context context) {
        return new TeamLeagueDaoSQLITE(new DataBaseHelper(context));
    }
}
