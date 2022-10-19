package kingshard;

import java.sql.*;

public class KingshardJdbcDemo {
    public static void main(String[] args) throws Exception {
        read();
    }

    public static void read() throws Exception {
        Connection conn = getConnection();
        //创建语句对象
        Statement stmt = conn.createStatement();
        String sql = "SELECT id, name FROM test_shard_hash";
        //执行查询，获取结果集
        ResultSet rs = stmt.executeQuery(sql);

        //展开结果集
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            // 输出数据
            System.out.println("ID: " + id + ", 名称: " + name);
        }
        //关闭资源和连接
        rs.close();
        stmt.close();
        conn.close();
    }

    public static void write() throws Exception {
        Connection conn = getConnection();
        //创建语句对象
        String sql = "insert into test_shard_hash (id,name) values(?,?) ";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 100; i < 200; i++) {
            stmt.setInt(1, i);
            stmt.setString(2, i + "_name");
            stmt.execute();
        }

        //完成后关闭资源和连接
        stmt.close();
        conn.close();
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:9696/kingshard?useUnicode=true&characterEncoding=utf8";
        String username = "kingshard";
        String password = "kingshard";
        //Class.forName("com.mysql.cj.jdbc.Driver"); 不需要
        return DriverManager.getConnection(url, username, password);
    }
}
