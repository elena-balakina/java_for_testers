package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

public class DBConnectionTest {

    @Test
    public void testDBConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=&&serverTimezone=UTC");
            Statement st = conn.createStatement();
            // аналог коллекции - набор строк, которые извлекаются из таблицы
            // rs - текущая строка, курсор
            ResultSet rs = st.executeQuery("SELECT group_id, group_name, group_header, group_footer FROM group_list");

            Groups groups = new Groups();

            while (rs.next()) {
                groups.add(new GroupData().withId(rs.getInt("group_id")).withName(rs.getString("group_name")).
                        withHeader(rs.getString("group_header")).withFooter(rs.getString("group_footer")));
            }

            rs.close();
            st.close();
            conn.close();

            System.out.println(groups);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
