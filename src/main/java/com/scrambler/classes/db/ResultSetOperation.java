package com.scrambler.classes.db;

import java.sql.ResultSet;

public interface ResultSetOperation {
    void invoke(ResultSet resultSet);
}
