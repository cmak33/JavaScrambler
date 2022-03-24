package com.scrambler.classes.db;

import java.sql.ResultSet;

interface ResultSetOperation {
    void invoke(ResultSet resultSet);
}
