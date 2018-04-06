package kr.ac.jejunu;

import java.sql.*;

public class UserDao {
    private final ConnectionMaker connectionmaker;

    public UserDao(ConnectionMaker connectionmaker) {
        this.connectionmaker = connectionmaker;
    }

    public User get(int id) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user;
        try {
            //mysql driver load
            connection = connectionmaker.getConnection();
            //sql 작성하고
            preparedStatement = connection.prepareStatement("select * from userinfo where id = ?");
            preparedStatement.setInt(1, id);
            //sql 실행하고
            resultSet = preparedStatement.executeQuery();
            //결과를 User 에 매핑하고
            resultSet.next();
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        } finally {
            //자원을 해지하고
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //결과를 리턴한다.
        }

        return user;
    }


    public Integer insert(User user) throws ClassNotFoundException, SQLException {
        //mysql driver load
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer id;
        try {
            connection = connectionmaker.getConnection();
            //sql 작성하고
            preparedStatement = connection.prepareStatement("insert into userinfo(name, password) values (?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            //sql 실행하고
            preparedStatement.executeUpdate();
            //결과를 User 에 매핑하고

            preparedStatement = connection.prepareStatement("select last_insert_id()");
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            id = resultSet.getInt(1);
        } finally {
            //자원을 해지하고
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //오늘 배운 단축키 ^^
            //ctrl art T   surround with XXX
            //ctrl art L   beautify
            //ctrl art F   extract field
            //shift F6     rename
            //art ins      generate
        }

        //자원을 해지하고
        //결과를 리턴한다.

        return id;
    }
}